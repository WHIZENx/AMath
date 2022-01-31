package com.whizenx.amath.Game.Engine;

import static com.whizenx.amath.Game.Engine.Resources.paddingPiecePx;
import static com.whizenx.amath.Game.Engine.Resources.paddingSelectPiecePx;
import static com.whizenx.amath.Game.Engine.Resources.paddingSelectPx;
import static com.whizenx.amath.Game.Engine.Resources.paddingStrokePx;
import static com.whizenx.amath.Game.Engine.Resources.piece;
import static com.whizenx.amath.Game.Engine.Resources.piece_select;
import static com.whizenx.amath.Game.Setting.getSelectNum;
import static com.whizenx.amath.Game.Assets.Chip.getIV;
import static com.whizenx.amath.Game.Assets.Chip.getIVTag;
import static com.whizenx.amath.Game.Assets.Chip.saveIVTag;
import static com.whizenx.amath.Game.Assets.Interface.setChip;
import static com.whizenx.amath.Game.Assets.Interface.setChip_onTouch;
import static com.whizenx.amath.Game.Assets.Interface.setStatus;
import static com.whizenx.amath.Game.Assets.Interface.setStatus_onTouch;
import static com.whizenx.amath.Game.Assets.Table.getStatus;

import android.app.Activity;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.whizenx.amath.R;

import java.util.HashMap;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Motion {

    private int save_select = -1;
    private HashMap<String, Integer> save_state = new HashMap<>();
    private boolean selected = false;

    private final Activity activity;
    private final HashMap<String, Integer> idMap;

    public Motion(Activity activity, HashMap<String, Integer> idMap) {
        this.activity = activity;
        this.idMap = idMap;
    }

    void init() {
        View view = activity.findViewById(R.id.table);
        View view_select = activity.findViewById(R.id.select);

        tableOnTouch(view);
        selectOnTouch(view_select);

        submitOnTouch(activity.findViewById(idMap.get("submit")));
    }

    private void tableOnTouch(View obj) {
        obj.setOnTouchListener((view,motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                float x = motionEvent.getX();
                float y = motionEvent.getY();

                int x_table = (int) (x - paddingStrokePx) / (piece + paddingPiecePx);
                int y_table = (int) (y - paddingStrokePx) / (piece + paddingPiecePx);

                ImageView iv = getIV(activity, idMap, "x" + x_table + "y" + y_table);

                if ((Boolean) getIVTag(iv, "locked")) {
                    return false;
                }

                if (getIVTag(iv, "value") != null) {
                    if (getIVTag(iv, "index") != null) {
                        ImageView iv_select = getIV(activity, idMap, "select" + getIVTag(iv, "index"));
                        saveIVTag(iv_select, "used", false);
                        iv_select.setAlpha(1F);
                    }
                    setStatus(activity, iv, (String) getIVTag(iv, "type"));
                    saveIVTag(iv, "value", null);
                    saveIVTag(iv, "index", null);
                    return true;
                }

                if (save_state.isEmpty()) {
                    save_state.put("x", x_table);
                    save_state.put("y", y_table);
                    setStatus_onTouch(activity, iv, getStatus(y_table, x_table));
                    selected = true;
                    if (save_select != -1) {
                        ImageView iv_select = getIV(activity, idMap, "select" + save_select);
                        setChip(activity, iv_select, (String) getIVTag(iv_select, "value"));
                        save_select = -1;
                    }
                } else {
                    int xIndex = save_state.get("x");
                    int yIndex = save_state.get("y");
                    ImageView old_iv = getIV(activity, idMap, "x" + xIndex + "y" + yIndex);
                    setStatus(activity, old_iv, getStatus(yIndex, xIndex));
                    save_state = new HashMap<>();
                    selected = false;
                }
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                view.performClick();
            }
            return true;
        });
    }

    private void selectOnTouch(View obj) {
        obj.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                float x = motionEvent.getX();

                int x_select = (int) (x - paddingSelectPx) / (piece_select + paddingSelectPiecePx);

                if (x_select < 0 || x_select >= getSelectNum()){
                    return false;
                }

                ImageView iv = getIV(activity, idMap, "select" + x_select); // ปัจจุบัน
                if ((Boolean) getIVTag(iv, "used")) {
                    return false;
                }

                if (save_select == -1) {
                    save_select = x_select;
                    if(!selected) {
                        setChip_onTouch(activity, iv, (String) getIVTag(iv, "value"));
                    }
                } else {
                    ImageView iv_select =  getIV(activity, idMap, "select" + save_select); // ก่อน
                    String value = (String) getIVTag(iv, "value");
                    setChip(activity, iv, (String) getIVTag(iv_select, "value")); // แก้ตัวปัจจุบัน
                    setChip(activity, iv_select, value); // แก้ตัวก่อน
                    save_select = -1;
                }
                if (selected && !save_state.isEmpty()) {
                    ImageView old_iv = getIV(activity, idMap, "x" + save_state.get("x") + "y" + save_state.get("y"));
                    setChip(activity, old_iv, (String) getIVTag(iv, "value"));
                    saveIVTag(iv, "used", true);
                    saveIVTag(old_iv, "index", save_select);
                    save_state = new HashMap<>();
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

    private void submitOnTouch(Button btn) {
        Validate validate = new Validate(activity, idMap);
//        btn.setOnClickListener(view -> onButtonShowPopupWindowClick(activity, btn));
        btn.setOnClickListener(view -> validate.start());
    }
}
