package com.whizenx.amath.Game.UI;

import static com.whizenx.amath.Game.Setting.getNum;

import android.app.Activity;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Random;

public class Chip {

    static String[] types = new String[]{"0", "1", "2", "3", "4","5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "+" ,"+/-", "*", "/" ,"*//", "=" ,"blank"};

    public static String random_chip() {
        Random random = new Random();
        return types[random.nextInt(types.length)];
    }

    public static <T> void saveIVTag(ImageView iv, String key, T value) {
        HashMap<String, T> result = (HashMap<String, T>) iv.getTag();
        result.put(key, value);
        iv.setTag(result);
    }

    public static java.lang.Object getIVTag(ImageView iv, String key) {
        return ((HashMap<String, ?>) iv.getTag()).get(key);
    }

    public static String getValueIV(Activity activity,  HashMap<String, Integer> idMap, int x, int y) {
        if (x < 0 || x >= getNum() || y < 0 || y >= getNum()) {
            return null;
        }

        return (String) getIVTag(activity.findViewById(idMap.get("x" + x + "y" + y)), "value");
    }

    public static Integer getPointIV(Activity activity,  HashMap<String, Integer> idMap, int x, int y) {
        if (x < 0 || x >= getNum() || y < 0 || y >= getNum()) {
            return null;
        }

        return (Integer) getIVTag(activity.findViewById(idMap.get("x" + x + "y" + y)), "point");
    }
}
