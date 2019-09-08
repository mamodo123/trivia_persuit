package helpers;

import java.util.HashMap;

public abstract class Helper {
    public static HashMap<Integer, Integer> agrup_array (int[] array){
        HashMap<Integer, Integer> return_array = new HashMap<>();
        for (int i : array) {
            return_array.put(i, return_array.getOrDefault(i, 0) + 1);
        }
        return return_array;
    }
}
