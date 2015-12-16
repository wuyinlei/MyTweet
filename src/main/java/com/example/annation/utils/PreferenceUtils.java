package com.example.annation.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

/**
 * Created by ruolan on 2015/12/15.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */
public class PreferenceUtils {

    private static SharedPreferences mSharePerences;
    private static SharedPreferences.Editor mEditor;
    private static PreferenceUtils instance;
    private static final String SP_NAME = "MYTWEET";
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    private static final String IS_LOGIN = "IS_LOGIN";

    private PreferenceUtils() {

    }

    public static PreferenceUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (PreferenceUtils.class) {
                instance = new PreferenceUtils();
                mSharePerences = context.getSharedPreferences(SP_NAME, Activity.MODE_PRIVATE);
                mEditor = mSharePerences.edit();
            }
        }
        return instance;
    }

    public Oauth2AccessToken getToken(){
        String json = mSharePerences.getString(ACCESS_TOKEN,"");
        if (TextUtils.isEmpty(json)){
            return null;
        }
        return new Gson().fromJson(json,Oauth2AccessToken.class);
    }
    public void saveToken(Oauth2AccessToken accessToken) {
        mEditor.putString(ACCESS_TOKEN, new Gson().toJson(accessToken)).commit();
        mEditor.putBoolean(IS_LOGIN, true).commit();
    }

    public boolean isLogin() {
        return mSharePerences.getBoolean(IS_LOGIN, false);
    }

}
