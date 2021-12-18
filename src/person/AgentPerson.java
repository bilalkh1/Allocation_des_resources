package person;

import Tools.Person;
import Tools.Reservation;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AgentPerson extends GuiAgent {
    List<Person> personList;
    Boolean success = false;
    int m;

    @Override
    protected void setup() {
        m = 8;
        ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
        addBehaviour(parallelBehaviour);

        parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {

                // Setup ACL Message object
                ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
                aclMessage.addReceiver(new AID("Broker", AID.ISLOCALNAME));
                aclMessage.setOntology("Reservation");
                // for loop
                // get N number of persons
                for (int i = 0; i < 10; i++) {
                    // instantiate  the person
                    personList.add(new Person(i));

                    // loop to send reservations
                    // send reservation till success
                    // receive result
                    while(!success) {
                        try {
                            aclMessage.setContentObject((Serializable) new Reservation((int)(Math.random()*(15-8+1)+8), new Date()));
                            send(aclMessage);

                            MessageTemplate message1 = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                                    MessageTemplate.MatchOntology("check"));
                            ACLMessage acl1 = receive(message1);
                            success = (Boolean) acl1.getContentObject();

                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                    }
                    System.out.println("Person " + i + " successfully reserved the restaurant");
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


