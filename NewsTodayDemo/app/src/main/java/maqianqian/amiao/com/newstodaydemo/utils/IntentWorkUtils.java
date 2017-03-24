package maqianqian.amiao.com.newstodaydemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by lenovo on 2017/3/24.
 */

public class IntentWorkUtils {
    public static boolean isWork(Context context){
        ConnectivityManager manager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info!=null){
            return true;

        }else {
            return false;
        }
    }
}
