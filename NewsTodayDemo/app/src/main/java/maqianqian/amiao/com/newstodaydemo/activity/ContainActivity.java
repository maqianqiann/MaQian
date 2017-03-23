package maqianqian.amiao.com.newstodaydemo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import maqianqian.amiao.com.newstodaydemo.R;
import maqianqian.amiao.com.newstodaydemo.adapter.MediaAdapter;
import maqianqian.amiao.com.newstodaydemo.bean.ContainBean;
import maqianqian.amiao.com.newstodaydemo.dao.GridDao;

/**
 * Created by lenovo on 2017/3/21.
 */

public class ContainActivity extends AppCompatActivity {

    private GridDao dao;
    private ArrayList<ContainBean> list;
    private ListView listView;
    private MyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contain_layout);
        dao = new GridDao(ContainActivity.this);

        list = dao.queryContain();
        listView = (ListView) findViewById(R.id.contain_listView);
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
        //设置监听事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in=new Intent(ContainActivity.this,NewsInfoActivity.class);
                in.putExtra("url",list.get(position).url);
                startActivity(in);
                finish();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ContainActivity.this);
                builder.setTitle("是否要删除");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int p=list.get(position).id;
                        dao.deleteContain(p);
                        list.remove(position);
                        adapter.notifyDataSetChanged();
                        builder.create().dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      builder.create().dismiss();

                    }
                });

                builder.create().show();
                return false;
            }
        });

    }
    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           ViewHolder holder=null;
            if(convertView==null){
                convertView=View.inflate(ContainActivity.this,R.layout.contain_list,null);
                holder=new ViewHolder();
                holder.im= (ImageView) convertView.findViewById(R.id.image_contain);
                holder.title= (TextView) convertView.findViewById(R.id.title_contain);
              convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            holder.title.setText(list.get(position).title);
            Glide.with(ContainActivity.this).load(list.get(position).image).into(holder.im);

            return convertView;
        }
    }
   class ViewHolder{
       TextView title;
       ImageView im;

   }
}
