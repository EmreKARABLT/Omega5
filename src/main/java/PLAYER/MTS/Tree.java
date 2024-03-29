package PLAYER.MTS;

import GAME.Cell;
import GAME.HelloTensorFlow;
import GAME.State;
import PLAYER.MTS.SELECTION_HEURISTICS.Heuristics;
import PLAYER.Player;
import PLAYER.RandomBot;
import java.util.ArrayList;
import java.util.HashMap;

public class Tree {

    public int simNumber = 0;
    Node root;
    private State state;
    private final Boolean ACTIONS = true;
    HashMap<TuplePieces, Node> Hash_AMAF;
    public int counter = 0;
    public RandomBot randomBot = new RandomBot("white");
    public Heuristics heuristics ;
    public int treeID ;
    public HelloTensorFlow model;
    public Node lastMove = null;

    public Tree(State state, Heuristics heuristics, int treeID){
        this.heuristics = heuristics;
        this.state = state;
        root = new Node(null, state, null, null );
        this.treeID = treeID;
        root.setPlayerID(treeID);
        Hash_AMAF = new HashMap<>();
    }


    public Node selection(Node node){
        Node selected ;
        //TODO: find the ideal max
        int max = 500;
//        int max = node.getNumberOfSimulations() /300 + 5 ;
//        boolean unprune = node.getNumberOfSimulations()%300 == 0 ;
//        String name = (node.getPlayerID() == 0) ? "white" : "black";
//        System.out.print(name + " ->");

        if(node.getChildren().size() <= Math.min(node.numberOfPossibleMoves() , max )){
            ArrayList<Cell> moves = randomBot.randomMoves(node.getState());
            selected = new Node(node, state,moves.get(0),moves.get(1));
            selected = node.addChild(selected);

            return selected;

        } else if(node.getPlayerID()==treeID) {
            selected = getBest(node);
        }
        else{
            selected = getWorst(node);
        }

        return selected;

    }

    public void createTableAMAF(TuplePieces action, Node node){

    }

//    public void lookUp(Node node, double win){
//        Node exist = Hash_AMAF.get(new TuplePieces(node.getWhite(), node.getBlack()));
//        if (!(exist == null)){
//            if(root.getChildren().contains(exist)){
//                int index = root.getChildren().indexOf(exist);
//                root.getChildren().get(index).setNumberOfWinsAMAF(win);
//                root.getChildren().get(index).setNumberOfSimulationsAMAF(1);
//            }
//        }
//    }
    public void lookUp(Node node, double win){
        for (Node child :
                root.getChildren()) {
            if(child.getWhite().getId() == node.getWhite().getId() && child.getBlack().getId() == node.getBlack().getId() &&
                child.getDepth() != child.getDepth()){
                child.setNumberOfWinsAMAF(win);
                child.setNumberOfSimulationsAMAF(1);
            }
        }
    }

    public Node getBest(Node node){
        if(node.getChildren().size() == 0){
            return null;
        }
        node = this.heuristics.bestNode(node);
        return node;
    }
    public Node getWorst(Node node){
        if(node.getChildren().size() == 0){
            return null;
        }
        node = this.heuristics.worstNode(node);
        return node;
    }

    public void simulation(){

        Node tempRoot = root;
        Node node = root;

        simNumber=counter++;
        while(!state.isGameOver() ){
            Node nextNode = selection(node);
            nextNode.color();
            node =nextNode;
        }

        node.getState().updatePlayerScores();
        Player winner = node.getState().getWinner();

        double win =  0;
        if ( winner.getPlayerID() == treeID)
            win = 1;

        node.setNumberOfWins(node.getNumberOfWins() + win);
        node.setNumberOfSimulations(node.getNumberOfSimulations() + 1);
        node.add(win);

        backpropagation(tempRoot, node, win);
    }
    public void backpropagation(Node root, Node node, double win){

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
        this.root.setPlayerID(treeID);
        Hash_AMAF = new HashMap<>();
    }

    public void setRoot(State state, ArrayList<Cell> whites , ArrayList<Cell> blacks) {
        int n = whites.size();
        this.state = state;
        Node opponentsMove = new Node(null, state, whites.get(n - 1), blacks.get(n - 1));
        for (Node child : lastMove.getChildren()) {
            if (child.getWhite().getId() == opponentsMove.getWhite().getId() && child.getBlack().getId() == opponentsMove.getBlack().getId()) {
                opponentsMove = child;
                break;
            }
        }

        setRoot(opponentsMove);

    }
    public Node getRoot(){
        return this.root;
    }


    public void setLastMove(Node lastMove) {
        this.lastMove = lastMove;
    }

    public HelloTensorFlow getModel() {
        return model;
    }

    public void setModel(HelloTensorFlow model) {
        this.model = model;
    }
}
