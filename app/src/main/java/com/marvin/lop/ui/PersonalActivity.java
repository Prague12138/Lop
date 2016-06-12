package com.marvin.lop.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.marvin.lop.R;
import com.marvin.lop.config.Constants;
import com.marvin.lop.ui.base.BaseActivity;

/**
 * Created by Marvin on 2016/3/20.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 * 描述： 用户界面
 */
public class PersonalActivity extends BaseActivity implements OnClickListener {

    public static final String TAG = PersonalActivity.class.getSimpleName();

    //******************************** 登录前布局********************************
    private LinearLayout mIsLogin_none_linear;
    // 登录前的登录按钮
    private Button mPersonSignIn_btn;

    //**********************************登陆后布局*******************************
    private LinearLayout mIsLogin_yes_linear;
    // 登陆后的用户按钮
    private ImageButton mUserInterface_imgbtn;
    // 显示在用户头像下方的用户名
    private TextView mUserName_tv;

    // 退出程序按钮
    private Button mExit_btn;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    // 获取当前登录的用户名并显示在头像的下方
    private String mUserPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

//        DisPlay("Oncreate**");

        sharedPreferences = this.getSharedPreferences(Constants.SharedPreferencesConfig.FILENAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        mIsLogin_none_linear = (LinearLayout) findViewById(R.id.personal_islogin_none);
        mPersonSignIn_btn = (Button) findViewById(R.id.personal_login_button);

        mIsLogin_yes_linear = (LinearLayout) findViewById(R.id.personal_islogin_yes);
        mUserInterface_imgbtn = (ImageButton) findViewById(R.id.personal_user_icon_imgbtn);
        mUserName_tv = (TextView) findViewById(R.id.personal_username_textview);

        mExit_btn = (Button) findViewById(R.id.personal_exit);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在Activity继续状态时检查SharedPreferences登录状态和当前的登录用户
        switchLoginState(sharedPreferences.getBoolean(Constants.SharedPreferencesConfig.USER_LOGIN_STATE, false));
        Log.i(TAG, "登录状态：" + sharedPreferences.getBoolean(Constants.SharedPreferencesConfig.USER_LOGIN_STATE, false));
        mUserPhoneNumber = sharedPreferences.getString(Constants.SharedPreferencesConfig.PHONE_NUMBER, null);
        if (sharedPreferences.getBoolean(Constants.SharedPreferencesConfig.USER_LOGIN_STATE, false) != false) {
            // 如果是登录状态就显示当前的登录名
            mUserName_tv.setText(mUserPhoneNumber);
        } else {
            // 如果不是登录状态就什么都不显示
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // TODO: 2016/4/13 处理当PersonalActivity处于后台状态时的任务
    }

    @Override
    protected void initView() {
        mPersonSignIn_btn.setOnClickListener(this);
        mUserInterface_imgbtn.setOnClickListener(this);
        mExit_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 登录按钮
            case R.id.personal_login_button:
                Intent mIntent = new Intent(PersonalActivity.this, LoginActivity.class);
                startActivityForResult(mIntent, Constants.IntentRequestCode.IN_LOGIN);
                break;
            // 进入用户中心页面
            case R.id.personal_user_icon_imgbtn:
                Intent m = new Intent(PersonalActivity.this, PersonalCenterActivity.class);
                startActivityForResult(m, Constants.IntentRequestCode.IN_PERSONAL_CENTER);
                break;
            case R.id.personal_exit:
                exit();
                break;
            default:
                break;
        }
    }

    /**
     * @param requestCode 请求码
     * @param resultCode  响应码
     * @param data        Activity之间传的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.IntentRequestCode.IN_PERSONAL_CENTER) {
            switch (resultCode) {
                case Constants.IntentResultCode.SIGN_OUT_SUCCESS:
                    // 退出登录
                    switchLoginState(false);
                    break;
                case Constants.IntentResultCode.PersonalCenter2Personal:
                    // 在个人信息中心界面点击界面上的返回箭头返回当前界面, 根据正确的登录状态显示内容
                    switchLoginState(sharedPreferences.getBoolean(Constants.SharedPreferencesConfig.USER_LOGIN_STATE, false));
                    break;
                default:
                    break;
            }
        } else if (requestCode == Constants.IntentRequestCode.IN_LOGIN) {
            switch (resultCode) {
                case Constants.IntentResultCode.LOGIN_SUCCESS:
                    // 登陆成功
                    switchLoginState(true);
                    break;
                case Constants.IntentResultCode.Login2Personal:
                    // 从登录界面返回到个人界面
                    switchLoginState(sharedPreferences.getBoolean(Constants.SharedPreferencesConfig.USER_LOGIN_STATE, false));
                    break;
                default:
                    break;
            }
        } else {
            switch (resultCode) {
                case Constants.IntentResultCode.HandleAuthenList2Personal:
                    // 从认证处理列表返回到个人界面
                    switchLoginState(sharedPreferences.getBoolean(Constants.SharedPreferencesConfig.USER_LOGIN_STATE, false));
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 切换登录状态，根据SharedPreferences中的isUserLogin,true说明已经登录，false是未登录
     */
    private void switchLoginState(boolean isLogin) {
        if (isLogin) {
            mIsLogin_none_linear.setVisibility(View.GONE);
            mIsLogin_yes_linear.setVisibility(View.VISIBLE);
        } else {
            mIsLogin_none_linear.setVisibility(View.VISIBLE);
            mIsLogin_yes_linear.setVisibility(View.GONE);
        }
    }

    /**
     * 退出程序
     */
    private void exit() {
        // TODO: 2016/4/9 退出程序，把堆栈中的Activity释放并清空，检查用户登录状态，
        System.exit(0);
        this.finish();
    }

}

