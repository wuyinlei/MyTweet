package com.example.annation.presenter;

import android.util.Log;

import com.example.annation.http.BaseNetWork;
import com.example.annation.http.HttpResponse;
import com.example.annation.status.StatusEntity;
import com.example.annation.uri.Contants;
import com.example.annation.uri.ParameterKeySet;
import com.example.annation.utils.PreferenceUtils;
import com.example.annation.view.HomeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sina.weibo.sdk.net.WeiboParameters;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 若兰 on 2016/2/9.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public class MentionPresenterImp implements BasePresenter {

    private HomeView mView;
    private PreferenceUtils mUtils;
    private WeiboParameters mParameters;
    private int page=1;
    private List<StatusEntity> mEntities;

    public MentionPresenterImp(HomeView view) {
        mView = view;
        mUtils = PreferenceUtils.getInstance(mView.getActivity());
        mParameters = new WeiboParameters(Contants.APP_KEY);
        mEntities = new ArrayList<>();
    }

    @Override
    public void loadData(boolean showLoading) {
        page = 1;
        loadData(false,showLoading);
    }

    @Override
    public void loadMore(boolean showLoading) {
        page ++;
        loadData(true,showLoading);
    }

    private void loadData(final boolean loadMore,boolean showLoading){

        new BaseNetWork(mView,Contants.API.MENTIONS,showLoading){

            @Override
            public WeiboParameters onPrepares() {
                mParameters.put(ParameterKeySet.AUTH_ACCESS_TOKEN,mUtils.getToken().getToken());
                mParameters.put(ParameterKeySet.PAGE,page);
                mParameters.put(ParameterKeySet.COUNT,10);
                return mParameters;
            }

            @Override
            public void onFinish(HttpResponse response, boolean success) {
                if (success) {

                    Log.d("MentionPresenterImp", response.response);
                    Type type = new TypeToken<List<StatusEntity>>() {
                    }.getType();
                    List<StatusEntity> list = new Gson().fromJson(response.response, type);
                    if (!loadMore) {
                        mEntities.clear();
                    }
                    mEntities.addAll(list);
                    mView.onSuccess(mEntities);
                }
            }
        }.get();
    }
}
