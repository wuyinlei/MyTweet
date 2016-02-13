package com.example.annation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.annation.utils.CircleTransform;

/**
 * Created by 若兰 on 2016/2/13.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public class CommonViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> views;
    private View convertView;
    private Context mContext;

    public CommonViewHolder(View itemView) {
        super(itemView);
        views = new SparseArray<>();
        convertView = itemView;
        mContext = itemView.getContext();
    }

    protected <T extends View>T getView(int viewId){
        View view = views.get(viewId);
        if (view == null){
            view = convertView.findViewById(viewId);
            views.put(viewId,view);
        }
        return (T) view;
    }

    public void setText(int resId, String text) {
        TextView view = getView(resId);
        view.setText(text);
    }

    public void setImageByUrl(int resId, String url) {
        ImageView imageView = getView(resId);
        Glide.with(mContext).load(url).asBitmap().into(imageView);
    }
    public void setImageByUrlToCircle(int resId, String url) {
        ImageView imageView = getView(resId);
        Glide.with(mContext).load(url).asBitmap().transform(new CircleTransform(mContext)).into(imageView);
    }


}
