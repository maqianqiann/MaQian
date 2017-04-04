package com.bawei.qqlogin.app;

import android.app.Application;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * 1. 类的用途
 * 2. @author forever
 * 3. @date 2017/3/11 11:25
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UMShareAPI.get(this);

    }

    {

        PlatformConfig.setQQZone("1106036236", "mjFCi0oxXZKZEWJs");
    }
}
