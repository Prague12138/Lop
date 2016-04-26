package com.marvin.lop.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.marvin.lop.R;
import com.marvin.lop.config.Constants;
import com.marvin.lop.ui.base.BaseActivity;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;


/**
 * Created by Marvin on 2016/4/7.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 * 描述： 输入短信验证码验证手机号
 */
public class AuthenPhoneNumberActivity extends BaseActivity {

    private static final String TAG = AuthenPhoneNumberActivity.class.getSimpleName();

    private ImageButton mBackButton;
    private EditText mAuthenPhoneNumber_edittext;
    private Button mGetAuthenCode_button;
    private Button mAuthen_button;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authen_phonenumber);
        // 获取SharedPreferences对象
        sharedPreferences = this.getSharedPreferences(Constants.SharedPreferencesConfig.FILENAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // 注册BmobSMS
        BmobSMS.initialize(AuthenPhoneNumberActivity.this, Constants.BmobConfig.ApplicationID);

        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        mBackButton = (ImageButton) findViewById(R.id.authen_phonenumber_back_button);
        mAuthenPhoneNumber_edittext = (EditText) findViewById(R.id.authen_phonenumber_edittext);
        mGetAuthenCode_button = (Button) findViewById(R.id.authen_phonenumber_get_authen_code_button);
        mAuthen_button = (Button) findViewById(R.id.authen_phonenumber_authen_button);
    }

    @Override
    protected void initView() {
        // 返回上一级页面
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016/4/9 返回上一级界面
            }
        });
        // 重新获取验证码
        mGetAuthenCode_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016/4/9 添加时间，当时间大于1个小时才能重复给同一个号码发送短信验证码
                BmobSMS.requestSMSCode(AuthenPhoneNumberActivity.this, sharedPreferences.getString(
                        Constants.SharedPreferencesConfig.PHONE_NUMBER, null), Constants.BmobConfig.BmobSMSTemplateName,
                        new RequestSMSCodeListener() {
                            @Override
                            public void done(Integer smsId, BmobException ex) {
                                if (ex == null) {
                                    Log.i("BmobSMS", "短信ID：" + smsId);// 用于查询本次短信发送详情
                                    DisPlay("验证码发送成功，请注意查收！");
                                } else {
                                    DisPlay("验证码发送失败，请重新发送验证码");
                                    Log.i("BmobSMS", "错误原因： " + ex);
                                }
                            }
                        });
            }
        });
        // 验证短信验证码
        mAuthen_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取输入的验证码
                String authenCode = mAuthenPhoneNumber_edittext.getText().toString();
                phoneNumber = sharedPreferences.getString(Constants.SharedPreferencesConfig.PHONE_NUMBER, null);
                Log.i(TAG, phoneNumber + "");
                if (!authenCode.equals("")) { // 验证码不能为空
                    if (authenCode.length() == 6) { // 验证码的长度必须是6位
                        BmobSMS.verifySmsCode(AuthenPhoneNumberActivity.this, phoneNumber, authenCode, new VerifySMSCodeListener() {
                            @Override
                            public void done(BmobException ex) {
                                if (ex == null) { // 验证码验证成功
                                    DisPlay("验证通过");
                                    Log.i(TAG, "验证通过");
                                    // 跳转到设置密码界面
                                    Intent intent = new Intent(AuthenPhoneNumberActivity.this, SetPasswordActivity.class);
                                    startActivity(intent);
                                    AuthenPhoneNumberActivity.this.finish();
                                } else {
                                    DisPlay("验证失败，请重新验证");
                                    Log.i("BmobSMS", "验证失败，请重新验证");
                                    Log.i("BmobSMS", "验证失败：code =" + ex.getErrorCode() + ",msg = " + ex.getLocalizedMessage());
                                    // 将验证码输入框置空
                                    mAuthenPhoneNumber_edittext.setText("");
                                }
                            }
                        });
                    } else {
                        DisPlay("请输入正确的验证码");
                    }
                } else {
                    DisPlay("请输入验证码");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
