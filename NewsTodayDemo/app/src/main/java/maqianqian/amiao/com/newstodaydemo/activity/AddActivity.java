package maqianqian.amiao.com.newstodaydemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import maqianqian.amiao.com.newstodaydemo.R;
import maqianqian.amiao.com.newstodaydemo.adapter.GridViewAdapter;
import maqianqian.amiao.com.newstodaydemo.dao.GridDao;

/**
 * Created by lenovo on 2017/3/15.
 */

public class AddActivity extends AppCompatActivity {
    private GridDao dao;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
        dao = new GridDao(AddActivity.this);
       ImageView dialog_close= (ImageView) findViewById(R.id.dialog_close);
        final ArrayList<String> lists = (ArrayList<String>) dao.query();
        final ArrayList<String> lists1 = (ArrayList<String>) dao.query1();
        GridView grid1= (GridView) findViewById(R.id.dialog_grid1);
        GridView grid2= (GridView) findViewById(R.id.dialog_grid2);
        final GridViewAdapter adapter1 =  new GridViewAdapter(AddActivity.this, lists);
        final GridViewAdapter adapter2 = new GridViewAdapter(AddActivity.this, lists1);
        grid1.setAdapter(adapter1);
        grid2.setAdapter(adapter2);

        //设置点击事件
        grid1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent in=new Intent(AddActivity.this,NewsActivity.class);
                in.putExtra("position",position);
                startActivity(in);
                finish();
            }

        });
        grid2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dao.add(lists1.get(position));
                lists.add(lists1.get(position));
                //将其从中删除
                dao.delete(lists1.get(position));
                lists1.remove(lists1.get(position));
                adapter1.notifyDataSetChanged();
                adapter2.notifyDataSetChanged();

           }

        });

        dialog_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in=new Intent(AddActivity.this,NewsActivity.class);
                startActivity(in);
                finish();
            }
        });
    }
}
