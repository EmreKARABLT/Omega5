package PLAYER.MTS.SELECTION_HEURISTICS;

import PLAYER.MTS.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Pnaive extends Heuristics {

    public String name = "P Naive";

    public double value(Node node) {
        double W = (double) node.getNumberOfWins();
        double N = (double) node.getNumberOfSimulations();
        if (N == 0) {return Integer.MAX_VALUE;}
        return  W/N;

    }

    public Node bestNode(Node node){
        int visited = node.getNumberOfSimulations();
        ArrayList<Node> children = node.getChildren();
        return Collections.max(children, Comparator.comparing(c -> value(node)));
    }

    public Node worstNode(Node node){
        int visited = node.getNumberOfSimulations();
        ArrayList<Node> children = node.getChildren();
        return Collections.min(children, Comparator.comparing(c -> value(node)));
    }
    public String getName() {
        return name;
    }
}
