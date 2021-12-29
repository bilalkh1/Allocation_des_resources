package person;

import Tools.Init;
import Tools.Person;
import Tools.Reservation;
import Tools.Restaurant;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AgentPerson extends GuiAgent {
    int m = 8;
    List<Person> personList = new ArrayList<Person>();
    Boolean success = false;
    public static Init variables;

    @Override
    protected void setup() {
        ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
        addBehaviour(parallelBehaviour);

        parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                Boolean answer = false;
                MessageTemplate msg1 = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM),
                        MessageTemplate.MatchOntology("InformationR"));
                ACLMessage acl_1 = receive(msg1);
                Boolean start = false;
                if (acl_1 != null){
                    try {
                        System.out.println("-----");
                        Init r =(Init) acl_1.getContentObject();
                        variables = new Init(r.M,r.C,r.N,r.restaurants);
                        //acl_1.clearAllReceiver();
                        System.out.println(variables.M + " " + variables.C);
                        start = true;
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(start){
                    // for loop
                    // get N number of persons
                    for (int i = 0; i < variables.N; i++) {
                        // instantiate  the person
                        personList.add(new Person(i));

                        // loop to send reservations
                        // send reservation till success
                        // receive result
                        while(!success) {

                            try {
                                    if(!answer) {
                                        // Setup ACL Message object
                                        ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
                                        aclMessage.addReceiver(new AID("Broker", AID.ISLOCALNAME));
                                        aclMessage.setOntology("Reservation");
                                        aclMessage.setContentObject((Serializable) new Reservation((int) (Math.random() * (11 - 6 + 1) + 6), new Date()));
                                        send(aclMessage);
                                        answer = true;
                                    }


                                    MessageTemplate message1 = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                                    MessageTemplate.MatchOntology("check"));
                                    ACLMessage acl1 = receive(message1);
                                    if(acl1 != null){
                                        success = (Boolean) acl1.getContentObject();
                                        System.out.println(success);
                                        answer = false;
                                    }else block();

                            } catch (Exception ex) {
                                System.out.println(ex);
                            }
                        }
                        System.out.println("Person " + i + " successfully reserved the restaurant");
                    }
                }


                // back to the first loop till the last agent has a restaurant

            }
        });

    }

    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {
//        switch (guiEvent.getType()) {
//            case 1:
//                System.out.println("En Gui Event");
//                Map<String, Object> params = (Map<String, Object>) event.getParameter(0);
//
//                List<Ville> villes = (List<Ville>) params.get("v1");
//
//                ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
//                aclMessage.addReceiver(new AID("Intermediaire", AID.ISLOCALNAME));
//
//                try {
//                    aclMessage.setContentObject((Serializable) villes);
//
//                } catch (IOException ex) {
//                    System.out.println(ex);
//                }
//                aclMessage.setOntology("ca marche");
//                send(aclMessage);
//                break;
//
//            default:
//                break;
//
//        }
    }
}


