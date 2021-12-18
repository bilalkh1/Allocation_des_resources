package Broker;

import Tools.Reservation;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class BrokerAgent extends Agent {

    protected void setup(){

        System.out.println("The Broker is running !!");

        // create instance of parallel behaviour
        ParallelBehaviour comportementparallele = new ParallelBehaviour();
        // add the soub behaviour
        addBehaviour(comportementparallele);
        // add behaviour for messages
        comportementparallele.addSubBehaviour(new CyclicBehaviour() {

            @Override
            public void action() {

                // prepare for recieve messages
                MessageTemplate mt1 = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), MessageTemplate.MatchOntology("Reservation"));

                // recieve messages from other agents
                ACLMessage reponse1 = receive(mt1);

                MessageTemplate mt2 = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), MessageTemplate.MatchOntology("Availability"));
                ACLMessage reponse2 = receive(mt2);

                if(reponse1 != null){
                    try{
                        // we will get the content of the responce
                        Reservation reservation = (Reservation)reponse1.getContentObject();

                        ACLMessage reponse3 = new ACLMessage(ACLMessage.REQUEST);

                        //Modification des param�tres de la requete ACLMessage
                        reponse3.addReceiver(new AID("Restaurant", AID.ISLOCALNAME));
                        //On met la liste des ville dans le message
                        reponse3.setContentObject((Serializable) reservation);
                        reponse3.setOntology("Reservation");
                        //Envoi de message
                        send(reponse3);
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if(reponse2 != null){
                    try {

                        Boolean rep = (Boolean) reponse2.getContentObject();

                        ACLMessage reponse3 = new ACLMessage(ACLMessage.INFORM);

                        //Modification des param�tres de la requete ACLMessage
                        reponse3.addReceiver(new AID("Person", AID.ISLOCALNAME));
                        //On met le tableau des villes ordonn�es dans le message
                        reponse3.setContentObject(rep);
                        reponse3.setOntology("check");
                        //Envoi de message
                        send(reponse3);

                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // for block behaviours
                else block();

            }
        });
    }
}
