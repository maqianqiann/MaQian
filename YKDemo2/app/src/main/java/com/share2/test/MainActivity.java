package com.share2.test;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.share2.test.bean.NewsBean;
import com.share2.test.fragment.Fragment1;
import com.share2.test.fragment.Fragment2;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.Tencent;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewPager viewPager;
    private TextView news;
    private TextView wo;

    private ArrayList<Fragment> listf;
    private ArrayList<TextView> listt;
    private Fragment2 f2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化界面
        initViews();
    }

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.viewPager_main);
        news = (TextView) findViewById(R.id.news_main);
        wo = (TextView) findViewById(R.id.wo_main);
        news.setOnClickListener(this);
        wo.setOnClickListener(this);

        listf=new ArrayList<>();
        listf.add(new Fragment1());
        f2 = new Fragment2();
        listf.add(f2);
        listt=new ArrayList<>();
        listt.add(news);
        listt.add(wo);

        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        //设置监听事件
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                for(int i=0;i<listf.size();i++){
                    if(position==i){
                        listt.get(i).setSelected(true);
                    }else{
                        listt.get(i).setSelected(false);
                    }
                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
           case  R.id.news_main:
               viewPager.setCurrentItem(0);
               news.setSelected(true);
               wo.setSelected(false);
            break;
           case  R.id.wo_main:
               viewPager.setCurrentItem(1);
               news.setSelected(false);
               wo.setSelected(true);
            break;
        }
    }


    private class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return listf.get(position);
        }

        @Override
        public int getCount() {
            return listf.size();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,f2.mIUiListener);
        }
        if (null != f2.mTencent) {
            f2.mTencent.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
