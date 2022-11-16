package PLAYER.MTS;

import GAME.Cell;
import GAME.State;
import PLAYER.MTS.SELECTION_HEURISTICS.UCB1;
import PLAYER.Player;
import PLAYER.RandomBot;

import java.util.ArrayList;
import java.util.Collections;

public class Tree {
    Node root;
    State state ;
    ArrayList<Node> nodes = new ArrayList<>() ;
    public int simNumber = 0;
    public static int counter = 0;
    public Player randomBot = new RandomBot("white");
    public Tree(State state){
        this.state = state;
        root = new Node(null, state, null, null );
    }


    public Node selection(Node node){
        Node selected ;
        int max = 300;

        if(node.getChildren().size() < Math.min(node.numberOfPossibleMoves() , max )){
            ArrayList<Cell> moves = randomBot.getMoves(node.getState());
            selected = node.addChild(new Node(node,state,moves.get(0),moves.get(1)));
            return selected;

        } else
            selected = getBest(node);

        //TODO implement heuristics for selection instead of random selection
        return selected;
    }

    public Node getBest(Node node){
        if(node.getChildren().size() == 0){
            return null;
        }
        node = UCB1.bestNodeUTCB(node);
        return node;
    }

    public Node getWorst(Node node){
        if(node.getChildren().size() == 0){
            return null;
        }
        node = UCB1.bestNodeUTCB(node);
        return node;
    }

    public void simulation(Node node){
        simNumber=counter++;
        Node tempRoot = node;
        int a = 0;
        while(!state.isGameOver() || a == 5){

            Node nextNode = selection(node);
            nextNode.color();
            nodes.add(nextNode);
            node =nextNode;
            a++;
        }
        node.getState().updatePlayerScores();
        //TODO centerOfMass of Clusters()
        Player winner = node.getState().getWinner();
        Player loser = node.getState().getLoser();
        int score = node.getScoreOfBlack();

        double win =  (winner==null || loser==null)? 0.5 : winner.getPlayerID();

        //int winRate = calculate a ratio of win - lose
        //add the ratio to the linkedlist of node that stores winners
        node.setNumberOfWins(node.getNumberOfWins() + win);
        node.setNumberOfSimulations(node.getNumberOfSimulations() + 1);
        node.add(win);

        backpropagation(tempRoot, node, win);
    }
    public void backpropagation(Node root, Node node, double win ){
        while (!node.equals(root) ){
            Node parent = node.getParent();

            parent.setNumberOfWins(parent.getNumberOfWins() + win );
            parent.setNumberOfSimulations(parent.getNumberOfSimulations() + 1);
            parent.add(win);
            node.uncolor();
            node = parent;
        }


    }

    public void setRoot(Node node){
        this.root = node;
        //TODO after assigning the root an existing node , we may want to set the pieces to null
    }
    public void setRoot(State state ,ArrayList<Cell> whites , ArrayList<Cell> blacks){
        int n = whites.size();
        for(Node child : root.getChildren()){
            if (n>=2 && child.getWhite().equals(whites.get(n-2)) && child.getBlack().equals(blacks.get(n-2))) {
                for (Node grandchild : child.getChildren()) {
                    if(grandchild.getWhite().equals(whites.get(n-1)) &&grandchild.getBlack().equals(blacks.get(n-1))) {
                        grandchild.color();
                        simNumber = 0;
                        counter = 0;
                        setRoot(grandchild);
                        return;
                    }
                }
            }
        }
        nodes = new ArrayList<>();
        simNumber = 0 ;
        counter = 0;
        this.state = state;
        root = new Node(null, state, null, null );

    }

}
