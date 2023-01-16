package PLAYER.GA;
import GAME.Board;
import GAME.Cell;
import GAME.State;
import PLAYER.Player;
import PLAYER.RULE_BASED_BOT.RuleBasedBot;

import java.util.*;

public class GeneticAlgorithm {


    private final int POPULATION_SIZE = 100;
    private final int GENERATIONS = 10000;
    private final int BOARD_SIZE = 3;
    private final double MUTATION_RATE = 0.1;
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
            population = play(population);
            population = selection(population);
            population = mutation(population);
            population = HeapSort.sort(population);
            Random rn = new Random();
            int newFighter = rn.nextInt((int) (POPULATION_SIZE * 0.1));
            players.get(1).setW(population[0].getChromosome());
            if(population[0].getFitness() > 30) {
                System.out.println("GEN " + i);
                System.out.println(population[0]);
            }

        }
    }

    public Individuals[] play(Individuals[] population){

        State state = new State(new Board(3), players);
        for (int i = 0; i < population.length; i++) {
            state.getPlayers().get(1).setW(population[i].getChromosome());
            while(!state.isGameOver()){
                ArrayList<Cell> moves = state.getCurrentPlayer().getMoves(state);
                moves.get(0).setColor(0);
                moves.get(1).setColor(1);
                state.nextTurn();
                state.nextTurn();
            }
                state.getPlayers().get(0).setScore(state.getBoard().scoreOfAPlayer(0));
                state.getPlayers().get(1).setScore(state.getBoard().scoreOfAPlayer(1));
            population[i].setFitness(state.getPlayers().get(0).getScore(), state.getPlayers().get(1).getScore());
            state.restart();
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
        int crossOverPoint = (int) (Math.random() * jerry.getChromosome().length - 2) + 1;

        double[] j = jerry.getChromosome();
        double[] m = mary.getChromosome();
        double[] c1 = new double[j.length];
        double[] c2 = new double[j.length];
        for (int i = 0; i < j.length; i++) {
            if(i <= crossOverPoint ) {
                c1[i] = j[i];
                c2[i] = m[i];
            }else {
                c1[i] = m[i];
                c2[i] = j[i];
            }
        }

        jerry.setChromosome(c1);
        mary.setChromosome(c2);

        return new Individuals[] {jerry, mary};
    }

    public Individuals[] mutation(Individuals[] population){
        Random rn = new Random();
        for (int i = 0; i < population.length * MUTATION_RATE; i++) {
            int mutant_index = rn.nextInt(POPULATION_SIZE);
            population[mutant_index].setChromosome(generateChromosomes());
        }
        return population;
    }

    public Individuals[] generatePopulation(){
        Individuals[] population = new Individuals[POPULATION_SIZE];
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population[i] = new Individuals(generateChromosomes());
        }
        return population;
    }

    public double[] generateChromosomes(){
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
