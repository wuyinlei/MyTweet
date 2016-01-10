package com.example.annation.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

import com.example.annation.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 若兰 on 2016/1/10.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public class RichTextUtils {

    /**
     * 匹配http连接
     */
    private static String regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
    private static final Pattern WEB_PATTERN = Pattern.compile(regex);

    /**
     * 匹配@
     */
    private static final Pattern MENTION_PATTERN = Pattern.compile("@[\\u4e00-\\u9fa5a-zA-Z0-9_-]+");

    public static SpannableString getRichText(final Context context, String text) {
        SpannableString string = null;
        if (!TextUtils.isEmpty(text)) {
            //设置超链接的颜色是蓝色
            final int link_color = ContextCompat.getColor(context, R.color.tweet_blue);
            //设置@的颜色是红色
            final int mention_color = ContextCompat.getColor(context, R.color.tweet_red);
            string = new SpannableString(text);

            /**
             * 在这里处理超链接的逻辑
             */
            Matcher matcher = WEB_PATTERN.matcher(text);
            while (matcher.find()) {
                final String url = matcher.group();
                //如果知道了匹配http的连接
                string.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {

                        //跳转超链接
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        context.startActivity(intent);
                        //Log.d("RichTextUtils", url);
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(false);
                        ds.setColor(link_color);
                    }
                }, matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }


            /**
             * 在这里处理匹配@的逻辑
             */
            final Matcher matcher_aite = MENTION_PATTERN.matcher(text);
            while (matcher_aite.find()) {
                final String url = matcher_aite.group();
                //如果知道了匹配http的连接
                string.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {

                        Log.d("RichTextUtils", url);
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(false);
                        ds.setColor(mention_color);
                    }
                }, matcher_aite.start(), matcher_aite.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

        }
        return string;
    }

}
