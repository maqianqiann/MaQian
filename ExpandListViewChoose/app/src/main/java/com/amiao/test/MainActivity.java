package com.amiao.test;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import com.amiao.test.bean.NewsBean;
import com.amiao.test.view.CustomExpandableListView;
import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private CheckBox checkBox;
    private TextView memory;
    private TextView nums;
    private CustomExpandableListView ex_lv;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                String string= (String) msg.obj;
                Gson gson=new Gson();
                NewsBean newsBean = gson.fromJson(string, NewsBean.class);
                //设置适配器
                list = newsBean.getData();

                adapter = new MyAdapter(MainActivity.this, list);
                ex_lv.setAdapter(adapter);
                int count = ex_lv.getCount();
                for (int i = 0; i < count; i++) {
                    ex_lv.expandGroup(i);
                }
            }
        }
    };
    private MyAdapter adapter;
    private List<NewsBean.DataBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化界面
        initViews();
        initDatas();
    }

    private void initViews() {
        ex_lv = (CustomExpandableListView) findViewById(R.id.exp_lv);
        nums = (TextView) findViewById(R.id.num);
        memory = (TextView) findViewById(R.id.memory);
        checkBox = (CheckBox) findViewById(R.id.cb_main);
        checkBox.setOnClickListener(this);

    }
    //设置适配器的内容
    private void initDatas() {
        String url="http://api.ehuigou.com/Orders/searchCartsLog";
        //设置Ok网络请求对象
        OkHttpClient client=new OkHttpClient();
        //获得编码建造对象
        FormEncodingBuilder feb=new FormEncodingBuilder();
        feb.add("store_id","3850");
         //创建请求对象，设置请求的方式
        Request request=new Request.Builder().url(url).post(feb.build()).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                //需要发送handler
                String string= response.body().string();
                Message message = handler.obtainMessage(0, string);
                message.sendToTarget();

            }
        });
    }

    @Override
    public void onClick(View v) {
        boolean checked = ((CheckBox) v).isChecked();
        if(checked){
            //让一级全选
            for (int i = 0; i <list.size() ; i++) {
                list.get(i).falg_p=true;
                List<NewsBean.DataBean.DatasBean> datas = list.get(i).getDatas();
                for (int j = 0; j <datas.size() ; j++) {
                    datas.get(j).flag_c=true;
                }

            }
            notifyCheckAdapter();
        }else {
            for (int i = 0; i <list.size() ; i++) {
                list.get(i).falg_p=false;
                List<NewsBean.DataBean.DatasBean> datas = list.get(i).getDatas();
                for (int j = 0; j <datas.size() ; j++) {
                    datas.get(j).flag_c=false;
                }
            }
            notifyCheckAdapter();
        }

    }

    private class MyAdapter implements ExpandableListAdapter {


    private List<NewsBean.DataBean> list;
    private Context context;
    private CheckBox cb_c;
    private CheckBox cb_p;
        private TextView price;

        public MyAdapter(Context context, List<NewsBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getDatas().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getDatas().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.parent_layout, null);
        cb_p = (CheckBox) view.findViewById(R.id.cb_p);
        TextView title = (TextView) view.findViewById(R.id.title_p);
        title.setText(list.get(groupPosition).getTitle());
        //设置状态
        if (list.get(groupPosition).falg_p) {
            cb_p.setChecked(true);
        } else {
            cb_p.setChecked(false);
        }
       cb_p.setOnClickListener(new OnGroupListener(groupPosition, cb_p));

        return view;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.child_layout, null);
        cb_c = (CheckBox) view.findViewById(R.id.cb_c);
        TextView price =   (TextView) view.findViewById(R.id.price_c);
        price.setText(list.get(groupPosition).getDatas().get(childPosition).getPrice() + "元");
       if(list.get(groupPosition).getDatas().get(childPosition).flag_c){
           cb_c.setChecked(true);
       }else {
           cb_c.setChecked(false);
       }
        //设置监听
        cb_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(((CheckBox)v).isChecked()){
                   //如果为点击状态
                   list.get(groupPosition).getDatas().get(childPosition).flag_c=true;
                   notifyCheckAdapter();
               }else{
                   list.get(groupPosition).getDatas().get(childPosition).flag_c=false;
               }

                getD(groupPosition);

                int a=0;

                for (int i = 0; i <list.size(); i++) {
                    boolean f = list.get(i).falg_p;
                    if(!f){
                       a++;
                        return;
                      }

                }
                if (a==0) {
                    checkBox.setChecked(true);

                } else {
                    checkBox.setChecked(false);

                }
                notifyCheckAdapter();
            }
        });


        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    private class OnGroupListener implements View.OnClickListener {

        private int position;
        private CheckBox cb_p;

        public OnGroupListener(int position, CheckBox cb_p) {
            this.position = position;
            this.cb_p = cb_p;
        }

        @Override
        public void onClick(View v) {
            if (((CheckBox) v).isChecked()) {
                //一级全选
                setCheck(true);

            } else {
                //取消全选
                setCheck(false);
                checkBox.setChecked(false);
            }
            notifyCheckAdapter();
        }

        public void setCheck(boolean checkFlag) {

            NewsBean.DataBean dataBean = list.get(position);
            //一级状态
            dataBean.falg_p=checkFlag;

            List<NewsBean.DataBean.DatasBean> datas1 = dataBean.getDatas();
            for (NewsBean.DataBean.DatasBean news:datas1) {
                news.flag_c=checkFlag;

            }

            //全选状态
            int num = 0;
            for (int i = 0; i < list.size(); i++) {
                boolean allCheck = list.get(i).falg_p;
                if (!allCheck) {
                    num++;
                }

            }
            if (num == 0) {
                checkBox.setChecked(true);
            } else {
                checkBox.setChecked(false);
            }

        }

        }

    }


    //刷新适配器界面
    private void notifyCheckAdapter() {
       sum();
        ex_lv.setAdapter(adapter);
        int count = ex_lv.getCount();
        for (int i = 0; i < count; i++) {
            ex_lv.expandGroup(i);
        }
    }

    public void getD(int groupPosition){
        NewsBean.DataBean dataBean = list.get(groupPosition);
        List<NewsBean.DataBean.DatasBean> datas = list.get(groupPosition).getDatas();
        for (int i = 0; i <datas.size() ; i++) {
            if(!datas.get(i).flag_c){
                dataBean.falg_p=false;
                notifyCheckAdapter();
                return;
            }
            if(i==datas.size()-1){
                dataBean.falg_p=true;
                notifyCheckAdapter();
                return;
            }

        }
        sum();
    }
    //计算价钱
    public void sum(){
        int num=0;
        int price=0;
        for (int i = 0; i <list.size() ; i++) {
            for (int j = 0; j <list.get(i).getDatas().size() ; j++) {
                if(list.get(i).getDatas().get(j).flag_c){
                    price+=list.get(i).getDatas().get(j).getPrice();
                    num++;
                }

            }
       }
     memory.setText(price+"元");
     nums.setText(num+" ");
    }
}
