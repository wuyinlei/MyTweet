package com.example.annation.http;

import android.content.Context;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;


/**
 * Created by ruolan on 2015/12/16.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */
public abstract class BaseNetWork {

    private AsyncWeiboRunner mAsyncWeiboRunner;
    private String url;

    public BaseNetWork(Context context, String url) {
        mAsyncWeiboRunner = new AsyncWeiboRunner(context);
        this.url = url;
    }

    private RequestListener mRequestListener = new RequestListener() {
        @Override
        public void onComplete(String s) {
            /* {
                "request" : "/statuses/home_timeline.json",
                    "error_code" : "20502",
                    "error" : "Need you follow uid."
            }*/
            boolean success = false;
            HttpResponse response = new HttpResponse();
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(s);
            if (element.isJsonObject()) {
                JsonObject object = element.getAsJsonObject();
                if (object.has("error_code")) {
                    response.code = object.get("error_code").getAsInt();
                }
                if (object.has("error")) {
                    response.message = object.get("error").getAsString();
                }
                if (object.has("statuses")) {
                    response.response = object.get("statuses").toString();
                    success = true;
                } else if (object.has("users")) {
                    response.response = object.get("users").toString();
                    success = true;
                } else if (object.has("comments")) {
                    response.response = object.get("comments").toString();
                    success = true;
                } else {
                    response.response = s;
                    success = true;
                }

            }
            onFinish(response, success);
        }

        @Override
        public void onWeiboException(WeiboException e) {
            HttpResponse response = new HttpResponse();
            response.message = e.getMessage();
            onFinish(response, false);
        }
    };


    public void get() {
        mAsyncWeiboRunner.requestAsync(url, onPrepares(), "GET", mRequestListener);
    }

    public void post() {
        mAsyncWeiboRunner.requestAsync(url, onPrepares(), "POST", mRequestListener);

    }

    public void delete() {
        mAsyncWeiboRunner.requestAsync(url, onPrepares(), "DELETE", mRequestListener);
    }

    public abstract WeiboParameters onPrepares();

    public abstract void onFinish(HttpResponse response, boolean success);
}
