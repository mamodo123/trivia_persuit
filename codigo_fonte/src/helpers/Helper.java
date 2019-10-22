package helpers;

import java.util.HashMap;

public abstract class Helper {
    public static HashMap<Integer, Integer> agrup_array (int[] array){
        HashMap<Integer, Integer> return_array = new HashMap<>();
        for (int i : array) {
            Integer value = return_array.get(i);
            if (value == null) {
                value = 0;
            }
            return_array.put(i, value + 1);
        }
        return return_array;
    }
}
