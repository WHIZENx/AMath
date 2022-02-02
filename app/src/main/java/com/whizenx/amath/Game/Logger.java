package com.whizenx.amath.Game;

import static com.whizenx.amath.Game.Engine.Validate.calculate;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Logger {

    public static void printLog(String text, List<String> list_1, List<String> list_2, String operation, Boolean bool) {
        List<String> list_result = new ArrayList<>(list_1);
        list_result.add(operation);
        list_result.addAll(list_2);

        String result = TextUtils.join("",list_result);
        System.out.println(text + ": " + list_1 + "[" + operation + "]" + list_2 + " -> " +result);

        List<String> result_split = Arrays.asList(result.split("="));

        if (list_1.size() == 0 && list_2.size() == 0) {
            System.out.println("Equation: " + result_split + " -> Skip");
        } else if (list_result.get(0).equals("=") || list_result.get(list_result.size()-1).equals("=")) {
            System.out.println("Equation: " + result + " -> error");
        } else if (!result.contains("=")) {
            System.out.println("Equation: "+ result_split + " -> " + bool);
        } else {
            List<Double> result_eq = new ArrayList<>();
            for (String item : result_split) {
                result_eq.add(calculate(item));
            }
            System.out.println("Equation: " + result_split + " -> " + result_eq + " -> " + bool);
        }

    }

    public static void printResult(int count, int t, List<String> list_op, List<Boolean> list_eq) {
        System.out.println("All chip: " + count);
        System.out.println("Count chip travel: " + t + " Count chip operation: " + list_op.size());
        System.out.println("All equation: " + count + " All equation operate: " + list_eq.size());
        System.out.println("Correct: " + Collections.frequency(list_eq, true) + " Not Correct: " + Collections.frequency(list_eq, false) + " Error: " + Collections.frequency(list_eq, null));
    }

    public static void showMessage(Activity activity, String text, int duration) {
        // duration = 0 -> short
        // duration = 1 -> long
        Toast.makeText(activity, text, duration).show();
    }
}
