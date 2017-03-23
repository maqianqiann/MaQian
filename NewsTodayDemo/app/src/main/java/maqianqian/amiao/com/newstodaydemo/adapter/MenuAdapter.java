package maqianqian.amiao.com.newstodaydemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import maqianqian.amiao.com.newstodaydemo.R;

/**
 * Created by lenovo on 2017/3/13.
 */

public class MenuAdapter extends BaseAdapter {
    private Context context;
    public MenuAdapter(Context context) {
        this.context=context;
    }
    String[] str=new String[]{"好友动态","我的话题","收藏","活动","商城","反馈","我要你爆料"};
    int[] in=new int[]{R.mipmap.dynamicicon_leftdrawer,R.mipmap.topicicon_leftdrawer,
            R.mipmap.ic_action_favor_on_normal,R.mipmap.activityicon_leftdrawer,R.mipmap.sellicon_leftdrawer,R.mipmap.feedbackicon_leftdrawer,R.mipmap.dynamicicon_leftdrawer};
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
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=View.inflate(context,R.layout.menu_listcon,null);
        ImageView im= (ImageView) convertView.findViewById(R.id.menu_im);
        TextView title= (TextView) convertView.findViewById(R.id.menu_title);
        im.setImageResource(in[position]);
        title.setText(str[position]);
        return convertView;
    }
}
