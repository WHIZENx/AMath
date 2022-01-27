package com.whizenx.amath.Game.UI;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.R)
public class Table {

    static HashMap<Integer, HashMap<String, List<Integer>>> table = new HashMap<>();

    public static HashMap<Integer, HashMap<String, List<Integer>>> getTable() {
        return table;
    }

    public Table() {
        table = setTable();
    }

    static HashMap<Integer, HashMap<String, List<Integer>>> setTable() {
        HashMap<Integer, HashMap<String, List<Integer>>> initTable = new HashMap<>();

        // r, y, o, b, s
        initTable.put(0, setTableStatus(Arrays.asList(0, 7, 14), Arrays.asList(3, 11), Collections.emptyList(), Collections.emptyList(), Collections.emptyList()));
        initTable.put(1, setTableStatus(Collections.emptyList(), Arrays.asList(1, 13), Collections.emptyList(), Arrays.asList(5, 9), Collections.emptyList()));
        initTable.put(2, setTableStatus(Collections.emptyList(), Arrays.asList(2, 12), Arrays.asList(6, 8), Collections.emptyList(), Collections.emptyList()));
        initTable.put(3, setTableStatus(Collections.emptyList(), Arrays.asList(3, 11), Arrays.asList(0, 7, 14), Collections.emptyList(), Collections.emptyList()));
        initTable.put(4, setTableStatus(Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Arrays.asList(4, 10), Collections.emptyList()));
        initTable.put(5, setTableStatus(Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Arrays.asList(1, 5, 9, 13), Collections.emptyList()));
        initTable.put(6, setTableStatus(Collections.emptyList(), Arrays.asList(2, 6, 8, 12), Collections.emptyList(), Collections.emptyList(), Collections.emptyList()));
        initTable.put(7, setTableStatus(Arrays.asList(0, 14), Collections.emptyList(), Arrays.asList(3, 11), Collections.emptyList(), Collections.singletonList(7)));
        initTable.put(8, setTableStatus(Collections.emptyList(), Arrays.asList(2, 6, 8, 12), Collections.emptyList(), Collections.emptyList(), Collections.emptyList()));
        initTable.put(9, setTableStatus(Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Arrays.asList(1, 5, 9, 13), Collections.emptyList()));
        initTable.put(10, setTableStatus(Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Arrays.asList(4, 10), Collections.emptyList()));
        initTable.put(11, setTableStatus(Collections.emptyList(), Arrays.asList(3, 11), Arrays.asList(0, 7, 14), Collections.emptyList(), Collections.emptyList()));
        initTable.put(12, setTableStatus(Collections.emptyList(), Arrays.asList(2, 12), Arrays.asList(6, 8), Collections.emptyList(), Collections.emptyList()));
        initTable.put(13, setTableStatus(Collections.emptyList(), Arrays.asList(1, 13), Collections.emptyList(), Arrays.asList(5, 9), Collections.emptyList()));
        initTable.put(14, setTableStatus(Arrays.asList(0, 7, 14), Arrays.asList(3, 11), Collections.emptyList(), Collections.emptyList(), Collections.emptyList()));

        return initTable;
    }

    static HashMap<String, List<Integer>> setTableStatus(List<Integer> red, List<Integer> yellow, List<Integer> orange, List<Integer> blue, List<Integer> star) {
        HashMap<String, List<Integer>> table_row = new HashMap<>();
        table_row.put("r", red);
        table_row.put("y", yellow);
        table_row.put("o", orange);
        table_row.put("b", blue);
        table_row.put("s", star);

        return table_row;
    }

    public static String getStatus(int row, int index) {
        HashMap<String, List<Integer>> table_row = table.get(row);

        for(Map.Entry<String, List<Integer>> entry : table_row.entrySet()) {
            String key = entry.getKey();
            List<Integer> value = entry.getValue();

            if (value.contains(index)) {
                return key;
            }
        }

        return "blank";
    }
}
