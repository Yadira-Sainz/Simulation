/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handson5_multiagent.regression.slr;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.lang.reflect.Array;
import java.util.Arrays;
import handson5_multiagent.DataSet;
import handson5_multiagent.DataSet;
import handson5_multiagent.DiscreteMaths;


/**
 *
 * @author yadira
 */
public class SLRServiceAgent extends Agent {
    
    protected void setup() {
        System.out.println("SLR-service " + getLocalName() + " started.");

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
        addBehaviour(new SLRServiceAgentBehaviour());

    }

    private class SLRServiceAgentBehaviour extends Behaviour {

        @Override
        public void action() {
            //System.out.println("Performing Action in SLRServiceAgent");
            ACLMessage msg = receive();
            if (msg != null) {
                //System.out.println("Recibido: desde el agente SLR: " + msg.getContent());
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
                DiscreteMaths discreteMaths = new DiscreteMaths();
                
                double b0;
                double b1;
                   
                b0 = discreteMaths.b0(dataSet.getX(), dataSet.getY());                
                b1 = discreteMaths.b1(dataSet.getX(), dataSet.getY());          
                //System.out.println("BETAS DESDE SLRServiceAgent:");
                //System.out.println("B0 = " + discreteMaths.b0(dataSet.getX(), dataSet.getY()));
                //System.out.println("B1 = " + discreteMaths.b1(dataSet.getX(), dataSet.getY()));
                ACLMessage reply = msg.createReply();
                reply.setContent(b0 + "," + b1 + ",SLR");
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
