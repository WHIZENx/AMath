package com.whizenx.amath.Game.Engine;

import android.app.Activity;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.whizenx.amath.Game.Map.Map1;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Game {

    private final Activity activity;

    public Game(Activity activity) {
        this.activity = activity;
    }

    public void start(int map) {

        HashMap<String, Integer> idMap = new HashMap<>();
        HashMap<Integer, List<String>> table_map = new HashMap<>();
        HashMap<Integer, String> select_chip = new HashMap<>();

        try {
            Class<?> cls = Class.forName("com.whizenx.amath.Game.Map.Map"+map);
            Constructor<?> constructor = cls.getConstructor(HashMap.class, HashMap.class);

            constructor.newInstance(table_map, select_chip);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            new Map1(table_map, select_chip);
        }

        Resources resources = new Resources(activity, idMap, table_map, select_chip);
        Motion motion = new Motion(activity, idMap);

        resources.init();
        motion.init();
    }
}
