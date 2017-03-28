package maqianqian.amiao.com.newstodaydemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import maqianqian.amiao.com.newstodaydemo.R;

/**
 * Created by lenovo on 2017/3/17.
 */

public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        Button button= (Button) findViewById(R.id.button_search);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent in=new Intent(SearchActivity.this,GuangActivity.class);
                startActivity(in);
            }
        });
    }
}
