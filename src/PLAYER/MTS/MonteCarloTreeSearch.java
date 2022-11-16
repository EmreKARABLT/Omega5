package PLAYER.MTS;

import GAME.Cell;
import GAME.State;
import PLAYER.MTS.SELECTION_HEURISTICS.UCT;

import java.util.ArrayList;

public class MonteCarloTreeSearch {

    private final double SEARCH_TIME = 20000;
    Tree tree;
    public MonteCarloTreeSearch(State state){
        this.tree = new Tree(state);

    }

    public ArrayList<Cell> bestMove(State state,ArrayList<Cell> whites ,ArrayList<Cell> blacks){
        ArrayList<Cell> move = new ArrayList<>();
        this.tree.setRoot(state,whites,blacks);

        double a = 0;

        while ( a < SEARCH_TIME){
            double start = System.currentTimeMillis();

            tree.simulation(tree.root);

            double finish = System.currentTimeMillis();
            a += finish-start;
        }

        Node winner = tree.getBest(tree.root);
        System.out.println("UCT : " + UCT.UCT(winner.getParent().getNumberOfSimulations(),winner.getNumberOfWins(),winner.getNumberOfSimulations()));
        System.out.println("wins : " +  winner.getNumberOfWins() + " sims: " + winner.getNumberOfSimulations());
        System.out.println(tree.simNumber);
        move.add(winner.getWhite());
        move.add(winner.getBlack());
        return move;
    }
}
