package com.marvin.lop.receiver;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.marvin.lop.R;
import com.marvin.lop.config.Constants;
import com.marvin.lop.ui.ChatActivity;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;
import cn.bmob.newim.listener.ConversationListener;
import cn.bmob.newim.notification.BmobNotificationManager;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by Marvin on 2016/6/11.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public class IMMessageHandler extends BmobIMMessageHandler {

    Context context;

    public IMMessageHandler(Context context) {
        this.context = context;
    }

    @Override
    public void onMessageReceive(final MessageEvent messageEvent) {
        super.onMessageReceive(messageEvent);
        //当接收到服务器发来的消息时，此方法被调用
        //可以统一在此检测更新会话及用户信息
        final Bitmap btm = BitmapFactory.decodeResource(context.getResources(), R.drawable.small_logo_blue_72x72);
        Intent intent = new Intent(context, ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("chat", messageEvent.getMessage().getBmobIMConversation());
        intent.putExtras(bundle);
        BmobNotificationManager.getInstance(context).showNotification(btm, "聊天信息", messageEvent.getMessage().getContent()
                , "你有一条新的聊天信息!", intent);
    }

    @Override
    public void onOfflineReceive(OfflineMessageEvent offlineMessageEvent) {
        super.onOfflineReceive(offlineMessageEvent);
        //每次调用connect方法时会查询一次离线消息，如果有，此方法会被调用
    }
}
