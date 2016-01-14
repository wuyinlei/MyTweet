package com.example.annation.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.annation.R;
import com.example.annation.adapter.ArticleCommentAdapter;
import com.example.annation.presenter.ArticleCommentPresenter;
import com.example.annation.presenter.ArticleCommentPresenterImp;
import com.example.annation.status.CommentEntity;
import com.example.annation.status.StatusEntity;
import com.example.annation.utils.DividerItemDecoration;
import com.example.annation.view.ArticleCommentView;
import com.example.annation.widget.PullToRefreshRecyclerView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

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

public class ArticleCommentActivity extends BaseActivity implements ArticleCommentView{

    private StatusEntity mEntities;
    private PullToRefreshRecyclerView recycleview;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration mItemDecoration;

    private ArticleCommentAdapter articleCommentAdapter;
    private List<CommentEntity> mDataSet;

    private ArticleCommentPresenter commentPresenter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getmToolbarX().setTitle(R.string.article_comment_title);
        commentPresenter = new ArticleCommentPresenterImp(this);
        mEntities = (StatusEntity) getIntent().getSerializableExtra(StatusEntity.class.getSimpleName());
        mDataSet = new ArrayList<>();
        initialize();
    }

    @Override
    public int getLayoutId() {
        return R.layout.tweet_commen_recycleview;
    }

    private void initialize() {

        recycleview = (PullToRefreshRecyclerView) findViewById(R.id.recycle_view);
        mLayoutManager = new LinearLayoutManager(this);
        recycleview.setLayoutManager(mLayoutManager);
        mItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);

        recycleview.addItemDecotation(mItemDecoration);

        articleCommentAdapter = new ArticleCommentAdapter(this,mEntities,mDataSet);
        recycleview.setAdapter(articleCommentAdapter);


        recycleview.setMode(PullToRefreshBase.Mode.BOTH);
        commentPresenter.loadData();
        recycleview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                commentPresenter.loadData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                commentPresenter.loadMore();
            }
        });



    }


    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(List<CommentEntity> list) {
        recycleview.onRefreshComplete();
        if (null != list && list.size() > 0){
            mDataSet.clear();
            mDataSet.addAll(list);
            articleCommentAdapter.notifyDataSetChanged();
        }
    }
}
