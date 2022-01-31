package com.whizenx.amath.Game;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.whizenx.amath.R;

public class Popup {

    public static void onButtonShowPopupWindowClick(Activity activity, View view) {

        view.setEnabled(false);

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.popup_window, null);

        Button close = popupView.findViewById(R.id.popup_close);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        PopupWindow popupWindow = new PopupWindow(popupView, width, height);
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        popupWindow.setAnimationStyle(R.style.popup_window_animation);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        close.setOnClickListener(viewBtn -> {
            popupWindow.dismiss();
            view.setEnabled(true);
        });
    }

}
