package PLAYER.MTS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ProbabilityNaive {

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
