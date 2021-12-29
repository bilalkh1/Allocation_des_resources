package Broker;

import Tools.Init;
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

                MessageTemplate init_1 = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM),
                        MessageTemplate.MatchOntology("Information"));
                ACLMessage acl_1 = receive(init_1);
                System.out.println("--------");
                if(acl_1 != null){
                    System.out.println("///////");
                    try {
                            Init r = (Init) acl_1.getContentObject();
                            System.out.println(r.M + " " + r.C);
                            ACLMessage inf = new ACLMessage(ACLMessage.INFORM);
                            inf.addReceiver(new AID("Person", AID.ISLOCALNAME));
                            inf.setContentObject(r);
                            inf.setOntology("InformationR");
                            send(inf);
                            acl_1.clearAllReceiver();
                        } catch (UnreadableException e) {
                            e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    ACLMessage inf = new ACLMessage(ACLMessage.INFORM);
//                    inf.addReceiver(new AID("Person", AID.ISLOCALNAME));
//                    inf.setOntology("Information_1");
//                    try {
//                        Init r = (Init) acl_1.getContentObject();
//                        System.out.println(r.M + " " + r.C);
//                    } catch (UnreadableException e) {
//                        e.printStackTrace();
//                    }
//                    send(acl_1);
//                    acl_1.setContent(null);

                }


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
                        reponse3.setOntology("ReservationM");
                        //Envoi de message
                        send(reponse3);
                    } catch (UnreadableException | IOException e) {
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

                    } catch (UnreadableException | IOException e) {
                        e.printStackTrace();
                    }
                } else if(acl_1 != null){
                    try {
                        ACLMessage inf = new ACLMessage(ACLMessage.INFORM);
                        inf.addReceiver(new AID("Person", AID.ISLOCALNAME));
                        inf.setOntology("Information_1");
                        Init r = (Init) acl_1.getContentObject();
                        System.out.println(r.M + " " + r.C);

                        send(acl_1);
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                }
                // for block behaviours
                else block();

            }
        });
    }
}
