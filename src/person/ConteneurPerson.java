package person;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

public class ConteneurPerson {

    public static  void main(String[] args){
        try{
            // On r�cup�re l'instance singleton du JADE Runtime
            Runtime runtime=Runtime.instance();
            //Cr�e une impl�mentation de profil
            ProfileImpl profileImpl=new ProfileImpl(false);
            //On sp�cifie l'h�te r�seau sur lequel s'ex�cute le conteneur principal JADE
            profileImpl.setParameter(ProfileImpl.MAIN_HOST, "localhost");
            //On Cr�e un conteneur p�riph�rique JADE
            AgentContainer agentContainer = runtime.createAgentContainer(profileImpl);
            //On cr�� l'instance de l'agent
            AgentController agentController = agentContainer.createNewAgent("Person", AgentPerson.class.getName(), new Object[]{});

            //D�marr� l'instance de l'agent
            agentController.start();

        } catch (ControllerException e){
            e.printStackTrace();
        }
    }

}
