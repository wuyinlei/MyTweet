package com.example.annation.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.annation.R;
import com.example.annation.http.BaseNetWork;
import com.example.annation.http.HttpResponse;
import com.example.annation.uri.Contants;
import com.example.annation.uri.ParameterKeySet;
import com.example.annation.utils.PreferenceUtils;
import com.example.annation.utils.RichTextUtils;
import com.sina.weibo.sdk.net.WeiboParameters;

import de.greenrobot.event.EventBus;

public class RepostActivity extends BaseActivity {

    private EditText etContent;

    /**
     * 转发人的id
     */
    private long id;

    /**
     * 转发的内容
     */
    private String content;

    private String action;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取到转发人的id
        id = getIntent().getLongExtra(ParameterKeySet.ID, 0);


        action = getIntent().getAction();
        switch (action) {
            //评论
            case "COMMENT":
                getmToolbarX().setTitle(R.string.lbl_comment);
                url = Contants.API.COMMENT_CREATE;
                break;
            //转发
            case "REPOST":
                getmToolbarX().setTitle(R.string.lbl_post);

                //获取到转发的内容
                content = getIntent().getStringExtra(ParameterKeySet.STATUS);
                url = Contants.API.STATUS_REPOST;
                break;
        }

        initialize();
    }

    @Override
    public int getLayoutId() {

        /**
         * 控制反转的方式加载布局
         */
        return R.layout.activity_repost;
    }

    private void initialize() {

        etContent = (EditText) findViewById(R.id.etContent);
        //判断转发内容是不是为空
        if (!TextUtils.isEmpty(content)) {
            etContent.setText(RichTextUtils.getRichText(getApplicationContext(), "//" + content));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_repost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        postRepost(etContent.getText().toString());
        return true;
    }


    /**
     * 转发内容的逻辑处理
     *
     * @param string
     */
    private void postRepost(final String string) {
        new BaseNetWork(this, url) {
            @Override
            public WeiboParameters onPrepares() {
                WeiboParameters weiboParameters = new WeiboParameters(Contants.APP_KEY);
                weiboParameters.put(ParameterKeySet.AUTH_ACCESS_TOKEN, PreferenceUtils.getInstance
                        (getApplicationContext()).getToken().getToken());
                if (action.equals("REPOST")) {
                    weiboParameters.put(ParameterKeySet.STATUS,  string);
                } else if (action.equals("COMMENT")) {
                    weiboParameters.put(ParameterKeySet.COMMENT, string);
                }
                weiboParameters.put(ParameterKeySet.ID, id);
                return weiboParameters;
            }

            @Override
            public void onFinish(HttpResponse response, boolean success) {
                if (success) {
                    Toast.makeText(RepostActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post("onFinish");
                    finish();
                }
            }
        }
                //不要忘记这个post了，要不然提交不了
                .post();
    }
}
