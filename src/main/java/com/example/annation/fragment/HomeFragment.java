package com.example.annation.fragment;

import android.annotation.TargetApi;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.annation.R;
import com.example.annation.presenter.HomePresenter;
import com.example.annation.presenter.HomePresenterImp;
import com.example.annation.utils.DividerItemDecoration;
import com.example.annation.view.HomeView;
import com.example.annation.widget.PullToRefreshRecyclerView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import de.greenrobot.event.EventBus;

/**
 * Created by ruolan on 2015/12/15.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */


public class HomeFragment extends BaseFragment implements HomeView{



    private PullToRefreshRecyclerView mRecyclerView;
    //private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration mItemDecoration;


    private HomePresenter mPresenter;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //第一步、注册eventbus
        EventBus.getDefault().register(this);
        mPresenter = new HomePresenterImp(this);

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
        /*View view = inflater.inflate(R.layout.item_mytweet_content,container,false);
        mRecyclerView = (PushToRefreshRecycleView) view.findViewById(R.id.recycle_view);*/
        mRecyclerView = (PullToRefreshRecyclerView) inflater.inflate(R.layout.tweet_commen_recycleview, container, false);
        initAdapter();
        mPresenter.loadData();


        return mRecyclerView;
    }

    /**
     * 初始化adapter
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void initAdapter() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.getRefreshableView().setLayoutManager(mLayoutManager);
        mItemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
        mRecyclerView.getRefreshableView().addItemDecoration(mItemDecoration);
        mRecyclerView.getRefreshableView().setAdapter(mPresenter.getHomeAdapter());
       /* mHomeAdapter.onItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                LogUtils.d(position + "");
            }
        });*/
        mRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);
        /**
         * 上下拉刷新的监听事件
         */
        mRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {

            /**
             * 下拉操作
             * @param refreshView
             */
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mPresenter.loadData();
            }

            /**
             * 上拉操作
             * @param refreshView
             */
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
               mPresenter.loadMore();
            }
        });
       /* mRecyclerView.getRefreshableView().setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            }
        });*/
    }

    /**
     * 在这里使用EventBus来传递消息
     *
     * @param event
     */
    public void onEventMainThread(Object event) {
        if (event instanceof Integer) {
            //判断类型是integer
            int id = (int) event;
            switch (id) {
                case R.id.first_menu:
                    mPresenter.requestHomeTimeLine();
                    break;
                case R.id.mine_menu:
                    mPresenter.requestUserTimeLine();
                    break;
            }
            //判断类型是String，在这里处理刷新的功能
        } else if (event instanceof String) {
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

    */

    /**
     * 在主线程中接受消息
     *//*
    public void onEventMainThread(){}*/
    @Override
    public void onDestroy() {
        super.onDestroy();
        //反注册一下
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onSuccess() {
        mRecyclerView.onRefreshComplete();
    }

    @Override
    public void onError(String error) {
        /**
         * 在请求失败的时候，调用请求数据完成的方法，要不然会一直出现下拉进度条
         */
        mRecyclerView.onRefreshComplete();
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }
}
