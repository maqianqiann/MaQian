package test.share7.com.ykdemo7.application;

import android.app.Application;

import org.xutils.x;

/**
 * Created by lenovo on 2017/4/1.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
