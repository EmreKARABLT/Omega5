package LA_BUNDES_PREMIER_LIG_MLS_ONE;

import PLAYER.*;
import PLAYER.MTS.SELECTION_HEURISTICS.*;
import PLAYER.P_BOT.*;
import PLAYER.RULE_BASED_BOT.*;

import java.util.ArrayList;

public class Calendar {

    public static ArrayList<ArrayList<Player>> getPermutation(Player[] playerList) {
        ArrayList<ArrayList<Player>> result = new ArrayList<>();

        for (int i = 0; i < playerList.length; i++) {

            for (int j = 0; j < playerList.length; j++) {

                if (i != j) {
                    ArrayList<Player> tmp = new ArrayList<>();
                    tmp.add(playerList[i]);
                    tmp.add(playerList[j]);
                    result.add(tmp);
                }
            }
        }
        return result;
    }
}
