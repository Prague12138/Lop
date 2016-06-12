package com.marvin.lop.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TabHost;

import com.marvin.lop.R;

import cn.bmob.newim.notification.BmobNotificationManager;

/**
 * Created by Marvin on 2016/3/19.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 * Description: 程序Home界面
 */
public class HomeActivity extends TabActivity {

    public static final String TAG = HomeActivity.class.getSimpleName();

    private RadioGroup mTabButtonGroup;
    private TabHost mTabHost;

    public static final String TAB_MAIN = "MAIN_ACTIVITY";
    public static final String TAB_SEARCH = "SEARCH_ACTIVITY";
    //    public static final String TAB_CATEGORY = "CATEGORY_ACTIVITY";
//    public static final String TAB_CART = "CART_ACTIVITY";
    public static final String TAB_PERSONAL = "PERSONAL_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViewById();
        initView();
    }

    private void findViewById() {
        mTabButtonGroup = (RadioGroup) findViewById(R.id.home_radio_button_group);
    }

    private void initView() {
        mTabHost = getTabHost();

        Intent i_main = new Intent(this, IndexActivity.class);
        Intent i_search = new Intent(this, SearchActivity.class);
//        Intent i_category = new Intent(this, CategoryActivity.class);
//        Intent i_cart = new Intent(this, CartActivity.class);
        Intent i_personal = new Intent(this, PersonalActivity.class);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_MAIN).setIndicator(TAB_MAIN).setContent(i_main));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_SEARCH).setIndicator(TAB_SEARCH).setContent(i_search));
//        mTabHost.addTab(mTabHost.newTabSpec(TAB_CATEGORY).setIndicator(TAB_CATEGORY).setContent(i_category));
//        mTabHost.addTab(mTabHost.newTabSpec(TAB_CART).setIndicator(TAB_CART).setContent(i_cart));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_PERSONAL).setIndicator(TAB_PERSONAL).setContent(i_personal));

        mTabHost.setCurrentTabByTag(TAB_MAIN);

        mTabButtonGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.home_tab_main:
                        mTabHost.setCurrentTabByTag(TAB_MAIN);
                        break;
                    case R.id.home_tab_search:
                        mTabHost.setCurrentTabByTag(TAB_SEARCH);
                        break;
//                    case R.id.home_tab_category:
//                        mTabHost.setCurrentTabByTag(TAB_CATEGORY);
//                        break;
//                    case R.id.home_tab_cart:
//                        mTabHost.setCurrentTabByTag(TAB_CART);
//                        break;
                    case R.id.home_tab_personal:
                        mTabHost.setCurrentTabByTag(TAB_PERSONAL);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
//        Toast.makeText(HomeActivity.this, "TabHost----Response", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BmobNotificationManager.getInstance(HomeActivity.this).clearObserver();
    }
}
