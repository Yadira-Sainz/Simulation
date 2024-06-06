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
import java.util.Arrays;

/**
 *
 * @author yadira
 */
public class ClassifierAgent extends Agent {

    protected void setup() {
        System.out.println("ClassifierAgent " + getLocalName() + " started.");

        // Crear la descripción del servicio que ofrece este agente
        ServiceDescription sd = new ServiceDescription();
        sd.setType("classifier-service");
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
        addBehaviour(new ClassifierAgentBehaviour());

    }

    private class ClassifierAgentBehaviour extends Behaviour {

        private String bestTechnique = "";
        private double bestError = Double.MAX_VALUE;

        @Override
        public void action() {
            // Recibir el mensaje
            //System.out.println("Performing Action in ClassifierAgent");
            String response = "";
            String bestResultOptimization = "";
            String bestResultRegression = "";
            ACLMessage msgToOptimizationService = new ACLMessage(ACLMessage.INFORM);
            ACLMessage msgToRegressionService = new ACLMessage(ACLMessage.INFORM);
            String betas = "";

            ACLMessage msg = receive();
            if (msg != null) {
                //System.out.println("Message Recived in ClassifierAgent:" + msg.getContent());
                msgToOptimizationService.setContent(msg.getContent());
                DFAgentDescription[] resultOpt = Utils.searchAgent("search-optimization-services", myAgent);
                for (DFAgentDescription agent : resultOpt) {
                    msgToOptimizationService.addReceiver(agent.getName());
                }
                send(msgToOptimizationService);

                ACLMessage replyOpt = blockingReceive();
                if (replyOpt != null) {
                    //System.out.println("Response from the service search-optimization-services: --> " + replyOpt.getContent());
                    bestResultOptimization = replyOpt.getContent();
                }

                msgToRegressionService.setContent(msg.getContent());
                //System.out.println("msgToRegressionService ---->" + msgToRegressionService);
                DFAgentDescription[] resultReg = Utils.searchAgent("search-regression-services", myAgent);
                for (DFAgentDescription agent : resultReg) {
                    msgToRegressionService.addReceiver(agent.getName());
                }
                send(msgToRegressionService);

                ACLMessage replyReg = blockingReceive();
                if (replyReg != null) {
                    //System.out.println("Response from the service search-regression-services: --> " + replyReg.getContent());
                    bestResultRegression = replyReg.getContent();
                }

                ACLMessage replayToConsumer = msg.createReply();
                // Parcear los resultados de B0 & B1 y decidir cual es mejor.
                double meanOptimization = Utils.calculateMean(bestResultOptimization);
                double errorOpt = Utils.calculateR2(bestResultOptimization, meanOptimization);
                double meanRegression = Utils.calculateMean(bestResultRegression);
                double errorReg = Utils.calculateR2(bestResultRegression, meanRegression);
                if (errorOpt < errorReg) {
                    bestError = errorOpt;
                    bestTechnique = replyOpt.getContent().split(",")[2];
                    betas = replyOpt.getContent();
                } else {
                    bestError = errorReg;
                    bestTechnique = replyReg.getContent().split(",")[2];
                    betas = replyReg.getContent();
                }
                replayToConsumer.setContent(betas);
                send(replayToConsumer);

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
