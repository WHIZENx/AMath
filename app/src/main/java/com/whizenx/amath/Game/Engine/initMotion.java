package com.whizenx.amath.Game.Engine;

import static com.whizenx.amath.Game.Engine.Validate.start;
import static com.whizenx.amath.Game.Engine.initObj.paddingPiecePx;
import static com.whizenx.amath.Game.Engine.initObj.paddingSelectPiecePx;
import static com.whizenx.amath.Game.Engine.initObj.paddingSelectPx;
import static com.whizenx.amath.Game.Engine.initObj.paddingStrokePx;
import static com.whizenx.amath.Game.Engine.initObj.piece;
import static com.whizenx.amath.Game.Engine.initObj.piece_select;
import static com.whizenx.amath.Game.Popup.onButtonShowPopupWindowClick;
import static com.whizenx.amath.Game.Setting.getSelectNum;
import static com.whizenx.amath.Game.UI.Chip.getIVTag;
import static com.whizenx.amath.Game.UI.Chip.saveIVTag;
import static com.whizenx.amath.Game.UI.Interface.setChip;
import static com.whizenx.amath.Game.UI.Interface.setChip_onTouch;
import static com.whizenx.amath.Game.UI.Interface.setStatus;
import static com.whizenx.amath.Game.UI.Interface.setStatus_onTouch;
import static com.whizenx.amath.Game.UI.Table.getStatus;

import android.app.Activity;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.whizenx.amath.R;

import java.util.HashMap;

public class initMotion {

    static int save_select = -1;
    static String save_state = "";
    static boolean selected = false;

    @RequiresApi(api = Build.VERSION_CODES.R)
    public static void initMotionObj(Activity activity, HashMap<String, Integer> idMap) {
        View view = activity.findViewById(R.id.table);
        View view_select = activity.findViewById(R.id.select);

        tableOnTouch(activity, view, idMap);
        selectOnTouch(activity, view_select, idMap);

        submitOnTouch(activity, activity.findViewById(idMap.get("submit")), idMap);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private static void tableOnTouch(Activity activity, View obj, HashMap<String, Integer> idMap) {
        obj.setOnTouchListener((view,motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                float x = motionEvent.getX();
                float y = motionEvent.getY();

                int x_table = (int) (x - paddingStrokePx) / (piece + paddingPiecePx);
                int y_table = (int) (y - paddingStrokePx) / (piece + paddingPiecePx);

                ImageView iv = activity.findViewById(idMap.get("x" + x_table + "y" + y_table));

                if ((Boolean) getIVTag(iv, "locked")) {
                    return false;
                }

                if (getIVTag(iv, "index") != null) {
                    ImageView iv_select = activity.findViewById(idMap.get("select" + getIVTag(iv, "index")));
                    setStatus(activity, iv, (String) getIVTag(iv, "type"));
                    saveIVTag(iv_select, "used", false);
                    saveIVTag(iv, "value", null);
                    saveIVTag(iv, "index", null);
                    iv_select.setAlpha(1F);
                    return true;
                }

                if (save_state.equals("")) {
                    save_state = x_table + "_" + y_table;
                    setStatus_onTouch(activity, iv, getStatus(y_table, x_table));
                    selected = true;
                    if (save_select != -1) {
                        ImageView iv_select = activity.findViewById(idMap.get("select" + save_select));
                        setChip(activity, iv_select, (String) getIVTag(iv_select, "value"));
                        save_select = -1;
                    }
                } else {
                    int xIndex = Integer.parseInt(save_state.split("_")[0]);
                    int yIndex = Integer.parseInt(save_state.split("_")[1]);
                    ImageView old_iv = activity.findViewById(idMap.get("x" + xIndex + "y" + yIndex));
                    setStatus(activity, old_iv, getStatus(yIndex, xIndex));
                    save_state = "";
                    selected = false;
                }
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                view.performClick();
            }
            return true;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static void selectOnTouch(Activity activity, View obj, HashMap<String, Integer> idMap) {
        obj.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                float x = motionEvent.getX();

                int x_select = (int) (x - paddingSelectPx) / (piece_select + paddingSelectPiecePx);

                if (x_select < 0 || x_select >= getSelectNum()){
                    return false;
                }

                ImageView iv = activity.findViewById(idMap.get("select" + x_select)); // ปัจจุบัน
                if ((Boolean) getIVTag(iv, "used")) {
                    return false;
                }

                if (save_select == -1) {
                    save_select = x_select;
                    if(!selected) {
                        setChip_onTouch(activity, iv, (String) getIVTag(iv, "value"));
                    }
                } else {
                    ImageView iv_select = activity.findViewById(idMap.get("select" + save_select)); // ก่อน
                    String value = (String) getIVTag(iv, "value");
                    setChip(activity, iv, (String) getIVTag(iv_select, "value")); // แก้ตัวปัจจุบัน
                    setChip(activity, iv_select, value); // แก้ตัวก่อน
                    save_select = -1;
                }
                if (selected && !save_state.equals("")) {
                    ImageView old_iv = activity.findViewById(idMap.get("x" + save_state.split("_")[0] + "y" + save_state.split("_")[1]));
                    setChip(activity, old_iv, (String) getIVTag(iv, "value"));
                    saveIVTag(iv, "used", true);
                    saveIVTag(old_iv, "index", save_select);
                    save_state = "";
                    iv.setAlpha(0.5F);
                    selected = false;
                    save_select = -1;
                }
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                view.performClick();
            }
            return true;
        });
    }

    private static void submitOnTouch(Activity activity, Button btn, HashMap<String, Integer> idMap) {
//        btn.setOnClickListener(view -> onButtonShowPopupWindowClick(activity, btn));
        btn.setOnClickListener(view -> start(activity, idMap));
    }
}
