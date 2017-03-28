package maqianqian.amiao.com.newstodaydemo.utils;

import maqianqian.amiao.com.newstodaydemo.application.MyApplication;

/**
 * Created by lenovo on 2017/3/27.
 */

public class WenUtils {

        /**
         * 根据手机的分辨率dp的单位转为px(像素)
         * @param px
         * @return
         */
        public static int px2dip(int px) {
            //获取像素密度
            float density = MyApplication.getContext().getResources().getDisplayMetrics().density;
            int dip = (int) (px / density + 0.5f);
            return dip;

    }
}
