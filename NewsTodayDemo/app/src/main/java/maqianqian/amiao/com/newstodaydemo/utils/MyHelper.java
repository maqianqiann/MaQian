package maqianqian.amiao.com.newstodaydemo.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lenovo on 2017/3/15.
 */

public class MyHelper extends SQLiteOpenHelper {
    public MyHelper(Context context) {
        super(context, "news.db", null,2);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table title (_id Integer primary key autoincrement,name char)");
        db.execSQL("create table titlemore (_id Integer primary key autoincrement,name char)");
        db.execSQL("create table contain(_id Integer primary key autoincrement,title char(20), image char(30), url char(30) )");
        db.execSQL("insert into title(name) values('推荐')");
        db.execSQL("insert into title(name) values('热点')");
        db.execSQL("insert into title(name) values('国际')");
        db.execSQL("insert into title(name) values('社会')");
        db.execSQL("insert into title(name) values('国内')");
        db.execSQL("insert into title(name) values('娱乐')");
        db.execSQL("insert into title(name) values('科技')");
        db.execSQL("insert into title(name) values('汽车')");
        db.execSQL("insert into title(name) values('体育')");
        db.execSQL("insert into title(name) values('财经')");
        db.execSQL("insert into title(name) values('视频')");
       /* db.execSQL("insert into titlemore(name) values('汽车')");
        db.execSQL("insert into titlemore(name) values('体育')");
        db.execSQL("insert into titlemore(name) values('财经')");*/
        db.execSQL("insert into titlemore(name) values('军事')");
        db.execSQL("insert into titlemore(name) values('国际')");
        db.execSQL("insert into titlemore(name) values('段子')");
        db.execSQL("insert into titlemore(name) values('趣图')");
        db.execSQL("insert into titlemore(name) values('健康')");
        db.execSQL("insert into titlemore(name) values('美女')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
