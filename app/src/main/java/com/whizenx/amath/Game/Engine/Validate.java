package com.whizenx.amath.Game.Engine;

import static com.whizenx.amath.Game.PrintAMath.printLog;
import static com.whizenx.amath.Game.Setting.getNum;
import static com.whizenx.amath.Game.UI.Chip.getValueIV;
import static com.whizenx.amath.Game.isNumeric.isNumeric;

import android.app.Activity;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Validate {

    static ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");

    static List<String> list_op_state = new ArrayList<>();
    static List<String> list_op = new ArrayList<>();
    static List<Boolean> list_eq = new ArrayList<>();
    static String check_operation;

    static void start(Activity activity, HashMap<String, Integer> idMap) {
        HashMap<String, ?> dict = find_and_count(activity, idMap);
        List<Integer> x_list = (List<Integer>) dict.get("x");
        List<Integer> y_list = (List<Integer>) dict.get("y");
        List<String> o = (List<String>) dict.get("operation");
        int count = (int) dict.get("count");

        if (o.contains("=")){
            Integer t = travel(activity, idMap, x_list.get(0), y_list.get(0), new ArrayList<>());
            if (t == count){
                list_op_state = new ArrayList<>();
                list_op = new ArrayList<>();
                list_eq = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    int x = x_list.get(i);
                    int y = y_list.get(i);
                    String index = x + "_" + y;
                    if (!list_op_state.contains(index)){
                        check_operation = o.get(i);
                        if (check_operation.equals("=") && !list_op.contains(index)){
                            list_op.add(index);
                        }
                        System.out.println("No: " + (list_eq.size()+1) + " | X: " + x + " | Y: " + y + " | Operation: " + check_operation);
                        List<String> up = direction(activity, idMap, x, y, "up", 0, 14);
                        List<String> down = direction(activity, idMap, x, y, "down", 0, 14);
                        Boolean up_down = check_eq(up, down, check_operation);
                        List<String> left = direction(activity, idMap, x, y, "left", 0, 14);
                        List<String> right = direction(activity, idMap, x, y, "right", 0, 14);
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
                        System.out.println("---");
                    }
                }
                System.out.println("All chip: " + count);
                System.out.println("Count chip travel: " + t + " Count chip operation: " + list_op.size());
                System.out.println("All equation: " + count + " All equation operate: " + list_eq.size());
                System.out.println("Correct: " + Collections.frequency(list_eq, true) + " Not Correct: " + Collections.frequency(list_eq, false) + " Error: " + Collections.frequency(list_eq, null));
                if (list_eq.contains(null) || list_op.size() != t){
                    System.out.println("> Error");
                }
                else if (!list_eq.contains(false)) {
                    System.out.println("> Pass");
                }
                else {
                    System.out.println("> NOT Pass");
                }
            } else {
                System.out.println("All chip: " + count);
                System.out.println("Count chip travel: " + t);
                System.out.println("> ERROR");
            }
        } else {
            System.out.println("Can't find equation");
            System.out.println("> ERROR");
        }
    }

    static boolean emptyList(List<String> up, List<String> down, List<String> left, List<String> right) {
        return up.size() == 0 && down.size() == 0 && left.size() == 0 && right.size() == 0;
    }

    static HashMap<String, ?> find_and_count(Activity activity, HashMap<String, Integer> idMap){
        HashMap<String, Object> dict = new HashMap<>();
        List<Integer> x = new ArrayList<>();
        List<Integer> y = new ArrayList<>();
        List<String> o = new ArrayList<>();
        dict.put("count", 0);
        for (int i = 0; i < getNum(); i++) {
            for (int j = 0; j < getNum(); j++) {
                String value = getValueIV(activity, idMap, j, i);
                if (value != null){
                    dict.put("count", (Integer) dict.get("count")+1);
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
        return dict;
    }
    static Integer travel(Activity activity, HashMap<String, Integer> idMap, int x, int y, List<String> list){

        String value = getValueIV(activity, idMap, x, y);
        if ( x < 0 || x > 14 || y < 0 || y > 14 || value == null || list.contains(x+"_"+y)){
            return null;
        }
        list.add(x+"_"+y);

        travel(activity, idMap, x, y-1, list);
        travel(activity, idMap, x, y+1, list);
        travel(activity, idMap, x-1, y, list);
        travel(activity, idMap, x+1, y, list);
        return list.size();
    }

    static List<String> direction(Activity activity, HashMap<String, Integer> idMap, int x, int y, String direct, int min, int max){
        if (direct.equals("up")){
            return engine(activity, idMap, direct, x, y, min, -1);
        }
        else if (direct.equals("down")){
            return engine(activity, idMap,direct, x, y, max, 1);
        }
        else if (direct.equals("left")){
            return engine(activity, idMap,direct, y, x, min, -1);
        }
        else if (direct.equals("right")){
            return engine(activity, idMap,direct, y, x, max, 1);
        }
        return null;
    }

    static boolean around_coordinate(Activity activity, HashMap<String, Integer> idMap, int x, int y){
        return (x - 1 < 0 || getValueIV(activity, idMap, x-1, y) == null ) && (x+1 > 14 || getValueIV(activity, idMap, x+1, y) == null) || (y-1 < 0 || getValueIV(activity, idMap, x, y-1) == null) && (y+1 > 14 || getValueIV(activity, idMap, x, y+1) == null);
    }

    static List<String> engine(Activity activity, HashMap<String, Integer> idMap, String direct, int con, int start, int end, int increment){
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
                if ((direct.equals("up") || direct.equals("down")) && around_coordinate(activity, idMap, con, start) && !list_op_state.contains(cur_index)){
                    list_op_state.add(cur_index);
                }
                else if((direct.equals("left") || direct.equals("right")) && around_coordinate(activity, idMap, start, con) && !list_op_state.contains(cur_index)){
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
            if (!isNumeric(String.valueOf(text.charAt(i)))){
                count = 0;
            }
            else{
                if (count >  2){
                    return null;
                }
                count += 1;
            }
            if (i < text.length()-1 && !isNumeric(String.valueOf(text.charAt(i))) && !isNumeric(String.valueOf(text.charAt(i+1)))){
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

    static Boolean check_eq(List<String> list_1, List<String> list_2, String operation){
        if (list_1.size() == 0 && list_2.size() == 0){
            return true;
        }

        List<String> list_result = new ArrayList<>();
        list_result.addAll(list_1);
        list_result.add(operation);
        list_result.addAll(list_2);
        String result = TextUtils.join("",list_result);
        if (!result.contains("=")){
            return null;
        }

        List<String> result_split = Arrays.asList(result.split("="));
        List<Double> result_eq = new ArrayList<>();
        for (String item : result_split) {
            result_eq.add(calculate(item));
        }
        if (result_eq.contains(null)){
            return null;
        }
        Set<Double> set = new HashSet<>(result_eq);
        return set.size() == 1;
    }
}