package PLAYER.MTS;

import GAME.Cell;
import GAME.State;

import java.util.ArrayList;

public class MonteCarloTreeSearch {

    private final double SEARCH_TIME = 1000; // 5 seconds
    Tree tree;
    public MonteCarloTreeSearch(State state){
        this.tree = new Tree(state);
    }

    public ArrayList<Cell> bestMove(State state){

        ArrayList<Cell> move = new ArrayList<>();
        //TODO change this !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        this.tree = new Tree(state);
        double a = 0;
        while (a < SEARCH_TIME){
            double start = System.currentTimeMillis();
            Node bestNode = tree.selection(tree.root);
            Node exploration = bestNode;
            //tree.expansion(exploration);
            tree.simulation(exploration);
            double finish = System.currentTimeMillis();

            a += finish-start;
        }

        Node winner = tree.selection(tree.root);
        tree.setRoot(winner);
        move.add(state.getBoard().getCells().get(winner.getWhite()));
        move.add(state.getBoard().getCells().get(winner.getBlack()));
        System.out.println(winner.getWhite() + "  " + winner.getBlack());
        return move;
    }
}
