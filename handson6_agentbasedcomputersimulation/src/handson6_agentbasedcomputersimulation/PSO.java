/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package handson6_agentbasedcomputersimulation;

/**
 *
 * @author yadira
 */
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import java.util.Random;

public class PSO extends Agent {

    // Parámetros del algoritmo PSO
    static int swarmSize = 25; // Tamaño de enjambre
    static int maxIterations = 60;
    static double wMax = 0.9; // Peso de inercia máximo
    static double wMin = 0.4; // Peso de inercia mínimo
    static double c1 = 2.0; // Constante cognitiva
    static double c2 = 2.0; // Constante social

    // Mejor posición global y su valor
    static double[] gBestPosition;
    static double gBestValue = Double.MAX_VALUE;

    protected void setup() {
        addBehaviour(new PSOBehaviour());
    }

    private class PSOBehaviour extends OneShotBehaviour {

        public void action() {
            Particle[] swarm = new Particle[swarmSize];
            gBestPosition = new double[2]; // Inicialización de gBestPosition

            // Inicialización del enjambre
            for (int i = 0; i < swarmSize; i++) {
                swarm[i] = new Particle();
            }

            // Iteraciones del algoritmo PSO
            for (int iter = 0; iter < maxIterations; iter++) {
                for (Particle particle : swarm) {
                    particle.updateVelocity();
                    particle.updatePosition();
                    particle.updatePersonalBest();

                    // Imprimir la posición de la partícula en cada iteración
                    System.out.println("Iteración " + iter + ", Posición de la partícula: " + java.util.Arrays.toString(particle.position));
                }

                updateGlobalBest(swarm);

                // Si el COD es del 98% o superior, terminar el algoritmo
                if (calculateCOD(gBestPosition) >= 0.98) {
                    break;
                }
            }

            // Imprimir resultados
            System.out.println("Mejor posición global: B0 = " + gBestPosition[0] + ", B1 = " + gBestPosition[1]);
            System.out.println("Valor MSE asociado: " + gBestValue);
            System.out.println("Coeficiente de Determinación (COD): " + calculateCOD(gBestPosition));

        }

        // Actualización de la mejor posición global
        void updateGlobalBest(Particle[] swarm) {
            for (Particle particle : swarm) {
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
            double mse = 0;
            for (int i = 0; i < DataSet.x.length; i++) {
                double salesPredicted = b0 + b1 * DataSet.x[i];
                mse += Math.pow((salesPredicted - DataSet.y[i]), 2);
            }
            return mse / DataSet.x.length;
        }

        // Calcular el Coeficiente de Determinación (COD)
        double calculateCOD(double[] position) {
            double b0 = position[0];
            double b1 = position[1];
            double yMean = 0;
            for (double yi : DataSet.y) {
                yMean += yi;
            }
            yMean /= DataSet.y.length;

            double ssTot = 0;
            double ssRes = 0;
            for (int i = 0; i < DataSet.x.length; i++) {
                double yPred = b0 + b1 * DataSet.x[i];
                ssTot += Math.pow((DataSet.y[i] - yMean), 2);
                ssRes += Math.pow((DataSet.y[i] - yPred), 2);
            }

            return 1 - (ssRes / ssTot);
        }
    }
}
