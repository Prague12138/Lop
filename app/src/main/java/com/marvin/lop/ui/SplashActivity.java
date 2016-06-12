package com.marvin.lop.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.marvin.lop.R;
import com.marvin.lop.config.Constants;
import com.marvin.lop.receiver.IMMessageHandler;
import com.marvin.lop.service.PushCommodityInfoService;
import com.marvin.lop.ui.base.BaseActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cn.bmob.newim.BmobIM;
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

    SharedPreferences sharedPreferences;

    @Override
    protected void findViewById() {
        mSplashItem_iv = (ImageView) findViewById(R.id.splash_loading_item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 初始化Bmob SDK
        Bmob.initialize(this, Constants.BmobConfig.ApplicationID);

        //只有主进程运行的时候才需要初始化
        if (getApplicationInfo().packageName.equals(getMyProcessName())){
            //NewIM初始化
            BmobIM.init(this);
            //注册消息接收器
            BmobIM.registerDefaultMessageHandler(new IMMessageHandler(this));
        }

        sharedPreferences = this.getSharedPreferences(Constants.SharedPreferencesConfig.FILENAME, MODE_PRIVATE);
        if (sharedPreferences != null) {
            // 根据用户objectid获取推荐的商品信息
            String objectid = sharedPreferences.getString(Constants.SharedPreferencesConfig.USER_OBJECT_ID, null);
            if (objectid != null) {
                // 执行查询操作
                Intent serviceIntent = new Intent(SplashActivity.this, PushCommodityInfoService.class);
                Log.i(TAG, "启动商品推荐服务...");
                startService(serviceIntent);
            }
        }

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Constants.SCREEN_DENSITY = metrics.density;
        Constants.SCREEN_HEIGHT = metrics.heightPixels;
        Constants.SCREEN_WIDTH = metrics.widthPixels;

        mHandler = new Handler(getMainLooper());
        findViewById();
        initView();
    }

    /**
     * 获取当前运行的进程名
     * @return
     */
    public static String getMyProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
