package com.example.annation.fragment;

import android.content.ComponentCallbacks;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.annation.R;
import com.example.annation.status.StatusEntity;
import com.example.annation.uri.Contants;
import com.example.annation.utils.PreferenceUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruolan on 2015/12/15.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */


public class HomeFragment extends BaseFragment {

    private AsyncWeiboRunner mAsyncWeiboRunner;
    private WeiboParameters mParameters;

    private PreferenceUtils mPreferenceUtils;

    private String heepMethod;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAsyncWeiboRunner = new AsyncWeiboRunner(getActivity());
        mParameters = new WeiboParameters(Contants.APP_KEY);
        heepMethod = "GET";
        mPreferenceUtils = PreferenceUtils.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mParameters.put(WBConstants.AUTH_ACCESS_TOKEN, mPreferenceUtils.getToken().getToken());
        //请求地址、参数、请求类型、请求回调
        mAsyncWeiboRunner.requestAsync(Contants.API.HOME_TIMELINE, mParameters, heepMethod, new RequestListener() {

            @Override
            public void onComplete(String s) {
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse(s).getAsJsonObject();
                JsonArray array = object.get("statuses").getAsJsonArray();
                List<StatusEntity> list = new ArrayList<StatusEntity>();
                Type type = new TypeToken<List<StatusEntity>>() {
                }.getType();
                list = new Gson().fromJson(array,type);
                Log.d("HomeFragment", "list.size():" + list.size());
            }

            @Override
            public void onWeiboException(WeiboException e) {

            }
        });

        return view;
    }
}
