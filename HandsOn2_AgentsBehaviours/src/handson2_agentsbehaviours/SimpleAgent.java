/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package handson2_agentsbehaviours;

import handson2_agentsbehaviours.DataSet;
import handson2_agentsbehaviours.DiscreteMaths;
import handson2_agentsbehaviours.SLR;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import java.util.Scanner;

/**
 *
 * @author yadira
 */
public class SimpleAgent extends Agent {

    protected void setup() {
        System.out.println("Agent " + getLocalName() + " started.");

        // Add the CyclicBehaviour
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                System.out.println("Cycling");
            }
        });
        // Add the generic behaviour
        addBehaviour(new FourStepBehaviour_Agent1());
        addBehaviour(new FourStepBehaviour_Agent2());
        addBehaviour(new FourStepBehaviour_Agent3());
    }

    /**
     * Inner class FourStepBehaviour
     */
    private class FourStepBehaviour_Agent1 extends Behaviour {

        private int step = 1;

        public void action() {
            //Salary DataSet
            DataSet dataSet = new DataSet(
                    new double[]{1.20, 1.40, 1.60, 2.10, 2.30, 3.00, 3.10, 3.30, 3.30, 3.80, 4.00, 4.10, 4.10, 4.19, 4.6, 5.0, 5.19, 5.39, 6.0, 6.1, 6.89, 7.19, 8.00, 8.29, 8.79, 9.10, 9.60, 9.70, 10.40, 10.60},
                    new double[]{39344, 46206, 37732, 43526, 39892, 56643, 60151, 54446, 64446, 57190, 63219, 55795, 56958, 57082, 61112, 67939, 66030, 83089, 81364, 93941, 91739, 98274, 101303, 113813, 109432, 105583, 116970, 112636, 122392, 121873}
            );
            DiscreteMaths discreteMaths = new DiscreteMaths();

            SLR slr = new SLR(dataSet, discreteMaths);
            switch (step) {
                case 1:
                    System.out.println("Agent 1 - Step 1. Calculating the Regression Line");
                    slr.calculateIntersection();
                    break;
                case 2:
                    System.out.println("Agent 1 - Step 2. Print the Regression Line");
                    slr.printRegEquation();
                    break;
                case 3:
                    System.out.println("Agent 1 - Step 3. Correlation of Coefficient and Determination of Coefficient");
                    slr.printCorrelationCoefficient();
                    slr.printDeterminationCoefficient();
                    break;
                case 4:
                    System.out.println("Agent 1 - Step 4. Predictions");
                    slr.predict(1.20);
                    slr.predict(1.40);
                    slr.predict(1.60);
                    break;
            }
            step++;
        }

        public boolean done() {
            return step == 5;
        }

    }

    private class FourStepBehaviour_Agent2 extends Behaviour {

        private int step = 1;

        public void action() {
            //India´s Population
            DataSet dataSet = new DataSet(
                    new double[]{2050, 2045, 2040, 2035, 2030, 2025, 2020, 2019, 2018, 2017, 2016, 2015, 2010, 2005, 2000, 1995, 1990, 1985, 1980, 1975, 1970, 1965, 1960, 1955},
                    new double[]{1639176033, 1620619200, 1592691513, 1553723810, 1503642322, 1445011620, 1380004385, 1366417754, 1352642280, 1338676785, 1324517249, 1310152403, 1234281170, 1147609927, 1056575549, 963922588, 873277798, 784360008, 698952844, 623102897, 555189792, 499123324, 450547679, 409880595}
            );
            DiscreteMaths discreteMaths = new DiscreteMaths();

            SLR slr = new SLR(dataSet, discreteMaths);
            switch (step) {
                case 1:
                    System.out.println("Agent 2 - Step 1. Calculating the Regression Line");
                    slr.calculateIntersection();
                    break;
                case 2:
                    System.out.println("Agent 2 - Step 2. Print the Regression Line");
                    slr.printRegEquation();
                    break;
                case 3:
                    System.out.println("Agent 2 - Step 3. Correlation of Coefficient and Determination of Coefficient");
                    slr.printCorrelationCoefficient();
                    slr.printDeterminationCoefficient();
                    break;
                case 4:
                    System.out.println("Agent 2 - Step 4. Predictions");
                    slr.predict(2050);
                    slr.predict(2045);
                    slr.predict(2040);
                    break;
            }
            step++;
        }

        public boolean done() {
            return step == 5;
        }

    }

    private class FourStepBehaviour_Agent3 extends Behaviour {

        private int step = 1;

        public void action() {
            //Insurance DataSet
            DataSet dataSet = new DataSet(
                    new double[]{18, 22, 23, 26, 28, 31, 33},
                    new double[]{10000, 15000, 18000, 21000, 24000, 26500, 27000}
            );
            DiscreteMaths discreteMaths = new DiscreteMaths();

            SLR slr = new SLR(dataSet, discreteMaths);
            switch (step) {
                case 1:
                    System.out.println("Agent 3 - Step 1. Calculating the Regression Line");
                    slr.calculateIntersection();
                    break;
                case 2:
                    System.out.println("Agent 3 - Step 2. Print the Regression Line");
                    slr.printRegEquation();
                    break;
                case 3:
                    System.out.println("Agent 3 - Step 3. Correlation of Coefficient and Determination of Coefficient");
                    slr.printCorrelationCoefficient();
                    slr.printDeterminationCoefficient();
                    break;
                case 4:
                    System.out.println("Agent 3 - Step 4. Predictions");
                    slr.predict(18);
                    slr.predict(22);
                    slr.predict(23);
                    break;
            }
            step++;
        }

        public boolean done() {
            return step == 5;
        }

        public int onEnd() {
            myAgent.doDelete();
            return super.onEnd();
        }
    }

}
