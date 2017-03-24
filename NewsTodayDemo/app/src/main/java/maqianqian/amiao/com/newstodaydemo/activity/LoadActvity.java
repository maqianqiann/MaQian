package maqianqian.amiao.com.newstodaydemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import maqianqian.amiao.com.newstodaydemo.R;
import maqianqian.amiao.com.newstodaydemo.dao.GridDao;

/**
 * Created by lenovo on 2017/3/22.
 */

public class LoadActvity extends AppCompatActivity {

    private ListView listView;
    private GridDao dao;
    private List<String> list;
    private ArrayList<Boolean> listb=new ArrayList<>();
    private MyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_layout);
        dao = new GridDao(this);

        //初始化界面
        initViews();

    }

    private void initViews() {
        listView = (ListView) findViewById(R.id.listView_load);
        list = dao.query();
        for(int i=0;i<list.size();i++){
            listb.add(false);
        }
        adapter = new MyAdapter();
        listView.setAdapter(adapter);

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
        public View getView(final int position, View convertView, ViewGroup parent) {
             ViewHolder holder=null;
            if(convertView==null){
                convertView=View.inflate(LoadActvity.this,R.layout.loadcon_layout,null);
               holder=new ViewHolder();
                holder.title= (TextView) convertView.findViewById(R.id.title_loadCon);
               holder.cb= (CheckBox) convertView.findViewById(R.id.ck_loadCon);
                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            holder.title.setText(list.get(position));
            holder.cb.setChecked(listb.get(position));
            holder.cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listb.add(position,!listb.get(position));
                    adapter.notifyDataSetChanged();
                    //进行下载：
                }
            });
            holder.cb.setChecked(listb.get(position));
            return convertView;
        }
    }
    class ViewHolder{
        TextView title;
        CheckBox cb;
    }

}
