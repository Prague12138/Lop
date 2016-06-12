package com.marvin.lop.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.marvin.lop.config.Constants;
import com.marvin.lop.ui.ProductDetailsActivity;
import com.marvin.lop.utils.QueryFromServer;
import com.marvin.lop.utils.ShowNotification;

/**
 * Created by Marvin on 2016/6/12.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public class PushCommodityInfoService extends Service {

    private static final String TAG = PushCommodityInfoService.class.getSimpleName();

    private SharedPreferences sharedPreferences;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = this.getSharedPreferences(Constants.SharedPreferencesConfig.FILENAME, MODE_PRIVATE);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "推荐商品信息查询服务已经启动");
                // 当用户登录后启动推荐服务，或者当用户登陆后再次进入软件后启动查询服务
                // 获取用户当前登录状态和用户对象ID
                String objectid = sharedPreferences.getString(Constants.SharedPreferencesConfig.USER_OBJECT_ID, null);
                boolean isLogin = sharedPreferences.getBoolean(Constants.SharedPreferencesConfig.USER_LOGIN_STATE, false);
                Log.i(TAG, "登录情况：" + isLogin + "用户ID:" + objectid);
                if ((isLogin != false) && (objectid != null)) {
                    Log.i(TAG, "成功登录，正在查询商品推荐信息");
                    new QueryFromServer().pushCommodityInfoFromServer(PushCommodityInfoService.this, objectid);
                    SharedPreferences s = PushCommodityInfoService.this.getSharedPreferences(Constants.PushInfoSharePreferencesConfig.FileName, MODE_PRIVATE);
                    if (s.getString(Constants.PushInfoSharePreferencesConfig.objectid, null) != null) {
                        // 获取推荐的商品信息，在通知栏提醒
                        ShowNotification.handleNotification(PushCommodityInfoService.this,
                                s.getString(Constants.PushInfoSharePreferencesConfig.CommodityTitle, null),
                                s.getString(Constants.PushInfoSharePreferencesConfig.ComodityDescribe, null),
                                "商品推荐", ProductDetailsActivity.class);
                    }
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
