package PLAYER.MTS.SELECTION_HEURISTICS;

import PLAYER.MTS.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RAVE extends Heuristics {

    public final static double K = 1000;

    public static double RAVE(int numberOfSimulationsParents, double numberOfWins, int numberOfSimulations, double WA, double NA) {

        if (numberOfSimulations == 0) {return Integer.MAX_VALUE;} // W/N = 2147483647

        double QUCT = UCT.UCT(numberOfSimulationsParents, numberOfWins, numberOfSimulations);
        int n = (int) numberOfSimulations;
        double Q = UCT.UCT(numberOfSimulationsParents, WA, n);
        return (1-beta(NA) * QUCT + beta(NA) * Q);
    }

    public static double beta(double NS){

        return Math.sqrt((K /(NS*3+K)));
    }

    public static Node bestNodeRAVE(Node node){
        int visited = node.getNumberOfSimulations();
        ArrayList<Node> childrens = node.getChildren();
        return Collections.max(childrens, Comparator.comparing(c ->
                RAVE(
                        visited,
                        c.getNumberOfWins(),
                        c.getNumberOfSimulations(),
                        c.getNumberOfWinsAMAF(),
                        c.getNumberOfSimulationsAMAF()
                )));
    }
}
