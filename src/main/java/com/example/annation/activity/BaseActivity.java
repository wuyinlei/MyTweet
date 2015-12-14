package com.example.annation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.annation.R;
import com.example.annation.widget.ToolBarX;


public abstract class BaseActivity extends AppCompatActivity {

    private RelativeLayout rlContent;
    private Toolbar mToolbar;
    private ToolBarX mToolbarX;

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.anim_in_right_left, R.anim.anim_out_right_left);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        overridePendingTransition(R.anim.anim_in_right_left,R.anim.anim_out_right_left);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        rlContent = (RelativeLayout) findViewById(R.id.rlContent);
        View view = getLayoutInflater().inflate(getLayoutId(), rlContent, false);  //IOC   控制反转  在父类中调用子类的实现
        rlContent.addView(view);
        mToolbarX = new ToolBarX(mToolbar,this);
    }

    public abstract int getLayoutId();

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_in_left_right, R.anim.anim_out_left_right);
    }

    public ToolBarX getmToolbarX() {
        if (mToolbarX == null)
            mToolbarX = new ToolBarX(mToolbar,this);
        return mToolbarX;
    }
}
