package com.marvin.lop.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.marvin.lop.R;
import com.marvin.lop.config.Constants;
import com.marvin.lop.ui.PersonalCenterActivity;
import com.marvin.lop.ui.ProductDetailsActivity;

/**
 * Created by Marvin on 2016/4/16.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 * 描述： 用来处理通知的
 */
public class ShowNotification {

    /**
     * 处理通知
     *
     * @param context      上下文
     * @param contentTitle 通知的标题
     * @param contentText  通知的内容
     * @param ticker       提示通知显示在通知栏的消息
     * @param cls          要跳转到的页面
     */
    public static void handleNotification(Context context, String contentTitle, String contentText, String ticker, Class<?> cls) {
        // 获取系统通知管理器
        Bitmap btm = BitmapFactory.decodeResource(context.getResources(), R.drawable.small_logo_blue_72x72);
        Notification.Builder mNotificationBuilder = new Notification.Builder(context)
                .setSmallIcon(R.drawable.small_logo_blue_72x72).setContentTitle(contentTitle)
                .setContentText(contentText);
        mNotificationBuilder.setTicker(ticker);// 第一次提示消息的时候显示在通知栏
        mNotificationBuilder.setLargeIcon(btm);
        mNotificationBuilder.setAutoCancel(true);//自己维护通知的消失

        Intent resultIntent = new Intent();
        if (cls == ProductDetailsActivity.class) {
            resultIntent.putExtra("activity", "push");
        }
        // 构建一个Intent
        resultIntent.setClass(context, cls);
        // 封装一个Intent
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
                0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        // 设置通知主题的意图
        mNotificationBuilder.setContentIntent(resultPendingIntent);
        // 获取通知管理器对象
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(Constants.NotificationID.Certified, mNotificationBuilder.build());
    }
}
