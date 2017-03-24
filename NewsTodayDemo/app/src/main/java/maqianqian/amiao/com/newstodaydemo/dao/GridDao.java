package maqianqian.amiao.com.newstodaydemo.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import maqianqian.amiao.com.newstodaydemo.bean.ContainBean;
import maqianqian.amiao.com.newstodaydemo.utils.MyHelper;

/**
 * Created by lenovo on 2017/3/15.
 */

public class GridDao {


    private final SQLiteDatabase db;

    public GridDao(Context context) {
        MyHelper helper=new MyHelper(context);
        db = helper.getWritableDatabase();
    }
    public List<String> query(){
         List<String> titles=new ArrayList<>();
         Cursor cursor = db.rawQuery("select * from title", null);
        while(cursor.moveToNext()){
            titles.add(cursor.getString(1));

        }
        return titles;
    }
    public List<String> query1(){
        List<String> titless=new ArrayList<>();
        Cursor cursor1 = db.rawQuery("select * from titlemore", null);
        while(cursor1.moveToNext()){
            titless.add(cursor1.getString(1));
        }
        return titless;
    }

    public void add(String name){
        String sql="insert into title(name) values(?)";
        db.execSQL(sql,new Object[]{name});
     }
    public void delete(String name){
        String sql="delete from titlemore where name=?";
        db.execSQL(sql,new String[]{name});
     }

    public void addContain(String title,String image,String url){
        String sql="insert into contain(title,image,url) values(?,?,?)";
        db.execSQL(sql,new Object[]{title,image,url});

    }
    public  ArrayList<ContainBean> queryContain(){
        ArrayList<ContainBean> list=new ArrayList<>();
        String sql="select * from contain";
        Cursor cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String title= cursor.getString(1);
            String image= cursor.getString(2);
            String url= cursor.getString(3);
            ContainBean bean=new ContainBean();
            bean.id=id;
            bean.title=title;
            bean.image=image;
            bean.url=url;
            list.add(bean);
        }
         return list;
    }
    public void deleteContain(int position){
        String sql="delete from contain where _id="+position;
        db.execSQL(sql);
    }


}
