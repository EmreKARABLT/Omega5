package PLAYER.MTS;

import GAME.Cell;
import GAME.State;
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
        ArrayList<ArrayList<Node>> nodes = best_worst_Nodes(node, max);
        ArrayList<Node> bests = nodes.get(0);
        ArrayList<Node> worsts = nodes.get(1);
        ArrayList<Cell> moves = randomBot.getMoves(node.getState());
        if(node.getChildren().size() < max ){
            selected = node.addChild(new Node(node,state,moves.get(0),moves.get(1)));
            return selected;

        } else if(node.getCurrentPlayersID() == 1)
            selected = getBest(node);

        else
            selected = getWorst(node);

        //TODO implement heuristics for selection instead of random selection
        return selected;
    }

    public Node getBest(Node node){
        if(node.getChildren().size() == 0){
            return null;
        }
        node = UCT.bestNodeUTC(node);
        return node;
    }

    public Node getWorst(Node node){
        if(node.getChildren().size() == 0){
            return null;
        }
        node = UCT.worstNodeUTC(node);
        return node;
    }

    public void simulation(Node node){
        simNumber=counter++;
        Node tempRoot = node;
        while(!state.isGameOver()){

            Node nextNode = selection(node);
            nextNode.color();
            nodes.add(nextNode);
            node =nextNode;
        }
        node.getState().updatePlayerScores();
        //TODO centerOfMass of Clusters()
        Player winner = node.getState().getWinner();
        Player loser = node.getState().getLoser();

        double win =  (winner==null || loser==null)? 0.5 : winner.getPlayerID();

        node.setNumberOfWins(node.getNumberOfWins() + win);
        node.setNumberOfSimulations(node.getNumberOfSimulations() + 1);

        backpropagation(tempRoot, node, win);
    }
    public void backpropagation(Node root, Node node, double win ){
        while (!node.equals(root) ){
            Node parent = node.getParent();

            parent.setNumberOfWins(parent.getNumberOfWins() + win );
            parent.setNumberOfSimulations(parent.getNumberOfSimulations() + 1);
            node.uncolor();
            node = parent;
        }


    }

    private ArrayList<ArrayList<Node>> best_worst_Nodes(Node parent, int maxChildrenSize) {

        ArrayList<Cell> ec = state.getBoard().getEmptyCells();
        ArrayList<Node> allCombinations = new ArrayList<>();
        for (int i = 0; i < ec.size(); i++) {
            for (int j = i + 1; j < ec.size(); j++) {
                allCombinations.add(new Node(parent, state, ec.get(i), ec.get(j)));
                allCombinations.add(new Node(parent, state, ec.get(j), ec.get(i)));
            }
        }
        try {
            Collections.sort(allCombinations);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Node> bestN = new ArrayList<>();
        ArrayList<Node> worstN = new ArrayList<>();
        for (int i = 0; i < Math.min(maxChildrenSize, allCombinations.size()); i++) {
//            System.out.println(allCombinations.get(i).eval());
            bestN.add(allCombinations.get(i));
            worstN.add(allCombinations.get(allCombinations.size()-1-i));
        }
        ArrayList<ArrayList<Node>> result = new ArrayList<>();
        result.add(bestN);
        result.add(worstN);
        return result;
    }
    private ArrayList<Node> worstNodes(Node parent , int maxChildrenSize){

        ArrayList<Cell> ec = state.getBoard().getEmptyCells();
        ArrayList<Node> allCombinations = new ArrayList<>();
        for(int i = 0 ; i < ec.size() ; i++){
            for (int j = i+1; j < ec.size(); j++) {
                allCombinations.add(new Node(parent, state, ec.get(i), ec.get(j)));
                allCombinations.add(new Node(parent, state, ec.get(j), ec.get(i)));
            }
        }
        try {
            Collections.sort(allCombinations);
        }catch (Exception e){
            e.printStackTrace();
        }
            ArrayList<Node> bestN = new ArrayList<>();
        for (int i = 0; i < Math.min(maxChildrenSize, allCombinations.size()); i++) {
//            System.out.println(allCombinations.get(i).eval());
            bestN.add(allCombinations.get(i));
        }
        return allCombinations;
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
