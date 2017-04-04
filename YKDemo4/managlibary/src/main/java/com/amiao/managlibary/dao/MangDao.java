package com.amiao.managlibary.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.amiao.managlibary.utils.MyHelper;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/3/30.
 */

public class MangDao {

    private final SQLiteDatabase db;

    public MangDao(Context context) {
        MyHelper helper=new MyHelper(context);
        db = helper.getWritableDatabase();

    }
    //添加的方法
    public void addMy(String title){
          String sql=" insert into my_table(title) values(?)";
          db.execSQL(sql,new Object[]{title});

    }
    //添加的方法
    public void addMore(String title){
        String sql="insert into more_table(title) values(?)";
        db.execSQL(sql,new Object[]{title});

    }
    //删除的方法我的频道
    public void deleteMy(String title){
        String sql="delete from my_table where title=?";
        db.execSQL(sql,new Object[]{title});

    }
    //删除的方法其他频道
    public void deleteMore(String title){
        String sql="delete from more_table where title=?";
        db.execSQL(sql,new Object[]{title});

    }
    //查询的方法我的频道
    public  ArrayList<String> query_My(){
        ArrayList<String> list=new ArrayList<>();
        String sql="select * from my_table";
        Cursor cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()){
            String name=cursor.getString(1);
            list.add(name);
        }
        return list;
    }
    //查询的方法其他频道
    public  ArrayList<String> query_More(){
        ArrayList<String> list=new ArrayList<>();
        String sql="select * from more_table";
        Cursor cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()){
            String name=cursor.getString(1);
            list.add(name);
        }
        return list;
    }


    //调用其他频道之后的调用方法，第一个参数是我的频道的数据的集合，第二个参数是其他频道的数据的参数
    //第三个参数是点击的条目的信息
    public void getMyTable(ArrayList<String> list,ArrayList<String> list1,String title){
           list.add(title);
           list1.remove(title);
           addMy(title);//需要向我的频道进行添加
           deleteMore(title);//其他频道进行减少
        }
    //调用我的频道之后的调用方法，第一个参数是我的频道的数据的集合，第二个参数是其他频道的数据的参数
    //第三个参数是点击的条目的信息
    public void getMoreTable(ArrayList<String> list,ArrayList<String> list1,String title){
        list1.add(title);
        list.remove(title);
        addMore(title);
        deleteMy(title);

    }
}
