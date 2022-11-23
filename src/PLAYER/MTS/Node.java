package PLAYER.MTS;

import GAME.Board;
import GAME.Cell;
import GAME.State;
import PLAYER.HumanPlayer;
import PLAYER.Player;
import PLAYER.RULE_BASED_BOT.Rules;
import PLAYER.RandomBot;

import java.util.ArrayList;
import java.util.Objects;

public class Node implements Comparable{
    //TODO store the score of players (white , black )

    private Node parent;
    private State state;
    private final int playerID;
    private ArrayList<Node> children ;
    private Cell white ;
    private Cell black ;
    private int depth;
    private int numberOfSimulations;
    private double numberOfWins;
    private int numberOfSimulationsAMAF;
    private double numberOfWinsAMAF;
    private int scoreOfWhite;
    private int scoreOfBlack;
    private ArrayList<Integer> groupsOfWhite;
    private ArrayList<Integer> groupsOfBlack;
    private ArrayList<Double> data = new ArrayList<>();

    public Node(Node parent,State state,Cell white , Cell black){
        this.parent = parent;
        this.depth =  (this.parent != null)  ? this.parent.getDepth() + 1 : 0;
        this.playerID = (this.parent == null) ? state.getCurrentPlayer().getPlayerID() : ( ( this.parent.playerID + 1 ) % 2 );

        this.state = state;
        this.white = white;
        this.black = black;
        numberOfSimulationsAMAF = 0;
        numberOfWinsAMAF = 0;
        numberOfWins = 0;
        numberOfSimulations = 0;
        children = new ArrayList<>();
//        updateScores();
    }

    public void updateScores(){
        groupsOfWhite = state.getBoard().getGroupForColor(0);
        state.getBoard().setAllCellsToNotVisited();
        groupsOfBlack = state.getBoard().getGroupForColor(1);
        state.getBoard().setAllCellsToNotVisited();
        this.scoreOfWhite = Board.multiplyTheGivenArrayList(groupsOfWhite);
        this.scoreOfBlack = Board.multiplyTheGivenArrayList(groupsOfBlack);
    }

    public double eval(){
        double[] w ={0.41939539486680855, 0.596762048047888, 0.1683721532546104, 0.2370089632926684, 0.2703059553712044};
        Cell myCell = (playerID == 0) ? white : black;
        Cell opponents = (playerID == 0) ? black : white;
        double myScore=
                w[2] * Rules.neigbourColors(myCell) +
                w[3] * Rules.radius(myCell) +
                w[4] * Rules.N_neibourgs(myCell);

        double opponentsScore =
                w[2] * Rules.neigbourColors(opponents) +
                w[3] * Rules.radius(opponents) +
                w[4] * Rules.N_neibourgs(opponents);
        return myScore - opponentsScore;
    }

    public Node addChild(Node child){
        for (Node node: children) {
            if(node.equals(child)){
                return node;
            }
        }
        children.add(child);
        return child;
    }
    public void color(){

        if(white != null) {
            this.depth = this.parent.getDepth() + 1 ;
            state.colorWhite(white);
            state.colorBlack(black);
        }
        //updateScores();
    }
    public void uncolor(){
        if(white != null) {
            state.uncolor(white);
            state.uncolor(black);
        }
    }

    public int numberOfPossibleMoves(){
        int n = this.state.getBoard().getNumberOfEmptyCells();
        int k = 2;
        return binomCoef(n,k);
    }

    public void add(double win){
        this.data.add(win);
    }
    public int binomCoef (int n , int k) {
        int mult = 1;
        if(n-k <= 0)
            return mult;
        for (int i = n-k+1; i <= n; i++) {
            mult*=i;
        }
        return mult;
    }



    public int getNumberOfSimulationsAMAF() {
        return numberOfSimulationsAMAF;
    }

    public void setNumberOfSimulationsAMAF(int numberOfSimulationsAMAF) {
        this.numberOfSimulationsAMAF += numberOfSimulationsAMAF;
    }

    public double getNumberOfWinsAMAF() {
        return numberOfWinsAMAF;
    }

    public void setNumberOfWinsAMAF(double numberOfWinsAMAF) {
        this.numberOfWinsAMAF += numberOfWinsAMAF;
    }


    public int getCurrentPlayersID() {
        return playerID;
    }

    public Node getParent() {
        return parent;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }


    public int getPlayerID() {
        return playerID;
    }

    public ArrayList<Double> getData() {
        return data;
    }

    public void setData(ArrayList<Double> data) {
        this.data = data;
    }

    public State getState() {return state;}
    public void setState(State state) {this.state = state;}
    public int getNumberOfSimulations() {return numberOfSimulations;}
    public void setNumberOfSimulations(int numberOfSimulations) {this.numberOfSimulations = numberOfSimulations;}
    public double getNumberOfWins() {return numberOfWins;}
    public void setNumberOfWins(double numberOfWins) {this.numberOfWins = numberOfWins;}
    public Cell getBlack() {return black;}
    public void setBlack(Cell black) {this.black = black;}
    public Cell getWhite() {return white;}
    public void setWhite(Cell white) {this.white = white;}
    public int getDepth() {return depth;}
    public void setDepth(int depth) {this.depth = depth;}

    public int getScoreOfWhite() {
        return scoreOfWhite;
    }

    public void setScoreOfWhite(int scoreOfWhite) {
        this.scoreOfWhite = scoreOfWhite;
    }

    public int getScoreOfBlack() {
        return scoreOfBlack;
    }

    public void setScoreOfBlack(int scoreOfBlack) {
        this.scoreOfBlack = scoreOfBlack;
    }

    public ArrayList<Integer> getGroupsOfWhite() {
        return groupsOfWhite;
    }

    public void setGroupsOfWhite(ArrayList<Integer> groupsOfWhite) {
        this.groupsOfWhite = groupsOfWhite;
    }

    public ArrayList<Integer> getGroupsOfBlack() {
        return groupsOfBlack;
    }

    public void setGroupsOfBlack(ArrayList<Integer> groupsOfBlack) {
        this.groupsOfBlack = groupsOfBlack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return white.equals(node.white) && black.equals(node.black) && this.parent.equals(node.parent);
    }
    @Override
    public int compareTo(Object o) {
        return (((Node)o).eval() > this.eval()) ? 1 : 0 ;
    }
    @Override
    public int hashCode() {
        return Objects.hash(parent, state, white, black, depth);
    }

    @Override
    public String toString() {
        return "Node Depth " + depth +"  ||  w:" + ((white==null)? "null" :white.getId()) + " - b :" + ((black==null) ? "null"  :black.getId()) ;
    }


    public static void main(String[] args) {
;
    }


}

