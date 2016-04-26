package com.marvin.lop.ui;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.marvin.lop.R;
import com.marvin.lop.config.Constants;
import com.marvin.lop.ui.base.BaseActivity;

import cn.bmob.v3.Bmob;

/**
 * Created by Marvin on 2016/3/19.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 * Description: 应用程序闪屏界面，程序入口界面
 */
public class SplashActivity extends BaseActivity{

    public static final String TAG = SplashActivity.class.getSimpleName();

    private ImageView mSplashItem_iv = null;

//    AppManager am;

    @Override
    protected void findViewById() {
        mSplashItem_iv = (ImageView) findViewById(R.id.splash_loading_item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        am = AppManager.getInstance();
//        am.addActivity(this);

        // 初始化Bmob SDK
        Bmob.initialize(this, Constants.BmobConfig.ApplicationID);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Constants.SCREEN_DENSITY = metrics.density;
        Constants.SCREEN_HEIGHT = metrics.heightPixels;
        Constants.SCREEN_WIDTH = metrics.widthPixels;

        mHandler = new Handler(getMainLooper());
        findViewById();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        am.killActivity(this);
    }

    @Override
    protected void initView() {
        Animation translate = AnimationUtils.loadAnimation(this, R.anim.splash_loading);
        translate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // 启动HomeActivity，相当于Intent
                openActivity(HomeActivity.class);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                SplashActivity.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mSplashItem_iv.setAnimation(translate);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
    }
}
