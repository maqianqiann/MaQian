package test.share6.com.ykdemo6;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by lenovo on 2017/4/1.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration icn=new ImageLoaderConfiguration.Builder(getApplicationContext()).memoryCacheExtraOptions(480,800)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                //硬盘缓存10MB
                .diskCacheSize(10 * 1024 * 1024)
                //内存缓存2M
                 .memoryCacheSize(2 * 1024 * 1024)

                .build();
        ImageLoader.getInstance().init(icn);
        DisplayImageOptions options=new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

    }
}
