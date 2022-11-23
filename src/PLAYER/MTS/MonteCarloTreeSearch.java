package PLAYER.MTS;

import GAME.Cell;
import GAME.State;

import java.util.ArrayList;

public class MonteCarloTreeSearch {

    private final double SEARCH_TIME = 2000;
    Tree tree;
    public MonteCarloTreeSearch(State state){
        this.tree = new Tree(state);

    }

    public ArrayList<Cell> bestMove(State state){
        ArrayList<Cell> move = new ArrayList<>();
        this.tree.setRoot(state , state.getWhites(),state.getBlacks());

        double a = 0;

        while ( a < SEARCH_TIME){
            double start = System.currentTimeMillis();

            tree.simulation(tree.root);

            double finish = System.currentTimeMillis();
            a += finish-start;
        }

        Node winner = tree.getBest(tree.root);

        move.add(winner.getWhite());
        move.add(winner.getBlack());
        // System.out.println(winRateCalculator(winner));
        return move;
    }

    public String winRateCalculator(Node winner){
        double rate = 0;
        rate = (winner.getNumberOfWins() /  winner.getNumberOfSimulations()) * 100;
        int decimals = 100000;
        double winRate = (double) Math.round(rate * decimals) / decimals;

        return  "\n" +
                "[Player = " + winner.getState().getCurrentPlayer().getPlayerName() + " \n" +
                "Heuristic = " + winner.getState().getCurrentPlayer() + " \n" +
                "wins : " +  winner.getNumberOfWins() + " sims: " + winner.getNumberOfSimulations() + " \n" +
                "Win Rate = " + winRate + " %" + "\n" +
                "Your chances of victory = " + (100 - winRate) + " %]";

    }
}
