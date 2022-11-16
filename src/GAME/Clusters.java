package GAME;

import java.util.ArrayList;
import java.util.HashMap;

public class Clusters {

     ArrayList<Cell> cluster;
     HashMap<Cell, Boolean> contains = new HashMap<>();

     public Clusters(Cell root){
          cluster = new ArrayList<>();
          cluster.add(root);
     }

     public void add(Cell cell){
          cluster.add(cell);
          contains.put(cell, true);
     }

     public boolean contains(Cell cell){
          if(contains.get(cell) == null){return false;}
          return true;
     }

     public static void main(String[] args) {
          HashMap<Integer, Integer> a = new HashMap<>();

          a.put(1,1);
          a.put(1,2);
          System.out.println(a.size());
          System.out.println(a.get(1));
          System.out.println(a.get(2));
     }
}
