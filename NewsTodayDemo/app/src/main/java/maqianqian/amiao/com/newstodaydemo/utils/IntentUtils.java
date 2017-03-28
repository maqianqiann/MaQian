package maqianqian.amiao.com.newstodaydemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by lenovo on 2017/3/27.
 */

public class IntentUtils {
  public static boolean getIntent(Context context){
     ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo info = manager.getActiveNetworkInfo();
      if(info!=null){
          return true;

      }else {
          return false;
      }
  }
    public static boolean getModile(Context context){
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info!=null&&info.getType()==manager.TYPE_MOBILE){
            return true;

        }else {
            return false;
        }
    }
}
