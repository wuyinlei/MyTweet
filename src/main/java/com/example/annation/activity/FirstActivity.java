package com.example.annation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.annation.R;
import com.example.annation.bean.Tab;
import com.example.annation.fragment.FirstFragment;
import com.example.annation.fragment.SecondFragment;
import com.example.annation.fragment.ThirdFragment;
import com.example.annation.widget.FragmentTabHost;
import com.example.annation.widget.ToolBarX;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuyinlei on 2015/12/8.
 */
public class FirstActivity extends BaseActivity {

    private RadioGroup radio_custom;
    private Toolbar mToolbar;
    private ToolBarX mToolbaxX;


    private LayoutInflater mInflater;

    public FragmentTabHost mTabHost;
    private ImageView img;
    private TextView text;
    private List<Tab> mTabs = new ArrayList<>(3);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        radio_custom = (RadioGroup) getLayoutInflater().inflate(R.layout.view_custom, null);

        mToolbaxX = getmToolbarX();
        mToolbaxX.setDisplayHomeAsUpEnabled(true)
               /* .setTitle("我是标题")
                .setSubTitle("我是子标题")
                .setNavigationIcon(R.drawable.actionbar_add_icon)
                .setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(FirstActivity.this, "我被点击了", Toast.LENGTH_SHORT).show();
                    }
                })*/.setCustom(radio_custom);

         initTab();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


   /**
     * 初始化Tab
     */
    private void initTab() {
        Tab home = new Tab(R.drawable.select_icon_home, FirstFragment.class, R.string.home);
        Tab mine = new Tab(R.drawable.select_icon_mine, SecondFragment.class, R.string.mine);
        Tab find = new Tab(R.drawable.select_icon_find, ThirdFragment.class, R.string.find);

        mTabs.add(home);
        mTabs.add(mine);
        mTabs.add(find);

        mInflater = LayoutInflater.from(this);
        //这个是“假的”tabhost，在这里用的是系统自带的tabhost，就是占个位置
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        //第二步是:调用setup（）方法
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        //第三步是:添加tabSpec
        for (Tab t : mTabs) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(getString(t.getTitle()));
            tabSpec.setIndicator(builderIndiator(t));
            mTabHost.addTab(tabSpec, t.getFragment(), null);
        }
        //去掉分割线
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);

        //设置Tabhost监听事件
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

            }
        });

        //设置当前按下的是第一个
        mTabHost.setCurrentTab(0);

    }
    /**
     * 创建Indiator，就是tabhost上的图片和title
     * @param tab
     * @return
     */
    private View builderIndiator(Tab tab) {

        //把Indicator需要的布局读取出来
        View view = mInflater.inflate(R.layout.tab_indicator, null);
        img = (ImageView) view.findViewById(R.id.icon_tab);
        text = (TextView) view.findViewById(R.id.text_indicator);
        img.setBackgroundResource(tab.getImage());
        text.setText(tab.getTitle());
        return view;
    }
}
