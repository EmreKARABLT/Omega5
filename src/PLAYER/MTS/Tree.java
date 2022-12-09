package PLAYER.MTS;

import GAME.Cell;
import GAME.State;
import PLAYER.MTS.SELECTION_HEURISTICS.Heuristics;
import PLAYER.Player;
import PLAYER.RandomBot;
import java.util.ArrayList;
import java.util.HashMap;

public class Tree {
    private int numberOfNodesDiscovered = 0 ;
    private int numberOfLeafNodes = 0 ;
    Node root;
    private State state;
    private final Boolean ACTIONS = true;
    HashMap<TuplePieces, Node> Hash_AMAF;
    public int simNumber = 0;
    public static int counter = 0;
    public Player randomBot = new RandomBot("white");
    public Heuristics heuristics ;



    public Node lastMove = null;

    public Tree(State state, Heuristics heuristics){
        this.heuristics = heuristics;
        this.state = state;
        root = new Node(null, state, null, null );
        Hash_AMAF = new HashMap<>();
    }


    public Node selection(Node node){
        Node selected ;
        //TODO: find the ideal max
        int max = Integer.MAX_VALUE;

        if(node.getChildren().size() < Math.min(node.numberOfPossibleMoves() , max )){
            ArrayList<Cell> moves = randomBot.getMoves(node.getState());
            Node amafNode = new Node(node, state,moves.get(0),moves.get(1));
            TuplePieces action = new TuplePieces(moves.get(0),moves.get(1));
            selected = node.addChild(amafNode);
            createTableAMAF(action, amafNode);
            return selected;

        } else
            selected = getBest(node);

        return selected;
    }

    public void createTableAMAF(TuplePieces action, Node node){
        Hash_AMAF.put(action, node);
    }

    public void lookUp(Node node, double win){
        Node exist = Hash_AMAF.get(new TuplePieces(node.getWhite(), node.getBlack()));
        if (!(exist == null)){
            if(root.getChildren().contains(exist)){
                int index = root.getChildren().indexOf(exist);
                root.getChildren().get(index).setNumberOfWinsAMAF(win);
                root.getChildren().get(index).setNumberOfSimulationsAMAF(1);
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

        return node;
    }

    public void simulation(){
        Node node = root;
        simNumber=counter++;
        Node tempRoot = node;

        while(!state.isGameOver()){

            Node nextNode = selection(node);

            if(nextNode.getNumberOfSimulations() == 0 ) {
                numberOfNodesDiscovered++;
            }
            nextNode.color();
            node =nextNode;
        }
        if(node.getNumberOfSimulations() == 0)
            numberOfLeafNodes++;
        node.getState().updatePlayerScores();
        //TODO centerOfMass of Clusters()
        Player winner = node.getState().getWinner();

        double win =  0;

        if ( winner.getPlayerID() == root.getCurrentPlayersID())
            win = 1;

        node.setNumberOfWins(node.getNumberOfWins() + win);
        node.setNumberOfSimulations(node.getNumberOfSimulations() + 1);
        node.add(win);

        backpropagation(tempRoot, node, win);
    }
    public void backpropagation(Node root, Node node, double win){

        while (!node.equals(root) ){
            Node parent = node.getParent();
            lookUp(node, win);

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
    public void setRoot(State state, ArrayList<Cell> whites , ArrayList<Cell> blacks) {
        int n = whites.size();
        this.state = state;
        try {
            Node opponentsMove = new Node(lastMove, state, whites.get(n - 1), blacks.get(n - 1));
            opponentsMove = lastMove.addChild(opponentsMove);
            opponentsMove.color();
            setRoot(opponentsMove);
        }catch (Exception ignored){
            setRoot(new Node(null , this.state, null,null));
        }
    }
    public int getNumberOfNodesDiscovered() {
        return numberOfNodesDiscovered;
    }

    public int getNumberOfLeafNodes() {
        return numberOfLeafNodes;
    }
    public void setLastMove(Node lastMove) {
        this.lastMove = lastMove;
    }


}
