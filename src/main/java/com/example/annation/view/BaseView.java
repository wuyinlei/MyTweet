package com.example.annation.view;

import android.app.Activity;

/**
 * Created by 若兰 on 2016/1/14.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public interface BaseView {

    Activity getActivity();

    /**
     * 请求失败的时候
     * @param error
     */
    void onError(String error);

    /**
     * 展示等待对话框
     */
    void showLoading();

    /**
     * 隐藏等待对话框
     */
    void hideLoading();

}
