package PLAYER.MTS;

import GAME.Cell;
import GAME.State;
import PLAYER.Player;

import java.util.ArrayList;

public class Tree {
    Node root;
    ArrayList<Node> nodes;
    State state ;
    public int simNumber = 0;
    public static int counter = 0;
    public Tree(State state){
        this.state = state;
        root = new Node(null, state, null, null );
    }


    public Node selection(Node node){
        Node selected ;

        int numberOfPossible = node.numberOfPossibleMoves();
        if(node.getChildren().size()>= Math.min(numberOfPossible,500)) {
            if(node.getPlayerID()==1)
                selected = getBest(node);
            else
                selected = getWorst(node);
            return selected;
        }
        ArrayList<Cell> emptyCells = state.getBoard().getEmptyCells();
        Cell cell_white = emptyCells.get((int) (emptyCells.size() * Math.random()));
        Cell cell_black = emptyCells.get((int) (emptyCells.size() * Math.random()));
        while(cell_black.equals(cell_white))
            cell_black = emptyCells.get((int) (emptyCells.size() * Math.random()));


        selected = node.addChild(new Node(node,node.getState(),cell_white, cell_black));
        return selected;
    }

    public void expansion(Node node){
        node.allChildren();
    }
    public Node getBest(Node node){
        if(node.getChildren().size() == 0){
            return null;
        }
//        while(!(node.getChildren().size() == 0)){
//            node = UCT.bestNodeUTC(node);
//        }
        node = UCT.bestNodeUTC(node);
        return node;
    }
    public Node getWorst(Node node){
        if(node.getChildren().size() == 0){
            return null;
        }
//        while(!(node.getChildren().size() == 0)){
//            node = UCT.bestNodeUTC(node);
//        }
        node = UCT.worstNodeUTC(node);
        return node;
    }

    public void simulation(Node node){
        simNumber=counter++;
//        System.out.println(simNumber++);
        Node tempRoot = node;
        while(!state.isGameOver()){
            Node nextNode = selection(node);
            nextNode.color();
            node =nextNode;

        }
        node.getState().updatePlayerScores();

        Player winner = node.getState().getWinner();
        Player loser = node.getState().getLoser();

        double win = 0;
        int winnerId = 0;
        if(winner.getScore() == loser.getScore()){
            win = 0.5;
            winnerId = -1;
        }else if(winner.getPlayerID() == 1){
            win = 1;
            winnerId=1;
        }

        node.setNumberOfWins(node.getNumberOfWins() + win);
        node.setNumberOfSimulations(node.getNumberOfSimulations() + 1);

        backpropagation(tempRoot, node, win,winnerId);
    }
    //TODO fix this method
    public void backpropagation(Node root, Node node, double win , int winnerId){
        while (!node.isRoot()){
            Node parent = node.getParent();
            parent.setNumberOfSimulations(parent.getNumberOfSimulations() + 1);
            if(winnerId == -1)
                parent.setNumberOfWins(parent.getNumberOfWins()+win);
            else if(parent.getPlayerID()==winnerId)
                parent.setNumberOfWins(parent.getNumberOfWins() + win);

            node.uncolor();
            node = parent;
        }

    }

    public void setRoot(Node node){
        this.root = node;
    }

}
