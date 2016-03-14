package com.example.annation.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.annation.R;
import com.example.annation.fragment.HomeFragment;
import com.example.annation.fragment.MessageFragment;
import com.example.annation.fragment.MineFragment;

import de.greenrobot.event.EventBus;

/**
 * Created by ruolan on 2015/12/15.
 * 一个懂得了编程乐趣的小白，希望自己
 * 能够在这个道路上走的很远，也希望自己学习到的
 * 知识可以帮助更多的人,分享就是学习的一种乐趣
 * QQ:1069584784
 * csdn:http://blog.csdn.net/wuyinlei
 */


public class HomePageActivity extends BaseActivity {


    private FrameLayout flContainer;
    private FragmentTabHost tabHost;
    private RadioGroup rgTab;
    private RadioButton rdHome, rdMessage, rdMine;
    //一个存放fragment的数组
    private Class fragment[];
    //在这里给一个menu_id,来判断是否需要menu
    private int menu_id = R.menu.menu_main;

    private boolean isExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getmToolbarX().setDisplayHomeAsUpEnabled(false).setTitle(R.string.home);
        fragment = new Class[]{HomeFragment.class, MessageFragment.class, MineFragment.class};
        //Log.d("LandingPageActivity", "到这类");
        initControl();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_page;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menu_id == -1){
            menu.clear();
        }else {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        EventBus.getDefault().post(item.getItemId());
       /* Intent intent = new Intent();
        intent.setAction("change");
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);*/
        return true;
    }

    /**
     * 初始化控件
     */
    private void initControl() {


        flContainer = (FrameLayout) findViewById(R.id.flContainer);
        tabHost = (FragmentTabHost) findViewById(R.id.tabHost);
        rgTab = (RadioGroup) findViewById(R.id.rgTab);
        rdHome = (RadioButton) findViewById(R.id.rdHome);
        rdMessage = (RadioButton) findViewById(R.id.rdMessage);
        rdMine = (RadioButton) findViewById(R.id.rdMine);
        initTabHost();
    }

    /**
     * 初始化TabHost
     */
    private void initTabHost() {
        //调用setup()方法，传入三个参数
        tabHost.setup(this, getSupportFragmentManager(), R.id.flContainer);
        for (int i = 0; i < fragment.length; i++) {
            //添加TabSpec
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(String.valueOf(i))
                    .setIndicator(String.valueOf(i));
            tabHost.addTab(tabSpec, fragment[i], null);
        }
        //设置当前的指定页面
        tabHost.setCurrentTab(0);
        //给RadioGroup设置点击监听事件
        rgTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rdHome:
                        tabHost.setCurrentTab(0);
                        menu_id = R.menu.menu_main;
                        break;
                    case R.id.rdMessage:
                        tabHost.setCurrentTab(1);
                        menu_id = -1;
                        break;
                    case R.id.rdMine:
                        tabHost.setCurrentTab(2);
                        menu_id = -1;
                        break;
                }
                //重新调用一遍onCreateOpenMenu()
                supportInvalidateOptionsMenu();
            }
        });
    }

    /**
     * 连续点击两次退出登录
     */
    public void onBackPressed(){
        if (isExit){
            this.finish();
        } else {
            Toast.makeText(this, "R.string.toast_press_again_to_exit:" + R.string.toast_press_again_to_exit, Toast.LENGTH_SHORT).show();
            isExit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit = false;
                }
            },1000);
        }

    }

}
