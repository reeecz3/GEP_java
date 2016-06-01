/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.softtechdesign.ga.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Mateusz
 */
public class EdgeColoring extends GAStringsSeq {

    public EdgeColoring(Integer size, String allowGenes, double mutation) throws GAException {
        super(size, //size of chromosome
                10000, //population has N chromosomes
                0.25, //crossover probability
                1, //random selection chance % (regardless of fitness)
                200, //max generations
                0, //num prelim runs (to build good breeding stock for final/full run)
                10000, //max generations per prelim run
                mutation, //chromosome mutation prob.
                0, //number of decimal places in chrom
                allowGenes.split(" "), //gene space (possible gene values)
                Crossover.ctTwoPoint, //crossover type
                true); //compute statisitics?
    }

    @Override
    protected double getFitness(int iChromIndex) {
        ChromStrings chromosome = (ChromStrings) this.getChromosome(iChromIndex);
        double fitness = 0;
        ArrayList list = new ArrayList();

        for (int EdgeNumber = 0; EdgeNumber < chromosomeDim; EdgeNumber++) {
            String Color = chromosome.getGene(EdgeNumber);
            if (!list.contains(Color)) {
                list.add(Color);
            }
//            
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
                        fitness += 1;
                        continue;
                    }
                }
            }
        }

//            if (blad) {
//                
//            }
        // Ilosc blednych kolorów - za duzo uzytych
        int ErrorColor = list.size() - LoadData.chromaticIndex;

        fitness += ErrorColor;

        return (1 / fitness);
    }

    public static void main(String[] args) {
        System.out.println("EdgeColoring GA...");
        try {
            LoadData data = new LoadData("dane/409.txt", false);
            double mutation;
            mutation = 0.8;
//            for (int x = 0; x < 8; x++) {
//                mutation += 0.1;
                EdgeColoring gaEdgeColoring = new EdgeColoring(data.getEdgeCount(), data.posible_genes, mutation);
                Thread watek = new Thread(gaEdgeColoring);
                watek.start();
//            }
        } catch (GAException gae) {
            System.out.println(gae.getMessage());
        }
    }

}
