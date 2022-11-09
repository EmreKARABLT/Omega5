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

        if(node.getChildren().size()>= Math.min(numberOfPossible,300)) {
            //TODO think about the way to update the score of each node according to playerID and looking for the best or worst
//            if(node.getPlayerID()==1)
                selected = getBest(node);
//            else
//                selected = getWorst(node);
            return selected;
        }

        //TODO implement heuristics for selection instead of random selection
        ArrayList<Cell> emptyCells = state.getBoard().getEmptyCells();
        Cell cell_white = emptyCells.get((int) (emptyCells.size() * Math.random()));
        Cell cell_black = emptyCells.get((int) (emptyCells.size() * Math.random()));
        while(cell_black.equals(cell_white))
            cell_black = emptyCells.get((int) (emptyCells.size() * Math.random()));


        selected = node.addChild(new Node(node,node.getState(),cell_white, cell_black));
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


            nextNode.getState().updatePlayerScores();
            //TODO we need to save players score to corresponding cell after each move in this way while backtracking we wont have to calculate it again and again
            node =nextNode;
        }
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
            if(winnerId == -1)
                parent.setNumberOfWins(parent.getNumberOfWins()+win);
            //TODO Think and research about updating score for each parent node by taking playerId of the parent !!!!! It may effect to look for the best or the worst score !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            else if(parent.getPlayerID()==winnerId)
                parent.setNumberOfWins(parent.getNumberOfWins() + win);

            node.uncolor();
            //TODO assign the scores according to saved scores
            node = parent;
        }

    }

    public void setRoot(Node node){
        this.root = node;
        //TODO after assigning the root an existing node , we may want to set the pieces to null
    }

}
