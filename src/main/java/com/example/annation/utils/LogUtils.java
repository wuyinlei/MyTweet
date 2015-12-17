package com.example.annation.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by ruolan on 2015/12/16.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */
public class LogUtils {

    private static final String TAG = "wuyinlei";

    public static void e(String message) {
        if (!TextUtils.isEmpty(message)) {
            Log.e(TAG, message);
        }
    }

    public static void d(String message) {
        if (!TextUtils.isEmpty(message)) {
            Log.e(TAG, message);
        }
    }

}
