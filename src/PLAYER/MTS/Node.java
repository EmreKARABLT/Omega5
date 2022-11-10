package PLAYER.MTS;

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
    private Node root;
    private State state;
    private final int playerID;
    private ArrayList<Node> children ;
    private Cell white ;
    private Cell black ;
    private int depth;
    private int numberOfSimulations;
    private double numberOfWins;
    private int numberOfChildren;
    private int myScore ;
    private int opponentsScore;
//    private ArrayList<Cell> emptyCells;

    public Node(Node parent,State state,Cell white , Cell black){
        this.parent = parent;
        this.depth =  (this.parent != null)  ? this.parent.getDepth() +1 : 0;
        this.playerID = (this.parent == null) ? 1 : ( ( this.parent.playerID + 1 ) % 2 );
        this.state = state;
        this.white = white;
        this.black = black;
        numberOfWins = 0;
        numberOfChildren = 0;
        numberOfSimulations = 0;
        children = new ArrayList<>();
        color();
            this.myScore = state.getBoard().scoreOfAPlayer(playerID);
            this.opponentsScore = state.getBoard().scoreOfAPlayer((playerID+1)%2);
        uncolor();

    }


    public double eval(){
        double[] w = {1, 1, 1, 1, 1};
        Cell myCell = (playerID==0) ? white : black;
        Cell opponents = (playerID==0) ? black : white;
        int incrementOfMyScore = (parent!=null) ? myScore - parent.opponentsScore : 0;
        int incrementOfOpponentsScore = (parent!=null) ? opponentsScore - parent.myScore : 0;

        double myScore=
                incrementOfMyScore;

        double opponentsScore =
                incrementOfOpponentsScore;

        return myScore - opponentsScore;
    }
    public boolean isRoot(){
        return this.equals(root) ;
    }

    public Node addChild(Node child){
        for (Node node: children) {
            if(node.equals(child)){
                return node;
            }
        }
        children.add(child);
        numberOfChildren = children.size();
        return child;
    }
    public void color(){
        if(white != null) {
            state.colorWhite(white);
            state.colorBlack(black);
        }
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



    public int getPlayerID() {
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
    public Node getRoot() {return root;}
    public void setRoot(Node root) {this.root = root;}
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
    public int getNumberOfChildren() {return numberOfChildren;}
    public void setNumberOfChildren(int numberOfChildren) {this.numberOfChildren = numberOfChildren;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return white.equals(node.white) && black.equals(node.black) && this.depth == node.depth;
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
        return "Node Depth " + depth +" / " + ((white==null)? "null" :white.getId()) + " - " + ((black==null)? "null" :black.getId()) ;
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

