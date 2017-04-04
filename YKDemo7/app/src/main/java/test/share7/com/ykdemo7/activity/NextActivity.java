package test.share7.com.ykdemo7.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import test.share7.com.ykdemo7.R;

/**
 * Created by lenovo on 2017/4/1.
 */

public class NextActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_layout);
        Intent intent = getIntent();
        String url=intent.getStringExtra("url");
        WebView web= (WebView) findViewById(R.id.web_next);
        web.loadUrl(url);
    }
}
