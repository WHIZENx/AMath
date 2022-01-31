package com.whizenx.amath.Game;

import android.app.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RandomId {

    public static int getRandomId(Activity activity, HashMap<String, Integer> idMap, String name) {

        boolean notInGeneralResources;
        boolean foundInIdMap = false;

        String packageName = activity.getPackageName();
        Random rnd = new Random();

        //Repeat loop in case random number already exists
        while (true) {

            // Step 1 - generate a random id
            int generated_id = rnd.nextInt(999999);

            // Step 2 - Check R.id
            try {
                name = activity.getResources().getResourceName(generated_id);
            } catch (Exception e) {
                name = null;
            }

            notInGeneralResources = name == null || !name.startsWith(packageName);

            // Step 3 - Check in id HashMap
            if (notInGeneralResources) {
                List<Integer> valueList = new ArrayList<>(idMap.values());

                foundInIdMap = false;
                for (Integer value : valueList) {
                    if (generated_id == value) {
                        foundInIdMap = true;
                        break;
                    }
                }
            }

            // Step 4 - Return ID, or loop again.
            if (!foundInIdMap) {
                return generated_id;
            }
        }
    }

}
