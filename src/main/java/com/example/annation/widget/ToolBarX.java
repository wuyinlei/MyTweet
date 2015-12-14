package com.example.annation.widget;

import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.annation.R;


/**
 * Created by ruolan on 2015/12/14.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远 ，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public class ToolBarX {

    private Toolbar mToolbar;
    private AppCompatActivity mActivity;
    private ActionBar mActionBar;
    private RelativeLayout rlCustom;

    public ToolBarX(Toolbar mToolbar, final AppCompatActivity mActivity) {
        this.mToolbar = mToolbar;
        //这个是Toolbar中的一个相对布局，在这里我们可以在Toolbar中加入我们想要的效果
        rlCustom = (RelativeLayout) mToolbar.findViewById(R.id.rlCustom);
        this.mActivity = mActivity;
        //通过setSupportActionBar()把Toolbar设置到actionBar中
        mActivity.setSupportActionBar(mToolbar);
        //然后actionBar通过getSupportActionBar()来获取到Toolbar
        mActionBar = mActivity.getSupportActionBar();
    }

    /**
     * 设置标题    通过传入字符串
     * @param title
     * @return
     */
    public ToolBarX setTitle(String title) {
        mActionBar.setTitle(title);
        return this;
    }

    /**
     * 设置标题    通过资源值
     * @param resId
     * @return
     */
    public ToolBarX setTitle(int resId) {
        mActionBar.setTitle(resId);
        return this;
    }

    /**
     * 设置子标题  通过传入字符串
     * @param title
     * @return
     */
    public ToolBarX setSubTitle(String title) {
        mActionBar.setSubtitle(title);
        return this;
    }


    /**
     * 设置子标题   通过资源值
     * @param resId
     * @return
     */
    public ToolBarX setSubTitle(int resId) {
        mActionBar.setSubtitle(resId);
        return this;
    }

    /**
     * 给Nacigarion设置点击监听事件
     * @param listener
     * @return
     */
    public ToolBarX setNavigationOnClickListener(View.OnClickListener listener) {
        mToolbar.setNavigationOnClickListener(listener);
        return this;
    }

    /**
     * 给Navigation设置图片   通过资源值
     * @param resId
     * @return
     */
    public ToolBarX setNavigationIcon(int resId) {
        mToolbar.setNavigationIcon(resId);
        return this;
    }

    /**
     * 给Navigation设置图片    通过传入的drawable
     * @param icon
     * @return
     */
    public ToolBarX setNavigationIcon(Drawable icon) {
        mToolbar.setNavigationIcon(icon);
        return this;
    }

    public ToolBarX setDisplayHomeAsUpEnabled(boolean show){
        mActionBar.setDisplayHomeAsUpEnabled(show);
        return this;
    }

    /**
     * 给ToilbarX设置自定义布局
     * @param view
     * @return
     */
    public ToolBarX setCustom(View view){
        rlCustom.removeAllViews();
        rlCustom.addView(view);
        return this;
    }


}
