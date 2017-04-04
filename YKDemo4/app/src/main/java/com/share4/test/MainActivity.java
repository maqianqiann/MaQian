package com.share4.test;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.share4.test.bean.NewsBean;
import com.share4.test.fragment.Fragment1;
import com.share4.test.fragment.Fragment2;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<Fragment> listf;
    private ViewPager viewPager;
    private TextView new_text;
    private TextView wo_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化界面
        initViews();
    }

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        new_text = (TextView) findViewById(R.id.news_text);
        wo_text = (TextView) findViewById(R.id.wo_text);
        new_text.setOnClickListener(this);
        wo_text.setOnClickListener(this);
        listf = new ArrayList<>();
        listf.add(new Fragment1());
        Fragment2 f2 = new Fragment2();
        listf.add(f2);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.news_text:
              viewPager.setCurrentItem(0);
                new_text.setSelected(true);
                wo_text.setSelected(false);
                break;
            case R.id.wo_text:
                viewPager.setCurrentItem(1);
                wo_text.setSelected(true);
                new_text.setSelected(false);
               break;
        }
    }
    //解析

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

}
