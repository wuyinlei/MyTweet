package com.example.annation.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.annation.R;

/**
 * Created by 若兰 on 2016/1/14.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public class LoadingView {

    private RelativeLayout mRelativeLayout;
    private View mView;
    private LayoutInflater mInflater;
    private ImageView ivLoading;
    private TextView tvLoading;
    private Context mContext;
    private String mTag = "LoadingView";

    public LoadingView(RelativeLayout mRelativeLayout) {
        this.mRelativeLayout = mRelativeLayout;
        mContext = mRelativeLayout.getContext();
        mInflater = LayoutInflater.from(mContext);
        mView = mInflater.inflate(R.layout.layout_loading, null);
        mView.setTag(mTag);
        if (mRelativeLayout.findViewWithTag(mTag) != null){
            return;
        }
        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        mRelativeLayout.addView(mView, layoutParams);
        initialize(mView);
        mView.setVisibility(View.GONE);
    }

    private void initialize(View view) {

        ivLoading = (ImageView) view.findViewById(R.id.ivLoading);
        tvLoading = (TextView) view.findViewById(R.id.tvLoading);
    }

    public void show() {
        if (null != mView) {
            mView.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(R.mipmap.bg_loading).into(ivLoading);
        }
    }

    public void hide() {
        if (null != mView) {
            mView.setVisibility(View.GONE);
        }
    }


}
