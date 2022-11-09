package PLAYER.RULE_BASED_BOT;

public class Functions {

    public static double FClusters(double x){
        return (Math.E) / ((x - Math.E) * (x - Math.E) - Math.log(x - 0.5) +1);
    }

    public static double FNClusters(double x, double variance){
        return ((x)) / ((x - Math.E) * (x - Math.E) + variance);
    }

    public static double FNeigbours(double x){
        return (Math.E * Math.E) / ((x - Math.E) * (x - Math.E) + Math.log(Math.pow(Math.E, x)));
    }


}
