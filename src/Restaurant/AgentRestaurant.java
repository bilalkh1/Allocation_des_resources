package restaurant;

import Tools.Init;
import Tools.Restaurant;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.List;

public class AgentRestaurant extends Agent {

    List<Restaurant> restaurants = Init.restaurants;

    @Override
    protected void setup() {
        System.out.println("setup of AgentRestaurant");
        ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
        addBehaviour(parallelBehaviour);
        parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {

            @Override
            public void action() {
                MessageTemplate msg1 = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                        MessageTemplate.MatchOntology("Reservation"));
                // Receive reservation messages from broker
                ACLMessage reservationMsg = receive(msg1);
                if (reservationMsg != null) {
                    try {
                        // Get content of reservationMsg (id of restaurant)
                        int restaurantId = (int) reservationMsg.getContentObject();
                        // build response msg
                        ACLMessage responseMsg = new ACLMessage(ACLMessage.INFORM);
                        responseMsg.addReceiver(new AID("Broker", AID.ISLOCALNAME));
                        responseMsg.setOntology("Availability");
                        // check availability of this restaurant
                        if (restaurants.get(restaurantId).nbrPlaceEmpty > 0) {
                            // place available
                            restaurants.get(restaurantId).nbrPlaceEmpty--;
                            responseMsg.setContentObject(true);
                            // send response
                            send(responseMsg);
                        } else {
                            // fully booked
                            responseMsg.setContentObject(false);
                            // send response
                            send(responseMsg);
                        }
                        System.out.println(restaurants);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else block();
            }
        });
    }
}