package maqianqian.amiao.com.newstodaydemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import maqianqian.amiao.com.newstodaydemo.R;

/**
 * Created by lenovo on 2017/3/27.
 */

public class GuangActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guang_layout);
        WebView web= (WebView) findViewById(R.id.web_gu);
        web.loadUrl("http://www.toutiao.com/redian");

    }
}
