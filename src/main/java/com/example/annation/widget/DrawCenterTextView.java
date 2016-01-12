package com.example.annation.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by 若兰 on 2016/1/10.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public class DrawCenterTextView extends TextView {

    public DrawCenterTextView(Context context) {
        this(context, null);
    }

    public DrawCenterTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawCenterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DrawCenterTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable[] drawables = getCompoundDrawables();
        Drawable drawableLeft = drawables[0];
        if (null != drawableLeft) {

            setGravity(Gravity.START);
            //得到图片的宽度
            int drawWidth = drawableLeft.getIntrinsicWidth();

            int drawablePadding = getCompoundDrawablePadding();
            //得到文本的宽度
            int textWidth = (int) getPaint().measureText(getText().toString());

            //得到整个文本的宽度
            int bodeWidth = drawWidth + textWidth + drawablePadding;

            canvas.translate((getWidth() - bodeWidth) / 2, 0);
        }

        Drawable drawableRight = drawables[2];
        if (null != drawableRight) {

            setGravity(Gravity.END);
            //得到图片的宽度
            int drawWidth = drawableRight.getIntrinsicWidth();

            int drawablePadding = getCompoundDrawablePadding();
            //得到文本的宽度
            int textWidth = (int) getPaint().measureText(getText().toString());

            //得到整个文本的宽度
            int bodeWidth = drawWidth + textWidth + drawablePadding;

            canvas.translate(-(getWidth() - bodeWidth) / 2, 0);
        }

        super.onDraw(canvas);
    }


}
