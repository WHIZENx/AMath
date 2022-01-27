package com.whizenx.amath.Game.Engine;

import static com.whizenx.amath.Game.Engine.initMotion.initMotionObj;
import static com.whizenx.amath.Game.Engine.initObj.initAllObj;

import android.app.Activity;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

public class Game {

    static HashMap<String, Integer> idMap = new HashMap<>();

    @RequiresApi(api = Build.VERSION_CODES.R)
    public static void start(Activity activity, int map) {

        HashMap<Integer, List<String>> table_map = new HashMap<>();

        try {
            Class<?> cls = Class.forName("com.whizenx.amath.Game.Map.Map"+map);
            Constructor<?> constructor = cls.getConstructor(HashMap.class);

            constructor.newInstance(table_map);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        initAllObj(activity, idMap, table_map);
        initMotionObj(activity, idMap);
    }
}
