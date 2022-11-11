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
    private int scoreOfWhite;
    private int scoreOfBlack;
    private ArrayList<Integer> groupsOfWhite;
    private ArrayList<Integer> groupsOfBlack;

    public Node(Node parent,State state,Cell white , Cell black){
        this.parent = parent;
        this.depth =  (this.parent != null)  ? this.parent.getDepth() + 1 : 0;
        this.playerID = (this.parent == null) ? state.getCurrentPlayer().getPlayerID() : ( ( this.parent.playerID + 1 ) % 2 );
        this.state = state;
        this.white = white;
        this.black = black;
        numberOfWins = 0;
        numberOfSimulations = 0;
        children = new ArrayList<>();
        updateScores();
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
//        double [] w ={0.6659401716146303, 0.4816490601419914, 0.2750383637396092, 0.8453549473690194, 0.42878832082425156};
//        double[] w = {0.3221567417337733, 0.8007750919590251, 0.2123912612165917, 0.34145835593064766, 0.4302513217149678};
//        double[] w = {1, 1, 1, 1, 1};
        Cell myCell = (playerID == 0) ? white : black;
        Cell opponents = (playerID == 0) ? black : white;
        int incrementOfMyScore = (parent.playerID == 0) ? scoreOfWhite - parent.scoreOfWhite : scoreOfBlack - parent.scoreOfBlack ;
        int incrementOfOpponentsScore = (parent.playerID ==1) ? scoreOfWhite - parent.scoreOfWhite : scoreOfBlack - parent.scoreOfBlack;
        int myColorsScore = (parent.playerID == 0) ? scoreOfWhite : scoreOfBlack ;
        int opponentsColorsScore = (parent.playerID ==1) ? scoreOfWhite : scoreOfBlack ;
        double myScore=
                w[0] * Rules.clusters(myCell, playerID) +
                w[1] * Rules.Nclusters(this, playerID) +
                w[2] * Rules.neigbourColors(myCell) +
                w[3] * Rules.radius(myCell) +
                w[4] * Rules.N_neibourgs(myCell)
                +incrementOfMyScore/(double)myColorsScore * 100
                ;

        double opponentsScore =
                w[0] * Rules.clusters(opponents, (playerID +1)%2) +
                w[1] * Rules.Nclusters(this, (playerID +1)%2) +
                w[2] * Rules.neigbourColors(opponents) +
                w[3] * Rules.radius(opponents) +
                w[4] * Rules.N_neibourgs(opponents)
               +incrementOfOpponentsScore/(double)opponentsColorsScore * 100;
                ;
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
        updateScores();
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


    public int binomCoef (int n , int k) {
        int mult = 1;
        if(n-k <= 0)
            return mult;
        for (int i = n-k+1; i <= n; i++) {
            mult*=i;
        }
        return mult;
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

    public State getState() {return state;}
    public void setState(State state) {this.state = state;}
    public int getNumberOfSimulations() {return numberOfSimulations;}
    public void setNumberOfSimulations(int numberOfSimulations) {this.numberOfSimulations = numberOfSimulations;}
    public double getNumberOfWins() {return numberOfWins;}
    public void setNumberOfWins(double numberOfWins) {this.numberOfWins = numberOfWins;}
    public Cell getBlack() {return black;}
    public void setBlack(Cell black) {this.black = black;}
//    public ArrayList<Cell> getEmptyCells() {return emptyCells;}
//    public void setEmptyCells(ArrayList<Cell> emptyCells) {this.emptyCells = emptyCells;}
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
        return Double.compare(((Node)o).eval(), this.eval());
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
        ArrayList<Player> players = new ArrayList<>(){};
        players.add(new HumanPlayer("White") );
        players.add(new RandomBot("Black") );
        State state = new State(3 , players);

        Cell white = state.getBoard().getCells().get(0);
        Cell black = state.getBoard().getCells().get(1);
        Cell black2 = state.getBoard().getCells().get(2);
        Node node1 = new Node(null,state,white,black );
        Node node2 = new Node(null,state,white,black );
        System.out.println(node1.equals(node2));
    }


}

