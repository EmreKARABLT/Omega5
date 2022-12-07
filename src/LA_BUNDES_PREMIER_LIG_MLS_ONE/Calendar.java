package LA_BUNDES_PREMIER_LIG_MLS_ONE;

import PLAYER.*;

import java.util.ArrayList;

public class Calendar {

    public static ArrayList<ArrayList<Player>> getPermutation(Player[] whitePlayers, Player[] blackPlayers) {
        ArrayList<ArrayList<Player>> pairing = new ArrayList<>();

        for (int i = 0; i < whitePlayers.length; i++) {

            for (int j = 0; j < blackPlayers.length; j++) {

                if (i != j) {
                    ArrayList<Player> tmp = new ArrayList<>();
                    tmp.add(whitePlayers[i]);
                    tmp.add(blackPlayers[j]);
                    pairing.add(tmp);
                }
            }
        }
        return pairing;
    }
}
