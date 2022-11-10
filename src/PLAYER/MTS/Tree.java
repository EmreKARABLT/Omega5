package PLAYER.MTS;

import GAME.Cell;
import GAME.State;
import PLAYER.Player;
import PLAYER.RULE_BASED_BOT.RuleBasedBot;
import jdk.swing.interop.SwingInterOpUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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


    public Node selection(Node node,ArrayList<Node> best_N){
        Node selected ;
        if(node.getChildren().size() < best_N.size() ){
            int random = (int) (best_N.size() * Math.random());
            selected = node.addChild( best_N.get(random));
        }else {
            selected = getBest(node);
        }
            //TODO think about the way to update the score of each node according to playerID and looking for the best or worst
//            if(node.getPlayerID()==1)
//        if(!node.getChildren().isEmpty())
//            else
//                selected = getWorst(node);

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
            ArrayList<Node> best_n = best_N_Nodes(node,50);
            Node nextNode = selection(node,best_n);
            nextNode.color();


            //TODO we need to save players score to corresponding cell after each move in this way while backtracking we wont have to calculate it again and again
            node =nextNode;
        }
            node.getState().updatePlayerScores();
        //TODO we may need to calculate method after each move
        //TODO centerOfMass of Clusters()
        Player winner = node.getState().getWinner();
        Player loser = node.getState().getLoser();

        double win = 0;
        int winnerId = 0;

        if(winner == null || loser==null){
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
    public void backpropagation(Node root, Node node, double win , int winnerId){
        while (!node.equals(root) ){
            Node parent = node.getParent();
            parent.setNumberOfSimulations(parent.getNumberOfSimulations() + 1);
            //TODO Think and research about updating score for each parent node by taking playerId of the parent !!!!! It may effect to look for the best or the worst score !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            parent.setNumberOfWins(parent.getNumberOfWins() + Math.abs(1-win));

            node.uncolor();
            //TODO assign the scores according to saved scores
            node = parent;
        }

    }
    private ArrayList<Node> best_N_Nodes(Node parent , int maxChildrenSize){

        ArrayList<Cell> ec = state.getBoard().getEmptyCells();
        ArrayList<Node> allCombinations = new ArrayList<>();
        for(int i = 0 ; i < ec.size() ; i++){
            for (int j = i+1; j < ec.size(); j++) {
                allCombinations.add(new Node(parent, state, ec.get(i), ec.get(j)));
                allCombinations.add(new Node(parent, state, ec.get(j), ec.get(i)));
            }
        }
//        System.out.println(allCombinations);
        Collections.sort(allCombinations);
        ArrayList<Node> bestN = new ArrayList<>();
        for (int i = 0; i < Math.min(maxChildrenSize, allCombinations.size()); i++) {
            System.out.println(allCombinations.get(i).eval());
            bestN.add(allCombinations.get(i));
        }
        return bestN;
    }

    public void setRoot(Node node){
        this.root = node;
        //TODO after assigning the root an existing node , we may want to set the pieces to null
    }

}
