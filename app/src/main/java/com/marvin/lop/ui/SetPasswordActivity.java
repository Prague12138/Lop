package com.marvin.lop.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.marvin.lop.R;
import com.marvin.lop.bean.CertifiedUsers;
import com.marvin.lop.config.Constants;
import com.marvin.lop.ui.base.BaseActivity;
import com.marvin.lop.utils.CommonTools;
import com.marvin.lop.utils.CreateNewUser;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Marvin on 2016/4/9.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public class SetPasswordActivity extends BaseActivity {

    private static final String TAG = SetPasswordActivity.class.getSimpleName();

    private ImageButton mBackButton_imBtn;
    private EditText mPassword_et;
    private EditText mRePassowrd_et;
    private Button mRegister_btn;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private CertifiedUsers certifiedUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        sharedPreferences = this.getSharedPreferences(Constants.SharedPreferencesConfig.FILENAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        mBackButton_imBtn = (ImageButton) findViewById(R.id.set_password_back_button);
        mPassword_et = (EditText) findViewById(R.id.set_password_edittext);
        mRePassowrd_et = (EditText) findViewById(R.id.set_password_repassword_edittext);
        mRegister_btn = (Button) findViewById(R.id.set_password_button);
    }

    @Override
    protected void initView() {
        certifiedUsers = new CertifiedUsers();
        // 返回上一级
        mBackButton_imBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        // 设置密码
        mRegister_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mPassword_et.getText().toString().equals("")) { // 密码不为空
                    if (CommonTools.checkPassword(mPassword_et.getText().toString())) { // 符合密码设置规范
                        if (!mRePassowrd_et.getText().toString().equals("")) { // 确认密码不为空
                            if (mRePassowrd_et.getText().toString().equals(mPassword_et.getText().toString())) { // 密码和确认密码相同
                                String password = mRePassowrd_et.getText().toString();
                                editor.putString(Constants.SharedPreferencesConfig.USER_PASSWORD, password);// 把密码存入SharedPreferences
                                String phoneNumber = sharedPreferences.getString(Constants.SharedPreferencesConfig.PHONE_NUMBER, null);//把手机号从SharedPreferences中取出
                                Log.i(TAG, phoneNumber + "");

                                CreateNewUser cnu = new CreateNewUser();
                                certifiedUsers = cnu.createUserInServer(phoneNumber, password, 0, 0, null, null, null, null, null, false);
                                certifiedUsers.save(SetPasswordActivity.this, new SaveListener() {
                                    @Override
                                    public void onSuccess() {
                                        DisPlay("注册成功！");
                                        editor.putString(Constants.SharedPreferencesConfig.USER_OBJECT_ID, certifiedUsers.getObjectId());
//                                        Intent intent = new Intent(SetPasswordActivity.this, LoginActivity.class);
//                                        startActivity(intent);
                                        // 将该Activity移除
                                        SetPasswordActivity.this.finish();
                                    }

                                    @Override
                                    public void onFailure(int i, String s) {
                                        DisPlay("注册失败!");
                                        Log.i("SetPasswordActivity", s + "  " + i);
                                    }
                                });
                            }
                        }
                    }
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
