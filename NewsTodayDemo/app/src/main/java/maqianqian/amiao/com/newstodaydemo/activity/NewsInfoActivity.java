package maqianqian.amiao.com.newstodaydemo.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.ArrayList;

import maqianqian.amiao.com.newstodaydemo.R;
import maqianqian.amiao.com.newstodaydemo.bean.ContainBean;
import maqianqian.amiao.com.newstodaydemo.dao.GridDao;

/**
 * Created by lenovo on 2017/3/17.
 */

public class NewsInfoActivity extends AppCompatActivity {

    private ImageView im;
    private GridDao dao;
    private String url;
    private String image;
    private String title;
    private ImageView share;
    private static final String APP_ID = "1105602574"; //获取的APPID
    private ShareUiListener mIUiListener;
    private Tencent mTencent;
    final int RIGHT = 0;
    private GestureDetector gestureDetector;
    final int LEFT = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsinfo_layout);
        gestureDetector=new GestureDetector(NewsInfoActivity.this,onGestureListener);
        //加载第三方的平台
        mTencent=mTencent.createInstance(APP_ID,NewsInfoActivity.this.getApplicationContext());

        dao = new GridDao(NewsInfoActivity.this);
        final Intent intent = getIntent();
        url = intent.getStringExtra("url");
        image = intent.getStringExtra("image");
        title = intent.getStringExtra("title");
        share = (ImageView) findViewById(R.id.share_newsInfo);
        im = (ImageView) findViewById(R.id.xi_newsInfo);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag=true;

                ArrayList<ContainBean> list = dao.queryContain();

                for(ContainBean cb:list){
                    if(title.equals(cb.title)){
                      flag=false;
                    }
                }

                if(flag){
                    dao.addContain(title, image, url);
                    im.setSelected(true);
                    Toast.makeText(NewsInfoActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(NewsInfoActivity.this, "已经收藏过", Toast.LENGTH_SHORT).show();
                }
            }
        });
        WebView web= (WebView) findViewById(R.id.newsInfo_web);
        web.loadUrl(url);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出dialog
                final AlertDialog dialog = new AlertDialog.Builder(NewsInfoActivity.this).create();
                View view=View.inflate(NewsInfoActivity.this,R.layout.share_news_layout,null);
                //设置布局
                dialog.setView(view);
                //获得所依附的activity的窗体
                Window window = dialog.getWindow();
                window.setGravity(Gravity.BOTTOM);//设置位置
                WindowManager.LayoutParams lp= window.getAttributes();
                lp.x=20;
                lp.y=20;

                //设置窗体的属性
                window.setAttributes(lp);

                ImageView share_qq= (ImageView) view.findViewById(R.id.news_share_qq);
                ImageView share_qqk= (ImageView) view.findViewById(R.id.news_share_qqk);
                TextView dis_share= (TextView) view.findViewById(R.id.dis_share_news);

                //设置点击事件
                share_qq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Bundle params = new Bundle();
                        //设置分享的类型
                        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                        params.putString(QQShare.SHARE_TO_QQ_TITLE,"今天头条分享:"+title);
                        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,url);
                        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,image);
                        params.putString(QQShare.SHARE_TO_QQ_APP_NAME,"今日头条");
                        //发送
                        mTencent.shareToQQ(NewsInfoActivity.this,params,new ShareUiListener() );
                        dialog.dismiss();
                    }
                });

                share_qqk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       final Bundle params=new Bundle();
                        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,QzoneShare.SHARE_TO_QZONE_TYPE_NO_TYPE);
                        params.putString(QzoneShare.SHARE_TO_QQ_TITLE,"今天头条分享:"+title);
                        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL,url);
                        params.putString(QzoneShare.SHARE_TO_QQ_IMAGE_URL,image);
                        params.putString(QzoneShare.SHARE_TO_QQ_APP_NAME,"今日头条");
                        mTencent.shareToQzone(NewsInfoActivity.this,params,new ShareUiListener());
                        dialog.dismiss();
                    }
                });
                dis_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
   }
    private GestureDetector.OnGestureListener onGestureListener=new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //e1就是初始状态的MotionEvent对象，e2就是滑动了过后的MotionEvent对象
            //velocityX和velocityY就是滑动的速率
            float x = e2.getX() - e1.getX();//滑动后的x值减去滑动前的x值 就是滑动的横向水平距离(x)
            float y = e2.getY() - e1.getY();//滑动后的y值减去滑动前的y值 就是滑动的纵向垂直距离(y)
            Log.w("tag", "x>" + x);
            Log.w("tag", "y>" + y);
            Log.w("tag", "velocityX>" + velocityX);
            Log.w("tag", "velocityY>" + velocityY);
            //如果滑动的横向距离大于100，表明是右滑了，那么就执行下面的方法，可以是关闭当前的activity
            if (x > 100) {
                doResult(RIGHT);
                Log.w("tag", "RIGHT>" + x);
            }
            //如果滑动的横向距离大于100，表明是左滑了(因为左滑为负数，所以距离大于100就是x值小于-100)
            if (x < -100) {
                Log.w("tag", "LEFT>" + x);
                doResult(LEFT);
            }

            return true;
        }
    };

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println(" ACTION_DOWN");//手指在屏幕上按下
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println(" ACTION_MOVE");//手指正在屏幕上滑动
                break;
            case MotionEvent.ACTION_UP:
                System.out.println(" ACTION_UP");//手指从屏幕抬起了
                break;
            default:
                break;
        }
        return gestureDetector.onTouchEvent(event);
    }

    public void doResult(int action) {

        switch (action) {
            case RIGHT:
                System.out.println("go right");
                finish();
                break;
            case LEFT:
                System.out.println("go left");
                break;
        }
    }

    private class ShareUiListener implements IUiListener{

        @Override
        public void onComplete(Object o) {

        }

        @Override
        public void onError(UiError uiError) {

        }

        @Override
        public void onCancel() {

        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null != mTencent) {
            mTencent.onActivityResult(requestCode, resultCode, data);
        }
    }


}
