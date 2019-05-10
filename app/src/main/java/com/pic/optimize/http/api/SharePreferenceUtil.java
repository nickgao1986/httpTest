package com.pic.optimize.http.api;

import android.content.Context;
import android.content.SharedPreferences;

import com.pic.optimize.fresco.PicApplication;

public class SharePreferenceUtil {

    private static final String TAG = SharePreferenceUtil.class.getSimpleName();
    private static final String SHARED_NAME = "Book";

    public static SharedPreferences getSharedPreferences(Context context) {
        return (context == null ? PicApplication.getContext() : context).getSharedPreferences(SHARED_NAME,
                Context.MODE_PRIVATE);
    }

    public static void putLong(Context context, String Key, long Value) {
        getSharedPreferences(context).edit().putLong(Key, Value).apply();
    }

    public static long getString (Context context, String Key) {
        return getSharedPreferences(context).getLong(Key, 0L);
    }


}
