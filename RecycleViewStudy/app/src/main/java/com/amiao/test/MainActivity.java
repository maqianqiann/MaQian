package com.amiao.test;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.amiao.test.adapter.MyAdapter;
import com.amiao.test.bean.NewsBean;
import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView rec;
    private TextView pu;
    private TextView list;
    private TextView grid;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                String str= (String) msg.obj;
                Gson gson=new Gson();
                NewsBean bean = gson.fromJson(str, NewsBean.class);
                List<NewsBean.DataBean> list = bean.getData();
                //设置适配器
                MyAdapter adapter=new MyAdapter(MainActivity.this,list);
                rec.setAdapter(adapter);
                 //设置布局管理
                rec.setLayoutManager(new LinearLayoutManager(MainActivity.this, VERTICAL,false));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        grid = (TextView) findViewById(R.id.gv);
        list = (TextView) findViewById(R.id.lv);
        pu = (TextView) findViewById(R.id.pu);
        rec = (RecyclerView) findViewById(R.id.rec);

        //设置点击事件
        grid.setOnClickListener(this);
        list.setOnClickListener(this);
        pu.setOnClickListener(this);

        //解析数据
        initData();

    }

    private void initData() {
        String url="http://www.93.gov.cn/93app/data.do?channelId=0&startNum=0";
        OkHttpClient client=new OkHttpClient();
        //创建请求
        Request request=new Request.Builder().url(url).build();
        //创建Call对象
        Call call=client.newCall(request);
        //加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                //解析的数据，这是子线程
                String string=response.body().string();
                Message message = handler.obtainMessage(0, string);
                message.sendToTarget();

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gv:
                //设置布局管理
                rec.setLayoutManager(new GridLayoutManager(MainActivity.this,3,GridLayoutManager.VERTICAL,false));

                break;
            case R.id.lv:
                //设置布局管理
                rec.setLayoutManager(new LinearLayoutManager(MainActivity.this, VERTICAL,false));

                break;
            case R.id.pu:
                //设置布局管理
                rec.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));

                break;
        }
    }

}
