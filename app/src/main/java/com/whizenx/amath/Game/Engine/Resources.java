package com.whizenx.amath.Game.Engine;

import static com.whizenx.amath.Game.RandomId.getRandomId;
import static com.whizenx.amath.Game.Setting.getNum;
import static com.whizenx.amath.Game.Setting.getSelectNum;
import static com.whizenx.amath.Game.UI.Interface.setChip;
import static com.whizenx.amath.Game.UI.Interface.setStatus;
import static com.whizenx.amath.Game.UI.Obj.createBgObj;
import static com.whizenx.amath.Game.UI.Obj.createBgTableObj;
import static com.whizenx.amath.Game.UI.Obj.createBtn;
import static com.whizenx.amath.Game.UI.Obj.createChip;
import static com.whizenx.amath.Game.UI.Obj.createObj;
import static com.whizenx.amath.Game.UI.Option.getPaddingDp;
import static com.whizenx.amath.Game.UI.Option.getPaddingPieceDp;
import static com.whizenx.amath.Game.UI.Option.getPaddingSelectDp;
import static com.whizenx.amath.Game.UI.Option.getPaddingSelectPieceDp;
import static com.whizenx.amath.Game.UI.Option.getPaddingStrokeDp;
import static com.whizenx.amath.Game.UI.Table.getStatus;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.whizenx.amath.Game.UI.Table;
import com.whizenx.amath.R;

import java.util.HashMap;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Resources {

    static int paddingPx, paddingPiecePx, paddingStrokePx, paddingSelectPx, paddingSelectPiecePx, piece, piece_select;
    private final Activity activity;
    private final HashMap<String, Integer> idMap;
    private final HashMap<Integer, List<String>> table_map;
    private final HashMap<Integer, String> select_chip;

    Resources(Activity activity, HashMap<String, Integer> idMap, HashMap<Integer, List<String>> table_map, HashMap<Integer, String> select_chip) {
        this.activity = activity;
        this.idMap = idMap;
        this.table_map = table_map;
        this.select_chip = select_chip;
        initAllObj();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initAllObj() {

        ConstraintLayout table = activity.findViewById(R.id.table);
        ConstraintLayout select = activity.findViewById(R.id.select);
        ConstraintLayout selectAfter = activity.findViewById(R.id.selectAfter);
        new Table();

        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int density = (int) activity.getResources().getDisplayMetrics().density;

        paddingPx = getPaddingDp() * density;
        paddingPiecePx = getPaddingPieceDp() * density;
        paddingStrokePx = getPaddingStrokeDp() * density;

        int width = metrics.widthPixels - paddingPx * 2;
        piece = (width - (paddingPiecePx * (getNum() - 1))) / getNum();
        int height = (piece * getNum()) + (paddingPiecePx * getNum());
        createBgTableObj(table, width, height, activity.getDrawable(R.drawable.bg_table), paddingPx, paddingStrokePx);
        createTable(table, piece, paddingPiecePx, paddingStrokePx);

        int width_select_bg = metrics.widthPixels - (metrics.widthPixels / 4);
        paddingSelectPx = getPaddingSelectDp() * density;
        paddingSelectPiecePx = getPaddingSelectPieceDp() * density;

        int width_select = width_select_bg - paddingSelectPx * 2;
        piece_select = (width_select - (paddingSelectPiecePx * (getSelectNum() - 1))) / getSelectNum();

        int height_select_bg = piece_select + (20 * density);
        int x_select = (metrics.widthPixels - width_select_bg) / 2;
        int y_select = height + (50 * density);
        createBgObj(select, width_select_bg, height_select_bg, activity.getDrawable(R.drawable.black), x_select, y_select);

        int width_selectAfter = width_select_bg + (20 * density);
        int height_selectAfter = 5 * density;
        int x_selectAfter = (metrics.widthPixels - width_selectAfter) / 2;
        int y_selectAfter = y_select + height_select_bg;
        createObj(selectAfter, width_selectAfter, height_selectAfter, Color.BLACK, x_selectAfter, y_selectAfter);

        createSelect(select, piece_select, 10 * density, paddingSelectPx, paddingSelectPiecePx);

        int x_btn = (metrics.widthPixels - 250) / 2;
        int y_btn = y_selectAfter + 70;
        createBtn(activity, idMap, "submit", new Button(activity), 250, 125, "SUBMIT", x_btn, y_btn);
    }

    private void createTable(ConstraintLayout table, int size, int paddingPiecePx, int paddingStrokePx) {
        for (int i = 0; i < getNum(); i++) {
            for (int j = 0; j < getNum(); j++) {

                ImageView iv = new ImageView(activity);

                String idName = "x" + j + "y" + i;
                int generated_R_id = getRandomId(activity, idMap, idName);
                idMap.put(idName, generated_R_id);

                iv.setId(idMap.get(idName));

                String value = table_map.get(i).get(j);

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("value", value);
                hashMap.put("type", getStatus(i, j));
                hashMap.put("index", null);
                hashMap.put("locked", false); // value != null
                hashMap.put("calculate", false);
                iv.setTag(hashMap);

                if (value == null) {
                    setStatus(activity, iv, getStatus(i, j));
                } else {
                    setChip(activity, iv, value);
                }

                float x = (j * size) + paddingStrokePx + (j * paddingPiecePx);
                float y = (i * size) + paddingStrokePx + (i * paddingPiecePx);

                createChip(table, iv, size, x, y);
            }
        }
    }

    private void createSelect(ConstraintLayout select, int size, float y, int paddingSelectPx, int paddingSelectPiecePx) {

        for (int i = 0; i < getSelectNum(); i++) {

            ImageView iv = new ImageView(activity);

            String idName = "select" + i;
            int generated_R_id = getRandomId(activity, idMap, idName);
            idMap.put(idName, generated_R_id);

            iv.setId(idMap.get(idName));

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("value", select_chip.get(i));
            hashMap.put("used", false);
            iv.setTag(hashMap);

            setChip(activity, iv, select_chip.get(i));

            float x = (i * size) + paddingSelectPx + (i * paddingSelectPiecePx);

            createChip(select, iv, size, x, y);
        }
    }
}
