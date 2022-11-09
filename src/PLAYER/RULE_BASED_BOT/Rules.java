package PLAYER.RULE_BASED_BOT;

import GAME.Cell;

import java.util.ArrayList;
import java.util.LinkedList;

public class Rules {


    public static double clusters(Cell cell, int color){

        double N = 1;

        LinkedList<Cell> queue = new LinkedList<>();
        queue.add(cell);
        cell.setVisited(true);
        while(!queue.isEmpty()){
            Cell currentCell = queue.pollLast();
            for (Cell neighborCell :
                    currentCell.getNeighbors()) {
                if(!neighborCell.isVisited() && neighborCell.getColor() == color){
                    neighborCell.setVisited(true);
                    queue.add(neighborCell);
                    N++;
                }
            }
        }

        return Functions.FClusters(N);
    }

    public static double Nclusters(Cell cell, int color, ArrayList<Cell> cells){

        ArrayList<Double> groups = new ArrayList<>();

        for(Cell startingCell : cells ){
            if(startingCell.getColor() == color){
                double numberOfPiecesConnectedToStartingCell = clusters(startingCell, color);

                if(numberOfPiecesConnectedToStartingCell > 0 ){ //to avoid 0 in multiplication we will have >0 or != 0 condition
                    groups.add(numberOfPiecesConnectedToStartingCell);
                }
            }
        }

        double mean = 0;
        double variance = 0;

        for (int i = 0; i < groups.size(); i++) {
            mean += groups.get(i);
            variance += groups.get(i) * groups.get(i);
        }
        mean = mean/groups.size();
        variance = variance/groups.size();
        variance = variance - mean * mean;
        if (variance < 0.1){
            variance = 0.1;
        }
        return Functions.FNClusters(mean, variance);
    }

    public static double neigbourColors(Cell cell){
        double counter = 0;

        for (int i = 0; i < cell.getNeighbors().size(); i++) {
            if(cell.getNeighbors().get(i).getColor() == 0){
                counter ++;
            }
        }
        return Functions.FNeigbours(counter);
    }

    public static double radius(Cell cell){
        return Math.max(Math.abs(cell.getQ()), Math.abs(cell.getR()));
    }

    public static double N_neibourgs(Cell cell){
        return cell.getNeighbors().size();
    }
}
