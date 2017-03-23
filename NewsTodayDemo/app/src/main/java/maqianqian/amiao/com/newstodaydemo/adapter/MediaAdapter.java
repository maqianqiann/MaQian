package maqianqian.amiao.com.newstodaydemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import maqianqian.amiao.com.newstodaydemo.R;
import maqianqian.amiao.com.newstodaydemo.bean.MediaBean;

/**
 * Created by lenovo on 2017/3/17.
 */

public class MediaAdapter extends BaseAdapter {
    private ArrayList<MediaBean> list;
    private Context context;
    public MediaAdapter(Context context, ArrayList<MediaBean> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.media_layout,null);
            holder=new ViewHolder();
            holder.title= (TextView) convertView.findViewById(R.id.text_media_title);
            holder.jcv= (JCVideoPlayerStandard) convertView.findViewById(R.id.jcv_media);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.title.setText(list.get(position).getTitle());
        boolean up = holder.jcv.setUp(list.get(position).getMp4_url(), JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
        if(up){
            Glide.with(context).load(list.get(position).getCover()).into(holder.jcv.thumbImageView);

        }

        //直接进入全屏
        holder.jcv.startFullscreen(context, JCVideoPlayerStandard.class,list.get(position).getMp4_url(),"");
        //模拟用户点击开始按钮，NORMAL状态下点击开始播放视频，播放中点击暂停视频
        holder.jcv.startButton.performClick();

        return convertView;
    }

    class ViewHolder{
        JCVideoPlayerStandard jcv;
        TextView title;
   }

}
