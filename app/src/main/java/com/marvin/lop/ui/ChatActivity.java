package com.marvin.lop.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.marvin.lop.R;
import com.marvin.lop.config.Constants;
import com.marvin.lop.ui.base.BaseActivity;

import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMTextMessage;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.listener.MessageListHandler;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.newim.listener.ObseverListener;
import cn.bmob.newim.notification.BmobNotificationManager;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by Marvin on 2016/6/11.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public class ChatActivity extends BaseActivity implements View.OnClickListener, MessageListHandler, ObseverListener {

    private static final String TAG = ChatActivity.class.getSimpleName();

    private ImageButton back;
    private TextView chat_content;
    private EditText chatInput;
    private Button send;
    private ScrollView scrollView;

    SharedPreferences sharedPreferences;

    private BmobIMConversation chat;

    private String userObjectid;

    private String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        sharedPreferences = this.getSharedPreferences(Constants.SharedPreferencesConfig.FILENAME, MODE_PRIVATE);
        userObjectid = sharedPreferences.getString(Constants.SharedPreferencesConfig.USER_OBJECT_ID, null);


        //在聊天页面的onCreate方法中，通过如下方法创建新的会话实例,这个obtain方法才是真正创建一个管理消息发送的会话
        Bundle bundle = this.getIntent().getExtras();
        chat = BmobIMConversation.obtain(BmobIMClient.getInstance(), (BmobIMConversation) bundle.getSerializable("chat"));

        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        back = (ImageButton) findViewById(R.id.chat_back_button);
        chat_content = (TextView) findViewById(R.id.chat_content);
        chatInput = (EditText) findViewById(R.id.edit_msg);
        send = (Button) findViewById(R.id.btn_chat_send);
        scrollView = (ScrollView) findViewById(R.id.chat_scrollview);
        back.setOnClickListener(this);
        send.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        // 监听到当前长链接的连接状态
        BmobIM.getInstance().setOnConnectStatusChangeListener(new ConnectStatusChangeListener() {
            @Override
            public void onChange(ConnectionStatus connectionStatus) {
                Log.i(TAG, "" + connectionStatus.getMsg());
                switch (connectionStatus.getCode()) {
                    case 0:
                        DisPlay("与服务器断开连接");
                        break;
                    case 1:
                        DisPlay("正在与服务器连接中...");
                        break;
                    case 2:
                        DisPlay("与服务器连接成功");
                        break;
                    case -1:
                        DisPlay("网络不可用");
                        break;
                    case -2:
                        DisPlay("");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chat_back_button:
                // 断开与服务器的连接
                BmobIM.getInstance().disConnect();
                ChatActivity.this.finish();
                break;
            case R.id.btn_chat_send:
                if (!chatInput.getText().toString().equals("")) {
                    BmobIMTextMessage msg = new BmobIMTextMessage();
                    str = chatInput.getText().toString();
                    msg.setContent(str);
                    chat.sendMessage(msg, new MessageSendListener() {

                        @Override
                        public void onStart(BmobIMMessage bmobIMMessage) {
                            super.onStart(bmobIMMessage);
                            chat_content.setTextColor(getResources().getColor(R.color.blue));
                            chat_content.append("我:\n");
                            chat_content.append(ChatActivity.this.str + "\n\n");
                            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                        }

                        @Override
                        public void done(BmobIMMessage bmobIMMessage, BmobException e) {
                            chatInput.setText("");
                            if (e != null) {
                                Log.i(TAG, e.getMessage());
                            }
                        }
                    });
                } else {
                    DisPlay("请输入聊天内容");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onMessageReceive(List<MessageEvent> list) {
        //当注册页面消息监听时候，有消息（包含离线消息）到来时会回调该方法
        for (int i = 0; i < list.size(); i++) {
            chat_content.setTextColor(getResources().getColor(R.color.green));
            chat_content.append("对方:\n");
            chat_content.append(list.get(i).getMessage().getContent() + "\n\n");
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 添加页面消息监听器
        BmobIM.getInstance().addMessageListHandler(this);
        BmobNotificationManager.getInstance(ChatActivity.this).addObserver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 移除页面消息监听器
        BmobIM.getInstance().removeMessageListHandler(this);
        BmobNotificationManager.getInstance(ChatActivity.this).removeObserver(this);
    }


}
