package com.example.annation.presenter;

import android.util.Log;

import com.example.annation.adapter.HomeAdapter;
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
 * Created by 若兰 on 2016/1/13.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public class HomePresenterImp implements HomePresenter {

    private String url = Contants.API.HOME_TIME_LINE;

    private int page = 1;


    private WeiboParameters mParameters;
    private PreferenceUtils mPreferenceUtils;

    private List<StatusEntity> mEntities;

    private HomeView homeView;

    public List<StatusEntity> getmEntities() {
        return mEntities;
    }

    public void setmEntities(List<StatusEntity> mEntities) {
        this.mEntities = mEntities;
    }

    private HomeAdapter mHomeAdapter;

    /**
     * 构造函数，在这里实例化我们需要用到的各种工具
     *
     * @param homeView
     */
    public HomePresenterImp(HomeView homeView) {
        this.homeView = homeView;
        mEntities = new ArrayList<>();
        mPreferenceUtils = PreferenceUtils.getInstance(homeView.getActivity());
        //mHomeAdapter = new HomeAdapter(mEntities, homeView.getActivity());
        mParameters = new WeiboParameters(Contants.APP_KEY);
    }

    @Override
    public void loadData(boolean showLoading) {
        page = 1;
        loadData(false,showLoading);
    }

    @Override
    public void loadMore(boolean showLoading) {
        page++;
        loadData(true,showLoading);

    }

    @Override
    public void requestHomeTimeLine() {
        url = Contants.API.HOME_TIME_LINE;
        loadData(true);
    }

    @Override
    public void requestUserTimeLine() {
        url = Contants.API.USER_TIME_LINE;
        loadData(true);
    }

   /* @Override
    public HomeAdapter getHomeAdapter() {
        return mHomeAdapter;
    }*/

    /**
     * 加载数据
     * <p/>
     * 不管是上拉加载更多、下拉刷新、或者是其他方式导致了数据更新，都会调用这个方法的
     */
    private void loadData(final boolean loadMore,boolean showLoading) {
        new BaseNetWork(homeView, url) {
            @Override
            public WeiboParameters onPrepares() {
                mParameters.put(ParameterKeySet.AUTH_ACCESS_TOKEN, mPreferenceUtils.getToken().getToken());
                mParameters.put(ParameterKeySet.PAGE, page);
                //每次加载5条数据
                mParameters.put(ParameterKeySet.COUNT, 5);
                return mParameters;
            }

            @Override
            public void onFinish(HttpResponse response, boolean success) {

                if (success) {
                    //用来存放json数据的
                    List<StatusEntity> list = new ArrayList<StatusEntity>();
                    Type type = new TypeToken<List<StatusEntity>>() {
                    }.getType();

                    //解析json数据
                    list = new Gson().fromJson(response.response, type);
                   /* if (null != list && list.size() > 0) {
                        mEntities.clear();
                        mEntities.addAll(list);
                    }*/
                    if (!loadMore) {
                        mEntities.clear();
                    }
                    mEntities.addAll(list);
                    //mHomeAdapter.notifyDataSetChanged();
                    homeView.onSuccess(mEntities);
                    // Log.d("HomeFragment", "list.size():" + list.size());
                } else {
                     Log.d("HomeFragment", "error");
                }
            }
        }.get();
    }

}
