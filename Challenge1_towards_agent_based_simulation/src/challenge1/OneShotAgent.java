/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package challenge1;

import challenge1.DataSet;
import challenge1.DiscreteMaths;
import challenge1.SLR;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import java.util.Scanner;


/**
 *
 * @author yadira
 */
public class OneShotAgent extends Agent {
    protected void setup() {
    System.out.println("Agent "+ getLocalName() + " started.");
    addBehaviour(new MyOneShotBehaviour());
  } 

  private class MyOneShotBehaviour extends OneShotBehaviour {

    public void action() {
	Scanner reading = new Scanner(System.in);
        DataSet dataSet = new DataSet();
        DiscreteMaths discreteMaths = new DiscreteMaths();

        SLR slr = new SLR(dataSet, discreteMaths);

        slr.calculateIntersection();
        System.out.println("x^2: " + discreteMaths.XPow2(dataSet.getX()));
        System.out.println("Σx^2: " + discreteMaths.sumXPow2(dataSet.getX()));
        System.out.println("Σx * Σy: " + discreteMaths.sumXY(dataSet.getX(), dataSet.getY()));
        System.out.println("Beta 1: " + discreteMaths.b1(dataSet.getX(), dataSet.getY()));
        System.out.println("Beta 0: " + discreteMaths.b0(dataSet.getX(), dataSet.getY()));
        slr.printRegEquation();
        System.out.println("Enter the value of X: ");
        float num = reading.nextFloat();
        slr.predict(num);
    } 

    public int onEnd() {   
       myAgent.doDelete();
       return super.onEnd();
    } 
  }
    
}
