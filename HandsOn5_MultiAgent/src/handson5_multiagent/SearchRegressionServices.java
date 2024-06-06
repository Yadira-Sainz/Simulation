/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handson5_multiagent;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author yadira
 */
public class SearchRegressionServices extends Agent {

    @Override
    protected void setup() {

        System.out.println("SearchRegressionServices " + getLocalName() + " started.");

        // Crear la descripción del servicio que ofrece este agente
        ServiceDescription sd = new ServiceDescription();
        sd.setType("search-regression-services");
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
        addBehaviour(new SearchRegresionServicesBehaviour());

    }

    private class SearchRegresionServicesBehaviour extends Behaviour {

        @Override
        public void action() {
            String bestTechnique = "";
            double bestError = Double.MAX_VALUE;
            ACLMessage msgToOptSercive = new ACLMessage(ACLMessage.INFORM);
            ACLMessage msg = receive();
            String betas = "";
            if (msg != null) {

                //System.out.println("--> " + msg.getContent());
                ACLMessage replayToClassifier = msg.createReply();
                //System.out.println("Recived msg in SearchRegressionServices:" + msg.getContent());
                msgToOptSercive.setContent(msg.getContent());
                // Crear el mensaje
                DFAgentDescription[] result1 = Utils.searchAgent("regression-service", myAgent);
                // Having GA and PSO optimization sertives:
                for (DFAgentDescription agent : result1) {
                    msgToOptSercive.addReceiver(agent.getName());
                    send(msgToOptSercive);
                    ACLMessage reply = blockingReceive();
                    if (reply != null) {
                        //System.out.println("Betas Reg--> " + reply.getContent());
                        // Add logic to capture both resutls
                        String content = reply.getContent();
                        double mean = Utils.calculateMean(content);
                        double error = Utils.calculateR2(content, mean);
                        if (error < bestError) {
                            bestError = error;
                            bestTechnique = reply.getContent().split(",")[2];
                            betas = reply.getContent();
                        }
                    }
                    msgToOptSercive.clearAllReceiver();
                }
                System.out.println("Best agent of regression is " + bestTechnique + " betas: " + betas);
                replayToClassifier.setContent(betas);
                send(replayToClassifier);
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
