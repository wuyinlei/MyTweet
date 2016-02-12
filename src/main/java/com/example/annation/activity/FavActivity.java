package com.example.annation.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.annation.R;
import com.example.annation.adapter.FavListAdapter;
import com.example.annation.presenter.BasePresenter;
import com.example.annation.presenter.FavPresenterImp;
import com.example.annation.status.FavEntity;
import com.example.annation.utils.DividerItemDecoration;
import com.example.annation.view.FavoriteView;
import com.example.annation.widget.PullToRefreshRecyclerView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

public class FavActivity extends BaseActivity implements FavoriteView{

    private PullToRefreshRecyclerView rlv;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration mItemDecoration;
    private List<FavEntity> mDataSet;
    private FavListAdapter mAdapter;
    private BasePresenter mBasePresenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getmToolbarX().setTitle(R.string.lbl_like);
        initialize();
    }

    public int getLayoutId() {
        return R.layout.activity_liked;
    }

    public void onSuccess(List<FavEntity> list) {
        rlv.onRefreshComplete();
        if(null!=list&&list.size()>0){
            mDataSet.clear();
            mDataSet.addAll(list);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void initialize() {

        rlv = (PullToRefreshRecyclerView) findViewById(R.id.rlv);
        rlv = (PullToRefreshRecyclerView) findViewById(R.id.rlv);
        rlv.setMode(PullToRefreshBase.Mode.BOTH);
        mLayoutManager = new LinearLayoutManager(this);
        rlv.setLayoutManager(mLayoutManager);
        mDataSet = new ArrayList<>();
        mItemDecoration = new DividerItemDecoration(this,LinearLayoutManager.VERTICAL);
        rlv.addItemDecotation(mItemDecoration);
        rlv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mBasePresenter.loadData(false);

            }

            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mBasePresenter.loadMore(false);
            }
        });
        mAdapter = new FavListAdapter(mDataSet,this);
        rlv.setAdapter(mAdapter);
        mBasePresenter = new FavPresenterImp(this);
        mBasePresenter.loadData(true);
    }
}
