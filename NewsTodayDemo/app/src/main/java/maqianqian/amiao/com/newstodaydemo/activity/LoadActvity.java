package maqianqian.amiao.com.newstodaydemo.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import maqianqian.amiao.com.newstodaydemo.R;
import maqianqian.amiao.com.newstodaydemo.bean.LoadBean;
import maqianqian.amiao.com.newstodaydemo.dao.GridDao;
import maqianqian.amiao.com.newstodaydemo.utils.IntentUtils;

/**
 * Created by lenovo on 2017/3/22.
 */

public class LoadActvity extends AppCompatActivity {

    private ListView listView;
    private GridDao dao;
    private List<String> list;
    private ArrayList<Boolean> listb=new ArrayList<>();
    private MyAdapter adapter;
    private String url="http://mapp.qzone.qq.com/cgi-bin/mapp/mapp_subcatelist_qq?yyb_cateid=-10&categoryName=%E8%85%BE%E8%AE%AF%E8%BD%AF%E4%BB%B6&pageNo=1&pageSize=20&type=app&platform=touch&network_type=unknown&resolution=412x732";
    private ArrayList<String> lists=new ArrayList<>();
    private ArrayList<String> listUrl=new ArrayList<>();
    private int[] in;
    private String[] str;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_layout);
        dao = new GridDao(this);

        //初始化界面
        initViews();

    }

    private void initViews() {
        listView = (ListView) findViewById(R.id.listView_load);
        in = new int[]{R.mipmap.qqicon_login_profile,R.mipmap.weixin_allshare_normal,R.mipmap.qqliu,R.mipmap.qqguan,R.mipmap.qqmusic,R.mipmap.qqkj_allshare_normal,R.mipmap.qqxin,R.mipmap.qqshi,
                R.mipmap.qqyue,R.mipmap.qqicon_login_profile,R.mipmap.weixin_allshare_normal,R.mipmap.qqliu,R.mipmap.qqguan,R.mipmap.qqmusic,R.mipmap.qqkj_allshare_normal,R.mipmap.qqxin,R.mipmap.qqshi,
                R.mipmap.qqyue,R.mipmap.qqicon_login_profile};
        //解析数据
        getDatas();

        adapter = new MyAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //进行下载：
                boolean workIsAvailable = IntentUtils.getIntent(LoadActvity.this);
                if (!workIsAvailable) {
                    Toast.makeText(LoadActvity.this, "网络未连接，请设置网络", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(intent);
                } else {
                    //连接成功下载
                    Toast.makeText(LoadActvity.this, "网络连接成功", Toast.LENGTH_SHORT).show();

                    download(position);

                }
            }
        });
    }

    private void getDatas() {
        str = new String[]{"QQ","微信","QQ浏览器","QQ管家","QQ音乐","QQ空间","QQ视频","QQ","微信","QQ浏览器","QQ管家","QQ音乐","QQ空间","QQ视频","QQ浏览器","QQ管家","QQ音乐","QQ空间","QQ视频"};

        /*AsyncHttpClient client=new AsyncHttpClient();
        client.get(LoadActvity.this, url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Gson gson=new Gson();
                LoadBean bean = gson.fromJson(responseString,LoadBean.class);

                List<LoadBean.AppBean> app = bean.getApp();
                for(int i=0;i<app.size();i++){
                    lists.add(app.get(i).getName());
                    listUrl.add(app.get(i).getUrl());

                }

            }
        });
*/
    }

    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return str.length;
        }

        @Override
        public Object getItem(int position) {
            return str[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
             ViewHolder holder=null;
            if(convertView==null){
                convertView=View.inflate(LoadActvity.this,R.layout.loadcon_layout,null);
                holder=new ViewHolder();
                holder.title= (TextView) convertView.findViewById(R.id.title_loadCon);
                holder.im= (ImageView) convertView.findViewById(R.id.image_loadCon);
                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            holder.title.setText(str[position]);
            holder.im.setImageResource(in[position]);
               return convertView;
        }
    }

    private void download(final int p) {
        final String[] items = {"wifi", "手机流量"};
        //参数-1 默认被选中的position
        new AlertDialog.Builder(this).setTitle("网络选择").setIcon(R.mipmap.icon).setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0:
                        //wifi下 下载apk
                        downloadApk(p);
                        break;
                    case 1:
                        //手机流量下提醒用户
                        boolean mobile = IntentUtils.getModile(LoadActvity.this);
                        if (mobile) {
                            Toast.makeText(LoadActvity.this, "现在未使用wifi,将耗用手机流量", Toast.LENGTH_SHORT).show();
                            Intent wifiSettingsIntent = new Intent("android.settings.WIFI_SETTINGS");
                            startActivity(wifiSettingsIntent);
                        }

                        break;
                }
                dialog.dismiss();
            }
        }).show();
    }

    private void downloadApk(final int p) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("版本更新");
        builder.setMessage("现在检测到新版本，是否更新？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateApk(p);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();


    }

    private void updateApk(final int p) {
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("xxx", result.toString());
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("app");
                    JSONObject jo = jsonArray.getJSONObject(p);
                    //url  apk地址
                    String url = jo.getString("url");
                    String version = jo.getString("versionName");
                    Log.i("xxx", "url:" + url + ",versionName:" + version);
                    String versionName = getVersionName();

                    showChoiseDialog(url);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
 }
    //获取版本名称
    private String getVersionName() {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo info = null;
        try {
            info = packageManager.getPackageInfo(getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String versionName = info.versionName;
        return versionName;

    }

    public String getVersionCode() {

        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String versionCode = String.valueOf(packInfo.versionCode);
        return versionCode;
    }


    private void showChoiseDialog(final String url) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        android.app.AlertDialog dialog = null;

        builder.setTitle("版本更新");
        builder.setMessage("检测到新版本，是否下载更新？");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //下载
                downLoadApk(url);

            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    private void downLoadApk(String urls) {

        final RequestParams params = new RequestParams(urls);
        //自定义保存路径 Environment.getExternalStorageDirectory() sdcard 根目录

        params.setSaveFilePath(Environment.getExternalStorageDirectory() + "/app/");
        //自动为文件命令
        params.setAutoRename(true);
        x.http().post(params, new Callback.ProgressCallback<File>() {
        ProgressDialog pro;

            //网络请求成功时回调
            @Override
            public void onSuccess(File result) {


                 Toast.makeText(LoadActvity.this, "下载成功", Toast.LENGTH_SHORT).show();
                //apk下载完成后 调用系统的安装方法
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(result), "application/vnd.android.package-archive");
                startActivity(intent);
                Toast.makeText(LoadActvity.this, "安装成功", Toast.LENGTH_SHORT).show();

            }

            //网络请求错误时回调
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            //网络请求取消的时候回调
            @Override
            public void onCancelled(CancelledException cex) {

            }

            //网络请求完成的时候回调
            @Override
            public void onFinished() {

            }

            //网络请求之前回调
            @Override
            public void onWaiting() {

            }

            //网络请求开始的时候回调
            @Override
            public void onStarted() {
                //创建progressDialog方法
                pro=new ProgressDialog(LoadActvity.this);
                pro.setTitle("正在加载...");
                pro.setIcon(R.mipmap.icon);
                pro.setProgress(0);
                pro.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pro.show();


            }

            //下载的时候不断回调的方法
            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
               //设置进度条
                    pro.setMax((int) total);
                    pro.setProgress((int) current);
 }
        });
    }


    class ViewHolder{
        TextView title;
        ImageView im;
    }


}

