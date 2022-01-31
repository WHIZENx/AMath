package com.whizenx.amath.Game.Map;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Map2 {

    public Map2(HashMap<Integer, List<String>> table_map, HashMap<Integer, String> select_chip) {
        table_map.put(0, Arrays.asList("2", "=", "2", "=", "2", "=", "2", "=", "2", "-", "2", "+", "2", "=", "2"));
        table_map.put(1, Arrays.asList("=", null, "+", null, null, null, "*", null, "*", null, null, null, null, null, "="));
        table_map.put(2, Arrays.asList("4", null, "2", null, null, null, "2", null, "12", null, "20", "=", "10", "*", "2"));
        table_map.put(3, Arrays.asList("-", "2", "=", "-", "2", null, "=", null, "*", null, "-", null, null, null, null));
        table_map.put(4, Arrays.asList("2", null, "4", null, null, null, "2", null, "3", null, "7", null, null, null, null));
        table_map.put(5, Arrays.asList("=", null, "-", null, null, null, "+", null, "/", null, "*", null, "3", null, null));
        table_map.put(6, Arrays.asList("4", null, "0", null, null, null, "2", null, "6", null, "2", null, "=", null, null));
        table_map.put(7, Arrays.asList("-", null, "*", null, null, null, "=", null, "=", null, "=", null, "-", null, null));
        table_map.put(8, Arrays.asList("2", null, "0", null, "3", "*", "4", "=", "12", "=", "6", "+", "6", null, null));
        table_map.put(9, Arrays.asList("*", null, null, null, null, null, "=", null, null, null, null, null, "+", null, null));
        table_map.put(10, Arrays.asList("2", null, null, null, null, null, "4", null, null, null, null, null, "9", null, null));
        table_map.put(11, Arrays.asList("+", null, null, null, null, null, "*", null, null, null, null, null, "*", null, null));
        table_map.put(12, Arrays.asList("2", null, null, null, "2", "=", "2", "/", "1", null, null, null, "1", null, null));
        table_map.put(13, Arrays.asList("-", null, null, null, null, null, "/", null, null, null, null, null, null, null, null));
        table_map.put(14, Arrays.asList("0", null, null, null, null, null, "2", null, null, null, null, null, null, null, null));

        select_chip.put(0, "=");
        select_chip.put(1, "=");
        select_chip.put(2, "=");
        select_chip.put(3, "=");
        select_chip.put(4, "=");
        select_chip.put(5, "=");
        select_chip.put(6, "=");
        select_chip.put(7, "=");
    }

}
