package com.marvin.lop.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.marvin.lop.R;
import com.marvin.lop.config.Constants;
import com.marvin.lop.ui.base.BaseActivity;
import com.marvin.lop.utils.CommonTools;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;

/**
 * Created by Marvin on 2016/4/9.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 * 描述：快速注册
 */
public class FastRegisterActivity extends BaseActivity {

    private EditText phoneNumber_et;
    private Button nextPage_btn;

    private SharedPreferences sharePreferences;
    private SharedPreferences.Editor editor;

    private String phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);
        // Bmob短信验证初始化
        BmobSMS.initialize(FastRegisterActivity.this, Constants.BmobConfig.ApplicationID);

        // 获取SharedPreferences
        sharePreferences = this.getSharedPreferences(Constants.SharedPreferencesConfig.FILENAME, MODE_PRIVATE);
        editor = sharePreferences.edit();

        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        phoneNumber_et = (EditText) findViewById(R.id.register_info_username_edittext);
        nextPage_btn = (Button) findViewById(R.id.register_info_regist_button);
    }

    @Override
    protected void initView() {
        nextPage_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!phoneNumber_et.getText().toString().equals("")) {
                    phoneNum = phoneNumber_et.getText().toString().trim();
                    if (CommonTools.isMobileNO(phoneNum)) {
                        BmobSMS.requestSMSCode(FastRegisterActivity.this, phoneNumber_et.getText().toString(),
                                Constants.BmobConfig.BmobSMSTemplateName, new RequestSMSCodeListener() {
                                    @Override
                                    public void done(Integer smsId, BmobException ex) {
                                        if (ex == null) { // 验证码发送成功
                                            DisPlay("验证码发送成功，请注意查收!");
                                            Log.i("验证码发送", "成功！");
                                            // 把能成功验证的手机号保存到SharedPreferences中
                                            editor.putString(Constants.SharedPreferencesConfig.PHONE_NUMBER, phoneNum);

                                            // 跳转到验证手机号的界面
                                            Intent intent = new Intent(FastRegisterActivity.this, AuthenPhoneNumberActivity.class);
                                            startActivity(intent);
                                            FastRegisterActivity.this.finish();
                                        } else {

                                            // **********************************跳过验证手机号界面**********************************
                                            Log.i("FastRegister", "跳过手机号验证界面");
                                            editor.putString(Constants.SharedPreferencesConfig.PHONE_NUMBER, phoneNum);

                                            Intent intent = new Intent(FastRegisterActivity.this, SetPasswordActivity.class);
                                            startActivity(intent);
                                            FastRegisterActivity.this.finish();
                                            //**********************************跳过验证手机号界面**********************************


//                                            DisPlay("验证码发送失败，请重新输入手机号");
                                            Log.i("验证码发送", "失败!");
//                                            phoneNumber_et.setText("");
                                        }
                                    }
                                });
                    } else {
                        DisPlay("请输入正确的手机号");
                    }
                } else {
                    DisPlay("请输入手机号");
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        editor.commit();
    }

}
