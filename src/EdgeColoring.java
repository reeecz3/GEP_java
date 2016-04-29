/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.softtechdesign.ga.*;

/**
 *
 * @author Mateusz
 */
public class EdgeColoring extends GAStringsSeq {

    public EdgeColoring(Integer size, String allowGenes) throws GAException {
        super(size, //size of chromosome
                200, //population has N chromosomes
                0.7, //crossover probability
                1, //random selection chance % (regardless of fitness)
                200, //max generations
                0, //num prelim runs (to build good breeding stock for final/full run)
                20, //max generations per prelim run
                0.9, //chromosome mutation prob.
                0, //number of decimal places in chrom
                allowGenes.split(" "), //gene space (possible gene values)
                Crossover.ctRoulette, //crossover type
                true); //compute statisitics?
    }

    @Override
    protected double getFitness(int iChromIndex) {
        ChromStrings chromosome = (ChromStrings) this.getChromosome(iChromIndex);
        double fitness = 0;

        for (int EdgeNumber = 0; EdgeNumber < chromosomeDim; EdgeNumber++) {

            String temp = "";
            int ve1 = 0;
            int ve2 = 0;

            String edgeColor = chromosome.getGene(EdgeNumber);

            ve1 = LoadData.getFirstVerticalsEdge(EdgeNumber + 1);
            ve2 = LoadData.getSecondVerticalsEdge(EdgeNumber + 1);
            temp = LoadData.getSasiednieKrawedzie(ve1, ve2);
            boolean blad = false;
            String[] krawedzie = temp.split(" ");
            for (int i = 0; i < krawedzie.length; i++) {
                int NumerKrawedzi = Integer.valueOf(krawedzie[i]);
                if (NumerKrawedzi != EdgeNumber + 1) {
                    String edgeColorCompare = chromosome.getGene(NumerKrawedzi - 1);
                    if (edgeColor == edgeColorCompare) {
                        blad = true;
                    }
                }
            }

            if (blad) {
                fitness += 1;
            }

        }

        return (1 / fitness);
    }

    public static void main(String[] args) {
        System.out.println("EdgeColoring GA...");
        try {
            LoadData data = new LoadData("dane/50.txt");
            EdgeColoring gaEdgeColoring = new EdgeColoring(data.getEdgeCount(), data.posible_genes);
            Thread watek = new Thread(gaEdgeColoring);
            watek.start();
        } catch (GAException gae) {
            System.out.println(gae.getMessage());
        }
    }

}
