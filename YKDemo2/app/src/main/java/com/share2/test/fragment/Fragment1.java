package com.share2.test.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.share2.test.R;
import com.share2.test.bean.NewsBean;
import com.share2.test.utils.GildImageLoader;
import com.youth.banner.Banner;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/3/29.
 */

public class Fragment1 extends Fragment {
    private String url="http://v.juhe.cn/toutiao/index?type=top&key=32b9973df2e6ee0c2bf094b61c7d7844";
    private ArrayList<String> list=new ArrayList<>();
    private View view;
    private Banner banner;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.f1_layout,null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        banner = (Banner) view.findViewById(R.id.banner_f1);
        banner.setImageLoader(new GildImageLoader());
        listView = (ListView) view.findViewById(R.id.list_f1);
        getDatas();
    }

    private void getDatas() {
        RequestParams params=new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                NewsBean bean = gson.fromJson(result, NewsBean.class);
                List<NewsBean.ResultBean.DataBean> data = bean.getResult().getData();
                String pic_s = data.get(0).getThumbnail_pic_s();
                String pic_s2 = data.get(0).getThumbnail_pic_s02();
                String pic_s3 = data.get(0).getThumbnail_pic_s03();
                list.add(pic_s);
                list.add(pic_s2);
                list.add(pic_s3);
                banner.setImages(list);
                banner.start();
                listView.setAdapter(new MyAdapter(bean.getResult().getData()));

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
    private class MyAdapter extends BaseAdapter{
        private List<NewsBean.ResultBean.DataBean> list;
        public MyAdapter(List<NewsBean.ResultBean.DataBean> list) {
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
        public View getView(int position, View convertView, ViewGroup parent) {
             ViewHolder holder=null;
            if(convertView==null){
                convertView=View.inflate(getActivity(),R.layout.list_layout,null);
                holder=new ViewHolder();
                holder.title= (TextView) convertView.findViewById(R.id.list_title);
                holder.des= (TextView) convertView.findViewById(R.id.list_des);
                holder.im= (ImageView) convertView.findViewById(R.id.list_im);
               convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            holder.title.setText(list.get(position).getTitle());
            holder.des.setText(list.get(position).getCategory());
            Glide.with(getActivity()).load(list.get(position).getThumbnail_pic_s()).into(holder.im);

            return convertView;
        }
    }
    class ViewHolder{
        ImageView im;
        TextView title;
        TextView des;
    }

}
