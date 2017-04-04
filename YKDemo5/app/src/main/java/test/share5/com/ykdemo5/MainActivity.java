package test.share5.com.ykdemo5;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import bean.NewsBean;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {


    private String url="http://www.meirixue.com/api.php?c=category&a=getall";
    private PullToRefreshExpandableListView pl;
    int number=0;
    boolean flag=false;
    private TextView num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化界面
        initViews();
    }

    private void initViews() {
        num = (TextView) findViewById(R.id.num_main);
        TextView price= (TextView) findViewById(R.id.price_main);
        pl = (PullToRefreshExpandableListView) findViewById(R.id.pull_list);
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
        getDatas();

    }
    public void getDatas(){
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(MainActivity.this, url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Gson gson=new Gson();
                NewsBean[] been = gson.fromJson(responseString,NewsBean[].class);
                pl.getRefreshableView().setAdapter(new MyAdapters(been));
             }
        });
    }
    private class MyAdapters implements ExpandableListAdapter {
        private NewsBean[] been;

        public MyAdapters(NewsBean[] been) {
            this.been = been;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public int getGroupCount() {
            return been.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return been[groupPosition].getNodes().size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return been[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
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
            convertView=View.inflate(MainActivity.this,R.layout.grid_layout,null);
            TextView textView= (TextView) convertView.findViewById(R.id.title_g);
            textView.setText(been[groupPosition].getCname());
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            convertView=View.inflate(MainActivity.this,R.layout.grid_layout,null);
            TextView title= (TextView) convertView.findViewById(R.id.title_g);
            CheckBox radio= (CheckBox) convertView.findViewById(R.id.ck_g);
            radio.setVisibility(View.VISIBLE);
            title.setText(been[groupPosition].getNodes().get(childPosition).getCategory_name());
            //设置监听事件
            radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        number++;
                        num.setText(number+"台旧机器");

                    }else if(!isChecked){
                        number--;
                        num.setText(number+"台旧机器");
                    }
                }
            });
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
}
