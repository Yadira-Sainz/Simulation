/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handson5_multiagent.regression.pr;

import handson5_multiagent.DataSet;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.util.Arrays;

/**
 *
 * @author yadira
 */
public class PRServiceAgent extends Agent {

    private double B0 = 0;
    private double B1 = 0;
    private double learningRate = 0.0005;
    private int epochs = 70000;
    private DataSet dataSet;
    private RegressionMath regressionMath;
    private LinearRegression linearRegression;

    protected void setup() {
        System.out.println("PR-service " + getLocalName() + " started.");

        // Crear la descripción del servicio que ofrece este agente
        ServiceDescription sd = new ServiceDescription();
        sd.setType("regression-service");
        sd.setName(getLocalName());

        // Crear la descripción del agente
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        dfd.addServices(sd);

        try {
            // Registrar el agente en el servicio de páginas amarillas
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        addBehaviour(new PRServiceAgentBehaviour());

    }

    private class PRServiceAgentBehaviour extends Behaviour {

        @Override
        public void action() {
            //System.out.println("Performing Action in PRServiceAgent");
            ACLMessage msg = receive();
            if (msg != null) {
                //System.out.println("Recibido: desde el agente PR: " + msg.getContent());
                // Obtener el contenido del mensaje
                String content = msg.getContent();
                String[] parts = content.split(";");
                String xStr = parts[0];
                String yStr = parts[1];

                // Eliminar los corchetes y dividir por las comas
                String[] xValues = xStr.replace("[", "").replace("]", "").split(", ");
                String[] yValues = yStr.replace("[", "").replace("]", "").split(", ");

                // Crea los arrays de floats
                double[] array1 = new double[xValues.length];
                double[] array2 = new double[yValues.length];

                // Convierte cada elemento a un float y guárdalo en los arrays de floats
                for (int i = 0; i < xValues.length; i++) {
                    array1[i] = Double.parseDouble(xValues[i]);
                }

                for (int i = 0; i < yValues.length; i++) {
                    array2[i] = Double.parseDouble(yValues[i]);
                }
                //DataSet dataSet = null;
                DataSet dataSet = null;

                if (array1 == null || array2 == null) {
                    dataSet = new DataSet();
                } else {
                    dataSet = new DataSet(array1, array2);
                }

                RegressionMath regressionMath = new RegressionMath(dataSet);

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
                }

                double b0;
                double b1;

                b0 = B0;
                b1 = B1;
                //System.out.println("BETAS DESDE PRServiceAgent:");
                //System.out.println("B0 = " + b0);
                //System.out.println("B1 = " + b1);
                ACLMessage reply = msg.createReply();
                reply.setContent(b0 + "," + b1 + ",PR");
                send(reply);
            } else {
                block();
            }
        }

        @Override
        public boolean done() {
            return false;
        }

    }
}
