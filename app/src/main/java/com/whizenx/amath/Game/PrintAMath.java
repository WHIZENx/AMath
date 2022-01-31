package com.whizenx.amath.Game;

import static com.whizenx.amath.Game.Engine.Validate.calculate;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrintAMath {

    public static void printLog(String text, List<String> list_1, List<String> list_2, String operation, Boolean bool) {
        List<String> list_result = new ArrayList<>();
        list_result.addAll(list_1);
        list_result.add(operation);
        list_result.addAll(list_2);
        String result = TextUtils.join("",list_result);
        System.out.println(text + ": " + list_1 + "['" + operation + "']" + list_2 + " -> " +result);

        List<String> result_split = Arrays.asList(result.split("="));

        if (list_1.size() == 0 && list_2.size() == 0){
            System.out.println("Equation: "+ result_split + " -> Skip");
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
}
