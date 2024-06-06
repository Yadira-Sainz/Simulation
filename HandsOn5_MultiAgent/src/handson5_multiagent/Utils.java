package handson5_multiagent;

import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.core.Agent;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author yadira
 */
public class Utils {

    public static DFAgentDescription[] searchAgent(String type, Agent a) {
        DFAgentDescription[] result = null;
        // Crear la descripción del servicio que buscamos
        ServiceDescription sd = new ServiceDescription();
        sd.setType(type);

        // Crear la descripción del agente que buscamos
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.addServices(sd);
        try {
            // Buscar todos los agentes que ofrecen el servicio "Classifier"
            result = DFService.search(a, dfd);
            // Añadir todos los agentes encontrados como receptores del mensaje

        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        return result;
    }

    // Coeficiente de determinación (R^2)
    public static double calculateR2(String content, double mean) {
        String[] pairs = content.split(";");
        double ssr = 0; //Es la suma de los cuadrados de las residuales (la diferencia entre los valores predichos y los reales)
        double sst = 0; //Es la suma total de los cuadrados (la diferencia entre los valores reales y su media)
        for (String pair : pairs) {
            String[] values = pair.split(",");
            double prediction = Double.parseDouble(values[0]);
            double real = Double.parseDouble(values[1]);
            ssr += Math.pow(prediction - real, 2);
            sst += Math.pow(real - mean, 2);
        }
        return 1 - (ssr / sst);
    }

    // Medida de los valores reales
    public static double calculateMean(String content) {
        String[] pairs = content.split(";");
        double sum = 0;
        for (String pair : pairs) {
            String[] values = pair.split(",");
            double real = Double.parseDouble(values[1]);
            sum += real;
        }
        return sum / pairs.length;
    }

}
