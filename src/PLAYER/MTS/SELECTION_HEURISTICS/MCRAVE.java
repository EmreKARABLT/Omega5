package PLAYER.MTS.SELECTION_HEURISTICS;

import PLAYER.MTS.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MCRAVE {

    public static double UCB1(int numberOfSimulationsParents, double numberOfWins, int numberOfSimulations, double variance) {

        if (numberOfSimulations == 0) {return Integer.MAX_VALUE;} // W/N = 2147483647

        double W = (double) numberOfWins;
        double N = (double) numberOfSimulations;
        double c = 0.4;
        double T = (double) numberOfSimulationsParents;
        double UCB1 = W/N + c * Math.sqrt((Math.log(T)/N) * Math.min(0.25, variance + ((2*Math.log(T))/N)));

        return UCB1;
    }

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
