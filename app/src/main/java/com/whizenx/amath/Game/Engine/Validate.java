package com.whizenx.amath.Game.Engine;

import static com.whizenx.amath.Game.Logger.printLog;
import static com.whizenx.amath.Game.Logger.printResult;
import static com.whizenx.amath.Game.Logger.showMessage;
import static com.whizenx.amath.Game.Setting.getNum;
import static com.whizenx.amath.Game.Assets.Chip.getValueIV;
import static com.whizenx.amath.Game.isNumeric.isNumeric;

import android.app.Activity;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Validate {

    private static final ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");

    private List<String> list_op_state = new ArrayList<>();
    private List<String> list_op = new ArrayList<>();
    private String check_operation;

    private final Activity activity;
    private final HashMap<String, Integer> idMap;

    Validate(Activity activity, HashMap<String, Integer> idMap) {
        this.activity = activity;
        this.idMap = idMap;
    }

    void start() {
        HashMap<String, ?> dict = find_and_count();
        List<Integer> x_list = (List<Integer>) dict.get("x");
        List<Integer> y_list = (List<Integer>) dict.get("y");
        List<String> o_list = (List<String>) dict.get("operation");
        int count = (int) dict.get("count");

        if (o_list.contains("=")){
            int t = travel(x_list.get(0), y_list.get(0), new ArrayList<>());
            if (t == count){
                list_op_state = new ArrayList<>();
                list_op = new ArrayList<>();
                List<Boolean> list_eq = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    int x = x_list.get(i);
                    int y = y_list.get(i);
                    String index = x + "_" + y;
                    if (!list_op_state.contains(index)){
                        check_operation = o_list.get(i);
                        if (check_operation.equals("=") && !list_op.contains(index)){
                            list_op.add(index);
                        }
                        System.out.println("No: " + (list_eq.size()+1) + " | X: " + x + " | Y: " + y + " | Operation: " + check_operation);
                        List<String> up = direction(x, y, "up", 0, getNum()-1);
                        List<String> down = direction(x, y, "down", 0, getNum()-1);
                        Boolean up_down = check_eq(up, down, check_operation);
                        List<String> left = direction(x, y, "left", 0, getNum()-1);
                        List<String> right = direction(x, y, "right", 0, getNum()-1);
                        Boolean left_right = check_eq(left, right, check_operation);
                        printLog("UP&DOWN", up, down, check_operation, up_down);
                        printLog("LEFT&RIGHT", left, right, check_operation, left_right);
                        Boolean result;
                        if (up_down == null || left_right == null || emptyList(up, down, left, right)){
                            result = null;
                            System.out.println("> Error");
                        }
                        else if (!up_down || !left_right){
                            result = false;
                            System.out.println("> Not Correct");
                        }
                        else {
                            result = true;
                            System.out.println("> Correct");
                        }
                        list_eq.add(result);
                        System.out.println("---\n");
                    }
                }
                printResult(count, t, list_op, list_eq);
                if (list_eq.contains(null) || list_op.size() != t){
                    showMessage(activity, "Found error some equation", 1);
                    System.out.println("> Error");
                }
                else if (!list_eq.contains(false)) {
                    showMessage(activity, "Pass", 1);
                    System.out.println("> Pass");
                }
                else {
                    showMessage(activity, "NOT Pass", 1);
                    System.out.println("> NOT Pass");
                }
            } else {
                showMessage(activity, "Pattern equation in AMath not correct", 1);
                System.out.println("All chip: " + count);
                System.out.println("Count chip travel: " + t);
                System.out.println("> ERROR");
            }
        } else {
            showMessage(activity, "Can't find equation", 1);
            System.out.println("Can't find equation");
            System.out.println("> ERROR");
        }
    }

    static boolean emptyList(List<String> up, List<String> down, List<String> left, List<String> right) {
        return up.size() == 0 && down.size() == 0 && left.size() == 0 && right.size() == 0;
    }

    private HashMap<String, ?> find_and_count(){
        HashMap<String, Object> dict = new HashMap<>();
        List<Integer> x = new ArrayList<>();
        List<Integer> y = new ArrayList<>();
        List<String> o = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < getNum(); i++) {
            for (int j = 0; j < getNum(); j++) {
                String value = getValueIV(activity, idMap, j, i);
                if (value != null){
                    count++;
                    if (value.equals("=")){
                        x.add(0, j);
                        y.add(0, i);
                        o.add(0, value);
                    }else{
                        x.add(j);
                        y.add(i);
                        o.add(value);
                    }
                }
            }

        }
        dict.put("x", x);
        dict.put("y", y);
        dict.put("operation", o);
        dict.put("count", count);
        return dict;
    }
    private int travel(int x, int y, List<String> list){

        String value = getValueIV(activity, idMap, x, y);
        if ( x < 0 || x >= getNum() || y < 0 || y >= getNum() || value == null || list.contains(x+"_"+y)){
            return 0;
        }
        list.add(x+"_"+y);

        travel(x, y-1, list);
        travel(x, y+1, list);
        travel(x-1, y, list);
        travel(x+1, y, list);
        return list.size();
    }

    private List<String> direction(int x, int y, String direct, int min, int max){
        switch (direct) {
            case "up":
                return engine(direct, x, y, min, -1);
            case "down":
                return engine(direct, x, y, max, 1);
            case "left":
                return engine(direct, y, x, min, -1);
            case "right":
                return engine(direct, y, x, max, 1);
        }
        return new ArrayList<>();
    }

    private boolean around_coordinate(int x, int y){
        return (x - 1 < 0 || getValueIV(activity, idMap, x-1, y) == null ) && (x+1 >= getNum() || getValueIV(activity, idMap, x+1, y) == null) || (y-1 < 0 || getValueIV(activity, idMap, x, y-1) == null) && (y+1 >= getNum() || getValueIV(activity, idMap, x, y+1) == null);
    }

    private List<String> engine(String direct, int con, int start, int end, int increment){
        List<String> save_result = new ArrayList<>();
        start += increment;
        String result = null;
        String cur_index = null;
        while (start != end + increment){
            if (direct.equals("up") || direct.equals("down")){
                result = getValueIV(activity, idMap, con, start);
                cur_index = con+"_"+start;
            }
            else if (direct.equals("left") || direct.equals("right")) {
                result = getValueIV(activity, idMap, start, con);
                cur_index = start+"_"+con;
            }

            if (result == null) {
                break;
            }

            if (check_operation.equals("=")){
                if ((direct.equals("up") || direct.equals("down")) && around_coordinate(con, start) && !list_op_state.contains(cur_index)){
                    list_op_state.add(cur_index);
                }
                else if((direct.equals("left") || direct.equals("right")) && around_coordinate(start, con) && !list_op_state.contains(cur_index)){
                    list_op_state.add(cur_index);
                }
                if (!list_op.contains(cur_index)){
                    list_op.add(cur_index);
                }
            }
            save_result.add(result);
            start += increment;
        }
        if (increment == -1){
            Collections.reverse(save_result);
        }
        return save_result;
    }

    public static Double calculate(String eq_text){
        StringBuilder text = new StringBuilder(eq_text);
        if ((eq_text.equals("") || !isNumeric(String.valueOf(text.charAt(0))) && !String.valueOf(text.charAt(0)).equals("-")) ||
                (eq_text.length() > 1 &&
                        ((String.valueOf(text.charAt(0)).equals("-") && String.valueOf(text.charAt(1)).equals("0")) ||
                        (String.valueOf(text.charAt(0)).equals("0") && isNumeric(String.valueOf(text.charAt(1))))))) {
            return null;
        }

        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (!isNumeric(String.valueOf(text.charAt(i)))) {
                count = 0;
            } else {
                if (count >  2){
                    return null;
                }
                count += 1;
            }
            if (i < text.length()-1 && !isNumeric(String.valueOf(text.charAt(i))) && !isNumeric(String.valueOf(text.charAt(i+1)))) {
                return null;
            }

        }

        Double eq;
        try {
            eq = (Double) engine.eval(String.valueOf(text));
        } catch (Exception e) {
            return null;
        }
        return eq;
    }

    private Boolean check_eq(List<String> list_1, List<String> list_2, String operation){
        if (list_1.size() == 0 && list_2.size() == 0) {
            return true;
        }

        List<String> list_result = new ArrayList<>(list_1);
        list_result.add(operation);
        list_result.addAll(list_2);
        if (!list_result.contains("=") || list_result.get(0).equals("=") || list_result.get(list_result.size()-1).equals("=")) {
            return null;
        }

        String result = TextUtils.join("",list_result);

        List<Double> result_eq = new ArrayList<>();
        for (String item : result.split("=")) {
            result_eq.add(calculate(item));
        }
        if (result_eq.contains(null)){
            return null;
        }
        Set<Double> set = new HashSet<>(result_eq);
        return set.size() == 1;
    }
}