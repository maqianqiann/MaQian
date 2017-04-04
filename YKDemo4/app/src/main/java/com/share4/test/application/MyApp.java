package com.share4.test.application;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by lenovo on 2017/3/31.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
