package com.amiao.test.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amiao.test.MainActivity;
import com.amiao.test.R;
import com.amiao.test.bean.NewsBean;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by lenovo on 2017/4/12.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Context context;
    private List<NewsBean.DataBean> list;

    public MyAdapter(Context context, List<NewsBean.DataBean> list) {
        this.context=context;
        this.list=list;
    }

    //返回的View和VIE我Holder的创建
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=View.inflate(context, R.layout.item_layout,null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getIMAGEURL()).into(holder.im);
        holder.title.setText(list.get(position).getTITLE());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private  ImageView im;
        private TextView title;

        public ViewHolder(View itemView) {
             super(itemView);
             //找控件
            im = (ImageView) itemView.findViewById(R.id.im_item);
            title = (TextView) itemView.findViewById(R.id.title_item);

         }
        }
}
