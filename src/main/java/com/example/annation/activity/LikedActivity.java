package com.example.annation.activity;

import android.os.Bundle;

import com.example.annation.R;

public class LikedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getmToolbarX().setTitle(R.string.lbl_like);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_liked;
    }
}
