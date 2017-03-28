package maqianqian.amiao.com.newstodaydemo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import cz.msebera.android.httpclient.Header;
import maqianqian.amiao.com.newstodaydemo.R;
import maqianqian.amiao.com.newstodaydemo.adapter.GridViewAdapter;
import maqianqian.amiao.com.newstodaydemo.adapter.MenuAdapter;
import maqianqian.amiao.com.newstodaydemo.application.MyApplication;
import maqianqian.amiao.com.newstodaydemo.bean.Title;
import maqianqian.amiao.com.newstodaydemo.dao.GridDao;
import maqianqian.amiao.com.newstodaydemo.fragment.MediaFragment;
import maqianqian.amiao.com.newstodaydemo.fragment.NewsFragment;
import maqianqian.amiao.com.newstodaydemo.utils.ImageUtils;
import maqianqian.amiao.com.newstodaydemo.utils.IntentUtils;
import maqianqian.amiao.com.newstodaydemo.utils.Utils;

import static android.provider.UserDictionary.Words.APP_ID;

/**
 * Created by lenovo on 2017/3/12.
 */

public class NewsActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    private static final String  TAG = "NewsActivity";
    private ViewPager viewPager;
    private TabLayout tab;
    private String url="http://ic.snssdk.com/article/category/get/v2/?user_city=%E5%AE%89%E9%98%B3&bd_latitude=4.9E-324&bd_longitude=4.9E-324&bd_loc_time=1465099837&categories=%5B%22video%22%2C%22news_hot%22%2C%22news_local%22%2C%22news_society%22%2C%22subscription%22%2C%22news_entertainment%22%2C%22news_tech%22%2C%22news_car%22%2C%22news_sports%22%2C%22news_finance%22%2C%22news_military%22%2C%22news_world%22%2C%22essay_joke%22%2C%22image_funny%22%2C%22image_ppmm%22%2C%22news_health%22%2C%22positive%22%2C%22jinritemai%22%2C%22news_house%22%5D&version=17375902057%7C14%7C1465030267&iid=4471477475&device_id=17375902057&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=Samsung+Galaxy+S3+-+4.3+-+API+18+-+720x1280&os_api=18&os_version=4.3&openudid=7036bc89d44f680c";
    private SlidingMenu menu;
    private ArrayList<Fragment> listf;
    private ArrayList<String> listt=new ArrayList<>();
    private List<Title.DataBeanX.DataBean> list;
    private  Tencent mTencent;
    private static final String APP_ID = "1105602574";//官方获取的APPID
    private BaseUiListener mIUiListener;
    private UserInfo mUserInfo;
    private ImageView tou;

    private RelativeLayout sliding_relative2;
    private ImageView tou_big;
    private LinearLayout linear_next;
    private TextView log_more;
    private TextView user;
    private RadioButton sliding__radio_sz;
    private RadioButton sliding__radio_ry;
    private RadioButton sliding__radio_xz;
    private RadioGroup sliding__radio_z;
    private boolean flag=true;
    private ArrayList<String> listurl=new ArrayList<>();
    //设置默认为日间模式
    private int theme=R.style.AppTheme;
    private ImageView add_image;
    private GridDao dao;
    private GridViewAdapter adapter1;
    private ArrayList<String> stringList;
    public static int position=0;
    private ListView listView_menu;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    private static final int REQUESTCODE_PICK =1 ;
    protected static Uri tempUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //进行一个判空的处理
        if(savedInstanceState!=null){
            //拿出主题
            theme=savedInstanceState.getInt("theme");
            setTheme(theme);

        }
        boolean b = IntentUtils.getIntent(NewsActivity.this);
        if(!b){
            final AlertDialog.Builder builder = new AlertDialog.Builder(NewsActivity.this);
            builder.setTitle("是否通过移动数据访问？");
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    builder.create().dismiss();
                }
            });
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //通过隐式开启
                    Intent intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(intent);
                }
            });
            builder.create().show();
       }

        setContentView(R.layout.news_layout);
        SMSSDK.initSDK(NewsActivity.this,"1c11a2b17ace0","09776530f44c4c55fbf4f04ba861d501");
        mTencent = Tencent.createInstance(APP_ID,NewsActivity.this.getApplicationContext());
        dao = new GridDao(NewsActivity.this);
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        //设置视图
        initViews();


    }
    //找到控件
    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.viewPager_next);
        tab = (TabLayout) findViewById(R.id.tab_next);
        tou = (ImageView) findViewById(R.id.tou_news);
        add_image = (ImageView) findViewById(R.id.add_next);
        ImageView sou_new= (ImageView) findViewById(R.id.sou_news);
        //查询数据库中的对象
        stringList = (ArrayList<String>) dao.query();
        //将路径放入集合中
        String url1="http://v.juhe.cn/toutiao/index?type=shehui&key=32b9973df2e6ee0c2bf094b61c7d7844";
        String url2="http://v.juhe.cn/toutiao/index?type=redian&key=32b9973df2e6ee0c2bf094b61c7d7844";
        String url3="http://v.juhe.cn/toutiao/index?type=guoji&key=32b9973df2e6ee0c2bf094b61c7d7844";
        String url4="http://v.juhe.cn/toutiao/index?type=shehui&key=32b9973df2e6ee0c2bf094b61c7d7844";
        String url5="http://v.juhe.cn/toutiao/index?type=guomao&key=32b9973df2e6ee0c2bf094b61c7d7844";
        String url6="http://v.juhe.cn/toutiao/index?type=yule&key=32b9973df2e6ee0c2bf094b61c7d7844";
        String url7="http://v.juhe.cn/toutiao/index?type=keji&key=32b9973df2e6ee0c2bf094b61c7d7844";
        String url8="http://v.juhe.cn/toutiao/index?type=qiche&key=32b9973df2e6ee0c2bf094b61c7d7844";
        String url9="http://v.juhe.cn/toutiao/index?type=tiyu&key=32b9973df2e6ee0c2bf094b61c7d7844";
        String url10="http://v.juhe.cn/toutiao/index?type=caiji&key=32b9973df2e6ee0c2bf094b61c7d7844";
        listurl.add(url1);
        listurl.add(url2);
        listurl.add(url3);
        listurl.add(url4);
        listurl.add(url5);
        listurl.add(url6);
        listurl.add(url7);
        listurl.add(url8);
        listurl.add(url9);
        listurl.add(url10);
        if(stringList.size()>listurl.size()){
            int an=stringList.size()-listurl.size();
            for(int i=0;i<an;i++){
                listurl.add("http://v.juhe.cn/toutiao/index?type=qiche&key=32b9973df2e6ee0c2bf094b61c7d7844");
           }
        }
        sou_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(NewsActivity.this,SearchActivity.class);
                startActivity(in);
            }
        });


        //调用解析的数据
         getDatas();
        //设置侧滑
        getSlidingMenu();
        //头像点击进入侧滑界面
        tou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.toggle();
            }
        });
        //设置+的点击事件
        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in=new Intent(NewsActivity.this,AddActivity.class);
                startActivity(in);
            }
        });

  }

    private void getSlidingMenu() {
        menu = new SlidingMenu(NewsActivity.this);
        menu.setMode(SlidingMenu.LEFT);//设置侧滑的方向
        menu.setBehindOffset(80);
        menu.attachToActivity(NewsActivity.this,SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.sliding_menu);
        ListView listView= (ListView) menu.findViewById(R.id.listView_next);
        listView.setAdapter(new MenuAdapter(NewsActivity.this));
        ImageView sliding_phone= (ImageView) menu.findViewById(R.id.sliding_phone);
        ImageView sliding_qq= (ImageView) menu.findViewById(R.id.sliding_qq);
        ImageView sliding_wb= (ImageView) menu.findViewById(R.id.sliding_wb);
        linear_next = (LinearLayout) menu.findViewById(R.id.linear_next);

        //找到列表，进行对收藏的完善
        //设置点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==2){
                    Intent in=new Intent(NewsActivity.this,ContainActivity.class);
                    startActivity(in);

                }
            }
        });

        sliding_relative2 = (RelativeLayout) menu.findViewById(R.id.sliding_relative2);
        log_more = (TextView) menu.findViewById(R.id.logMath_sli);
        tou_big = (ImageView) menu.findViewById(R.id.sliding_qq_big);
        user = (TextView) menu.findViewById(R.id.sliding_user_big);

        sliding__radio_sz = (RadioButton) menu.findViewById(R.id.sliding__radio_sz);
        sliding__radio_ry = (RadioButton) menu.findViewById(R.id.sliding__radio_ry);
        sliding__radio_xz = (RadioButton) menu.findViewById(R.id.sliding__radio_xz);
        sliding__radio_z = (RadioGroup) menu.findViewById(R.id.sliding__radio_z);
        sliding__radio_z.setOnCheckedChangeListener(this);
        sliding__radio_ry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //切换日夜间模式
                theme = (theme == R.style.AppTheme) ? R.style.NightAppTheme : R.style.AppTheme;
                //重新创建
                recreate();
          }
        });

        sliding__radio_xz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进行跳转
                Intent in=new Intent(NewsActivity.this,LoadActvity.class);
                startActivity(in);
            }
        });

        sliding__radio_sz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置字体的大小
                String str[] =new String[]{"小","中","大"};
                AlertDialog.Builder builder = new AlertDialog.Builder(NewsActivity.this);
                builder.setTitle("请选择设置的字体的大小");
                builder.setIcon(R.mipmap.icon);
                builder.setSingleChoiceItems(str, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                MyApplication.info=0;
                                dialog.dismiss();
                                   break;
                            case 1:
                                MyApplication.info=1;
                                dialog.dismiss();
                                break;
                            case 2:
                                MyApplication.info=2;
                                dialog.dismiss();
                                break;

                        }
                    }
                });
                builder.create().show();
            }
        });

        //设置点击事件
        sliding_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mIUiListener = new BaseUiListener();
                //all表示获取所有权限
                mTencent.login(NewsActivity.this,"all", mIUiListener);

            }
        });
        sliding_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获得注册页的类
                RegisterPage page=new RegisterPage();
                //设置注册后的返回的结果
                page.setRegisterCallback(new EventHandler(){
                    @Override
                    public void afterEvent(int i, int i1, Object o) {
                        super.afterEvent(i, i1, o);
                        //返回的结果码
                        if(i1==SMSSDK.RESULT_COMPLETE) {
                            HashMap<String, Object> phoneMap = (HashMap<String, Object>) o;
                            String country = (String) phoneMap.get("country");
                            String phone = (String) phoneMap.get("phone");
                            Log.i("country:", country);//注册的地域的代码
                            Log.i("phone:", phone);//注册的号码
                            sliding_relative2.setVisibility(View.VISIBLE);
                            linear_next.setVisibility(View.INVISIBLE);
                            log_more.setVisibility(View.INVISIBLE);
                            user.setText("手机用户:"+phone);
                            tou_big.setImageResource(R.mipmap.ic_default_avatar_big_normal);
                            tou.setImageResource(R.mipmap.ic_default_avatar_big_normal);
                        }
                    }
                });
                //展示出来
                page.show(NewsActivity.this);

            }
        });

        //设置上传头像
        tou_big.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChoosePicDialog();
            }
        });

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId){
            case R.id.sliding__radio_xz:
                break;
            case R.id.sliding__radio_ry:
                if(flag){
                    sliding__radio_ry.setSelected(true);
                    sliding__radio_ry.setText("夜间");
                }else{
                    sliding__radio_ry.setSelected(false);
                    sliding__radio_ry.setText("日间");
                }
                flag=!flag;
                 break;
            case R.id.sliding__radio_sz:
                break;
        }
    }

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            Toast.makeText(NewsActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "response:" + response);
            JSONObject obj = (JSONObject) response;
            try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken,expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(getApplicationContext(),qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        Log.e(TAG,"登录成功"+response.toString());
                        JSONObject obj=(JSONObject) response;
                        try {
                            String name=obj.getString("nickname");
                            String figureurl_qq_1=obj.getString("figureurl_qq_1");
                            String figureurl_qq_2=obj.getString("figureurl_qq_2");
                            x.image().bind(tou,figureurl_qq_1, ImageUtils.getImage());
                            sliding_relative2.setVisibility(View.VISIBLE);
                            linear_next.setVisibility(View.INVISIBLE);
                            log_more.setVisibility(View.INVISIBLE);
                            x.image().bind(tou_big,figureurl_qq_2, ImageUtils.getImage());
                            user.setText(name);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e(TAG,"登录失败"+uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG,"登录取消");

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(NewsActivity.this, "授权失败", Toast.LENGTH_SHORT).show();

        }
        @Override
        public void onCancel() {
            Toast.makeText(NewsActivity.this, "授权取消", Toast.LENGTH_SHORT).show();

        }
     }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }

    //写个解析的方法
    public void getDatas(){
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(NewsActivity.this, url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                   Gson gson=new Gson();
                Title title = gson.fromJson(responseString, Title.class);
                list = title.getData().getData();
                listf=new ArrayList<Fragment>();
                //设置模式

                tab.setTabMode(TabLayout.MODE_SCROLLABLE);
                for(int a=0;a<stringList.size()-1;a++){

                    NewsFragment f1=new NewsFragment();

                    Bundle bundle=new Bundle();
                    bundle.putString("path",listurl.get(a));
                    f1.setArguments(bundle);
                    listf.add(f1);
                  }
                MediaFragment fr=new MediaFragment();
                listf.add(fr);

                MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
                viewPager.setAdapter(adapter);
                viewPager.setCurrentItem(position);
                adapter.notifyDataSetChanged();
                //设置与viewPager关联
                tab.setupWithViewPager(viewPager);
                tab.setTabsFromPagerAdapter(adapter);

        }
        });
    }
private class MyAdapter extends FragmentPagerAdapter{

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return listf.get(position);
    }

    @Override
    public int getCount() {
        return listf.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
       // return listt.get(position);
        return stringList.get(position);
    }
}

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //进行一个临时数据的存储
        outState.putInt("theme",theme);

    }

    //恢复数据
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        theme=savedInstanceState.getInt("theme");
    }

    @Override
    protected void onResume() {
        super.onResume();
        stringList= (ArrayList<String>) dao.query();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    /**
     * 显示修改头像的对话框
     */
    protected void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置头像");
        String[] items = { "选择本地照片", "拍照" };
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                        // 如果朋友们要限制上传到服务器的图片类型时可以直接写如：image/jpeg 、 image/png等的类型
                        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(pickIntent, REQUESTCODE_PICK);
                        break;
                    case TAKE_PICTURE: // 拍照
                        Intent openCameraIntent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        tempUri = Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), "image.jpg"));
                        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });
        builder.create().show();
    }
    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 保存裁剪之后的图片数据
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            photo = Utils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
            tou_big.setImageBitmap(photo);
            tou.setImageBitmap(photo);
            uploadPic(photo);
        }
    }

    private void uploadPic(Bitmap bitmap) {
        // 上传至服务器
        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了

        String imagePath = Utils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));
        Log.e("imagePath", imagePath+"");
        if(imagePath != null){
            // 拿着imagePath上传了
            // ...
        }
    }

}
