package PLAYER.MTS;

import GAME.Cell;
import GAME.State;
import PLAYER.HumanPlayer;
import PLAYER.Player;
import PLAYER.RandomBot;

import java.util.ArrayList;
import java.util.Objects;

public class Node {
    //TODO create the clone of the state all the time.

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
    private int noec;
//    private ArrayList<Cell> emptyCells;

    public Node(Node parent,State state,Cell white , Cell black){
        this.parent = parent;
        this.depth =  (this.parent != null)  ? this.parent.getDepth() +1 : 0;
        this.playerID = (this.parent == null) ? 1 : ( ( this.parent.playerID + 1 ) % 2 );
        this.state = state;
        this.white = white;
        this.black = black;
        this.noec = state.getBoard().getNumberOfEmptyCells();
        //TODO check if the root is updated during the backtracking !!! IMPORTANT
        //TODO maybe we can apply forward checking but what are constraints? Maybe it is the UCT values of each node ??? ( we need to know the intermediate steps)
        //TODO can we divide the problem to find optimal white and optimal black separately ( we may need to calculate score after each coloring)


        //FIXME remove copied state and apply setColor methods inside of when we visit them , and remove them when we visit the parent (DONE BUT THERE IS A BUG)
        //TODO 0.5 0 1 for draw lose win (DONE)

//        this.emptyCells = this.state.getBoard().getEmptyCells();
        numberOfWins = 0;
        numberOfChildren = 0;
        numberOfSimulations = 0;
        children = new ArrayList<>();
    }

    public int getNoec() {
        return noec;
    }

    public boolean isRoot(){
        return parent == null;
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
            this.noec = state.getBoard().getNumberOfEmptyCells();
        }
    }
    public void uncolor(){
        if(white != null) {
            state.uncolor(white);
            state.uncolor(black);
            this.noec = state.getBoard().getNumberOfEmptyCells();
        }
//        emptyCells = state.getBoard().getEmptyCells();

    }

    public Node getRandomChild(){
        if(children.size()==0)
            return null;
        return children.get((int)(Math.random()*children.size()));

    }

    public boolean terminalNode(){
        return state.isGameOver();
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


    public boolean duplicatedRandomMove(State state){

        for (Node child : children) {
            if (child.getState().equals(state)) {
                return false;
            }
        }
        return true;
    }
    //TODO fix all children
    public void allChildren(){

//        int desiredSize = 15;
//        //TODO fix Number of possible moves !!
//        int numberOfPossibleMoves = numberOfPossibleMoves();
////        System.out.println(numberOfPossibleMoves);
//
//        //TODO add if statement to create nodes in order if number of possible moves is smaller than possible states
//        if(numberOfPossibleMoves <= desiredSize ){
//            for (int i = 0; i < emptyCells.size(); i++) {
//                for (int j = i+1; j < emptyCells.size(); j++) {
//                    Cell c1= emptyCells.get(i);
//                    Cell c2= emptyCells.get(j);
//                    if(!this.doesContain(c1,c2))
//                        children.add(new Node(this,state,c1,c2));
//                    if(!this.doesContain(c2,c1))
//                        children.add(new Node(this,state,c2,c1));
//                }
//            }
//        }else {
//            while (children.size() < desiredSize) {
//                ArrayList<Cell> temp = state.getBoard().getEmptyCells();
//                //if(numberOfPossibleM
//                //getEmptyForWhite
//                //getEmptyForBlack
//                Cell cell_w = temp.get((int) (Math.random() * temp.size()));
//                temp.remove(cell_w);
//                Cell cell_b = temp.get((int) (Math.random() * temp.size()));
//                if (cell_w != cell_b && !this.doesContain(cell_w, cell_b))
//                    children.add(new Node(this, state, cell_b, cell_w));
//                else
//                    System.out.println(cell_w+" "+cell_b +" duplicated");
//            }
//        }
    }


    public double winningProbability(){
        if(numberOfSimulations > 0 )
            return numberOfWins/(double)numberOfSimulations;
        return 0 ;
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

    public boolean doesContain(Cell white ,Cell black) {
        if(this.children.size() == 0)
            return false;
        for (Node node :
                children) {
            if (Objects.equals(node.getWhite(), white) && Objects.equals(node.getBlack(),black)) {
                return true;
            }
        }
        return false;
    }
    public Node doContains(Cell white ,Cell black) {
        if(this.children.size() == 0)
            return null;
        for (Node node : children) {
            if (node.getWhite().getId()== white.getId() && node.getBlack().getId()==black.getId()) {
                return node;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return white.equals(node.white) && black.equals(node.black) && this.depth == node.depth;
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

