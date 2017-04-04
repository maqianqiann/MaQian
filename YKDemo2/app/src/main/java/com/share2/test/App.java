package com.share2.test;

import android.app.Application;

import org.xutils.x;

/**
 * Created by lenovo on 2017/3/29.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);

    }
}
