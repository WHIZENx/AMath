package com.whizenx.amath.Game;

public class Setting {

    private static int num = 15;
    private static int selectNum = 8;

    public static int getNum() {
        return num;
    }

    public static void setNum(int num) {
        Setting.num = num;
    }

    public static int getSelectNum() {
        return selectNum;
    }

    public static void setSelectNum(int selectNum) {
        Setting.selectNum = selectNum;
    }
}
