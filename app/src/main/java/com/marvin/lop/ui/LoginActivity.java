package com.marvin.lop.ui;

import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.marvin.lop.R;
import com.marvin.lop.bean.BeanConstants;
import com.marvin.lop.bean.CertifiedUsers;
import com.marvin.lop.config.Constants;
import com.marvin.lop.service.HandleCertifiedService;
import com.marvin.lop.ui.base.BaseActivity;
import com.marvin.lop.utils.QueryFromServer;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Marvin on 2016/3/31.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 * Description: 登录界面
 */
public class LoginActivity extends BaseActivity implements OnClickListener {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private ImageButton mBackButton;
    private EditText mLoginUserName;
    private EditText mLoginUserPassword;
    private Button loginBtn, register;
    private Intent mIntent;

    // 从服务器获得用户名和密码
    String password;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    // 判断是否从服务器登录，如果已登录，在Activity的onPause()中写入登录信息，否则不写入
//    private boolean isLogin = false;
    String objectId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = this.getSharedPreferences(Constants.SharedPreferencesConfig.FILENAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        mBackButton = (ImageButton) findViewById(R.id.login_back_button);
        mLoginUserName = (EditText) findViewById(R.id.login_phone_number_edittext);
        mLoginUserPassword = (EditText) findViewById(R.id.login_password_edittext);
        loginBtn = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
    }


    @Override
    protected void initView() {
        mBackButton.setOnClickListener(this);
        register.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_back_button:
                // 返回上一级
                setResult(Constants.IntentResultCode.Login2Personal);
                LoginActivity.this.finish();
                break;
            case R.id.register:
                // 点击注册按钮
                mIntent = new Intent(LoginActivity.this, FastRegisterActivity.class);
                startActivity(mIntent);
                break;
            case R.id.login:
                // 点击登录按钮
                if (!mLoginUserName.getText().toString().equals("")) { // 用户名不为空
                    if (!mLoginUserPassword.getText().toString().equals("")) { // 密码不为空
                        BmobQuery<CertifiedUsers> userQuery = new BmobQuery<>();
                        userQuery.addWhereEqualTo(BeanConstants.CertifiedUsersProperties.UserPhoneNumber,
                                mLoginUserName.getText().toString());
                        userQuery.findObjects(LoginActivity.this,
                                new FindListener<CertifiedUsers>() {
                                    @Override
                                    public void onSuccess(List<CertifiedUsers> list) {
                                       Log.i(TAG, "共返回" + list.size() + "条数据");
                                        if (list.size() > 0) { // 返回数据条数大于1，说明有这条数据
                                            password = list.get(0).getUserPassword();
                                            objectId = list.get(0).getObjectId();
                                            if (mLoginUserPassword.getText().toString().equals(password)) {
                                                DisPlay("登陆成功");
//                                                isLogin = true;
                                                // 从服务器获取认证的进度，保存在本地
                                                new QueryFromServer().getUserAuthenProgress(LoginActivity.this, objectId);
                                                // 在当前Activity 进入暂停状态时将SharedPreferences需要改变的数据提交
                                                // 将用户当前登录名更新在SharedPreferences中，每次进入PersonalActivity中都要检查
                                                editor.putString(Constants.SharedPreferencesConfig.PHONE_NUMBER, mLoginUserName.getText().toString());
                                                // 密码写入本地
                                                editor.putString(Constants.SharedPreferencesConfig.USER_PASSWORD, password);
                                                // 将用户登录状态记录在SharedPreferences中，每次进入PersonalActivity中都要检查
                                                editor.putBoolean(Constants.SharedPreferencesConfig.USER_LOGIN_STATE, true);
                                                // 当登录成功时，将该登录用于的ObjectID保存在本地用于查询其他数据
                                                editor.putString(Constants.SharedPreferencesConfig.USER_OBJECT_ID, objectId);
                                                editor.commit();

                                                // 当登录成功之后，启动检测认证情况的服务，如果用户已经发送自己的认证信息给服务器了
                                                // 就检测是否通过检测，并且还检测在自己的权限范围内是否有需要自己通过认证的新用户
                                                Intent serviceIntent = new Intent(LoginActivity.this, HandleCertifiedService.class);
                                                Log.i(TAG, "启动查询服务...");
                                                startService(serviceIntent);

                                                setResult(Constants.IntentResultCode.LOGIN_SUCCESS);
                                                LoginActivity.this.finish();
                                            } else {
                                                DisPlay("密码输入错误，请重新输入密码");
                                                mLoginUserPassword.setText("");
                                            }
                                        } else {
                                            mLoginUserName.setText("");
                                            mLoginUserPassword.setText("");
                                            DisPlay("用户不存在");
                                        }
                                    }

                                    @Override
                                    public void onError(int i, String s) {
                                        Log.i(TAG, "登录返回数据出错" + i + s);
                                        mLoginUserName.setText("");
                                        mLoginUserPassword.setText("");
                                    }
                                });
                    } else {
                        DisPlay("请输入密码");
                    }
                } else {
                    DisPlay("请输入用户名");
                }
                break;
            default:
                break;
        }
    }

}