/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handson3_agentbasedgradientdescent;

/**
 *
 * @author yadira
 */
public class RegressionMath {
    private DataSet dataSet;

    public RegressionMath(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    public double calculateMSE(double B0, double B1) {
        double sum = 0;
        double[] x = dataSet.getX();
        double[] y = dataSet.getY();
        int n = dataSet.getSize();

        for (int i = 0; i < n; i++) {
            sum += Math.pow(y[i] - (B0 + B1 * x[i]), 2);
        }
        return sum / n;
    }

    public double calculateGradientDescentB0(double B0, double B1) {
        double sum = 0;
        double[] x = dataSet.getX();
        double[] y = dataSet.getY();
        int n = dataSet.getSize();

        for (int i = 0; i < n; i++) {
            sum += y[i] - (B0 + B1 * x[i]);
        }
        return -2.0 / n * sum;
    }

    public double calculateGradientDescentB1(double B0, double B1) {
        double sum = 0;
        double[] x = dataSet.getX();
        double[] y = dataSet.getY();
        int n = dataSet.getSize();

        for (int i = 0; i < n; i++) {
            sum += x[i] * (y[i] - (B0 + B1 * x[i]));
        }
        return -2.0 / n * sum;
    }
}