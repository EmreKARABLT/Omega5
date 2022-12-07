package PLAYER.MTS;

import GAME.Cell;
import GAME.State;
import PLAYER.MTS.SELECTION_HEURISTICS.Heuristics;
import PLAYER.Player;
import PLAYER.RandomBot;
import java.util.ArrayList;
import java.util.HashMap;

public class Tree implements Runnable {
    Node root;
    private State state;
    private final Boolean ACTIONS = true;
    ArrayList<Node> nodes = new ArrayList<>();
    HashMap<TuplePieces, Node> Hash_AMAF;
    public int simNumber = 0;
    public static int counter = 0;
    public Player randomBot = new RandomBot("white");
    public Heuristics heuristics ;


    public Tree(State state, Heuristics heuristics){
        this.heuristics = heuristics;
        this.state = state;
        root = new Node(null, state, null, null );
        Hash_AMAF = new HashMap<>();
    }


    public Node selection(Node node){
        Node selected ;
        //TODO: find the ideal max
        int max = 500 ;

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
            if(nextNode == null)
                System.out.println();
            nextNode.color();
            nodes.add(nextNode);
            node =nextNode;
        }
        node.getState().updatePlayerScores();
        //TODO centerOfMass of Clusters()
        Player winner = node.getState().getWinner();
        Player loser = node.getState().getLoser();

        double win =  0;

        if(winner == null){
            win = 0.5;
        }else if ( winner.getPlayerID() == root.getCurrentPlayersID())
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
    public void setRoot(State state, ArrayList<Cell> whites , ArrayList<Cell> blacks){
        int n = whites.size();
        for(Node child : root.getChildren()){
            if (n>2 && child.getWhite().equals(whites.get(n-2)) && child.getBlack().equals(blacks.get(n-2))) {
                for (Node grandchild : child.getChildren()) {
                    if(grandchild.getDepth()>=2 && grandchild.getWhite().equals(whites.get(n-1)) &&grandchild.getBlack().equals(blacks.get(n-1))) {
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

    @Override
    public void run() {
        simulation();
    }
}
