package com.share2.test.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.share2.test.MainActivity;
import com.share2.test.R;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lenovo on 2017/3/29.
 */

public class Fragment2 extends Fragment {

    private View view;
    private TextView title;
    private Button fen;
    private Button log;
    private ImageView im;
    private static final String TAG = "Fragment2";
    private static final String APP_ID = "1105602574";//官方获取的APPID
    public Tencent mTencent;
    public BaseUiListener mIUiListener;
    private UserInfo mUserInfo;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.f2_layout,null);
        mTencent = Tencent.createInstance(APP_ID,getActivity().getApplicationContext());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fen = (Button) view.findViewById(R.id.button_f2_fen);
        log = (Button) view.findViewById(R.id.button_f2_log);
        im = (ImageView) view.findViewById(R.id.im_f2);
        title = (TextView) view.findViewById(R.id.title_f2);
        //设置点击事件
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIUiListener = new BaseUiListener();
                //all表示获取所有权限
                mTencent.login(getActivity(),"all",mIUiListener);
            }
        });
        fen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Bundle params = new Bundle();
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);//分享的类型
                params.putString(QQShare.SHARE_TO_QQ_TITLE, "愿你开心每天");//分享标题
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY,"不管是怎样的过程,最终目的还是那个理想的结果。");//要分享的内容摘要
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,"http://blog.csdn.net/sandyran");//内容地址
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://avatar.csdn.net/B/3/F/1_sandyran.jpg");//分享的图片URL
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试");//应用名称
                mTencent.shareToQQ(getActivity(), params, new ShareUiListener());
            }
        });
    }
      public class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            Toast.makeText(getActivity(), "授权成功", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "response:" + response);
            final JSONObject obj = (JSONObject) response;
            try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken, expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(getActivity().getApplicationContext(), qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        Log.e(TAG, "登录成功" + response.toString());
                        JSONObject object= (JSONObject)response;
                        try {
                            String name=object.getString("nickname");
                            String photo=object.getString("figureurl_qq_1");
                            title.setText(name);
                            Glide.with(getActivity()).load(photo).into(im);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e(TAG, "登录失败" + uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG, "登录取消");

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {

        }

        @Override
        public void onCancel() {

        }
    }
    private class ShareUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            //分享成功

        }

        @Override
        public void onError(UiError uiError) {
            //分享失败

        }

        @Override
        public void onCancel() {
            //分享取消

        }
    }

    }
