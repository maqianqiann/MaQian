package maqianqian.amiao.com.newstodaydemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import maqianqian.amiao.com.newstodaydemo.R;

/**
 * Created by lenovo on 2017/3/15.
 */

public class GridViewAdapter extends BaseAdapter {

    private ArrayList<String> list;
    private Context context;
    public GridViewAdapter(Context context,ArrayList<String> list) {
        this.list=list;
        this.context=context;
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
            convertView=View.inflate(context, R.layout.grid_layout,null);
            holder=new ViewHolder();
            holder.text= (TextView) convertView.findViewById(R.id.grid_title);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.text.setText(list.get(position));

        return convertView;
    }
    class ViewHolder{
        TextView text;
    }
}
