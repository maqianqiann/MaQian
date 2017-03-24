package maqianqian.amiao.com.newstodaydemo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import cz.msebera.android.httpclient.Header;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import maqian.baidu.com.xlistviewlibrary.XListView;
import maqianqian.amiao.com.newstodaydemo.R;
import maqianqian.amiao.com.newstodaydemo.activity.AddActivity;
import maqianqian.amiao.com.newstodaydemo.activity.NewsActivity;
import maqianqian.amiao.com.newstodaydemo.adapter.MediaAdapter;
import maqianqian.amiao.com.newstodaydemo.bean.MediaBean;

/**
 *
 * Created by lenovo on 2017/3/15.
 */

public class MediaFragment extends Fragment {

    private View view;
    private XListView xlv;
    private NewsActivity activity;
    private ArrayList<MediaBean> list=new ArrayList<>();
    private ArrayList<MediaBean> lists=new ArrayList<>();
    int page=0;
    private String[] st;
    private MediaAdapter1 adapter;
    private static final String APP_ID = "1105602574"; //获取的APPID
    private ShareUiListener mIUiListener;
    private Tencent mTencent;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mediafragment_layout,null);
        activity = (NewsActivity) getActivity();
        st = new String[]{"V9LG4CHOR","V9LG4E6VR","00850FRB"};
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //传入参数APPID
        mTencent = Tencent.createInstance(APP_ID, activity.getApplicationContext());
        //找到控件
        xlv = (XListView) view.findViewById(R.id.xlv_media);
        xlv.setPullRefreshEnable(true);
        xlv.setPullLoadEnable(true);
        xlv.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                page=0;
                getData();
                xlv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xlv.stopRefresh();
                    }
                },2000);
            }
            @Override
            public void onLoadMore() {
                 page++;
                if(page>2){
                    page=0;
                 }
                getData();
                xlv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xlv.stopLoadMore();
                    }
                },2000);
            }
        });
        //设置数据
        getData();
        xlv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //弹出dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                AlertDialog dialog = builder.create();
                View view1 = View.inflate(activity, R.layout.share_layout, null);
                dialog.setView(view1);
                //获取当前Activity所在的窗体
                Window window = dialog.getWindow();
                //设置在窗低
                window.setGravity(Gravity.BOTTOM);
                //获得窗体的属性
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.x=20;
                lp.y=20;
                //将属性设置给窗体
                window.setAttributes(lp);
                dialog.show();
                ImageView im_qq= (ImageView) view1.findViewById(R.id.image_share_qq);
                ImageView im_qqz= (ImageView) view1.findViewById(R.id.image_share_qqz);
                im_qq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Bundle params = new Bundle();
                        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);//分享的类型
                        params.putString(QQShare.SHARE_TO_QQ_TITLE, lists.get(position-1).getTitle());//分享标题
                        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,lists.get(position-1).getM3u8_url());//要分享的内容摘要
                        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,lists.get(position-1).getM3u8_url());//内容地址
                        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,lists.get(position-1).getCover());//分享的图片URL
                        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "今日头条");//应用名称
                        mTencent.shareToQQ(activity, params, new ShareUiListener());
                    }
                });

                 return true;
            }
        });
    }
    //进行解析数据
    private void getData() {

        String http_url = "http://c.3g.163.com/nc/video/list/"+st[page]+"/n/10-10.html";
        AsyncHttpClient client =new AsyncHttpClient();
        client.get(activity, http_url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Gson gson=new Gson();
                try {
                    JSONObject obj=new JSONObject(responseString);
                    //获得key键
                    Iterator<String> keys = obj.keys();
                   while(keys.hasNext()){
                       String next = keys.next();//每个key
                       JSONArray jsonArray = obj.optJSONArray(next);
                       for(int i=0;i<jsonArray.length();i++){
                           JSONObject object = jsonArray.optJSONObject(i);

                           MediaBean bean = gson.fromJson(object.toString(), MediaBean.class);
                           list.add(bean);
                           lists.add(bean);

                       }
                   }
                  getAdapter();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public void getAdapter(){
        if(adapter==null){
            adapter = new MediaAdapter1(activity, lists);
            xlv.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }

    }

    public class MediaAdapter1 extends BaseAdapter {
        private ArrayList<MediaBean> list;
        private Context context;
        public MediaAdapter1(Context context, ArrayList<MediaBean> list) {
            this.context=context;
            this.list=list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder=null;
            if(convertView==null){
                convertView=View.inflate(context, R.layout.media_layout,null);
                holder=new ViewHolder();
                holder.title= (TextView) convertView.findViewById(R.id.text_media_title);
                holder.jcv= (JCVideoPlayerStandard) convertView.findViewById(R.id.jcv_media);
                holder.share= (ImageView) convertView.findViewById(R.id.image_media_share);
                holder.xz= (ImageView) convertView.findViewById(R.id.image_media_xz);
                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            holder.title.setText(list.get(position).getTitle());
            boolean up = holder.jcv.setUp(list.get(position).getMp4_url(), JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
            if(up){
                Glide.with(context).load(list.get(position).getCover()).into(holder.jcv.thumbImageView);

            }

            holder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   //弹出dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    AlertDialog dialog = builder.create();
                    View view1 = View.inflate(activity, R.layout.share_layout, null);
                    dialog.setView(view1);
                    //获取当前Activity所在的窗体
                    Window window = dialog.getWindow();
                    //设置在窗低
                    window.setGravity(Gravity.BOTTOM);
                    //获得窗体的属性
                    WindowManager.LayoutParams lp = window.getAttributes();
                    lp.x=20;
                    lp.y=20;
                    //将属性设置给窗体
                    window.setAttributes(lp);
                    dialog.show();
                    ImageView im_qq= (ImageView) view1.findViewById(R.id.image_share_qq);
                    ImageView im_qqz= (ImageView) view1.findViewById(R.id.image_share_qqz);
                    im_qq.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Bundle params = new Bundle();
                            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);//分享的类型
                            params.putString(QQShare.SHARE_TO_QQ_TITLE, list.get(position).getTitle());//分享标题
                            params.putString(QQShare.SHARE_TO_QQ_SUMMARY,list.get(position).getM3u8_url());//要分享的内容摘要
                            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,list.get(position).getM3u8_url());//内容地址
                            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,list.get(position).getCover());//分享的图片URL
                            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "今日头条");//应用名称

                            mTencent.shareToQQ(activity, params, new ShareUiListener());
                        }
                    });
                    im_qqz.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int QzoneType = QzoneShare.SHARE_TO_QZONE_TYPE_NO_TYPE;
                            Bundle params = new Bundle();
                            params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneType);
                            params.putString(QzoneShare.SHARE_TO_QQ_TITLE, list.get(position).getTitle());//分享标题
                            params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, list.get(position).getM3u8_url());//分享的内容摘要
                            params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, list.get(position).getM3u8_url());//分享的链接
                            //分享的图片, 以ArrayList<String>的类型传入，以便支持多张图片（注：图片最多支持9张图片，多余的图片会被丢弃）
                            ArrayList<String> imageUrls = new ArrayList<String>();
                            imageUrls.add(list.get(position-1).getCover());//添加一个图片地址
                            params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);//分享的图片URL
                            mTencent.shareToQzone(activity, params, new ShareUiListener());

                        }
                    });

                  }
            });
            holder.xz.setOnClickListener(new View.OnClickListener() {

                private ProgressBar pb;

                @Override
                public void onClick(View v) {
                    //弹出一个对话框
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    final AlertDialog dialog = builder.create();
                    View view=View.inflate(activity,R.layout.progress_layout,null);
                    dialog.setView(view);
                    dialog.show();
                    pb = (ProgressBar) view.findViewById(R.id.progress_p);
                    final TextView zero= (TextView) view.findViewById(R.id.zero_progress);
                    TextView sure= (TextView) view.findViewById(R.id.sure_progress);
                    view.findViewById(R.id.no_progress).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    //设置点击事件
                    sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //设置请求的参数
                            RequestParams params=new RequestParams(list.get(position).getMp4_url());
                            //设置保存的文件夹
                            params.setSaveFilePath(Environment.getExternalStorageDirectory()+"/"+list.get(position).getTitle()+".mp4");
                            params.setUseCookie(true);
                            params.setAutoResume(true);
                            params.setAutoRename(false);
                            //发送请求
                            x.http().get(params, new Callback.ProgressCallback<File>() {

                                @Override
                                public void onSuccess(File result) {
                                    Toast.makeText(activity, "下载成功", Toast.LENGTH_SHORT).show();
                                     dialog.dismiss();
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

                                @Override
                                public void onWaiting() {

                                }

                                @Override
                                public void onStarted() {

                                }

                                @Override
                                public void onLoading(long total, long current, boolean isDownloading) {
                                    pb.setMax((int)total);
                                    pb.setProgress((int)current);
                                    String time = getTime(total);
                                    zero.setText(time);



                                }
                            });
                        }
                    });
                }
            });
            //直接进入全屏
            //holder.jcv.startFullscreen(context, JCVideoPlayerStandard.class,list.get(position).getMp4_url(),"");
            //模拟用户点击开始按钮，NORMAL状态下点击开始播放视频，播放中点击暂停视频
            //holder.jcv.startButton.performClick();

            return convertView;
        }

        class ViewHolder{
            JCVideoPlayerStandard jcv;
            TextView title;
            ImageView share;
            ImageView xz;
        }

    }

    private class ShareUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            //分享成功

        }

        @Override
        public void onError(UiError uiError) {
            //分享失败

        }

        @Override
        public void onCancel() {
            //分享取消

        }
    }

    /**
     * 回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null != mTencent) {
            mTencent.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    public String getTime(long current){
        SimpleDateFormat format=new SimpleDateFormat("mm:ss");
        Date date=new Date(current);
        String s = format.format(date);
        return s;
    }
}
