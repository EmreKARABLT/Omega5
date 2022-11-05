package PLAYER.MTS;

import GAME.Cell;
import GAME.State;
import PLAYER.Player;

import java.util.ArrayList;

public class Tree {
    Node root;
    ArrayList<Node> nodes;

    public Tree(State state){
        root = new Node(null, state, null, null );
    }

    public void simulaton(Node node){

        Node simNode = node;

        while(simNode.terminalNode()){
            State nextState = simNode.getRandomState();
            Node nextSimNode = simNode;
            nextSimNode.setState(nextState);
            simNode.addChild(nextSimNode);
        }
        Player winner = simNode.getState().getWinner();

        int win = 0;

        if(winner.isBot()){
            win = 1;
        }

        simNode.setNumberOfWins(simNode.getNumberOfWins() + win);
        simNode.setNumberOfSimulations(simNode.getNumberOfSimulations() + 1);

        bacpropagation(node, simNode, win);
    }

    public void bacpropagation(Node node, Node simNode, int win){

        while (simNode.equals(node)){
            Node simNOde = simNode.getParent();
            simNode.setNumberOfWins(simNode.getNumberOfWins() + win);
            simNode.setNumberOfSimulations(simNode.getNumberOfSimulations() + 1);
        }
    }

}
