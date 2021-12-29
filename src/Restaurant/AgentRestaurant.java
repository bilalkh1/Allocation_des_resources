package Restaurant;

import static Tools.Init.*;
import Tools.Init;
import Tools.Reservation;
import Tools.Restaurant;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AgentRestaurant extends Agent {

    public static Init var;

    @Override
    protected void setup() {

        // intialisation of parameters
        int i,C = 0;
        List<Restaurant> restaurants = new ArrayList<Restaurant>();
        // generate restaurant number between 10 and 5
        int M = (int)(Math.random()*(10-5+1)+5);
        // create instances of restaurants
        for (i=0;i<M;i++){
            // generate capacity in every restaurant
            int c = (int)(Math.random()*(15-8+1)+8);
            C += c;
            restaurants.add(new Restaurant(i+1,c,(int)(Math.random()*(c+1))));
        }
        // generate number of persons with 2*N < C and N > Ci(max)
        int N = (int)(Math.random()*(((int)C/2)-10+1)+10);
        var = new Init(M,C,N,restaurants);
        //System.out.println(M + " " + C);
        //System.out.println(restaurants);
        System.out.println("setup of AgentRestaurant");
        ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
        addBehaviour(parallelBehaviour);
        final Boolean[] start = {true};
        parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {

            @Override
            public void action() {

                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    if(start[0]){
                        ACLMessage inf = new ACLMessage(ACLMessage.INFORM);
                        inf.addReceiver(new AID("Broker", AID.ISLOCALNAME));
                        inf.setOntology("Information");
                        inf.setContentObject(var);
                        System.out.println(var.M + " " + var.C);
                        send(inf);
                        start[0] = false ;
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                MessageTemplate msg1 = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                        MessageTemplate.MatchOntology("ReservationM"));
                // Receive reservation messages from broker
                ACLMessage reservationMsg = receive(msg1);
                try {
                    if(reservationMsg !=null)
                        System.out.println((Serializable) reservationMsg.getContentObject().toString());
                } catch (UnreadableException e) {
                    e.printStackTrace();
                }
                if (reservationMsg != null) {
                    try {
                        // Get content of reservationMsg (id of restaurant)
                        int restaurantId = ((Reservation)reservationMsg.getContentObject()).getId_Restaurant() - 1;
                        // build response msg
                        ACLMessage responseMsg = new ACLMessage(ACLMessage.INFORM);
                        responseMsg.addReceiver(new AID("Broker", AID.ISLOCALNAME));
                        responseMsg.setOntology("Availability");
                        // check availability of this restaurant
                        if (restaurants.get(restaurantId).getNbrPlaceEmpty() > 0) {
                            // place available
                            restaurants.get(restaurantId).nbrPlaceEmpty--;
                            responseMsg.setContentObject(true);
                            // send response
                            send(responseMsg);
                            System.out.println("success");
                        } else {
                            // fully booked
                            responseMsg.setContentObject(false);
                            // send response
                            send(responseMsg);
                            System.out.println("failiaure");
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else block();
            }
        });
    }
}