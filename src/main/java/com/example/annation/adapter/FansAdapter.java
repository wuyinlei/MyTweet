package com.example.annation.adapter;

import com.example.annation.R;
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

public class FansAdapter extends CommenAdapter<UserEntity>{



    public FansAdapter(List<UserEntity> TList, int layoutId) {
        super(TList, R.layout.item_fans);
    }

    public FansAdapter(List<UserEntity> list){
        super(list,R.layout.item_fans);
    }

    @Override
    public void convert(CommonViewHolder holder, UserEntity userEntity) {
        holder.setImageByUrlToCircle(R.id.ivHeader,userEntity.profile_image_url);
        holder.setText(R.id.tvUserName, userEntity.screen_name);
        holder.setText(R.id.tvDes,userEntity.description);
    }
}