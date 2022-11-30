package PLAYER.MTS.SELECTION_HEURISTICS;

import PLAYER.MTS.Node;

public abstract class Heuristics {

    public String name;

    public double value(int numberOfSimulationsParents, double numberOfWins, int numberOfSimulations, double variance) {
        return 0;
    }
    public Node bestNode(Node node){return null;}

    public Node worstNode(Node node){return null;}

    public String getName() {return name;}
}
