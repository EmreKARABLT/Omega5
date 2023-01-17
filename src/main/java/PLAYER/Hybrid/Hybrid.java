package PLAYER.Hybrid;

import GAME.Cell;
import GAME.HelloTensorFlow;
import GAME.State;
import PLAYER.MTS.Node;
import PLAYER.MTS.SELECTION_HEURISTICS.Heuristics;
import PLAYER.MTS.SELECTION_HEURISTICS.RAVE;
import PLAYER.MTS.SELECTION_HEURISTICS.UCB1;
import PLAYER.MTS.SELECTION_HEURISTICS.UCT;
import PLAYER.MTS.Tree;
import PLAYER.Player;
import UI.Grid;

import javax.swing.*;
import java.util.ArrayList;

public class Hybrid extends Player{
	
	private final double SEARCHTIME = 100;
//	private final double MAX_SIMULATION = 10000;
	public static double winProb = 0;
	public HelloTensorFlow model ;
	public Hybrid(String playerName, Heuristics heuristics){
		this.heuristics = heuristics;
		this.playerName = playerName;
		this.heuristicName = heuristics.getName();
		if(playerName.contains("Black") || playerName.contains("black"))
			this.playerID = 1  ;
		else
			this.playerID = 0  ;

	}

	@Override
	public boolean isBot(){return true;};

	@Override
	public ArrayList<Cell> getMoves(State state){
		if(model == null ){
			model = new HelloTensorFlow("src/current_model");
		}
		this.tree = new TreeHybrid(state, this.heuristics,playerID);
		tree.setModel(model);

		double a = 0;
		double numberOfSim = 0;

		while ( a < SEARCHTIME){
			double start = System.currentTimeMillis();

			tree.simulation();
			numberOfSim++;
			double finish = System.currentTimeMillis();
			a += finish-start;
//			a++;
		}


		Node winner = tree.getBest(tree.getRoot());
		tree.setLastMove(winner);
		ArrayList<Cell> moves = new ArrayList<>();
//		for (Node n :tree.getRoot().getChildren()) {
//			System.out.printf("%.2f , " ,n.getNumberOfWins()/n.getNumberOfSimulations() );
//
//		}
//		System.out.println();
//		for(Node n:tree.getRoot().getChildren()){
//			n.color();
//			System.out.printf("%.2f , ",tree.getModel().predict(state.getBoard().getCells()));
//			n.uncolor();
//		}
//		System.out.println();
//		for(Node n:tree.getRoot().getChildren()){
//			System.out.printf("%d  , " ,n.getNumberOfSimulations());
//		}
//		System.out.println();

        System.out.println(winRateCalculator(winner));

		moves.add(winner.getWhite());
		moves.add(winner.getBlack());
		winner.getWhite().setColor(0);
		winner.getBlack().setColor(1);
		state.addWhite(winner.getWhite());
		state.addBlack(winner.getBlack());
		state.getPlayers().get(0).setScore(state.getBoard().scoreOfAPlayer(0));
		state.getPlayers().get(1).setScore(state.getBoard().scoreOfAPlayer(1));
		state.nextTurn();
		state.nextTurn();

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if(GUI!=null) {
					GUI.updateScores();
					GUI.updateColors();
					GUI.updateScores();
					GUI.updateColorBars();
					GUI.endFrame();
					GUI.repaint();
				}
			}
		});

		return moves;
	}


	public void reset(){
		this.score = 0 ;
		this.currentPiecesID = 0 ;
		this.tree = null;
	}

	public String winRateCalculator(Node winner){
		double rate = 0;
		rate = (winner.getNumberOfWins() /  winner.getNumberOfSimulations()) * 100;
		int decimals = 100000;
		double winRate = (double) Math.round(rate * decimals) / decimals;
		winProb = winRate;
		return  "\n" +
				"HYBRID = " + winner.getState().getCurrentPlayer().getPlayerName() + " // Win RATE = " + winRate
//                "Total Number Of Simulations = " + tree.getRoot().getNumberOfSimulations() + " \n"+
//				"Wins(U) : " +  winner.getNumberOfWins() + " sims: " + winner.getNumberOfSimulations() + " \n" +
//                "Win Rate (U)= " + winRate + " %" + "\n"
//				+ "Your chances of victory = " + (100 - winRate) + " %]"
				;
	}
}
