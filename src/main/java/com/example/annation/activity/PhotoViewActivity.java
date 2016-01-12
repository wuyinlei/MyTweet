package com.example.annation.activity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.annation.R;
import com.example.annation.status.PicUrlsEntity;

import uk.co.senab.photoview.PhotoView;

public class PhotoViewActivity extends BaseActivity {


    private PhotoView imageview;
    private PicUrlsEntity pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getmToolbarX().hide();
        pic = (PicUrlsEntity) getIntent().getSerializableExtra(PicUrlsEntity.class.getSimpleName());
        initialize();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_photo_view;
    }

    /**
     * 初始化布局控件
     */
    private void initialize() {

        imageview = (PhotoView) findViewById(R.id.image_view);
        Glide.with(this).load(pic.original_pic).asBitmap().fitCenter().into(imageview);

    }
}
