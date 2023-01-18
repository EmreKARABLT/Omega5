
package PLAYER.MTS.SELECTION_HEURISTICS;

import GAME.HelloTensorFlow;
import PLAYER.MTS.Node;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UCB1NN extends Heuristics{
	public String name = "UCB1";
	public double value(int numberOfSimulationsParents, double numberOfWins, int numberOfSimulations, double variance) {
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

	public Node bestNode(Node node) {
		int visited = node.getNumberOfSimulations();
		ArrayList<Node> childrens = node.getChildren();
		double variance = variance(node);
		return (Node)Collections.max(childrens, Comparator.comparing((c) -> {
			return value(visited, c.getNumberOfWins(), c.getNumberOfSimulations(), variance) + c.getAnnPrediction();
		}));
	}

	public Node worstNode(Node node) {
		int visited = node.getNumberOfSimulations();

		ArrayList<Node> childrens = node.getChildren();
		double variance = variance(node);
		return (Node)Collections.min(childrens, Comparator.comparing((c) -> {
			return value(visited, c.getNumberOfWins(), c.getNumberOfSimulations(), variance)+ c.getAnnPrediction();
		}));
	}
	public String getName() {
		return name;
	}

	public double variance(Node node ){
		ArrayList<Double> data = node.getData();
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
