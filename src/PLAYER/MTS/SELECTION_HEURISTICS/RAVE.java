package PLAYER.MTS.SELECTION_HEURISTICS;

import PLAYER.MTS.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RAVE extends Heuristics{

    public String name = "RAVE";
    public final static double K = 1000;
    public Heuristics UCT = new UCT();

    public double value(Node node) {

        double N = node.getNumberOfSimulations();
        double W = node.getNumberOfWins();
        double T = 1;
        double c = Math.sqrt(2);
        double WA = node.getNumberOfWinsAMAF();
        double NA = node.getNumberOfSimulationsAMAF();

        try {
            T = node.getParent().getNumberOfSimulations();

        }catch (Exception e){
            T = 1;
        }
        if (N == 0) {return Integer.MAX_VALUE;} // W/N = 2147483647

        double QUCT = W/N + c * Math.sqrt((Math.log(T)/N));

        double Q = WA/NA + c * Math.sqrt((Math.log(T)/N));

        double beta = Math.sqrt(((K) / (3 * NA + K)));
        return ((1-beta) * QUCT) + (beta * Q);

    }

    public Node bestNode(Node node){
        ArrayList<Node> children = node.getChildren();
        return Collections.max(children, Comparator.comparing(c -> value(node)));
    }

    public Node worstNode(Node node){
        ArrayList<Node> children = node.getChildren();
        return Collections.min(children, Comparator.comparing(c -> value(node)));
    }



}
