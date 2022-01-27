package com.whizenx.amath.Game;

public class isNumeric {

    public static boolean isNumeric(String string) {
        if(string == null || string.equals("")) {
            return false;
        }

        try {
            int intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException ignored) { }
        return false;
    }

}
