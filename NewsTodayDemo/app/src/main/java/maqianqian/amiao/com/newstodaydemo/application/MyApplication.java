package maqianqian.amiao.com.newstodaydemo.application;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by lenovo on 2017/3/13.
 */

public class MyApplication extends Application {
    public static int info=1;
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        x.Ext.init(this);
        ImageLoaderConfiguration mic=new ImageLoaderConfiguration.Builder(getApplicationContext())
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .memoryCacheExtraOptions(480,800)
                .build();
        ImageLoader.getInstance().init(mic);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);


    }
    public static Context getContext(){

        return  context;
    }


}
