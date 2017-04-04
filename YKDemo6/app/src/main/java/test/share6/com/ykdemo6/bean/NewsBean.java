package test.share6.com.ykdemo6.bean;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/4/1.
 */

public class NewsBean {
    public DataB data;
     public class DataB{
         public ArrayList<NewsInfo> zhuanList;
         public  class NewsInfo{
              public String courseName;
              public String  teacherAvatar;
               public String    onlineTime;
               public String    teacherName;

               @Override
               public String toString() {
                   return "NewsInfo{" +
                           "courseName='" + courseName + '\'' +
                           ", teacherAvatar='" + teacherAvatar + '\'' +
                           ", onlineTime='" + onlineTime + '\'' +
                           ", teacherName='" + teacherName + '\'' +
                           '}';
               }
           }
     }

    @Override
    public String toString() {
        return "NewsBean{" +
                "data=" + data +
                '}';
    }
}
