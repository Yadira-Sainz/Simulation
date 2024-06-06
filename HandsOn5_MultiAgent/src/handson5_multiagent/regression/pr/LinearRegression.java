/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handson5_multiagent.regression.pr;

import handson5_multiagent.DataSet;
import handson5_multiagent.DiscreteMaths;

/**
 *
 * @author yadira
 */
public class LinearRegression {

    private DataSet dataSet;
    private DiscreteMaths discreteMaths;

    public LinearRegression(DataSet dataSet, DiscreteMaths discreteMaths) {
        this.dataSet = dataSet;
        this.discreteMaths = discreteMaths;
    }

    public void calculateIntersection() {
        System.out.println("x: " + discreteMaths.sumX(dataSet.getX()));
        System.out.println("y: " + discreteMaths.sumY(dataSet.getY()));
    }

    public void printRegEquation() {
        System.out.println("Regression Equation = " + discreteMaths.b0(dataSet.getX(), dataSet.getY()) + " + "
                + discreteMaths.b1(dataSet.getX(), dataSet.getY()) + "x");
    }

    public void predict(double x) {
        double e = discreteMaths.b0(dataSet.getX(), dataSet.getY())
                + (discreteMaths.b1(dataSet.getX(), dataSet.getY()) * x);
        System.out.println("Predict of Y: " + e);
    }

    public void printCorrelationCoefficient() {
        double r = discreteMaths.correlationCoefficient(dataSet.getX(), dataSet.getY());
        System.out.println("Correlation Coefficient: " + r);
    }

    public void printDeterminationCoefficient() {
        double R2 = discreteMaths.determinationCoefficient(dataSet.getX(), dataSet.getY());
        System.out.println("Determination Coefficient: " + R2);
    }
}