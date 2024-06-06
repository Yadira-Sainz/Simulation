/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package challenge2_agentbasedpso;

/**
 *
 * @author yadira
 */
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class OptimumCheckerAgent extends Agent {

    private double bestRSquared = -1.0;
    private double[] bestParameters = null;

    protected void setup() {
        System.out.println("Agent " + getLocalName() + " started.");
        addBehaviour(new OptimumCheckerAgentBehaviour());
    }

    private class OptimumCheckerAgentBehaviour extends CyclicBehaviour {

        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // Extraer los valores de los parámetros y R² del mensaje
                System.out.println(msg.getContent());
                String[] parts = msg.getContent().split(": ");
                String[] values = parts[1].split(", ");
                double b0 = Double.parseDouble(values[0].split(" = ")[1]);
                double b1 = Double.parseDouble(values[1].split(" = ")[1]);
                double b2 = Double.parseDouble(values[2].split(" = ")[1]);
                double rSquared = Double.parseDouble(values[3].split(" = ")[1]);

                // Verificar si R² es mayor que el mejor R² encontrado hasta ahora
                if (rSquared > bestRSquared) {
                    bestRSquared = rSquared;
                    bestParameters = new double[] {b0, b1, b2};
                }
            } else {
                block();
                if (bestParameters != null) {
                    System.out.println("Valores óptimos: B0 = " + bestParameters[0] + ", B1 = " + bestParameters[1] + ", B2 = " + bestParameters[2]);
                }
            }
        }

    }
}