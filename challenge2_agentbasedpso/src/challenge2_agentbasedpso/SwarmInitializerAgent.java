/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package challenge2_agentbasedpso;

/**
 *
 * @author yadira
 */
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.Random;

public class SwarmInitializerAgent extends Agent {

    // Parámetros del algoritmo SwarmInitializerAgent
    static int swarmSize = 25; // Tamaño de enjambre
    static int maxIterations = 100;
    static double wMax = 0.9; // Peso de inercia máximo
    static double wMin = 0.4; // Peso de inercia mínimo
    static double c1 = 2.0; // Constante cognitiva
    static double c2 = 2.0; // Constante social

    // Mejor posición global y su valor
    static double[] gBestPosition;
    static double gBestValue = Double.MAX_VALUE;

    static {
        gBestPosition = new double[3]; // Inicialización de gBestPosition
    }

    protected void setup() {
        System.out.println("Agent " + getLocalName() + " started.");
        addBehaviour(new SwarmInitializerAgentBehaviour());
    }

    private class SwarmInitializerAgentBehaviour extends OneShotBehaviour {

        public void action() {
            ParticleAgent[] swarm = new ParticleAgent[swarmSize];
            gBestPosition = new double[3]; // Inicialización de gBestPosition

            // Inicialización del enjambre
            for (int i = 0; i < swarmSize; i++) {
                swarm[i] = new ParticleAgent();
                swarm[i].position = new double[3];
                swarm[i].velocity = new double[3];
                for (int j = 0; j < swarm[i].position.length; j++) {
                    swarm[i].position[j] = Math.round(randomInRange(0, 10)); // Población inicial aleatoria, LB es 0 y UB es 10
                    swarm[i].velocity[j] = randomInRange(0, 1) * swarm[i].position[j]; // Velocidad inicial es un valor aleatorio entre 0 y 1 multiplicado por la posición
                }
                swarm[i].pBestPosition = swarm[i].position.clone();
                swarm[i].pBestValue = evaluate(swarm[i].position);
            }

            // Iteraciones del algoritmo SwarmInitializerAgent
            for (int iter = 0; iter < maxIterations; iter++) {
                for (ParticleAgent particle : swarm) {
                    particle.updateVelocity();
                    particle.updatePosition();
                    particle.updatePersonalBest();
                    // Imprimir la posición de la partícula en cada iteración
                    //System.out.println("Iteración " + iter + ", Posición de la partícula: " + java.util.Arrays.toString(particle.position));

                }

                updateGlobalBest(swarm);

                // Enviar mensaje con los valores actuales de gBestPosition y R²
                double rSquared = calculateCOD(gBestPosition);
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID("OptimumCheckerAgent", AID.ISLOCALNAME));
                msg.setContent("ParticleAgent: B0 = " + gBestPosition[0] + ", B1 = " + gBestPosition[1] + ", B2 = " + gBestPosition[2] + ", R² = " + rSquared);
                send(msg);

                // Si el COD es del 98% o superior, terminar el algoritmo
                if (rSquared >= 0.98) {
                    break;
                }
            }

        }

        // Actualización de la mejor posición global
        void updateGlobalBest(ParticleAgent[] swarm) {
            for (ParticleAgent particle : swarm) {
                if (particle.pBestValue < gBestValue) {
                    gBestValue = particle.pBestValue;
                    gBestPosition = particle.pBestPosition.clone();
                }
            }
        }

        // Función de evaluación: Error cuadrático medio (MSE)
        double evaluate(double[] position) {
            double b0 = position[0];
            double b1 = position[1];
            double b2 = position[2];
            double mse = 0;
            for (int i = 0; i < DataSet.x.length; i++) {
                double salesPredicted = b0 + b1 * DataSet.x[i] + b2 * DataSet.z[i];
                mse += Math.pow((salesPredicted - DataSet.y[i]), 2);
            }
            return mse / DataSet.x.length;
        }

        // Calcular el Coeficiente de Determinación (COD)
        double calculateCOD(double[] position) {
            double b0 = position[0];
            double b1 = position[1];
            double b2 = position[2];
            double yMean = 0;
            for (double yi : DataSet.y) {
                yMean += yi;
            }
            yMean /= DataSet.y.length;

            double ssTot = 0;
            double ssRes = 0;
            for (int i = 0; i < DataSet.x.length; i++) {
                double yPred = b0 + b1 * DataSet.x[i] + b2 * DataSet.z[i];
                ssTot += Math.pow((DataSet.y[i] - yMean), 2);
                ssRes += Math.pow((DataSet.y[i] - yPred), 2);
            }

            return 1 - (ssRes / ssTot);
        }
    }

    // Generar número aleatorio en un rango específico
    double randomInRange(double min, double max) {
        Random r = new Random();
        return min + (max - min) * r.nextDouble();
    }
}
