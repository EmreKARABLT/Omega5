package PLAYER.MTS;

import GAME.State;
import PLAYER.Player;

import java.util.ArrayList;

public class Tree {
    Node root;
    ArrayList<Node> nodes;

    public Tree(State state){
        root = new Node(null, state, null, null );
    }


    public Node selection(Node root){

        Node node = root;
        if(node.getChildren().size() == 0){
            expansion(node);
        }
        while(!(node.getChildren().size() == 0)){
            node = UCT.bestNodeUTC(node);
        }

        return node;
    }

    public void expansion(Node node){
        node.allChildrens();
    }

    public void simulaton(Node node){

        Node simNode = node;

        while(!simNode.terminalNode()){
            Node nextNode = simNode.getRandomChild();
            if(nextNode==null){
                expansion(simNode);
                nextNode = simNode.getRandomChild();
            }
            State nextState = nextNode.getState();
            nextNode.setState(nextState);
//            simNode.addChild(nextNode);
            simNode = nextNode;
        }
        Player winner = simNode.getState().getWinner();

        int win = 0;

        if(winner.isBot() && winner.getPlayerID() == 1){
            win = 1;
        }

        simNode.setNumberOfWins(simNode.getNumberOfWins() + win);
        simNode.setNumberOfSimulations(simNode.getNumberOfSimulations() + 1);

        backpropagation(node, simNode, win);
    }

    public void backpropagation(Node node, Node simNode, int win){
        while (!simNode.equals(node)){
            simNode.getWhite().setColor(-1);
            simNode.getBlack().setColor(-1);
            Node parent = simNode.getParent();
            parent.setNumberOfWins(simNode.getNumberOfWins() + win);
            parent.setNumberOfSimulations(simNode.getNumberOfSimulations() + 1);
            simNode = parent ;
        }
    }

    public void setRoot(Node node){
        this.root = node;
    }

}
