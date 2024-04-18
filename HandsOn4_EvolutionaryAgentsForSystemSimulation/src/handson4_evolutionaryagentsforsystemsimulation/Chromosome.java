/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handson4_evolutionaryagentsforsystemsimulation;

/**
 *
 * @author yadira
 */
import java.util.Random;

class Chromosome {

    private double b0;
    private double b1;
    private double fitness;
    private DataSet dataSet;

    public Chromosome(double b0, double b1, DataSet dataSet) {
        this.b0 = b0;
        this.b1 = b1;
        this.dataSet = dataSet;
    }

    public double getB0() {
        return b0;
    }

    public void setB0(double b0) {
        this.b0 = b0;
    }

    public double getB1() {
        return b1;
    }

    public void setB1(double b1) {
        this.b1 = b1;
    }

    public double getFitness() {
        return fitness;
    }

    public void calculateFitness(double[] x, double[] y) {
        double predictedY;
        double totalError = 0;
        double meanY = 0;

        for (double yi : y) {
            meanY += yi;
        }

        meanY /= y.length;

        for (int i = 0; i < x.length; i++) {
            predictedY = b0 + b1 * x[i];
            totalError += Math.pow((y[i] - predictedY), 2);
        }

        double rSquared = 1 - (totalError / totalVariance(y, meanY));
        this.fitness = rSquared;
    }

    private double totalVariance(double[] y, double meanY) {
        double totalVariance = 0;

        for (double yi : y) {
            totalVariance += Math.pow((yi - meanY), 2);
        }

        return totalVariance;
    }

    public void mutate() {
        Random random = new Random();
        if (random.nextBoolean()) {
            b0 = 1 + random.nextDouble() * (200 - 1);
        } else {
            b1 = random.nextDouble() * 50;
        }
    }

    public DataSet getDataSet() {
        return dataSet;
    }
}
