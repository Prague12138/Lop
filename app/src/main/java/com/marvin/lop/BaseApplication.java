package com.marvin.lop;

import android.app.Application;
import android.content.res.Configuration;

import com.marvin.lop.config.Constants;
import com.marvin.lop.image.ImageLoaderConfig;

/**
 * Created by Marvin on 2016/3/31.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public class BaseApplication extends Application {

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfig.initImageLoader(this, Constants.BASE_IMAGE_CACHE);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}

