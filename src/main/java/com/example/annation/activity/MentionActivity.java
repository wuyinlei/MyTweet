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

public class MentionActivity extends BaseActivity implements HomeView{

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
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mention;
    }

    private void initialize() {

        rlv = (PullToRefreshRecyclerView) findViewById(R.id.rlv);
        rlv.setMode(PullToRefreshBase.Mode.BOTH);
        mLayoutManager = new LinearLayoutManager(this);
        rlv.setLayoutManager(mLayoutManager);
        mStatusEntities = new ArrayList<>();
        mItemDecoration = new DividerItemDecoration(this,LinearLayoutManager.VERTICAL);
        rlv.addItemDecotation(mItemDecoration);

        rlv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {

            }
        });
        mMentionAdapter = new MentionAdapter(mStatusEntities,this);
        rlv.setAdapter(mMentionAdapter);
        mBasePresenter = new MentionPresenterImp(this);
        mBasePresenter.loadData(true);


    }

    @Override
    public void onSuccess(List<StatusEntity> list) {
        rlv.onRefreshComplete();
        if (list != null && list.size() > 0){
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
