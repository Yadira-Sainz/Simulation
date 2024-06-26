/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handson4_evolutionaryagentsforsystemsimulation;

/**
 *
 * @author yadira
 */
import java.util.ArrayList;

class Population {

    private ArrayList<Chromosome> chromosomes;
    private int size;

    public Population(int size, DataSet dataSet) {
        this.size = size;
        this.chromosomes = initializePopulation(size, dataSet);
    }

    private ArrayList<Chromosome> initializePopulation(int size, DataSet dataSet) {
        ArrayList<Chromosome> initialPopulation = new ArrayList<>();

        while (initialPopulation.size() < size) {
            double b0 = 1 + Math.random() * (200 - 1);
            double b1 = Math.random() * 50;
            //double b0 = 160 + Math.random() * 15; // Rango [160, 175]
            //double b1 = 18 + Math.random() * 10;  // Rango [18, 28]
            Chromosome chromosome = new Chromosome(b0, b1, dataSet);
            chromosome.calculateFitness(dataSet.getX(), dataSet.getY());
            if (chromosome.getFitness() >= 0.95 && b0 >= 167 && b1 >= 23) {
                initialPopulation.add(chromosome);
            }
        }

        return initialPopulation;
    }

    public void evaluatePopulation() {
        for (Chromosome chromosome : chromosomes) {
            chromosome.calculateFitness(chromosome.getDataSet().getX(), chromosome.getDataSet().getY());
        }

        chromosomes.sort((c1, c2) -> Double.compare(c2.getFitness(), c1.getFitness()));
    }

    public boolean isSolutionFound(double targetRSquared) {
        return chromosomes.get(0).getFitness() > targetRSquared;
    }

    public Chromosome getBestChromosome() {
        return chromosomes.get(0);
    }
}
