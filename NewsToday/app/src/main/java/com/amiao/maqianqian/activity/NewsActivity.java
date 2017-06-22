package com.amiao.maqianqian.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.amiao.maqianqian.R;
import com.amiao.maqianqian.bean.Title;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by lenovo on 2017/3/12.
 */

public class NewsActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tab;
    private ArrayList<String> listt=new ArrayList<>();
    private String url="http://ic.snssdk.com/article/category/get/v2/?user_city=%E5%AE%89%E9%98%B3&bd_latitude=4.9E-324&bd_longitude=4.9E-324&bd_loc_time=1465099837&categories=%5B%22video%22%2C%22news_hot%22%2C%22news_local%22%2C%22news_society%22%2C%22subscription%22%2C%22news_entertainment%22%2C%22news_tech%22%2C%22news_car%22%2C%22news_sports%22%2C%22news_finance%22%2C%22news_military%22%2C%22news_world%22%2C%22essay_joke%22%2C%22image_funny%22%2C%22image_ppmm%22%2C%22news_health%22%2C%22positive%22%2C%22jinritemai%22%2C%22news_house%22%5D&version=17375902057%7C14%7C1465030267&iid=4471477475&device_id=17375902057&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=Samsung+Galaxy+S3+-+4.3+-+API+18+-+720x1280&os_api=18&os_version=4.3&openudid=7036bc89d44f680c";
    private SlidingMenu menu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_layout);

        //设置视图
        initViews();
    }

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.viewPager_next);
        tab = (TabLayout) findViewById(R.id.tab_next);
        getDatas();

        //设置侧滑
        getSlidingMenu();


    }

    private void getSlidingMenu() {
        menu = new SlidingMenu(NewsActivity.this);
        menu.setMode(SlidingMenu.LEFT);//设置侧滑的方向
        menu.setBehindOffset(80);
        menu.attachToActivity(NewsActivity.this,SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.sliding_menu);


    }

    //写个解析的方法
    public void getDatas(){
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(NewsActivity.this, url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                   Gson gson=new Gson();
                Title title = gson.fromJson(responseString, Title.class);
                List<Title.DataBeanX.DataBean> list = title.getData().getData();
                for(int i=0;i<list.size();i++){
                   listt.add(list.get(i).getName());
                  }
                //设置模式
                tab.setTabMode(TabLayout.MODE_FIXED);
                for(int in=0;in<listt.size();in++){
                    tab.addTab(tab.newTab().setText(listt.get(in)));
                }
                //设置与viewPager关联
                tab.setupWithViewPager(viewPager);

        }
        });
    }


}
