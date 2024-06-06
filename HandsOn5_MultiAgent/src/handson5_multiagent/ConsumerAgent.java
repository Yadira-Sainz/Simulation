/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package handson5_multiagent;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.util.Arrays;

/**
 *
 * @author yadira
 */
public class ConsumerAgent extends Agent {

    //x = 23 26 30 34 43 48 52 57 58
    //y = 651 762 856 1063 1190 1298 1421 1440 1518
    //DataSet = 23 26 30 34 43 48 52 57 58,651 762 856 1063 1190 1298 1421 1440 1518
    
    private double[] x;
    private double[] y;

    protected void setup() {
        Object[] args = getArguments();
        if (args == null) {
            System.out.println("No data provided!");
            return;
        }

        String[] dataValues1 = args[0].toString().split(" ");
        String[] dataValues2 = args[1].toString().split(" ");

        x = new double[dataValues1.length];
        y = new double[dataValues2.length];

        for (int i = 0; i < dataValues1.length; i++) {
            x[i] = Double.parseDouble(dataValues1[i]);
            y[i] = Double.parseDouble(dataValues2[i]);
        }

        System.out.println("ConsumerAgent " + getLocalName() + " started with dataset: ");
        System.out.println("X values: " + Arrays.toString(x));
        System.out.println("Y values: " + Arrays.toString(y));

        addBehaviour(new ConsumerAgentBehavior());
    }

    private class ConsumerAgentBehavior extends OneShotBehaviour {

        @Override
        public void action() {
            //System.out.println("Performing Action in CosumerAgent");

            if (x != null | y != null) {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

                String xStr = java.util.Arrays.toString(x);
                String yStr = java.util.Arrays.toString(y);
                // Crear el mensaje

                DFAgentDescription[] result = Utils.searchAgent("classifier-service", myAgent);
                msg.setContent(xStr + ";" + yStr);
                for (DFAgentDescription agent : result) {
                    msg.addReceiver(agent.getName());
                }
                // Enviar el mensaje
                send(msg);

                ACLMessage reply = blockingReceive();
                if (reply != null) {
                    //System.out.println("Response from the classifier: technique --> " + reply.getContent());                    
                    Double b0 = Double.parseDouble(reply.getContent().split(",")[0]);
                    Double b1 = Double.parseDouble(reply.getContent().split(",")[1]);
                    Predictions prediction = new Predictions(b0, b1);
                    System.out.println("The best technique found is: " + reply.getContent().split(",")[2]);                    
                    prediction.printRegEquation();
                    System.out.println("PREDICTIONS:");                    
                    prediction.predict(10);
                    prediction.predict(23);
                    prediction.predict(30);
                    prediction.predict(40);
                    prediction.predict(50);
                }
            }else{
                System.out.println("No data provided!");
            }
        }
    }
}
