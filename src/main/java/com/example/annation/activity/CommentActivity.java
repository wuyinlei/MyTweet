package com.example.annation.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.annation.R;
import com.example.annation.adapter.CommentListAdapter;
import com.example.annation.presenter.BasePresenter;
import com.example.annation.presenter.CommentPresenterImp;
import com.example.annation.status.CommentEntity;
import com.example.annation.utils.DividerItemDecoration;
import com.example.annation.view.CommentView;
import com.example.annation.widget.PullToRefreshRecyclerView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends BaseActivity implements CommentView {

    private PullToRefreshRecyclerView rlv;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration mItemDecoration;
    private List<CommentEntity> mDataSet;
    private CommentListAdapter mAdapter;
    private BasePresenter mBasePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getmToolbarX().setTitle(R.string.lbl_comment);
        initialize();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_comment;
    }

    private void initialize() {

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
        mAdapter = new CommentListAdapter(mDataSet, this);
        rlv.setAdapter(mAdapter);
        mBasePresenter = new CommentPresenterImp(this);
        mBasePresenter.loadData(true);



    }
    public void onSuccess(List<CommentEntity> list) {
        rlv.onRefreshComplete();
        if(null!=list&&list.size()>0){
            mDataSet.clear();
            mDataSet.addAll(list);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void onError(String error) {
        super.onError(error);
        rlv.onRefreshComplete();
    }
}
