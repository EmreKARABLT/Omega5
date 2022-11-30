package LA_BUNDES_PREMIER_LIG_MLS_ONE;

import PLAYER.*;

import java.util.ArrayList;

public class Calendar {

    public static ArrayList<ArrayList<Player>> getPermutation(Player[] whitePlayers, Player[] blackPlayers) {
        ArrayList<ArrayList<Player>> pairing = new ArrayList<>();

        for (Player white : whitePlayers) {

            for (Player black : blackPlayers) {

                ArrayList<Player> tmp = new ArrayList<>();
                tmp.add(white);
                tmp.add(black);
                pairing.add(tmp);

            }
        }
        return pairing;
    }
}
