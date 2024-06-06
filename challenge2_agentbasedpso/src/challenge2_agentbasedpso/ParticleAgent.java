/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package challenge2_agentbasedpso;

/**
 *
 * @author yadira
 */
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import java.util.Random;

public class ParticleAgent extends Agent {

    double[] position;
    double[] velocity;
    double[] pBestPosition;
    double pBestValue;

    protected void setup() {
        System.out.println("Agent " + getLocalName() + " started.");
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            position = (double[]) args[0];
            velocity = (double[]) args[1];
        } else {
            position = new double[3]; // Asumiendo que hay tres dimensiones como por la matriz OUTPUT
            velocity = new double[3];
            for (int i = 0; i < position.length; i++) {
                position[i] = Math.round(randomInRange(0, 10)); // LB es 0 y UB es 10
                velocity[i] = randomInRange(0, 1) * position[i]; // Velocidad inicial es un valor aleatorio entre 0 y 1 multiplicado por la posición
            }
        }
        pBestPosition = position.clone();
        pBestValue = evaluate(position);
        addBehaviour(new PSOBehaviour());
    }

    private class PSOBehaviour extends OneShotBehaviour {

        public void action() {
            updateVelocity();
            updatePosition();
            updatePersonalBest();
        }
    }

    // Actualizar velocidad de la partícula
    void updateVelocity() {
        Random rand = new Random();
        double rand1 = rand.nextDouble();
        double rand2 = rand.nextDouble();
        double w = SwarmInitializerAgent.wMax - ((SwarmInitializerAgent.wMax - SwarmInitializerAgent.wMin) * rand.nextDouble());
        for (int i = 0; i < velocity.length; i++) {
            velocity[i] = w * velocity[i]
                    + SwarmInitializerAgent.c1 * rand1 * (pBestPosition[i] - position[i])
                    + SwarmInitializerAgent.c2 * rand2 * (SwarmInitializerAgent.gBestPosition[i] - position[i]);
        }
    }

    // Actualizar posición de la partícula
    void updatePosition() {
        for (int i = 0; i < position.length; i++) {
            position[i] += velocity[i];
        }
    }

    // Actualizar mejor posición personal
    void updatePersonalBest() {
        double value = evaluate(position);
        if (value < pBestValue) {
            pBestValue = value;
            pBestPosition = position.clone();
        }
    }

    // Generar número aleatorio en un rango específico
    double randomInRange(double min, double max) {
        Random r = new Random();
        return min + (max - min) * r.nextDouble();
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
}
