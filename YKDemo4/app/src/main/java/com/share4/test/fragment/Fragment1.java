package com.share4.test.fragment;

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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.share4.test.MainActivity;
import com.share4.test.R;
import com.share4.test.bean.NewsBean;
import com.share4.test.utils.BannImageUtils;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by lenovo on 2017/3/31.
 */

public class Fragment1 extends Fragment {
    private String url="http://v.juhe.cn/toutiao/index?type=shehui&key=32b9973df2e6ee0c2bf094b61c7d7844";
    private ArrayList<String> lists=new ArrayList<>();
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
         listView = (ListView) view.findViewById(R.id.listView_f1);
         banner.setImageLoader(new BannImageUtils());
         getDatas();
    }
    private void getDatas() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(getActivity(), url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Gson gson=new Gson();
                NewsBean bean = gson.fromJson(responseString, NewsBean.class);
                lists.add(bean.getResult().getData().get(0).getThumbnail_pic_s());
                lists.add(bean.getResult().getData().get(0).getThumbnail_pic_s02());
                lists.add(bean.getResult().getData().get(0).getThumbnail_pic_s03());
                banner.setImages(lists);
                banner.start();
                List<NewsBean.ResultBean.DataBean> list = bean.getResult().getData();
                listView.setAdapter(new MyApater(list));
            }
        });

    }
    private class MyApater extends BaseAdapter{
        List<NewsBean.ResultBean.DataBean> list;
        public MyApater(List<NewsBean.ResultBean.DataBean> list) {
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
                holder.im= (ImageView) convertView.findViewById(R.id.image_list);
                holder.title= (TextView) convertView.findViewById(R.id.title_list);
                holder.desc= (TextView) convertView.findViewById(R.id.desc_list);
             convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            holder.title.setText(list.get(position).getTitle());
            holder.desc.setText(list.get(position).getCategory());
            Glide.with(getActivity()).load(list.get(position).getThumbnail_pic_s()).into(holder.im);

            return convertView;
        }
    }
    class ViewHolder{
        ImageView im;
        TextView title;
        TextView desc;
    }
}
