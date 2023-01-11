package PLAYER.MTS.ROOT_PARALLELIZATION;

import GAME.Cell;
import GAME.State;
import PLAYER.MTS.Node;
import PLAYER.MTS.SELECTION_HEURISTICS.Heuristics;
import PLAYER.MTS.TuplePieces;
import PLAYER.Player;
import PLAYER.RandomBot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

public class TreeParallel implements Runnable{
	private int numberOfNodesDiscovered = 0 ;
	private int numberOfLeafNodes = 0 ;
	Node root;
	private State state;
	private final Boolean ACTIONS = true;
	HashMap<TuplePieces, Node> Hash_AMAF = new HashMap<>();
	public static int simNumber = 0;
	public static int counter = 0;
	public Player randomBot = new RandomBot("white");
	public Heuristics heuristics ;
	public boolean isRootExpanded = false;
	public CountDownLatch latch ;

	public Node lastMove = null;

	public TreeParallel(CountDownLatch latch , State state, Heuristics heuristics){
		this.heuristics = heuristics;
		this.state = state;
		this.latch = latch;
		root = new Node(null, state, null, null );

	}


	public Node selection(Node node){
		Node selected ;
		int max = 300;



		if(node.getChildren().size() <= max){
			ArrayList<Cell> moves =  randomBot.getMoves(node.getState());
			selected = new Node(node, state,moves.get(0),moves.get(1));
			selected = node.addChild(selected);
			return selected;
		} else
			selected = getBest(node);

		return selected;
	}



	//    public void lookUp(Node node, double win){
//        Node exist = Hash_AMAF.get(new TuplePieces(node.getWhite(), node.getBlack()));
//        if (!(exist == null)){
//            if(root.getChildren().contains(exist)){
//                int index = root.getChildren().indexOf(exist);
//                root.getChildren().get(index).setNumberOfWinsAMAF(win);
//                root.getChildren().get(index).setNumberOfSimulationsAMAF(1);
//            }
//        }
//    }
	public void lookUp(Node node, double win){
		for (Node child :
				root.getChildren()) {
			if(child.getWhite().getId() == node.getWhite().getId() && child.getBlack().getId() == node.getBlack().getId() &&
					child.getDepth() != child.getDepth()){
				child.setNumberOfWinsAMAF(win);
				child.setNumberOfSimulationsAMAF(1);
			}
		}
	}

	public Node getBest(Node node){
		if(node.getChildren().size() == 0){
			return null;
		}
		node = this.heuristics.bestNode(node);
		return node;
	}

//    public Node getWorst(Node node){
//        if(node.getChildren().size() == 0){
//            return null;
//        }
//
//        return node;
//    }

	public void simulation(){
		Node node = root;
		simNumber=counter++;
		Node tempRoot = node;

		while(!state.isGameOver()){

			Node nextNode = selection(node);

			if(nextNode.getNumberOfSimulations() == 0 ) {
				numberOfNodesDiscovered++;
			}
			nextNode.color();
			node =nextNode;
		}
		if(node.getNumberOfSimulations() == 0)
			numberOfLeafNodes++;
		node.getState().updatePlayerScores();
		//TODO centerOfMass of Clusters()
		Player winner = node.getState().getWinner();

		double win =  0;

		if ( winner.getPlayerID() == root.getCurrentPlayersID())
			win = 1;

		node.setNumberOfWins(node.getNumberOfWins() + win);
		node.setNumberOfSimulations(node.getNumberOfSimulations() + 1);
		node.add(win);

		backpropagation(tempRoot, node, win);
	}
	public void backpropagation(Node root, Node node, double win){

		while (!node.equals(root) ){
			Node parent = node.getParent();
			lookUp(node,win);
			parent.setNumberOfWins(parent.getNumberOfWins() + win );
			parent.setNumberOfSimulations(parent.getNumberOfSimulations() + 1);
			parent.add(win);
			node.uncolor();
			node = parent;
		}
	}

	public void setRoot(Node node){
		this.root = node;
	}

	public void setRoot(State state, ArrayList<Cell> whites , ArrayList<Cell> blacks) {
		int n = whites.size();
		this.state = state;
		try {
			Node opponentsMove = new Node(lastMove, state, whites.get(n - 1), blacks.get(n - 1));
			opponentsMove = lastMove.addChild(opponentsMove);
			opponentsMove.color();
			setRoot(opponentsMove);
		}catch (Exception ignored){
			setRoot(new Node(null , this.state, null,null));
		}
	}
	public int getNumberOfNodesDiscovered() {
		return numberOfNodesDiscovered;
	}

	public int getNumberOfLeafNodes() {
		return numberOfLeafNodes;
	}
	public void setLastMove(Node lastMove) {
		this.lastMove = lastMove;
	}

	public HashMap<TuplePieces, Node> getHash_AMAF() {
		return Hash_AMAF;
	}

	public ArrayList<Node> getChildrenOfRoot(){
		return root.getChildren();
	}

	public void rootExpansion(){
		ArrayList<Cell> emptyCells = this.state.getBoard().getEmptyCells();

		for (int i = 0; i < emptyCells.size()-1; i++) {
			for (int j = i+1; j < emptyCells.size(); j++) {
				this.root.addChild(new Node(this.root, this.state, emptyCells.get(i), emptyCells.get(j)));
				this.root.addChild(new Node(this.root, this.state, emptyCells.get(j), emptyCells.get(i)));
			}
		}
	}

	@Override
	public void run() {

		double limit = MonteCarloRootParallelization.SEARCHTIME;
		double timePassed = 0 ;
		while ( timePassed < limit){
			double start = System.currentTimeMillis();

			simulation();
			double finish = System.currentTimeMillis();
			timePassed += finish-start;
//			timePasses += 1 ;
		}
		latch.countDown();

	}

}
