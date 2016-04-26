package com.marvin.lop.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marvin.lop.R;
import com.marvin.lop.config.Constants;
import com.marvin.lop.ui.base.BaseActivity;

/**
 * Created by Marvin on 2016/4/9.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 * 描述： 用户中心页面
 */
public class PersonalCenterActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = PersonalCenterActivity.class.getSimpleName();

    private ImageButton mBack_btn;
    private TextView mAuthenYes_tv;
    private TextView mAuthenContinue_tv;
    private TextView mAuthenNo_tv;
    private Button mSignOut_btn;
    private RelativeLayout mRelativeLayout;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);

        sharedPreferences = this.getSharedPreferences(Constants.SharedPreferencesConfig.FILENAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        mRelativeLayout = (RelativeLayout) findViewById(R.id.personal_center_relative_layout_click);
        mBack_btn = (ImageButton) findViewById(R.id.personal_center_back_button);
        mAuthenNo_tv = (TextView) findViewById(R.id.personal_center_real_name_authen_no);
        mAuthenContinue_tv = (TextView) findViewById(R.id.personal_center_real_name_authen_continue);
        mAuthenYes_tv = (TextView) findViewById(R.id.personal_center_real_name_authen_yes);
        mSignOut_btn = (Button) findViewById(R.id.personal_center_signout_btn);
    }

    @Override
    protected void initView() {
        mBack_btn.setOnClickListener(this);
        mSignOut_btn.setOnClickListener(this);
        mRelativeLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_center_back_button:
                // 返回箭头，从当前界面返回到个人界面
                setResult(Constants.IntentResultCode.PersonalCenter2Personal);
                PersonalCenterActivity.this.finish();
                break;
            case R.id.personal_center_signout_btn:
                // 退出登录,并返回到个人界面
//                editor.putBoolean(Constants.SharedPreferencesConfig.USER_LOGIN_STATE, false);
                // 清除用户信息
                editor.clear();
                setResult(Constants.IntentResultCode.SIGN_OUT_SUCCESS);
                PersonalCenterActivity.this.finish();
                break;
            case R.id.personal_center_relative_layout_click:
                String progress = sharedPreferences.getString(Constants.SharedPreferencesConfig.USER_AUTHEN_PROGRESS, null);
                if (progress != null) {
                    if (progress.equals(Constants.UserAuthenState.CERTIFIED)) {
                        // 进入用户认证信息显示界面
                        Intent showIntent = new Intent(PersonalCenterActivity.this, ShowCertifiedInfoActivity.class);
                        startActivityForResult(showIntent, Constants.IntentRequestCode.PersonalCenter2ShowCertifiedInfo);
                    } else if (progress.equals(Constants.UserAuthenState.BEING_CERTIFIED)){
                        DisPlay("正在等待认证...");
                    } else if (progress.equals(Constants.UserAuthenState.UNAUTHORIZED)) {
                        // 进入认证界面
                        Intent mIntent = new Intent(PersonalCenterActivity.this, RealNameAuthenActivity.class);
                        startActivityForResult(mIntent, Constants.IntentRequestCode.IN_REAL_NAME_AUTHEN);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.IntentRequestCode.IN_REAL_NAME_AUTHEN) {
            switch (resultCode) {
                case Constants.IntentResultCode.Authen2PersonalCenter://在认证界面点返回按钮返回到个人信息中心界面
                    break;
                case Constants.IntentResultCode.AuthenCommitButton://在认证界面点击提交按钮后返回该界面
                    break;
                default:
                    break;
            }
        } else if (requestCode == Constants.IntentRequestCode.PersonalCenter2ShowCertifiedInfo) {
            switch (resultCode) {
                case Constants.IntentResultCode.ShowCertifiedInfo2PersonalCenter:
                    // 从显示认证信息界面返回到个人中心界面
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 当进入该界面时检查用户当前认证状态，根据认证状态显示内容
        switchAuthenState(sharedPreferences.getString(Constants.SharedPreferencesConfig.USER_AUTHEN_PROGRESS, Constants.UserAuthenState.UNAUTHORIZED));
    }

    @Override
    protected void onPause() {
        super.onPause();
        editor.commit();
    }

    /**
     * 根据SharedPreferences中的用户认证状态控制当前界面显示内容
     *
     * @param state 从SharedPreferences中获取的用户认证状态
     */
    private void switchAuthenState(String state) {
        switch (state) {
            case Constants.UserAuthenState.CERTIFIED: // 已验证
                mAuthenYes_tv.setVisibility(View.VISIBLE);
                mAuthenNo_tv.setVisibility(View.GONE);
                mAuthenContinue_tv.setVisibility(View.GONE);
                break;
            case Constants.UserAuthenState.BEING_CERTIFIED: // 正在验证
                mAuthenYes_tv.setVisibility(View.GONE);
                mAuthenNo_tv.setVisibility(View.GONE);
                mAuthenContinue_tv.setVisibility(View.VISIBLE);
                break;
            case Constants.UserAuthenState.UNAUTHORIZED: // 未验证
                mAuthenYes_tv.setVisibility(View.GONE);
                mAuthenNo_tv.setVisibility(View.VISIBLE);
                mAuthenContinue_tv.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
}
