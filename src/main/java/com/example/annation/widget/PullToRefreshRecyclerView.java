package com.example.annation.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

/**
 * Created by 若兰 on 2016/1/12.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public class PullToRefreshRecyclerView extends PullToRefreshBase<RecyclerView> {
/*
    public PullToRefreshRecyclerView(Context context) {
        super(context);
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode, AnimationStyle animStyle) {
        super(context, mode, animStyle);
    }


    @Override
    public Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        RecyclerView recyclerView = new RecyclerView(context, attrs);
        recyclerView.setId(com.handmark.pulltorefresh.library.R.id.recyclerview);
        return recyclerView;
    }

    @Override
    protected boolean isReadyForPullEnd() {
        Log.d("PushToRefreshRecycleVie", "isReadyForPullEnd():" + isReadyForPullEnd());
        return false;
    }

    @Override
    protected boolean isReadyForPullStart() {
      *//*  LogUtils.d("isReadyForPullStart");
        //如果我们的这个没有孩子了，就让他刷新
        if (mRefreshableView.getChildCount() == 0) {
            return true;
        }

        View view = mRefreshableView.getChildAt(0);
        int position = mRefreshableView.getChildLayoutPosition(view);
        if (position == 0) {
            return view.getTop() == mRefreshableView.getTop();
        }*//*
        return false;
    }*/

    public PullToRefreshRecyclerView(Context context) {
        super(context);
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode, AnimationStyle animStyle) {
        super(context, mode, animStyle);
    }

    public Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        RecyclerView recyclerView = new RecyclerView(context, attrs);
        recyclerView.setId(com.handmark.pulltorefresh.library.R.id.recyclerview);
        return recyclerView;
    }

    /**
     * 这个是上拉加载的实现逻辑
     * @return
     */
    protected boolean isReadyForPullEnd() {
        //判断时候是有子item，如果没有就返回true
        if (mRefreshableView.getChildCount() == 0) {
            return true;
        }
        int count = mRefreshableView.getChildCount();
        View view = mRefreshableView.getChildAt(count - 1);
        int position = mRefreshableView.getChildLayoutPosition(view);
        //在view中显示的是最下面的一个，并且mRefreshableView也是最下面的一个
        //可以显示上拉加载更多的
        if(position >= mRefreshableView.getAdapter().getItemCount()-1)
        {
            return view.getBottom() <= mRefreshableView.getBottom();
        }
        return false;
    }

    /**
     * 这个是下拉刷新的实现
     * @return
     */
    protected boolean isReadyForPullStart() {
        //如果在这里没有子孩子，也就是说没有数据，可以下拉刷新
        if (mRefreshableView.getChildCount() == 0) {
            return true;
        }
        View view = mRefreshableView.getChildAt(0);
        int position = mRefreshableView.getChildLayoutPosition(view);
        //还有就是如果position是0，并且我的view的最上面的和mRefreshableView的最上面的是一样的
        //那么也可以返回刷新的操作
        if (position == 0) {
            return view.getTop() == mRefreshableView.getTop();
        }
        return false;
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager){
        mRefreshableView.setLayoutManager(manager);
    }

    public void addItemDecotation(RecyclerView.ItemDecoration itemDecoration){
        mRefreshableView.addItemDecoration(itemDecoration);
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        mRefreshableView.setAdapter(adapter);
    }
}
