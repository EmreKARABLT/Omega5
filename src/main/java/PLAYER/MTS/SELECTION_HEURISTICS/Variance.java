package PLAYER.MTS.SELECTION_HEURISTICS;

import java.util.ArrayList;

public class Variance {

    public static double Variance(ArrayList<Double> data){
        double counter = 0;
        double counter2 = 0;
        for (int i = 0; i < data.size(); i++) {
            counter2 = counter2 + Math.pow(data.get(i), 2);
            if(data.get(i) == 1){
                counter++;
            }
        }
        double mean = Math.pow(counter / data.size(), 2);
        double variance = counter2/ data.size() - mean;
        return variance;
    }


}
