package com.whizenx.amath.Game.Assets;

import static com.whizenx.amath.Game.Assets.Chip.saveIVTag;
import static com.whizenx.amath.Game.isNumeric.isNumeric;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.whizenx.amath.R;

@SuppressLint("UseCompatLoadingForDrawables")
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Interface {

    public static void setStatus_onTouch(Activity activity, ImageView iv, String value) {
        switch (value) {
            case "r":
                iv.setImageDrawable(activity.getDrawable(R.drawable.tripple_eq_ch));
                break;
            case "y":
                iv.setImageDrawable(activity.getDrawable(R.drawable.doubble_eq_ch));
                break;
            case "o":
                iv.setImageDrawable(activity.getDrawable(R.drawable.doubble_p_ch));
                break;
            case "b":
                iv.setImageDrawable(activity.getDrawable(R.drawable.tripple_p_ch));
                break;
            case "s":
                iv.setImageDrawable(activity.getDrawable(R.drawable.star_ch));
                break;
            default:
                iv.setImageDrawable(activity.getDrawable(R.drawable.board_ch));
                break;
        }
    }

    public static void setStatus(Activity activity, ImageView iv, String value) {
        switch (value) {
            case "r":
                iv.setImageDrawable(activity.getDrawable(R.drawable.tripple_eq));
                break;
            case "y":
                iv.setImageDrawable(activity.getDrawable(R.drawable.doubble_eq));
                break;
            case "o":
                iv.setImageDrawable(activity.getDrawable(R.drawable.doubble_p));
                break;
            case "b":
                iv.setImageDrawable(activity.getDrawable(R.drawable.tripple_p));
                break;
            case "s":
                iv.setImageDrawable(activity.getDrawable(R.drawable.star));
                break;
            default:
                iv.setImageDrawable(activity.getDrawable(R.drawable.board));
                break;
        }
        saveIVTag(iv, "type", value);
    }

    public static void setChip(Activity activity, ImageView iv, String value) {
        if (isNumeric(value)) {
            int id = activity.getResources().getIdentifier("a" + value, "drawable", activity.getPackageName());
            iv.setImageResource(id);
        } else {
            switch (value) {
                case "+":
                    iv.setImageDrawable(activity.getDrawable(R.drawable.plus));
                    break;
                case "-":
                    iv.setImageDrawable(activity.getDrawable(R.drawable.delete));
                    break;
                case "*":
                    iv.setImageDrawable(activity.getDrawable(R.drawable.multiply));
                    break;
                case "/":
                    iv.setImageDrawable(activity.getDrawable(R.drawable.divide));
                    break;
                case "=":
                    iv.setImageDrawable(activity.getDrawable(R.drawable.equal));
                    break;
                case "+/-":
                    iv.setImageDrawable(activity.getDrawable(R.drawable.plus_delete));
                    break;
                case "*//":
                    iv.setImageDrawable(activity.getDrawable(R.drawable.multiply_divide));
                    break;
                case "blank":
                    iv.setImageDrawable(activity.getDrawable(R.drawable.blank));
                    break;
                default:
                    iv.setImageDrawable(activity.getDrawable(R.drawable.board));
                    break;
            }
        }
        saveIVTag(iv, "value", value);
    }

    public static void setChip_onTouch(Activity activity, ImageView iv, String value) {
        if (isNumeric(value)) {
            int id = activity.getResources().getIdentifier("a" + value + "_ch", "drawable", activity.getPackageName());
            iv.setImageResource(id);
        } else {
            switch (value) {
                case "+":
                    iv.setImageDrawable(activity.getDrawable(R.drawable.plus_ch));
                    break;
                case "-":
                    iv.setImageDrawable(activity.getDrawable(R.drawable.delete_ch));
                    break;
                case "*":
                    iv.setImageDrawable(activity.getDrawable(R.drawable.multiply_ch));
                    break;
                case "/":
                    iv.setImageDrawable(activity.getDrawable(R.drawable.divide_ch));
                    break;
                case "=":
                    iv.setImageDrawable(activity.getDrawable(R.drawable.equal_ch));
                    break;
                case "+/-":
                    iv.setImageDrawable(activity.getDrawable(R.drawable.plus_delete_ch));
                    break;
                case "*//":
                    iv.setImageDrawable(activity.getDrawable(R.drawable.multiply_divide_ch));
                    break;
                case "blank":
                    iv.setImageDrawable(activity.getDrawable(R.drawable.blank_ch));
                    break;
                default:
                    iv.setImageDrawable(activity.getDrawable(R.drawable.board_ch));
                    break;
            }
        }
    }

}
