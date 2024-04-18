/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package handson4_evolutionaryagentsforsystemsimulation;

/**
 *
 * @author yadira
 */
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import java.util.ArrayList;
import java.util.Random;

public class GeneticAgent extends Agent {

    private static final double CROSSOVER_RATE = 0.7;
    private static final double MUTATION_RATE = 0.05;
    private static final double TARGET_RSQUARED = 0.9;

    private Population population;

    protected void setup() {
        DataSet dataSet = new DataSet();

        population = new Population(100, dataSet);

        addBehaviour(new TickerBehaviour(this, 1000) {
            protected void onTick() {
                population.evaluatePopulation();

                if (population.isSolutionFound(TARGET_RSQUARED)) {
                    System.out.println("Solution found:");
                    System.out.println("B0: " + population.getBestChromosome().getB0());
                    System.out.println("B1: " + population.getBestChromosome().getB1());
                    printRegressionEquation(population.getBestChromosome().getB0(), population.getBestChromosome().getB1());
                    doDelete();
                }
            }
        });
    }

    private void printRegressionEquation(double b0, double b1) {
        System.out.println("Regression Equation: y = " + b0 + " + " + b1 + "x");
    }
}
