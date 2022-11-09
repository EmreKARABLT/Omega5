package PLAYER.GA;
import GAME.State;
import PLAYER.Player;
import PLAYER.RULE_BASED_BOT.RuleBasedBot;
import java.util.*;

public class GeneticAlgorithm {


    private final int POPULATION_SIZE = 100;
    private final int GENERATIONS = 10000;
    private final int BOARD_SIZE = 3;
    private final double MUTATION_RATE = 0.05;
    private final int ADN_SIZE = 5;

    private  Individuals[] population;
    private ArrayList<Player> players = new ArrayList<>(){};
    private final Player white = new RuleBasedBot("White") ;
    private final Player black = new RuleBasedBot("Black");


    public GeneticAlgorithm(){

        players.add(white);
        players.add(black);
        population = generatePopulation();
        generations(population);

    }

    public void generations(Individuals[] population){

        for (int i = 0; i < GENERATIONS; i++) {
            System.out.println("GEN " + i);
            population = play(population);
            population = selection(population);
            population = mutation(population);
            System.out.println(population[0].toString());
            Random rn = new Random();
            int newFighter = rn.nextInt(POPULATION_SIZE);
            players.get(1).setW(population[newFighter].getChromosome());
        }
    }

    public Individuals[] play(Individuals[] population){

        for (int i = 0; i < population.length; i++) {
            State state = new State(BOARD_SIZE, players);
            population[i].setFitness(state.getPlayers().get(0).getScore(), state.getPlayers().get(1).getScore());
        }
        HeapSort.sort(population);
        return population;
    }

    public Individuals[] selection(Individuals[] population){

        Random rn = new Random();

        for (int i = 0; i < population.length * 0.8; i++) {
            int i1 = rn.nextInt((int) (population.length * 0.7));
            int i2 = rn.nextInt((int) (population.length * 0.3));
            Individuals[] afterSex = crossover(population[i1], population[i2]);
            population[i1] = afterSex[0];
            population[i2] = afterSex[1];
        }

        return population;
    }

    public Individuals[] crossover(Individuals jerry, Individuals mary) {

        Random rn = new Random();
        int crossOverPoint = rn.nextInt(jerry.getChromosome().length);

        double[] t = jerry.getChromosome();
        double[] j = jerry.getChromosome();
        double[] m = jerry.getChromosome();

        for (int i = 0; i < crossOverPoint; i++) {
            j[i] = m[i];
            m[i] = t[i];
        }
        jerry.setChromosome(j);
        mary.setChromosome(m);

        return new Individuals[] {jerry, mary};
    }

    public Individuals[] mutation(Individuals[] population){
        Random rn = new Random();
        for (int i = 0; i < population.length * MUTATION_RATE; i++) {
            int mutant_index = rn.nextInt(POPULATION_SIZE);
            population[mutant_index].setChromosome(generateChomosomes());
        }
        return population;
    }

    public Individuals[] generatePopulation(){
        Individuals[] population = new Individuals[POPULATION_SIZE];
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population[i] = new Individuals(generateChomosomes());
        }
        return population;
    }

    public double[] generateChomosomes(){
        double[] weight = new double[ADN_SIZE];
        for (int i = 0; i < weight.length; i++) {
            weight[i] = Math.random();
        }
        return weight;
    }




    public static void main(String[] args) {

        GeneticAlgorithm a = new GeneticAlgorithm();
    }
}
