package com.xingshi.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class CitySPUtil {
    private static SharedPreferences userSP;

    public static void getInstance(Context context) {
        if (userSP == null) {
            userSP = context.getSharedPreferences("wyh", 0);
        }
    }

    public static void addParm(String key, String value) {
        userSP.edit().putString(key, value).commit();
    }

    public static String getStringValue(String key) {
        return userSP.getString(key, "");
    }

}
