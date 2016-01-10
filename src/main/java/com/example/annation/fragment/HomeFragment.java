package com.example.annation.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.annation.R;
import com.example.annation.adapter.HomeAdapter;
import com.example.annation.http.BaseNetWork;
import com.example.annation.http.HttpResponse;
import com.example.annation.status.StatusEntity;
import com.example.annation.uri.Contants;
import com.example.annation.uri.ParameterKeySet;
import com.example.annation.utils.DividerItemDecoration;
import com.example.annation.utils.LogUtils;
import com.example.annation.utils.PreferenceUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.WeiboParameters;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by ruolan on 2015/12/15.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */


public class HomeFragment extends BaseFragment {

    private WeiboParameters mParameters;
    private AsyncWeiboRunner mAsyncWeiboRunner;
    private PreferenceUtils mPreferenceUtils;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration mItemDecoration;

    private List<StatusEntity> mEntities;

    private HomeAdapter mHomeAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //第一步、注册eventbus
        EventBus.getDefault().register(this);
        mParameters = new WeiboParameters(Contants.APP_KEY);
        mPreferenceUtils = PreferenceUtils.getInstance(getActivity());
        mEntities = new ArrayList<>();
        mHomeAdapter = new HomeAdapter(mEntities,getActivity());

        IntentFilter filter = new IntentFilter();
        filter.addAction("change");

        /*LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        Log.d("HomeFragment", "onReceive");
                        loadData(Contants.API.USER_TIME_LINE);
                    }
                }, filter);*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.tweet_commen_recycleview, container, false);
        initAdapter();

        /**
         * {
         "statuses": [
         {
         "created_at": "Tue May 31 17:46:55 +0800 2011",
         "id": 11488058246,
         "text": "求关注。"，
         "source": "<a href="http://weibo.com" rel="nofollow">新浪微博</a>",
         "favorited": false,
         "truncated": false,
         "in_reply_to_status_id": "",
         "in_reply_to_user_id": "",
         "in_reply_to_screen_name": "",
         "geo": null,
         "mid": "5612814510546515491",
         "reposts_count": 8,
         "comments_count": 9,
         "annotations": [],
         "user": {
         "id": 1404376560,
         "screen_name": "zaku",
         "name": "zaku",
         "province": "11",
         "city": "5",
         "location": "北京 朝阳区",
         "description": "人生五十年，乃如梦如幻；有生斯有死，壮士复何憾。",
         "url": "http://blog.sina.com.cn/zaku",
         "profile_image_url": "http://tp1.sinaimg.cn/1404376560/50/0/1",
         "domain": "zaku",
         "gender": "m",
         "followers_count": 1204,
         "friends_count": 447,
         "statuses_count": 2908,
         "favourites_count": 0,
         "created_at": "Fri Aug 28 00:00:00 +0800 2009",
         "following": false,
         "allow_all_act_msg": false,
         "remark": "",
         "geo_enabled": true,
         "verified": false,
         "allow_all_comment": true,
         "avatar_large": "http://tp1.sinaimg.cn/1404376560/180/0/1",
         "verified_reason": "",
         "follow_me": false,
         "online_status": 0,
         "bi_followers_count": 215
         }
         },
         ...
         ],
         "ad": [
         {
         "id": 3366614911586452,
         "mark": "AB21321XDFJJK"
         },
         ...
         ],
         "previous_cursor": 0,      // 暂时不支持
         "next_cursor": 11488013766,     // 暂时不支持
         "total_number": 81655
         }
         */
      /*  mParameters.put(WBConstants.AUTH_ACCESS_TOKEN, mPreferenceUtils.getToken().getToken());
        //请求地址、参数、请求类型、请求回调
        mAsyncWeiboRunner.requestAsync(Contants.API.HOME_TIMELINE, mParameters, "GET", new RequestListener() {

            @Override
            public void onComplete(String s) {
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse(s).getAsJsonObject();
                JsonArray array = object.get("statuses").getAsJsonArray();


                List<StatusEntity> list = new ArrayList<StatusEntity>();
                Type type = new TypeToken<List<StatusEntity>>() {
                }.getType();
                list = new Gson().fromJson(array, type);
                Log.d("HomeFragment", "list.size():" + list.size());
            }

            @Override
            public void onWeiboException(WeiboException e) {

            }
        });*/
        loadData(Contants.API.HOME_TIME_LINE);
        return mRecyclerView;
    }

    /**
     * 加载数据
     */
    private void loadData(String url) {
        new BaseNetWork(getActivity(), url) {
            @Override
            public WeiboParameters onPrepares() {
                mParameters.put(ParameterKeySet.AUTH_ACCESS_TOKEN, mPreferenceUtils.getToken().getToken());
                mParameters.put(ParameterKeySet.PAGE, 1);
                mParameters.put(ParameterKeySet.COUNT, 100);
                return mParameters;
            }

            @Override
            public void onFinish(HttpResponse response, boolean success) {
                if (success) {
                    List<StatusEntity> list = new ArrayList<StatusEntity>();
                    Type type = new TypeToken<List<StatusEntity>>() {
                    }.getType();
                    list = new Gson().fromJson(response.response, type);
                    if (null != list && list.size() > 0) {
                        mEntities.clear();
                        mEntities.addAll(list);
                    }
                    mHomeAdapter.notifyDataSetChanged();
                    Log.d("HomeFragment", "list.size():" + list.size());
                } else {
                    Log.d("HomeFragment", "error");
                }
            }
        }.get();
    }

    private void initAdapter() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mItemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(mItemDecoration);
        mRecyclerView.setAdapter(mHomeAdapter);
        mHomeAdapter.onItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                LogUtils.d(position + "");
            }
        });
    }

    public void onEventMainThread(Integer event){
       switch (event){
           case R.id.first_menu:
               loadData(Contants.API.HOME_TIME_LINE);
               break;
           case R.id.mine_menu:
               loadData(Contants.API.USER_TIME_LINE);
               break;
       }
    }

   /* *//**
     * 开启异步线程处理接受消息
     *//*
    public void onEventAsync(){}

    *//**
     * 开启异步线程接受消息
     *//*
    public void onEventBackgroundThread(){}

    *//**
     * 在主线程中接受消息
     *//*
    public void onEventMainThread(){}*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        //反注册一下
        EventBus.getDefault().unregister(this);
    }
}
