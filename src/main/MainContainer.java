package main;

import Tools.Init;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;

public class MainContainer {
    public static void main(String[] args) {
        try {
            //new Init();
            //System.out.println(Init.restaurants);
            // create container agent
            Runtime runtime = Runtime.instance();
            Properties properties = new ExtendedProperties();
            properties.setProperty(Profile.GUI, "true");
            ProfileImpl profileImpl = new ProfileImpl(properties);
            AgentContainer mainContainer = runtime.createMainContainer(profileImpl);
            // start the agent instance
            mainContainer.start();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
