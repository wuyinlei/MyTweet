package com.example.annation.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.annation.R;
import com.example.annation.adapter.MentionAdapter;
import com.example.annation.presenter.BasePresenter;
import com.example.annation.presenter.MentionPresenterImp;
import com.example.annation.status.StatusEntity;
import com.example.annation.utils.DividerItemDecoration;
import com.example.annation.view.HomeView;
import com.example.annation.widget.PullToRefreshRecyclerView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

public class MentionActivity extends BaseActivity implements HomeView {

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration mItemDecoration;
    private List<StatusEntity> mStatusEntities;
    private PullToRefreshRecyclerView rlv;
    private MentionAdapter mMentionAdapter;
    private BasePresenter mBasePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getmToolbarX().setTitle(R.string.at_me);
        initialize();
        initController();
        initData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mention;
    }

    /**
     * 初始化控件
     */
    private void initialize() {

        rlv = (PullToRefreshRecyclerView) findViewById(R.id.rlv);
    }

    /**
     * 数据加载
     */
    private void initData() {
        rlv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mBasePresenter.loadData(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mBasePresenter.loadMore(false);
            }
        });
        mMentionAdapter = new MentionAdapter(mStatusEntities, this);
        rlv.setAdapter(mMentionAdapter);
        mBasePresenter = new MentionPresenterImp(this);
        mBasePresenter.loadData(true);
    }

    /**
     * 初始化recycleview的各种设置
     */
    private void initController() {
        rlv.setMode(PullToRefreshBase.Mode.BOTH);
        mLayoutManager = new LinearLayoutManager(this);
        rlv.setLayoutManager(mLayoutManager);
        mStatusEntities = new ArrayList<>();
        mItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        rlv.addItemDecotation(mItemDecoration);
    }

    @Override
    public void onSuccess(List<StatusEntity> list) {
        rlv.onRefreshComplete();
        if (list != null && list.size() > 0) {
            mStatusEntities.clear();
            mStatusEntities.addAll(list);
            mMentionAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String error) {
        super.onError(error);
        rlv.onRefreshComplete();
    }
}
