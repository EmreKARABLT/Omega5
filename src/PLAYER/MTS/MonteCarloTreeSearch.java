package PLAYER.MTS;

import GAME.Cell;
import GAME.State;

import java.util.ArrayList;

public class MonteCarloTreeSearch {

    private final double SEARCH_TIME = 30000;
    Tree tree;
    public MonteCarloTreeSearch(State state){
        this.tree = new Tree(state);
    }

    public ArrayList<Cell> bestMove(State state){

        ArrayList<Cell> move = new ArrayList<>();
        this.tree = new Tree(state);
        double a = 0;
        while (a < SEARCH_TIME){
            double start = System.currentTimeMillis();
            Node currentNode = tree.root;
//            Node exploration = bestNode;
////            tree.expansion(exploration);
            tree.simulation(currentNode);
            double finish = System.currentTimeMillis();

            a += finish-start;
        }

        Node winner = tree.getBest(tree.root);
        System.out.println(tree.simNumber);
//        state.setBoard(tree.root.getState().getBoard());
//        tree.setRoot(winner);
        move.add(winner.getWhite());
        move.add(winner.getBlack());
//        move.add(state.getBoard().getCells().get(winner.getBlack()));
        return move;
    }
}
