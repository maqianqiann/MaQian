package test.share6.com.ykdemo6;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import test.share6.com.ykdemo6.bean.Data1;
import test.share6.com.ykdemo6.bean.NewsBean;
import test.share6.com.ykdemo6.utils.StreamUtils;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private TextView class_n;
    private ArrayList<String> lists1=new ArrayList<>();
    private PopupWindow pop;
    private ListView list2;
    private String[] strings;
    private MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化界面
        initViews();
        strings = new String[]{"data1.json","data2.json","data3.json"};
        //解析
        initDatas(strings[0]);
    }



    private void initViews() {
        list = (ListView) findViewById(R.id.list_main);

        class_n = (TextView) findViewById(R.id.class_n);
         View view=View.inflate(MainActivity.this,R.layout.pop_layout,null);
        lists1.add("小学");
        lists1.add("初中");
        lists1.add("高中");

        //设置Pop弹框
        pop = new PopupWindow(view, WRAP_CONTENT, WRAP_CONTENT,true);
        pop.setOutsideTouchable(true);
        pop.setBackgroundDrawable(new BitmapDrawable());

        ListView list1= (ListView) view.findViewById(R.id.pop_list1);
        list2 = (ListView)  view.findViewById(R.id.pop_list2);
        String[] str=new String[]{"三年级","四年级","五年级"};
        list2.setAdapter(new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,str));
        list1.setAdapter(new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,lists1));
        //设置点击事件
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    String[] str=new String[]{"三年级","四年级","五年级"};
                    list2.setAdapter(new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,str));

                }
                if(position==1){
                    String[] str=new String[]{"初一","初二","初三"};
                    list2.setAdapter(new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,str));

                }
                if(position==2){
                    String[] str=new String[]{"高一","高二","高三"};
                    list2.setAdapter(new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,str));

                }

            }
        });

        //设置list2的点击事件
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 if(list2.getAdapter().getItem(position).equals("四年级")){
                     //解析
                     initDatas(strings[0]);
                     adapter.notifyDataSetChanged();

                 } if(list2.getAdapter().getItem(position).equals("初一")){
                    //解析
                    initDatas(strings[2]);
                    adapter.notifyDataSetChanged();

                }   if(list2.getAdapter().getItem(position).equals("高一")){
                  //解析
                  initDatas(strings[1]);
                  adapter.notifyDataSetChanged();

            }

            }
        });

        //设置点击事件
        class_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.showAsDropDown(class_n,0,0);
            }
        });

    }
    private void initDatas(String name) {
        try {
            InputStream stream = getApplicationContext().getClassLoader().getResourceAsStream("assets/"+name);
            Log.i("xxxx", stream + "");
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = stream.read(b)) != -1) {
                    bao.write(b, 0, len);
                }
                stream.close();
                bao.close();
                String path = bao.toString();
            Log.i("xxxx", path+ "");
                Gson gson = new Gson();
            NewsBean bean=gson.fromJson(path,NewsBean.class);
                Log.i("aaa", bean.toString());
            adapter = new MyAdapter(bean);
            list.setAdapter(adapter);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    private class MyAdapter extends BaseAdapter{

        NewsBean bean;
        public MyAdapter(NewsBean bean) {
            this.bean=bean;
        }

        @Override
        public int getCount() {
            return bean.data.zhuanList.size();
        }

        @Override
        public Object getItem(int position) {
            return bean.data.zhuanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           ViewHolder holder=null;
            if(convertView==null){
             convertView=View.inflate(MainActivity.this,R.layout.list_layout,null);
              holder=new ViewHolder();
                holder.title= (TextView) convertView.findViewById(R.id.title_list);
                holder.time= (TextView) convertView.findViewById(R.id.time_list);
                holder.name= (TextView) convertView.findViewById(R.id.name_list);
                holder.im= (ImageView) convertView.findViewById(R.id.im_list);
             convertView.setTag(holder);

            }else {
                holder= (ViewHolder) convertView.getTag();
            }
            holder.title.setText(bean.data.zhuanList.get(position).courseName);
            holder.time.setText(bean.data.zhuanList.get(position).onlineTime);
            holder.name.setText(bean.data.zhuanList.get(position).teacherName);
            Glide.with(MainActivity.this).load(bean.data.zhuanList.get(position).teacherAvatar).into(holder.im);
              return convertView;
        }
    }
      class ViewHolder{
          TextView title;
          TextView time;
          TextView name;
          ImageView im;
      }
}
