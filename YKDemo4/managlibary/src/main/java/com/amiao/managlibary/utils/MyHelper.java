package com.amiao.managlibary.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lenovo on 2017/3/30.
 */

public class MyHelper extends SQLiteOpenHelper {
    public MyHelper(Context context) {
        super(context,"manage.db",null,2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql1="create table my_table(_id Integer primary key autoincrement, title char(20))";
        String sql2="create table more_table(_id Integer primary key autoincrement, title char(20))";
        db.execSQL(sql1);
        db.execSQL(sql2);

        db.execSQL("insert into my_table(title) values('推荐1')");
        db.execSQL("insert into more_table(title) values('热点1')");
        db.execSQL("insert into my_table(title) values('推荐2')");
        db.execSQL("insert into more_table(title) values('热点2')");
        db.execSQL("insert into my_table(title) values('推荐3')");
        db.execSQL("insert into more_table(title) values('热点3')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
