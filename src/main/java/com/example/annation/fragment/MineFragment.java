package com.example.annation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.annation.R;
import com.example.annation.presenter.ProfilePresenter;
import com.example.annation.presenter.ProfilePresenterImp;
import com.example.annation.status.UserEntity;
import com.example.annation.view.ProFileView;

/**
 * Created by ruolan on 2015/12/15.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */
public class MineFragment extends BaseFragment implements View.OnClickListener, ProFileView {
    private ImageView ivHeader;
    private TextView tvUserName;
    private TextView tvDes;
    private TextView tvAttentions;
    private TextView tvFans;
    private TextView tvLoginOut;
    private ProfilePresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ProfilePresenterImp(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_profile, container, false);
        initialize(view);
        mPresenter.loadUserInfo();
        return view;
    }

    private void initialize(View view) {

        ivHeader = (ImageView) view.findViewById(R.id.ivHeader);
        tvUserName = (TextView) view.findViewById(R.id.tvUserName);
        tvDes = (TextView) view.findViewById(R.id.tvDes);
        tvAttentions = (TextView) view.findViewById(R.id.tvAttentions);
        tvFans = (TextView) view.findViewById(R.id.tvFans);
        tvLoginOut = (TextView) view.findViewById(R.id.tvLoginOut);
        tvLoginOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onLoadUserInfo(UserEntity userEntity) {
        Glide.with(getActivity()).load(userEntity.profile_image_url).into(ivHeader);
        tvUserName.setText(userEntity.screen_name);
        tvDes.setText(userEntity.description);
    }


}
