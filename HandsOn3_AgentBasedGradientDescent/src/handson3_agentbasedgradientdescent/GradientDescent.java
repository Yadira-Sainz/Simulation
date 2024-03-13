/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package handson3_agentbasedgradientdescent;

/**
 *
 * @author yadira
 */
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class GradientDescent extends Agent {

    private double B0 = 0;
    private double B1 = 0;
    private double learningRate = 0.0005;
    private int epochs = 70000;
    private DataSet dataSet;
    private RegressionMath regressionMath;
    private LinearRegression linearRegression;

    protected void setup() {
        // Inicializa el conjunto de datos
        this.dataSet = new DataSet();

        // Inicializa RegressionMath
        this.regressionMath = new RegressionMath(dataSet);

        // Inicializa LinearRegression
        this.linearRegression = new LinearRegression(dataSet, new DiscreteMaths());

        // Inicia el comportamiento de un solo disparo
        addBehaviour(new OneShotBehaviour(this) {
            public void action() {
                for (int epoch = 0; epoch < epochs; epoch++) {
                    // Cálculo del error cuadrático medio (Mean Squared Error)
                    double mse = regressionMath.calculateMSE(B0, B1);

                    // Si el error no es cercano a cero, actualiza los parámetros
                    if (Math.abs(mse) > 0.0001) {
                        // Cálculo de la fórmula de gradiente descendiente para B0 y B1
                        double gradientDescentB0 = regressionMath.calculateGradientDescentB0(B0, B1);
                        double gradientDescentB1 = regressionMath.calculateGradientDescentB1(B0, B1);

                        // Actualización de B0 y B1 usando las reglas de aprendizaje
                        B0 -= learningRate * gradientDescentB0;
                        B1 -= learningRate * gradientDescentB1;
                    }
                    // Imprimir los valores en cada iteracion
                    /*System.out.println("Epoch: " + (epoch + 1));
                    System.out.println("B0 actualizado: " + B0);
                    System.out.println("B1 actualizado: " + B1);
                    System.out.println("MSE: " + mse);*/
                }

                // Imprimir los valores finales después de todas las iteraciones
                System.out.println("Epoch: " + epochs);
                System.out.println("B0 actualizado: " + B0);
                System.out.println("B1 actualizado: " + B1);

                // Calcular MSE una vez más para los valores finales
                double finalMSE = regressionMath.calculateMSE(B0, B1);
                System.out.println("MSE: " + finalMSE);

                // Imprimir la línea de regresión, coeficiente de correlación y coeficiente de determinación
                linearRegression.printRegEquation();
                linearRegression.printCorrelationCoefficient();
                linearRegression.printDeterminationCoefficient();

                // Predicciones hardcoded
                System.out.println("Predicciones:");
                linearRegression.predict(23); // Predicción 1
                linearRegression.predict(26); // Predicción 2
                linearRegression.predict(30); // Predicción 3
            }
        });
    }
}