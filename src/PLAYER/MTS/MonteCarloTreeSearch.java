package PLAYER.MTS;

import GAME.Cell;
import GAME.State;

import java.util.ArrayList;

public class MonteCarloTreeSearch {

    private final double SEARCH_TIME = 1.1;
    Tree tree;

    public MonteCarloTreeSearch(State state){
        State clonedState = null;
        try {
            clonedState = (State) (state.clone());
        }catch (Exception e){
            e.printStackTrace();
        }
        this.tree = new Tree(clonedState);
    }

    public ArrayList<Cell> bestMove(){

        ArrayList<Cell> move = new ArrayList<>();

        int a = 0;
        while (a < SEARCH_TIME){

            Node bestNode = tree.selection(tree.root);
            Node exploration = bestNode;
            tree.expansion(exploration);
            tree.simulation(exploration);
            a++;
        }

        Node winner = tree.selection(tree.root);
        tree.setRoot(winner);
        move.add(winner.getWhite());
        move.add(winner.getBlack());

        return move;
    }
}
