package helpers;

import model.Player;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Helper {
    public static HashMap<Integer, ArrayList<Player>> agrup_array (ArrayList<Player> players){
        HashMap<Integer, ArrayList<Player>> return_array = new HashMap<>();
        for (Player i : players) {
            ArrayList<Player> list = return_array.get(i.getBoard_position());
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(i);
            return_array.put(i.getBoard_position(), list);
        }
        return return_array;
    }
}
