package PLAYER.MTS;

import GAME.Cell;
import GAME.State;

import java.util.ArrayList;

public class MonteCarloTreeSearch {

    private final double SEARCH_TIME = 100;
    Tree tree;

    public MonteCarloTreeSearch(State state){
        this.tree = new Tree(state);
    }

    public ArrayList<Cell> bestMove(){

        ArrayList<Cell> move = new ArrayList<>();

        int a = 0;
        while (a < SEARCH_TIME){

            Node bestNode = tree.selection(tree.root);
            Node exploration = bestNode;
            tree.expansion(exploration);
            tree.simulaton(exploration);
            a++;
        }

        Node winner = tree.selection(tree.root);
        tree.setRoot(winner);
        move.add(winner.getWhite());
        move.add(winner.getBlack());

        return move;
    }
}
