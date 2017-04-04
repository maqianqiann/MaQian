package com.share.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.share.test.bean.NewsBean;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private String url="http://www.babybuy100.com/API/getShopOverview.ashx";
    private ArrayList<String> listm=new ArrayList<>();
    private ArrayList<String> lists=new ArrayList<>();
    private Banner bann;
    private GridView gridView_p;
    private GridView gridView_x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        initViews();
    }

    private void initViews() {
        gridView_p = (GridView) findViewById(R.id.gridView_main_p);
        gridView_x = (GridView) findViewById(R.id.gridView_main_x);
        bann = (Banner) findViewById(R.id.banner_main);
        //调用图片加载的方法
        bann.setImageLoader(new GlideImageLoader());
        //解析数据
        getDatas();
        //设置点击事件
        gridView_p.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in=new Intent(MainActivity.this,NextActivity.class);
                in.putExtra("position",position);
                startActivity(in);

            }
        });
    }

    private void getDatas() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(MainActivity.this, url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                     Gson gson=new Gson();
                NewsBean bean = gson.fromJson(responseString, NewsBean.class);
                List<NewsBean.ResultBean.AdvsBean> advs = bean.getResult().getAdvs();
                for (int i = 0; i <advs.size() ; i++) {
                    listm.add(advs.get(i).getPic());
                }

                bann.setImages(listm);//图片的集合
                bann.start();
                List<NewsBean.ResultBean.BrandsBean> brands = bean.getResult().getBrands();
                for (int i = 0; i <brands.size() ; i++) {
                     lists.add(brands.get(i).getTitle());
                }
                gridView_p.setAdapter(new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,lists));
                List<NewsBean.ResultBean.IndexProductsBean> indexProducts = bean.getResult().getIndexProducts();
                gridView_x.setAdapter(new MyAdapter(indexProducts));


            }
        });
    }
    private class  MyAdapter extends BaseAdapter{
        List<NewsBean.ResultBean.IndexProductsBean> list;

        public MyAdapter(List<NewsBean.ResultBean.IndexProductsBean> list) {
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
            convertView=View.inflate(MainActivity.this,R.layout.grid_layout,null);
             holder=new ViewHolder();
              holder.title= (TextView) convertView.findViewById(R.id.title_g);
              holder.price= (TextView) convertView.findViewById(R.id.price_g);
                holder.im= (ImageView) convertView.findViewById(R.id.im_g);
                convertView.setTag(holder);
            }else{
               holder= (ViewHolder) convertView.getTag();
            }
            holder.title.setText(list.get(position).getName());
            holder.price.setText(list.get(position).getMarketPrice()+"￥");
            Glide.with(MainActivity.this).load(list.get(position).getPic()).into(holder.im);
           return convertView;
        }
    }
    class ViewHolder{
        TextView title;
        TextView price;
        ImageView im;
    }
}
