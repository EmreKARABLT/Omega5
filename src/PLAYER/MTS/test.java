package PLAYER.MTS;

import java.util.ArrayList;

public class test {
    public static int binomCoef (int n , int k) {
        int mult = 1;
        for (int i = n-k+1; i <= n; i++) {
            mult*=i;
        }
        return mult;
    }
    public static void main(String[] args) {
        System.out.println(binomCoef(3,2));
    }
}
