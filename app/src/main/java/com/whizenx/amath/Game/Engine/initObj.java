package com.whizenx.amath.Game.Engine;

import static com.whizenx.amath.Game.RandomId.getRandomId;
import static com.whizenx.amath.Game.Setting.getNum;
import static com.whizenx.amath.Game.Setting.getSelectNum;
import static com.whizenx.amath.Game.UI.Chip.random_chip;
import static com.whizenx.amath.Game.UI.Interface.setChip;
import static com.whizenx.amath.Game.UI.Interface.setStatus;
import static com.whizenx.amath.Game.UI.Object.createBgObj;
import static com.whizenx.amath.Game.UI.Object.createBgTableObj;
import static com.whizenx.amath.Game.UI.Object.createBtn;
import static com.whizenx.amath.Game.UI.Object.createChip;
import static com.whizenx.amath.Game.UI.Object.createObj;
import static com.whizenx.amath.Game.UI.Option.getPaddingDp;
import static com.whizenx.amath.Game.UI.Option.getPaddingPieceDp;
import static com.whizenx.amath.Game.UI.Option.getPaddingSelectDp;
import static com.whizenx.amath.Game.UI.Option.getPaddingSelectPieceDp;
import static com.whizenx.amath.Game.UI.Option.getPaddingStrokeDp;
import static com.whizenx.amath.Game.UI.Table.getStatus;

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

public class initObj {

    static int paddingPx, paddingPiecePx, paddingStrokePx, paddingSelectPx, paddingSelectPiecePx, piece, piece_select;

    @RequiresApi(api = Build.VERSION_CODES.R)
    public static void initAllObj(Activity activity, HashMap<String, Integer> idMap, HashMap<Integer, List<String>> table_map) {

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
        createTable(activity, idMap, table, piece, paddingPiecePx, paddingStrokePx, table_map);

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

        createSelect(activity, idMap, select, piece_select, 10 * density, paddingSelectPx, paddingSelectPiecePx);

        int x_btn = (metrics.widthPixels - 250) / 2;
        int y_btn = y_selectAfter + 70;
        createBtn(activity, idMap, "submit", new Button(activity), 250, 125, "SUBMIT", x_btn, y_btn);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    static void createTable(Activity activity, HashMap<String, Integer> idMap, ConstraintLayout table, int size, int paddingPiecePx, int paddingStrokePx, HashMap<Integer, List<String>> table_map) {
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    static void createSelect(Activity activity, HashMap<String, Integer> idMap, ConstraintLayout select, int size, float y, int paddingSelectPx, int paddingSelectPiecePx) {
        HashMap<Integer, String> selectValue = new HashMap<>();

        for (int i = 0; i < getSelectNum(); i++) {

            if (i < getSelectNum() - 1) {
                selectValue.put(i, random_chip());
            } else {
                selectValue.put(i, "=");
            }

            ImageView iv = new ImageView(activity);

            String idName = "select" + i;
            int generated_R_id = getRandomId(activity, idMap, idName);
            idMap.put(idName, generated_R_id);

            iv.setId(idMap.get(idName));

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("value", selectValue.get(i));
            hashMap.put("used", false);
            iv.setTag(hashMap);

            setChip(activity, iv, selectValue.get(i));

            float x = (i * size) + paddingSelectPx + (i * paddingSelectPiecePx);

            createChip(select, iv, size, x, y);
        }
    }
}
