package PLAYER.GA;

import java.util.Arrays;

public class Individuals {

    private double[] chromosome;
    private Integer fitness;
    private String phenotype;


    public Individuals(double[] chromosome) {
        this.chromosome = chromosome;
        this.fitness = 0;
        this.phenotype = fromGenotypeToPhenotype();
    }

    public String fromGenotypeToPhenotype(){

        StringBuilder phenotype = new StringBuilder("{");

        for (int i = 0; i < chromosome.length - 1; i++) {
            phenotype.append(chromosome[i]);
            phenotype.append(", ");
        }
        phenotype.append(chromosome[chromosome.length-1] + "}");
        return phenotype.toString();
    }

    public double[] getChromosome() {
        return chromosome;
    }

    public void setChromosome(double[] chromosome) {
        this.chromosome = chromosome;
    }

    public Integer getFitness() {
        return fitness;
    }

    public void setFitness(Integer win, Integer lose) {
        if(lose == 0){
            lose = 1;
        }
        this.fitness = win / lose;
    }

    public String getPhenotype() {
        return phenotype;
    }

    public void setPhenotype(String phenotype) {
        this.phenotype = phenotype;
    }

    @Override
    public String toString() {
        return "Individuals{" +
                "fitness=" + fitness +
                ", phenotype='" + phenotype + '\'' +
                '}';
    }
}
