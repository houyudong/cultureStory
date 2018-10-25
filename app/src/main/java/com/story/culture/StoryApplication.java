package com.story.culture;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.xutil.ThreadUtil;

import com.facebook.stetho.Stetho;

public class StoryApplication extends Application {
    private static Context mContext;
    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Stetho.initializeWithDefaults(this);
        ThreadUtil.init(3, 2);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }
}
