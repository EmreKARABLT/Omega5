package PLAYER.MTS.SELECTION_HEURISTICS;

import PLAYER.MTS.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UCB1{

    /**
     * This method is an Upper Confidence bound of Trees (UCT)
     * calculator.
     * It uses the following formula UCT = W/N + c * (log(T) / N)^0.5
     * The constant c = 2^0.5
     * If the number of simulations is 0 the UCT value will be infinity
     * because something divided by 0 = infinity
     * (In this case the value will be 2147483647)
     * @param numberOfSimulationsParents = N
     * @param numberOfWins = W
     * @param numberOfSimulations = T
     * @return the value UCT of the node
     */
    public static double UCB1(int numberOfSimulationsParents, double numberOfWins, int numberOfSimulations, double variance) {

        if (numberOfSimulations == 0) {return Integer.MAX_VALUE;} // W/N = 2147483647

        double W = (double) numberOfWins;
        double N = (double) numberOfSimulations;
        double c = 0.4;
        double T = (double) numberOfSimulationsParents;
        double UCB1 = W/N + c * Math.sqrt((Math.log(T)/N) * Math.min(0.25, variance + ((2*Math.log(T))/N)));

        return UCB1;
    }

    /**
     * We give the node that we want to evaluate,
     * from that node we get the children and we return the children
     * that has the best UTC value.
     * @param node Node that we are going to evaluate the children
     * @return Node with best UTC
     */
    public static Node bestNodeUTCB(Node node){
        int visited = node.getNumberOfSimulations();
        ArrayList<Node> childrens = node.getChildren();
        double variance = Variance.Variance(node.getData());
        return Collections.max(childrens, Comparator.comparing(c -> UCB1(visited, c.getNumberOfWins(), c.getNumberOfSimulations(), variance)));
    }
    public static Node worstNodeUTC(Node node){
        int visited = node.getNumberOfSimulations();
        ArrayList<Node> childrens = node.getChildren();
        double variance = Variance.Variance(node.getData());
        return Collections.min(childrens, Comparator.comparing(c -> UCB1(visited, c.getNumberOfWins(), c.getNumberOfSimulations(), variance)));
    }
}
