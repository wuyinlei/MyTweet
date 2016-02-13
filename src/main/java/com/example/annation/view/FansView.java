package com.example.annation.view;

import com.example.annation.status.UserEntity;

import java.util.List;

/**
 * Created by 若兰 on 2016/2/13.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public interface FansView extends BaseView {
    void onSuccess(List<UserEntity> list);
}
