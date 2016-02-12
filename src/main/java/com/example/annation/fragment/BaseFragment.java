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
import android.support.v4.app.Fragment;

import com.example.annation.R;
import com.example.annation.view.BaseView;


public class BaseFragment extends Fragment implements BaseView {

    @Override
    public void startActivity(Intent intent) {
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.anmi_in_right_left, R.anim.anmi_out_right_left);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        getActivity().startActivityForResult(intent,requestCode);
        getActivity().overridePendingTransition(R.anim.anmi_in_right_left, R.anim.anmi_out_right_left);
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
