package com.marvin.lop.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.marvin.lop.service.HandleCertifiedService;

/**
 * Created by Marvin on 2016/4/14.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 * 用来唤醒处理认证工作的服务
 */
public class HandleCertifiedReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, HandleCertifiedService.class);
        context.startService(i);
    }
}
