/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package handson5_multiagent.optimization.pso;

/**
 *
 * @author yadira
 */
import handson5_multiagent.DataSet;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.util.Arrays;

public class PSOServiceAgent extends Agent {

    // Parámetros del algoritmo PSO
    static int swarmSize = 25; // Tamaño de enjambre
    static int maxIterations = 60;
    static double wMax = 0.9; // Peso de inercia máximo
    static double wMin = 0.4; // Peso de inercia mínimo
    static double c1 = 2.0; // Constante cognitiva
    static double c2 = 2.0; // Constante social

    // Mejor posición global y su valor
    static double[] gBestPosition;
    static double gBestValue = Double.MAX_VALUE;

    protected void setup() {

        System.out.println("PSO-service " + getLocalName() + " started.");

        // Crear la descripción del servicio que ofrece este agente
        ServiceDescription sd = new ServiceDescription();
        sd.setType("optimization-service");
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
        addBehaviour(new PSOBehaviour());
    }

    private class PSOBehaviour extends Behaviour {

        Particle particle = new Particle();

        public void action() {
            //System.out.println("Performing Action in PSOServiceAgent");
            ACLMessage msg = receive();
            if (msg != null) {
                //System.out.println("Recibido: desde el agente PSO: " + msg.getContent());
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

                Particle[] swarm = new Particle[swarmSize];
                gBestPosition = new double[2]; // Inicialización de gBestPosition

                // Inicialización del enjambre
                for (int i = 0; i < swarmSize; i++) {
                    swarm[i] = new Particle();
                }

                // Iteraciones del algoritmo PSO
                for (int iter = 0; iter < maxIterations; iter++) {
                    for (Particle particle : swarm) {
                        particle.updateVelocity();
                        particle.updatePosition();
                        particle.updatePersonalBest();

                    }

                    particle.updateGlobalBest(swarm);

                    // Si el COD es del 98% o superior, terminar el algoritmo
                    if (particle.calculateCOD(gBestPosition) >= 0.98) {
                        break;
                    }
                }

                double b0 = gBestPosition[0];
                double b1 = gBestPosition[1];
                /*System.out.println("BETAS DESDE PSOServiceAgent:");
                System.out.println("B0 = " + b0);
                System.out.println("B1 = " + b1);*/
                ACLMessage reply = msg.createReply();
                reply.setContent(b0 + "," + b1 + ",PSO");
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
