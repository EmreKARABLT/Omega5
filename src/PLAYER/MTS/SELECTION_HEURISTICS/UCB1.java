
package PLAYER.MTS.SELECTION_HEURISTICS;

import PLAYER.MTS.Node;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UCB1 {

    public static double UCB1(int numberOfSimulationsParents, double numberOfWins, int numberOfSimulations, double variance) {
        if (numberOfSimulations == 0) {
            return 2.147483647E9D;
        } else {
            double N = (double)numberOfSimulations;
            double c = 0.4D;
            double T = (double)numberOfSimulationsParents;
            double UCB1 = numberOfWins / N + c * Math.sqrt(Math.log(T) / N * Math.min(0.25D, variance + 2.0D * Math.log(T) / N));
            return UCB1;
        }
    }

    public static Node bestNodeUTCB(Node node) {
        int visited = node.getNumberOfSimulations();
        ArrayList<Node> childrens = node.getChildren();
        double variance = Variance.Variance(node.getData());
        return (Node)Collections.max(childrens, Comparator.comparing((c) -> {
            return UCB1(visited, c.getNumberOfWins(), c.getNumberOfSimulations(), variance);
        }));
    }

    public static Node worstNodeUTC(Node node) {
        int visited = node.getNumberOfSimulations();
        ArrayList<Node> childrens = node.getChildren();
        double variance = Variance.Variance(node.getData());
        return (Node)Collections.min(childrens, Comparator.comparing((c) -> {
            return UCB1(visited, c.getNumberOfWins(), c.getNumberOfSimulations(), variance);
        }));
    }
}
