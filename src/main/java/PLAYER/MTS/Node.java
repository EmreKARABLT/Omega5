package main.java.PLAYER.MTS;

import main.java.GAME.Cell;
import main.java.GAME.State;
import main.java.PLAYER.HumanPlayer;
import main.java.PLAYER.Player;
import main.java.PLAYER.RandomBot;
import java.util.ArrayList;
import java.util.Objects;

public class Node {

    Node parent;
    Node root;
    State state;
    ArrayList<Node> children;
    Cell white ;
    Cell black ;
    int depth;
    int numberOfSimulations;
    int numberOfWins = 0;
    ArrayList<Cell> emptyCells;

    public Node(Node parent,State state,Cell white , Cell black){
        this.parent = parent;
        this.state = state;
        this.white = white;
        this.black = black;
        this.emptyCells = state.getBoard().getEmptyCells();
    }

    public Node addChild(Node child){
        for (Node node: children) {
            if( child.equals(node) )
                return node;
        }
        children.add(child);
        return child;
    }
    /*
    public void possibleStates(){

        for (int i = 0; i < emptyCells.size(); i++) {

            for (int j = i + 1; j < emptyCells.size(); j++) {

                Cell c1 = emptyCells.get(i);
                Cell c2 = emptyCells.get(j);
                c1.setColor(0);
                c2.setColor(1);

                children.add(new Node(this, state));
            }
        }
    }
    */

    public double winningProbability(){
        if(numberOfSimulations > 0 )
            return numberOfWins/(double)numberOfSimulations;
        return 0 ;
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

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public int getNumberOfSimulations() {
        return numberOfSimulations;
    }

    public void setNumberOfSimulations(int numberOfSimulations) {
        this.numberOfSimulations = numberOfSimulations;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public void setNumberOfWins(int numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return Objects.equals(state, node.state) && Objects.equals(white, node.white) && Objects.equals(black, node.black);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, state, white, black, depth);
    }


    /*
        public Node(State state, Node parent, Cell cellWhite, Cell cellBlack){
        this.state = state;
        this.parent = parent;
        this.white = cellWhite;
        this.black = cellBlack;
    }
    */
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

