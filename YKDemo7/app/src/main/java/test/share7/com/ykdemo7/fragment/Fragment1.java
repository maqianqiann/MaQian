package test.share7.com.ykdemo7.fragment;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import test.share7.com.ykdemo7.MainActivity;
import test.share7.com.ykdemo7.R;
import test.share7.com.ykdemo7.activity.NextActivity;
import test.share7.com.ykdemo7.bean.InfoBean;
import test.share7.com.ykdemo7.bean.NewsBean;
import test.share7.com.ykdemo7.utils.ImageUtils;

/**
 * Created by lenovo on 2017/
 * 4/1.
 */

public class Fragment1 extends Fragment {

    private View view;
    private ArrayList<String> list;
    private Banner banner;
    private ArrayList<String> lists;
    private PullToRefreshExpandableListView pl;
    private MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.f1_layout,null);
        activity = (MainActivity) getActivity();
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        banner = (Banner) view.findViewById(R.id.banner_f1);
        pl = (PullToRefreshExpandableListView) view.findViewById(R.id.list_f1);
        pl.setMode(PullToRefreshBase.Mode.BOTH);


        pl.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ExpandableListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                 pl.postDelayed(new Runnable() {
                     @Override
                     public void run() {
                         pl.onRefreshComplete();
                     }
                 },2000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                pl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pl.onRefreshComplete();
                    }
                },2000);
            }
        });
        banner.setImageLoader(new ImageUtils());
        //设置点击事件
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent in=new Intent(getActivity(),NextActivity.class);
                in.putExtra("url",lists.get(position));
                startActivity(in);
            }
        });
        getDatas();
        getDatas1();
    }
    //解析数据
    private void getDatas() {
        String url="http://v.juhe.cn/toutiao/index?type=shehui&key=32b9973df2e6ee0c2bf094b61c7d7844";
        RequestParams params=new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                NewsBean newsBean = gson.fromJson(result, NewsBean.class);

                list=new ArrayList<>();
                list.add(newsBean.getResult().getData().get(0).getThumbnail_pic_s());
                list.add(newsBean.getResult().getData().get(0).getThumbnail_pic_s02());
                list.add(newsBean.getResult().getData().get(0).getThumbnail_pic_s03());

                lists=new ArrayList<String>();
                List<NewsBean.ResultBean.DataBean> beanList = newsBean.getResult().getData();
                for (int i = 0; i <3; i++) {
                    lists.add(beanList.get(i).getUrl());

                }

                banner.setImages(list);

                banner.start();
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

    //进行解析数据
    public void getDatas1(){
        String url="http://www.meirixue.com/api.php?c=category&a=getall";
        RequestParams params=new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
             Gson gson=new Gson();
                InfoBean[] infoBean = gson.fromJson(result, InfoBean[].class);
                Log.i("xxxx",infoBean.toString());
                pl.getRefreshableView().setAdapter(new MyAapater(infoBean));
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

private class MyAapater implements ExpandableListAdapter{
    private InfoBean[] infoBean;
    public MyAapater(InfoBean[] infoBean) {
        this.infoBean=infoBean;
    }
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return 6;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return infoBean[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return infoBean[groupPosition].getNodes().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView=View.inflate(activity,R.layout.parent_layout,null);
        TextView text= (TextView) convertView.findViewById(R.id.parent_text);
        text.setText(infoBean[groupPosition].getCname()+"0909as09");

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
         convertView=View.inflate(activity,R.layout.child_layout,null);
         GridView gridView= (GridView) convertView.findViewById(R.id.grid_child);
          gridView.setAdapter(new MyGridAdapter(infoBean[groupPosition].getNodes()));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }
   }
    private class MyGridAdapter extends BaseAdapter{
        private List<InfoBean.NodesBean> nodesBean;
        public MyGridAdapter(List<InfoBean.NodesBean> nodesBean) {
            this.nodesBean=nodesBean;
        }

        @Override
        public int getCount() {
            return nodesBean.size();
        }

        @Override
        public Object getItem(int position) {
            return nodesBean.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           convertView=View.inflate(activity,R.layout.parent_layout,null);
           TextView text= (TextView) convertView.findViewById(R.id.parent_text);
            text.setText(nodesBean.get(position).getCategory_name());
            return convertView;
        }
    }

}
