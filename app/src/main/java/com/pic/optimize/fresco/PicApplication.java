package com.pic.optimize.fresco;

import android.app.Application;
import android.content.Context;

import com.pic.optimize.http.OkHttpUtil;

public class PicApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        OkHttpUtil.init();
    }

    public static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    public static String getResString(int resid) {
        return mContext.getString(resid);
    }

}
