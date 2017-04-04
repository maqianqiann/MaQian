package com.share.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.share.test.bean.NewsBean;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by lenovo on 2017/3/29.
 */

public class NextActivity extends AppCompatActivity {
    private String url="http://www.babybuy100.com/API/getShopOverview.ashx";
    private TextView title;
    private ListView listView;
    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_layout);
        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);
        //初始化界面
        initViews();
    }

    private void initViews() {
        title = (TextView) findViewById(R.id.title_next);
        listView = (ListView) findViewById(R.id.listView_next);
        getDatas();
    }
    private void getDatas() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(NextActivity.this, url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Gson gson=new Gson();
                NewsBean bean = gson.fromJson(responseString, NewsBean.class);
                List<NewsBean.ResultBean.BrandsBean> brands = bean.getResult().getBrands();
                listView.setAdapter(new MyAdapter(brands.get(position).getProducts()));
                title.setText(brands.get(position).getTitle());
            }
        });
       
    }
    private class MyAdapter extends BaseAdapter{

        List<NewsBean.ResultBean.BrandsBean.ProductsBean> lists;
        public MyAdapter(List<NewsBean.ResultBean.BrandsBean.ProductsBean> lists) {
            this.lists=lists;
        }

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder=null;
            if(convertView==null){
                convertView=View.inflate(NextActivity.this,R.layout.grid_layout,null);
                holder=new ViewHolder();
                holder.title= (TextView) convertView.findViewById(R.id.title_g);
                holder.price= (TextView) convertView.findViewById(R.id.price_g);
                holder.im= (ImageView) convertView.findViewById(R.id.im_g);
                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            holder.title.setText(lists.get(position).getName());
            holder.price.setText(lists.get(position).getPrice()+"￥");
            Glide.with(NextActivity.this).load(lists.get(position).getPic()).into(holder.im);
            return convertView;
        }
    }
    class ViewHolder{
        TextView title;
        TextView price;
        ImageView im;
    }
}
