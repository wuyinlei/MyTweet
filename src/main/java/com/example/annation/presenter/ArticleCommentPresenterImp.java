package com.example.annation.presenter;

import android.util.Log;

import com.example.annation.http.BaseNetWork;
import com.example.annation.http.HttpResponse;
import com.example.annation.status.CommentEntity;
import com.example.annation.status.StatusEntity;
import com.example.annation.uri.Contants;
import com.example.annation.uri.ParameterKeySet;
import com.example.annation.utils.PreferenceUtils;
import com.example.annation.view.ArticleCommentView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.sina.weibo.sdk.net.WeiboParameters;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 若兰 on 2016/1/14.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */

public class ArticleCommentPresenterImp implements ArticleCommentPresenter {


    private List<CommentEntity> mDataSet;
    private PreferenceUtils mPres;

    private ArticleCommentView commentView;
    private int page = 1;


    public ArticleCommentPresenterImp( ArticleCommentView commentView) {
        this.commentView = commentView;
        mPres = PreferenceUtils.getInstance(commentView.getActivity().getApplicationContext());
      mDataSet = new ArrayList<>();
    }

    @Override
    public StatusEntity getEntity() {
        return (StatusEntity) commentView.getActivity().getIntent().getSerializableExtra(
                StatusEntity.class.getSimpleName()
        );
    }

    @Override
    public void loadData() {

        page = 1;
        loadData(false);
    }

    @Override
    public void loadMore() {
        page ++;
        loadData(true);
    }

    private void loadData(final boolean loadMore) {

        new BaseNetWork(commentView.getActivity(), Contants.API.COMMENT_SHOW) {
            @Override
            public WeiboParameters onPrepares() {
                WeiboParameters parameters = new WeiboParameters(Contants.APP_KEY);
                parameters.put(ParameterKeySet.ID,getEntity().id);
                parameters.put(ParameterKeySet.PAGE,1);
                parameters.put(ParameterKeySet.COUNT,10);
                parameters.put(ParameterKeySet.AUTH_ACCESS_TOKEN,mPres.getToken().getToken());

                return parameters;
            }

            @Override
            public void onFinish(HttpResponse response, boolean success) {
                if (success) {
                    Log.d("ArticleCommentActivity", "response:" + response.response);
                    Type type = new TypeToken<ArrayList<CommentEntity>>(){}.getType();
                    JsonParser parser = new JsonParser();
                    JsonElement element = parser.parse(response.response);
                    if (element.isJsonArray()){
                        List<CommentEntity> list = new Gson().fromJson(element,type);
                        if (!loadMore){
                            mDataSet.clear();
                        }
                        mDataSet.addAll(list);
                        commentView.onSuccess(mDataSet);
                    }
                }
            }
        }.get();


    }


}
