package com.example.annation.fragment;
/**
 * Created by ruolan on 2015/12/15.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.annation.R;
import com.example.annation.activity.CommentActivity;
import com.example.annation.activity.LikedActivity;
import com.example.annation.activity.MentionActivity;


public class MessageFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout llAt;
    private LinearLayout llComments;
    private LinearLayout llLike;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        initialize(view);

        return view;
    }

    private void initialize(View view) {

        llAt = (LinearLayout) view.findViewById(R.id.llAt);
        llComments = (LinearLayout) view.findViewById(R.id.llComments);
        llLike = (LinearLayout) view.findViewById(R.id.llLike);
        llAt.setOnClickListener(this);
        llComments.setOnClickListener(this);
        llLike.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llAt:
                startActivity(new Intent(getContext(), MentionActivity.class));
                break;
            case R.id.llComments:
                startActivity(new Intent(getContext(), CommentActivity.class));
                break;
            case R.id.llLike:
                startActivity(new Intent(getContext(), LikedActivity.class));
                break;
        }
    }
}
