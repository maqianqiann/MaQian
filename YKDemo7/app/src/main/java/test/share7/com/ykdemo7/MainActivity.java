package test.share7.com.ykdemo7;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import test.share7.com.ykdemo7.fragment.Fragment1;
import test.share7.com.ykdemo7.fragment.Fragment2;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Fragment> listf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化界面
        initViews();
    }

    private void initViews() {
       ViewPager viewPager= (ViewPager) findViewById(R.id.viewPager_main);
       TextView news= (TextView) findViewById(R.id.news_text);
       TextView wo= (TextView)findViewById(R.id.wo_text);
        listf=new ArrayList<>();
        listf.add(new Fragment1());
        listf.add(new Fragment2());

        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        //设置适配器
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    private  class MyAdapter extends FragmentPagerAdapter{

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
