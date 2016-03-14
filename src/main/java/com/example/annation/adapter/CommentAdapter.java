package com.example.annation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 若兰 on 2016/2/13.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public abstract class CommentAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {

    private List<T> mTList;
    private int layoutId;

    public CommentAdapter(List<T> TList, int layoutId) {
        mTList = TList;
        this.layoutId = layoutId;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layoutId, null);
        return new CommonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        convert(holder, mTList.get(position));
    }

    public abstract void convert(CommonViewHolder holder, T t);

    @Override
    public int getItemCount() {
        return mTList.size();
    }
}
