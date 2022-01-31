package com.whizenx.amath.Game.UI;

import static com.whizenx.amath.Game.RandomId.getRandomId;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.HashMap;

public class Object {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void createBgTableObj(ConstraintLayout obj, int width, int height, Drawable drawable, int paddingPx, int paddingStrokePx) {
        ConstraintLayout.LayoutParams layout1Params = new ConstraintLayout.LayoutParams(width + paddingStrokePx, height + paddingStrokePx);
        obj.setBackground(drawable);
        obj.setX(paddingPx);
        obj.setY(paddingPx);
        obj.setLayoutParams(layout1Params);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void createChip(ConstraintLayout obj, ImageView iv, int size, float x, float y) {
        LinearLayout.LayoutParams layoutIvParams = new LinearLayout.LayoutParams(size, size);
        iv.setLayoutParams(layoutIvParams);
        iv.setX(x);
        iv.setY(y);
        obj.addView(iv);
    }

    public static void createBtn(Activity activity, HashMap<String, Integer> idMap, String name, Button btn, int width, int height, String text, float x, float y) {
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(width, height);

        int generated_R_id = getRandomId(activity, idMap, name);
        idMap.put(name, generated_R_id);

        btn.setId(idMap.get(name));

        btn.setText(text);
        btn.setX(x);
        btn.setY(y);
        activity.addContentView(btn, layoutParams);
    }

    public static void createObj(ConstraintLayout obj, int width, int height, int color, float x, float y) {
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(width, height);
        obj.setBackgroundColor(color);
        obj.setX(x);
        obj.setY(y);
        obj.setLayoutParams(layoutParams);
    }

    public static void createBgObj(ConstraintLayout obj, int width, int height, Drawable drawable, float x, float y) {
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(width, height);
        obj.setBackground(drawable);
        obj.setX(x);
        obj.setY(y);
        obj.setLayoutParams(layoutParams);
    }

}
