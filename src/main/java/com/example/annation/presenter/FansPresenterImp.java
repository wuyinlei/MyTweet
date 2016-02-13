package com.example.annation.presenter;

import com.example.annation.http.BaseNetWork;
import com.example.annation.http.HttpResponse;
import com.example.annation.status.UserEntity;
import com.example.annation.uri.Contants;
import com.example.annation.uri.ParameterKeySet;
import com.example.annation.utils.PreferenceUtils;
import com.example.annation.view.FansView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sina.weibo.sdk.net.WeiboParameters;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 若兰 on 2016/2/13.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public class FansPresenterImp implements FansPresenter{

    private FansView mFansView;
    private List<UserEntity> mList;
    private PreferenceUtils mUtils;
    private WeiboParameters mParameters;
    private String url;
    private int page = 0;

    public FansPresenterImp(FansView fansView) {
        mFansView = fansView;
        mList = new ArrayList<>();
        mUtils = PreferenceUtils.getInstance(mFansView.getActivity());
        mParameters = new WeiboParameters(Contants.APP_KEY);
        url = Contants.API.FRIENDS;
    }

    @Override
    public void requestMyFans() {
        url = Contants.API.FOLLOWERS;
    }

    @Override
    public void requestAttentions() {
            url = Contants.API.FRIENDS;
    }

    @Override
    public void loadData(boolean showLoading) {
        page = 0;
        loadData(false,showLoading);
    }

    @Override
    public void loadMore(boolean showLoading) {
        page++;
        loadData(true,false);
    }

    private void loadData(final boolean loadMore,boolean showLoading){
        new BaseNetWork(mFansView, url, showLoading) {
            @Override
            public WeiboParameters onPrepares() {
                mParameters.put(ParameterKeySet.AUTH_ACCESS_TOKEN,mUtils.getToken().getToken());
                mParameters.put(ParameterKeySet.GAME_PARAMS_UID,mUtils.getToken().getUid());
                mParameters.put(ParameterKeySet.COUNT,10);
                return mParameters;
            }

            @Override
            public void onFinish(HttpResponse response, boolean success) {
                if (success){
                    Type type = new TypeToken<ArrayList<UserEntity>>(){}.getType();
                    List<UserEntity> list = new Gson().fromJson(response.response,type);
                    if (!loadMore){
                        mList.clear();
                    }
                    mList.addAll(list);
                    mFansView.onSuccess(mList);
                }
            }
        }.get();
    }
}
