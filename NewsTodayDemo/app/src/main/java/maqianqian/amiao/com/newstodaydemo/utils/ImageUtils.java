package maqianqian.amiao.com.newstodaydemo.utils;

import org.xutils.image.ImageOptions;

/**
 * Created by lenovo on 2017/3/13.
 */

public class ImageUtils {
    public static ImageOptions getImage(){
        ImageOptions options=new ImageOptions.Builder()
                .setCircular(true)
                .setCrop(true)
                .setSize(35,35)
                .build();

          return options;
    }
}
