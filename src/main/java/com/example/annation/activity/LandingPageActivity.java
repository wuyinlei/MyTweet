package com.example.annation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.annation.R;
import com.example.annation.uri.Contants;
import com.example.annation.utils.PreferenceUtils;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

/**
 * Created by ruolan on 2015/12/15.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */
public class LandingPageActivity extends BaseActivity {

    private SsoHandler mSsoHandler;

    private AuthInfo mAuthInfo;

    private PreferenceUtils mPreferenceUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getmToolbarX().hide();
        mPreferenceUtils = PreferenceUtils.getInstance(this);
        mAuthInfo = new AuthInfo(getApplicationContext(), Contants.APP_KEY, Contants.REDIRECT_URL, Contants.SCOPE);
        mSsoHandler = new SsoHandler(this, mAuthInfo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLogin();
            }
        }, 500);
    }

    private void checkLogin() {
        if (mPreferenceUtils.isLogin()) {
            startActivity(new Intent(LandingPageActivity.this, HomePageActivity.class));
            finish();
        } else {
            mSsoHandler.authorize(new WeiboAuthListener() {
                @Override
                public void onComplete(Bundle bundle) {
                    Log.d("LandingPageActivity", bundle + "");
                    Oauth2AccessToken token = Oauth2AccessToken.parseAccessToken(bundle);
                    mPreferenceUtils.saveToken(token);
                    startActivity(new Intent(LandingPageActivity.this, HomePageActivity.class));
                    finish();
                }

                @Override
                public void onWeiboException(WeiboException e) {

                }

                @Override
                public void onCancel() {

                }
            });
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_landing_page;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != mSsoHandler) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}
