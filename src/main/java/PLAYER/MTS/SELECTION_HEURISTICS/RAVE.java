package PLAYER.MTS.SELECTION_HEURISTICS;

import PLAYER.MTS.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RAVE extends Heuristics{
    public RAVE(){
        this.name = "RAVE";
    }

    public double value(Node node) {
        double K = node.getNumberOfSimulationsAMAF();

        double N = node.getNumberOfSimulations();
        double W = node.getNumberOfWins();
        double T = 1;
        double c = Math.sqrt(2);
        double WA = node.getNumberOfWinsAMAF();
        double NA = node.getNumberOfSimulationsAMAF();

        try {
            T = node.getParent().getNumberOfSimulations();

        }catch (Exception ignored){}

        if (N == 0) {return Integer.MAX_VALUE;} // W/N = 2147483647

        double Q = W/N + c * Math.sqrt((Math.log(T)/N));

        double QRAVE = WA/NA + c * Math.sqrt((Math.log(T)/N));

        double beta = Math.sqrt( K / (3 * N + K));
        System.out.println("BETA " + beta);
//        double beta = 0.33;
        return ((1-beta) * Q) + (beta * QRAVE);

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
