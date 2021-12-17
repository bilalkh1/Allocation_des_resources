package Main;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;

public class MainContainer {
    public static void main(String[] args) {
        try {
            //instance pour creer l'agent container
            Runtime runtime=Runtime.instance();
            Properties properties=new ExtendedProperties();
            properties.setProperty(Profile.GUI, "true");
            //Cr�e une impl�mentation de profil en utilisant les proprietes donnees pour configurer le processus de d�marrage de la plateforme
            ProfileImpl profileImpl=new ProfileImpl(properties);
            AgentContainer mainContainer=runtime.createMainContainer(profileImpl);


            //start the agent instance
            mainContainer.start();
        }
        catch(Exception e) { e.printStackTrace();}
    }
}
