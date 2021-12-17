package Broker;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

public class BrokerContainer {

    public static void main(String[] args) {
        try {
            // jade runtime
            Runtime runtime = Runtime.instance();
            // create profile implimentation
            ProfileImpl profileImpl = new ProfileImpl(false);
            // specify the host
            profileImpl.setParameter(ProfileImpl.MAIN_HOST, "localhost");
            // create jade container
            AgentContainer agentContainer = runtime.createAgentContainer(profileImpl);
            // create instance
            AgentController agentController = agentContainer.createNewAgent("Broker", BrokerAgent.class.getName(), new Object[]{});
            // start agent instance
            agentController.start();

        } catch (ControllerException e){
            e.printStackTrace();
        }
    }
}
