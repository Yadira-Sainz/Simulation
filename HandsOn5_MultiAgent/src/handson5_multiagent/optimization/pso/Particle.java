/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handson5_multiagent.optimization.pso;

/**
 *
 * @author yadira
 */
import handson5_multiagent.DataSet;
import static handson5_multiagent.optimization.pso.PSOServiceAgent.gBestValue;
import static handson5_multiagent.optimization.pso.PSOServiceAgent.gBestPosition;
import static handson5_multiagent.optimization.pso.PSOServiceAgent.*;
import java.util.Random;

public class Particle {

    double[] position;
    double[] velocity;
    double[] pBestPosition;
    double pBestValue;

    private DataSet dataSet;

    public Particle() {

        position = new double[2]; // Asumiendo que hay dos dimensiones como por la matriz OUTPUT
        velocity = new double[2];
        for (int i = 0; i < position.length; i++) {
            position[i] = Math.round(randomInRange(0, 10)); // LB es 0 y UB es 10
            velocity[i] = randomInRange(0, 1) * position[i]; // Velocidad inicial es un valor aleatorio entre 0 y 1 multiplicado por la posición
        }
        pBestPosition = position.clone();
        dataSet =new DataSet();
        pBestValue = evaluate(position);

    }

    // Actualizar velocidad de la partícula
    void updateVelocity() {
        Random rand = new Random();
        double rand1 = rand.nextDouble();
        double rand2 = rand.nextDouble();
        double w = wMax - ((wMax - wMin) * rand.nextDouble());
        for (int i = 0; i < velocity.length; i++) {
            velocity[i] = w * velocity[i]
                    + c1 * rand1 * (pBestPosition[i] - position[i])
                    + c2 * rand2 * (gBestPosition[i] - position[i]);
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
        double mse = 0;
        for (int i = 0; i < dataSet.getX().length; i++) {
            double salesPredicted = b0 + b1 * dataSet.getX()[i];
            mse += Math.pow((salesPredicted - dataSet.getY()[i]), 2);
        }
        return mse / dataSet.getX().length;
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

    // Calcular el Coeficiente de Determinación (COD)
    double calculateCOD(double[] position) {
        double b0 = position[0];
        double b1 = position[1];
        double yMean = 0;
        for (double yi : dataSet.getY()) {
            yMean += yi;
        }
        yMean /= dataSet.getY().length;

        double ssTot = 0;
        double ssRes = 0;
        for (int i = 0; i < dataSet.getX().length; i++) {
            double yPred = b0 + b1 * dataSet.getX()[i];
            ssTot += Math.pow((dataSet.getY()[i] - yMean), 2);
            ssRes += Math.pow((dataSet.getY()[i] - yPred), 2);
        }

        return 1 - (ssRes / ssTot);
    }
}
