package com.example.annation.view;

import com.example.annation.status.FavEntity;

import java.util.List;

/**
 * Created by 若兰 on 2016/2/12.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public interface FavoriteView extends BaseView{
    void onSuccess(List<FavEntity> list);
}
