package PLAYER.MTS.SELECTION_HEURISTICS;

import PLAYER.MTS.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UCB1 extends Heuristics{

    public String name = "UCB1-Tuned";
    /**
     * This method is an Upper Confidence bound of Trees (UCT)
     * calculator.
     * It uses the following formula UCT = W/N + c * (log(T) / N)^0.5
     * The constant c = 2^0.5
     * If the number of simulations is 0 the UCT value will be infinity
     * because something divided by 0 = infinity
     * (In this case the value will be 2147483647)
     * @param node current node with wins, simulations and variance
     * @return the value UCT of the node
     */
    public double value(Node node) {
        double N = (double) node.getNumberOfSimulations();
        if (N == 0) {return Integer.MAX_VALUE;} // W/N = 2147483647
        double W = (double) node.getNumberOfWins();
        double c = 0.4;
        double T;
        if(node.getParent() == null){
            T = Math.E;
        }else {
            T = node.getParent().getNumberOfSimulations();
        }
        double variance = Variance.Variance(node.getData());

        /*
        try {
            T = (double) node.getParent().getNumberOfSimulations();
        }catch (Exception e){
            T = 1;

        }

         */
        return W/N + c * Math.sqrt((Math.log(T)/N) * Math.min(0.25, variance + ((2*Math.log(T))/N)));
    }

    public Node bestNode(Node node){
        ArrayList<Node> children = node.getChildren();
        return Collections.max(children, Comparator.comparing(c -> value(node)));
    }

    public Node worstNode(Node node){
        ArrayList<Node> children = node.getChildren();
        return Collections.min(children, Comparator.comparing(c -> value(node)));
    }

    public String getName() {
        return name;
    }
}

