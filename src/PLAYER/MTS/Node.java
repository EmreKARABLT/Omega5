package PLAYER.MTS;

import GAME.Board;
import GAME.Cell;
import GAME.State;
import PLAYER.RULE_BASED_BOT.Rules;

import java.util.ArrayList;
import java.util.Objects;

public class Node{

    private Node parent;
    private State state;
    private int playerID;
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
    private int votes = 0 ;
    private ArrayList<Integer> groupsOfWhite;
    private ArrayList<Integer> groupsOfBlack;
    private ArrayList<Double> data = new ArrayList<>();

    public Node(Node parent, State state, Cell white , Cell black){

        this.parent = parent;
        this.depth =  (this.parent != null)  ? this.parent.getDepth() + 1 : 0;
        this.playerID = (this.parent == null) ? state.getCurrentPlayer().getPlayerID() : (this.parent.getPlayerID()+1) % 2 ;

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
    public double ratioOfDiscovered(){
        double numberOfDiscovered = 0;
        for (Node child : this.children) {
            if(child.numberOfSimulations>0)
                numberOfDiscovered++;

        }

        return numberOfDiscovered/this.binomCoef(state.getBoard().getNumberOfEmptyCells()+2 , 2);
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
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
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

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

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
        return depth == node.depth && Objects.equals(parent, node.parent) && white.equals(node.white) && black.equals(node.black);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, white, black, depth);
    }

    public void concat(Node node){
        if (node.getBlack() == null) {
            this.numberOfSimulations+=node.numberOfSimulations;
            this.numberOfWins += node.numberOfWins;
            return;
        }
        if(node.getWhite().getId()==this.getWhite().getId() && node.getBlack().getId()==this.getBlack().getId()) {
            this.numberOfSimulations += node.numberOfSimulations;
            this.numberOfWins += node.numberOfWins;
            this.data.addAll(node.data);
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "numberOfSimulations=" + numberOfSimulations +
                ", numberOfWins=" + numberOfWins +
                '}';
    }
    public double getNaive(){
        if(numberOfSimulations == 0)
            return -1.0;
        return numberOfWins/numberOfSimulations;
    }
}

