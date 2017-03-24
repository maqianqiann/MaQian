package maqianqian.amiao.com.newstodaydemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amiao.bitmapimagelibary.BitmapUtils;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import maqian.baidu.com.xlistviewlibrary.XListView;
import maqianqian.amiao.com.newstodaydemo.R;
import maqianqian.amiao.com.newstodaydemo.activity.ImageActvity;
import maqianqian.amiao.com.newstodaydemo.activity.NewsActivity;
import maqianqian.amiao.com.newstodaydemo.activity.NewsInfoActivity;
import maqianqian.amiao.com.newstodaydemo.bean.NewsBean;

/**
 * Created by lenovo on 2017/3/13.
 */

public class NewsFragment extends Fragment {

    private NewsActivity activity;
    private View view;
    private XListView xlv;
    private String url;
    private String path;
    private List<NewsBean.ResultBean.DataBean> list;
    private BitmapUtils butils;
    private MyAdapter adapter;

    public void getUrl(String url){
        this.url=url;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragnews_layout,null);
        activity = (NewsActivity) getActivity();
        Bundle bundle = getArguments();
        path = bundle.getString("path");
        butils = new BitmapUtils(activity);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        xlv = (XListView) view.findViewById(R.id.xlv_fn);
        //设置时间的方法
        getTimeDate();
        xlv.setPullRefreshEnable(true);
        xlv.setPullLoadEnable(true);
        xlv.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                getTimeDate();
                //调用请求数据的方法
                getDatas1();
                xlv.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        xlv.stopRefresh();
                    }
                },2000);

            }

            @Override
            public void onLoadMore() {
                //下拉刷新
                getTimeDate();
                //调用请求数据的方法
                getDatas1();
                xlv.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //停止刷新
                        xlv.stopLoadMore();
                    }
                },2000);
            }
        });
        getDatas1();
        xlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳转加载信息
                Intent in=new Intent(activity,NewsInfoActivity.class);
                in.putExtra("url",list.get(position-1).getUrl());
                in.putExtra("image",list.get(position-1).getThumbnail_pic_s());
                in.putExtra("title",list.get(position-1).getTitle());
                startActivity(in);
            }
        });
    }//解析数据
    private void getDatas1(){
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(activity, path, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Gson gson=new Gson();
             NewsBean newsBean = gson.fromJson(responseString, NewsBean.class);
                list = newsBean.getResult().getData();
                adapter = new MyAdapter(list);
                xlv.setAdapter(adapter);

              }
        });
    }
   //设置适配器

private class MyAdapter extends BaseAdapter {
    private List<NewsBean.ResultBean.DataBean> list;
    private ArrayList<String> lists=new ArrayList<>();

    private final int TYPE_1 = 0;
    private final int TYPE_2 = 1;
    private final int TYPE_3 = 2;

    public MyAdapter(List<NewsBean.ResultBean.DataBean> list) {
        this.list = list;
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
        int type = getItemViewType(position);
        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;
        ViewHolder3 holder3 = null;
        if (convertView == null) {
            switch (type) {
                case TYPE_1:
                    convertView = View.inflate(activity, R.layout.type1_layout, null);
                    holder1 = new ViewHolder1();
                    holder1.title1 = (TextView) convertView.findViewById(R.id.text_adapter_title1);
                    holder1.imageView1 = (ImageView) convertView.findViewById(R.id.image_adapter_im1);
                    holder1.dis1= (ImageView) convertView.findViewById(R.id.type1_disLike);
                    convertView.setTag(holder1);
                    break;
                case TYPE_2:
                    convertView = View.inflate(activity, R.layout.type2_layout, null);
                    holder2 = new ViewHolder2();
                    holder2.title2 = (TextView) convertView.findViewById(R.id.text_adapter_title2);
                    holder2.imageView2 = (ImageView) convertView.findViewById(R.id.image_adapter_im2);
                    holder2.imageView21 = (ImageView) convertView.findViewById(R.id.image_adapter_im21);
                    holder2.dis2= (ImageView) convertView.findViewById(R.id.type2_disLike);

                    convertView.setTag(holder2);

                    break;
                case TYPE_3:
                    convertView = View.inflate(activity, R.layout.type3_layout, null);
                    holder3 = new ViewHolder3();
                    holder3.title3 = (TextView) convertView.findViewById(R.id.text_adapter_title3);
                    holder3.im1 = (ImageView) convertView.findViewById(R.id.image1_adapter_im3);
                    holder3.im2 = (ImageView) convertView.findViewById(R.id.image2_adapter_im3);
                    holder3.im3 = (ImageView) convertView.findViewById(R.id.image3_adapter_im3);
                    holder3.dis3= (ImageView) convertView.findViewById(R.id.type3_disLike);

                    convertView.setTag(holder3);


                    break;
            }

        } else {
            switch (type) {
                case TYPE_1:
                    holder1 = (ViewHolder1) convertView.getTag();

                    break;
                case TYPE_2:
                    holder2 = (ViewHolder2) convertView.getTag();

                    break;
                case TYPE_3:
                    holder3 = (ViewHolder3) convertView.getTag();

                    break;
            }
        }
        switch (type) {
            case TYPE_1:
                holder1.title1.setText(list.get(position).getTitle());
                ImageLoader.getInstance().displayImage(list.get(position).getThumbnail_pic_s(), holder1.imageView1);
                holder1.dis1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        final AlertDialog dialog = builder.create();
                        View view2=View.inflate(activity,R.layout.dis_layout,null);
                        dialog.setView(view2);
                        dialog.show();
                        TextView dis_dialog= (TextView) view2.findViewById(R.id.dis_dialog);
                        dis_dialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                list.remove(position);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(activity, "以后减少此类消息的推送", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                    }
                });
                break;
            case TYPE_2:
                holder2.title2.setText(list.get(position).getTitle());
                ImageLoader.getInstance().displayImage(list.get(position).getThumbnail_pic_s(), holder2.imageView2);
                ImageLoader.getInstance().displayImage(list.get(position).getThumbnail_pic_s02(), holder2.imageView21);
                holder2.dis2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        final AlertDialog dialog = builder.create();
                        View view2=View.inflate(activity,R.layout.dis_layout,null);
                        dialog.setView(view2);
                        dialog.show();
                        TextView dis_dialog= (TextView) view2.findViewById(R.id.dis_dialog);
                        dis_dialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                list.remove(position);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(activity, "以后减少此类消息的推送", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                    }
                });

                 break;
            case TYPE_3:
                holder3.title3.setText(list.get(position).getTitle());
                ImageLoader.getInstance().displayImage(list.get(position).getThumbnail_pic_s(), holder3.im1);
                ImageLoader.getInstance().displayImage(list.get(position).getThumbnail_pic_s02(), holder3.im2);
                ImageLoader.getInstance().displayImage(list.get(position).getThumbnail_pic_s03(), holder3.im3);
                //设置点击事件
                holder3.im1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lists.add(list.get(position).getThumbnail_pic_s());
                        lists.add(list.get(position).getThumbnail_pic_s02());
                        lists.add(list.get(position).getThumbnail_pic_s03());
                        Intent in=new Intent(activity,ImageActvity.class);
                        in.putStringArrayListExtra("listIm",lists);
                        activity.startActivity(in);
                    }
                });
                holder3.dis3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        final AlertDialog dialog = builder.create();
                        View view2=View.inflate(activity,R.layout.dis_layout,null);
                        dialog.setView(view2);
                        dialog.show();
                        TextView dis_dialog= (TextView) view2.findViewById(R.id.dis_dialog);
                        dis_dialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               list.remove(position);
                               adapter.notifyDataSetChanged();
                               Toast.makeText(activity, "以后减少此类消息的推送", Toast.LENGTH_SHORT).show();
                               dialog.dismiss();
                            }
                        });
                    }
                });
                break;
        }
     return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getThumbnail_pic_s()!= null && list.get(position).getThumbnail_pic_s02() == null && list.get(position).getThumbnail_pic_s03() == null) {
            return TYPE_1;
        } else if (list.get(position).getThumbnail_pic_s() != null && list.get(position).getThumbnail_pic_s02() != null && list.get(position).getThumbnail_pic_s03() == null) {

            return TYPE_2;
        } else if (list.get(position).getThumbnail_pic_s() != null && list.get(position).getThumbnail_pic_s02() != null && list.get(position).getThumbnail_pic_s03() != null) {
            return TYPE_3;
        }
        return TYPE_1;
    }
}

    class ViewHolder1{
        TextView title1;
        ImageView imageView1;
        ImageView dis1;
  }
    class ViewHolder2{
        TextView title2;
        ImageView imageView21;
        ImageView imageView2;
        ImageView dis2;
}
    class ViewHolder3{
        TextView title3;
        TextView content3;
        ImageView im1;
        ImageView im2;
        ImageView im3;
        ImageView dis3;
    }



    //写个获得系统时间的方法
    public void getTimeDate(){
        long millis = System.currentTimeMillis();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date(millis);
        String time = sdf.format(date);
        xlv.setRefreshTime(time);
 }
}
