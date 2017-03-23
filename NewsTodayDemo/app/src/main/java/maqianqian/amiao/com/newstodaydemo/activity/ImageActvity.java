package maqianqian.amiao.com.newstodaydemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import maqianqian.amiao.com.newstodaydemo.R;
import maqianqian.amiao.com.newstodaydemo.fragment.FragmentImage;

/**
 * Created by lenovo on 2017/3/21.
 */

public class ImageActvity extends AppCompatActivity {

    private ViewPager viewPager;
    private ArrayList<String> listIm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_layout);
        viewPager = (ViewPager) findViewById(R.id.viewPage_image);
        Intent intent = getIntent();
        listIm = intent.getStringArrayListExtra("listIm");
        //设置适配器
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

    }
    private class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            FragmentImage f1=new FragmentImage();
            Bundle bundle=new Bundle();
            bundle.putString("imageUrl",listIm.get(position));
            f1.setArguments(bundle);
            return f1;
        }

        @Override
        public int getCount() {
            return listIm.size();
        }
    }
}
