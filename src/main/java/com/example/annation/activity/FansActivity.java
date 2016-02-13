package com.example.annation.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.annation.R;
import com.example.annation.adapter.FansAdapter;
import com.example.annation.presenter.FansPresenter;
import com.example.annation.presenter.FansPresenterImp;
import com.example.annation.status.UserEntity;
import com.example.annation.utils.DividerItemDecoration;
import com.example.annation.view.FansView;
import com.example.annation.widget.PullToRefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FansActivity extends BaseActivity implements FansView{

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration mItemDecoration;
    private PullToRefreshRecyclerView rlv;
    private FansPresenter mPresenter;
    private FansAdapter mFansAdapter;
    private List<UserEntity> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new FansPresenterImp(this);
        String temp = getIntent().getAction();
        if (temp.equals("tvFans")){
            getmToolbarX().setTitle(R.string.lbl_my_attention);
            mPresenter.requestMyFans();
        } else if (temp.equals("tvAttentions")){
            getmToolbarX().setTitle(R.string.lbl_my_fans);
            mPresenter.requestAttentions();
        }
        initialize();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_fans;
    }

    private void initialize() {
        rlv = (PullToRefreshRecyclerView) findViewById(R.id.rlv);
        mItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        rlv.addItemDecotation(mItemDecoration);
        mLayoutManager = new LinearLayoutManager(this);
        rlv.setLayoutManager(mLayoutManager);

        mPresenter.loadData(true);
        mList = new ArrayList<>();
        mFansAdapter = new FansAdapter(mList);
        rlv.setAdapter(mFansAdapter);

    }

    @Override
    public void onSuccess(List<UserEntity> list) {
        rlv.onRefreshComplete();
        if (null != list && list.size() > 0){
            mList.clear();
            mList.addAll(list);
            mFansAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String error) {
        rlv.onRefreshComplete();
        super.onError(error);
    }
}
