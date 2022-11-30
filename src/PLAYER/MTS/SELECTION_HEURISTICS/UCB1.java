
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

    public static class ProbabilityNaive {

        /**
         * The policy or smnthg is basically the division betwen the wins and simulations
         * @param numberOfWins
         * @param numberOfSimulations
         * @return Best value of win rate
         */
        public static double PNaive(double numberOfWins, int numberOfSimulations) {

            if (numberOfSimulations == 0) {return Integer.MAX_VALUE;} // W/N = 2147483647
            double PNaive = (double)(numberOfWins/ numberOfSimulations);

            return PNaive;
        }

        /**
         * We give the node that we want to evaluate,
         * from that node we get the children and we return the children
         * that has the best PNaive value.
         * @param node Node that we are going to evaluate the children
         * @return Node with best PNaive
         */
        public static Node bestNodePNaive(Node node){
            ArrayList<Node> childrens = node.getChildren();
            return Collections.max(childrens, Comparator.comparing(c -> PNaive(c.getNumberOfWins(), c.getNumberOfSimulations())));
        }
    }
}
