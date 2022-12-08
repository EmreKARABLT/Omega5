
package PLAYER.MTS.SELECTION_HEURISTICS;

import PLAYER.MTS.Node;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UCB1 extends Heuristics{
    public String name = "UCB1";

    public double value(int numberOfSimulationsParents, double numberOfWins, int numberOfSimulations, double variance) {
        if (numberOfSimulations == 0) {
            return Integer.MAX_VALUE;
        } else {
            double N = (double)numberOfSimulations;
            double c = Math.sqrt(2);
            double T = (double)numberOfSimulationsParents;
            double UCB1 = numberOfWins / N + c * Math.sqrt(Math.log(T) / N * Math.min(0.25, variance + 2 * Math.log(T) / N));
            return UCB1;
        }
    }

    public Node bestNode(Node node) {
        int visited = node.getNumberOfSimulations();
        ArrayList<Node> childrens = node.getChildren();
        double variance = Variance.Variance(node.getData());
        return (Node)Collections.max(childrens, Comparator.comparing((c) -> {
            return value(visited, c.getNumberOfWins(), c.getNumberOfSimulations(), variance);
        }));
    }

    public Node worstNode(Node node) {
        int visited = node.getNumberOfSimulations();
        ArrayList<Node> childrens = node.getChildren();
        double variance = Variance(node.getData());
        return (Node)Collections.min(childrens, Comparator.comparing((c) -> {
            return value(visited, c.getNumberOfWins(), c.getNumberOfSimulations(), variance);
        }));
    }
    public String getName() {
        return name;
    }

    public double Variance(ArrayList<Double> data){
        double counter = 0;
        double counter2 = 0;
        for (int i = 0; i < data.size(); i++) {
            counter2 = counter2 + Math.pow(data.get(i), 2);
            if(data.get(i) == 1){
                counter++;
            }
        }
        double mean = Math.pow(counter / data.size(), 2);
        double variance = counter2/ data.size() - mean;
        return variance;
    }
}
