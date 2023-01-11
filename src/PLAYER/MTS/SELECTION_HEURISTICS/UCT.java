package PLAYER.MTS.SELECTION_HEURISTICS;

import PLAYER.MTS.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UCT extends Heuristics{

    public String name = "UCT";

    /**
     * This method is an Upper Confidence bound of Trees (UCT)
     * calculator.
     * It uses the following formula UCT = W/N + c * (log(T) / N)^0.5
     * The constant c = 2^0.5
     * If the number of simulations is 0 the UCT value will be infinity
     * because something divided by 0 = infinity
     * (In this case the value will be 2147483647)
     * @param node the node with all the information of simulations and wins
     * @return the value UCT of the node
     */
    public double value(Node node) {

        double W = (double) node.getNumberOfWins();
        double N = (double) node.getNumberOfSimulations();
        double c = Math.sqrt(2);
        double T = 1;
        try {
            T = (double) node.getParent().getNumberOfSimulations();
        }catch (Exception e){
            T = 1.0;
        }
//        if (N == 0) {return Integer.MAX_VALUE;} // W/N = 2147483647
        return  W/N + c * Math.sqrt((Math.log(T)/N)); //UCT = W/N + c * (log(T) / N)^0.5

    }

    public Node bestNode(Node node){
        int visited = node.getNumberOfSimulations();
        ArrayList<Node> children = node.getChildren();
        return Collections.max(children, Comparator.comparing(this::value));
    }


    public Node worstNode(Node node){
        int visited = node.getNumberOfSimulations();
        ArrayList<Node> children = node.getChildren();
        return Collections.min(children, Comparator.comparing(this::value));
    }
    public String getName() {
        return name;
    }
}
