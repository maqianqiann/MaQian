package com.wenxin.test;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cjt2325.cameralibrary.CheckPermissionsUtil;
import com.cjt2325.cameralibrary.FileUtil;
import com.cjt2325.cameralibrary.JCameraView;

public class MainActivity extends AppCompatActivity {

    private JCameraView mJCameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置activity为全屏幕
        View decorView = getWindow().getDecorView();
        //获得系统的UI填充整个屏幕
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        //获得 ActionBar的对象
        ActionBar actionBar = getSupportActionBar();
        //将actionBar进行隐藏
        actionBar.hide();

        //使用CheckPermissionsUtils工具类进行动态权限获取
        CheckPermissionsUtil checkPermissionsUtil = new CheckPermissionsUtil(this);
        //拿到全部的权限
        checkPermissionsUtil.requestAllPermission(this);
         //创建JCameraView的对象
        mJCameraView = (JCameraView) findViewById(R.id.cameraview);
        //设置视频保存路径（如果不设置默认为Environment.getExternalStorageDirectory().getPath()）
        mJCameraView.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath());
        //设置手动/自动对焦，默认为自动对焦
        mJCameraView.setAutoFoucs(false);
        //设置监听事件
        mJCameraView.setCameraViewListener(new JCameraView.CameraViewListener() {
            @Override
            public void quit() {
                //返回按钮的点击时间监听
                MainActivity.this.finish();
            }
            @Override
            public void captureSuccess(Bitmap bitmap) {
                //获取到拍照成功后返回的Bitmap
                //将拍摄的照片或视频保存到sd卡下
                FileUtil.saveBitmap(bitmap);
                Log.i("MainActivity","获取到照片Bitmap的宽:" + bitmap.getWidth());
                Log.i("MainActivity", "获取到照片Bitmap的高:" + bitmap.getHeight());
            }
            @Override
            public void recordSuccess(String url) {
                //获取成功录像后的视频路径
                Log.i("MainActivity","视频播放的路径："+url);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        //让生命周期同步，避免一些不必要的错误
        mJCameraView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mJCameraView.onPause();
    }
}
