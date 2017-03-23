package maqianqian.amiao.com.newstodaydemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import maqianqian.amiao.com.newstodaydemo.R;


public class MainActivity extends AppCompatActivity {
    private int time=3;
    Timer timer=new Timer();
    TimerTask task=new TimerTask() {
        @Override
        public void run() {
            if(time>0){
                time--;


            }else {
                Intent intent=new Intent(MainActivity.this,NewsActivity.class);
                startActivity(intent);
                finish();
                timer.cancel();//停止执行
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ImageView im= (ImageView) findViewById(R.id.welcome_activity);
        timer.schedule(task,1000,1000);
    }
}
