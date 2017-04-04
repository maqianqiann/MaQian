package test.share6.com.ykdemo6.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lenovo on 2017/3/31.
 */

public class StreamUtils {
    public static String getPath(InputStream is){
        ByteArrayOutputStream bao=new ByteArrayOutputStream();
        byte[] b=new byte[1024];
        int len=0;
        try {
            while((len=is.read(b))!=-1){
                bao.write(b,0,len);
            }
            is.close();
            bao.close();
            return bao.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
