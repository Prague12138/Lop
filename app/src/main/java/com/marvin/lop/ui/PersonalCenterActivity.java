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
    private RelativeLayout mPublishLayout;
    private RelativeLayout mBuyingLayout;
    private RelativeLayout mRentLayout;
    private RelativeLayout mBorrowLayout;

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
        mPublishLayout = (RelativeLayout) findViewById(R.id.personal_center_publish_layout_click);
        mBuyingLayout = (RelativeLayout) findViewById(R.id.personal_center_buying_layout_click);
        mRentLayout = (RelativeLayout) findViewById(R.id.personal_center_rent_layout_click);
        mBorrowLayout = (RelativeLayout) findViewById(R.id.personal_center_borrow_layout_click);
    }

    @Override
    protected void initView() {
        mBack_btn.setOnClickListener(this);
        mSignOut_btn.setOnClickListener(this);
        mRelativeLayout.setOnClickListener(this);
        mPublishLayout.setOnClickListener(this);
        mBuyingLayout.setOnClickListener(this);
        mRentLayout.setOnClickListener(this);
        mBorrowLayout.setOnClickListener(this);
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
                    } else if (progress.equals(Constants.UserAuthenState.BEING_CERTIFIED)) {
                        DisPlay("正在等待认证...");
                    } else if (progress.equals(Constants.UserAuthenState.UNAUTHORIZED)) {
                        // 进入认证界面
                        Intent mIntent = new Intent(PersonalCenterActivity.this, RealNameAuthenActivity.class);
                        startActivityForResult(mIntent, Constants.IntentRequestCode.IN_REAL_NAME_AUTHEN);
                    }
                }
                break;
            case R.id.personal_center_publish_layout_click:
                // 点击我要发布，跳转到物品发布界面
                Intent mIntent = new Intent(PersonalCenterActivity.this, PublishActivity.class);
                startActivityForResult(mIntent, Constants.IntentRequestCode.PersonalCenter2Publish);
                break;
            case R.id.personal_center_buying_layout_click:
                // 跳转到物品求购界面
                Intent mIntent1 = new Intent(PersonalCenterActivity.this, BuyingActivity.class);
                startActivityForResult(mIntent1, Constants.IntentRequestCode.PersonalCenter2Buying);
                break;
            case R.id.personal_center_rent_layout_click:
                // 跳转到物品出租界面
                Intent mIntent2 = new Intent(PersonalCenterActivity.this, RentActivity.class);
                startActivityForResult(mIntent2, Constants.IntentRequestCode.PersonalCenter2Rent);
                break;
            case R.id.personal_center_borrow_layout_click:
                // 跳转到物品求租界面
                Intent mIntent3 = new Intent(PersonalCenterActivity.this, BorrowActivity.class);
                startActivityForResult(mIntent3, Constants.IntentRequestCode.PersonalCenter2Borrow);
                // TODO: 2016/6/4 修改onActivityForResult方法
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.IntentRequestCode.IN_REAL_NAME_AUTHEN:
                switch (resultCode) {
                    case Constants.IntentResultCode.Authen2PersonalCenter://在认证界面点返回按钮返回到个人信息中心界面
                        break;
                    case Constants.IntentResultCode.AuthenCommitButton://在认证界面点击提交按钮后返回该界面
                        break;
                    default:
                        break;
                }
                break;
            case Constants.IntentRequestCode.PersonalCenter2ShowCertifiedInfo:
                switch (resultCode) {
                    case Constants.IntentResultCode.ShowCertifiedInfo2PersonalCenter:
                        // 从显示认证信息界面返回到个人中心界面
                        break;
                    default:
                        break;
                }
                break;
            case Constants.IntentRequestCode.PersonalCenter2Publish:
                switch (resultCode) {
                    case Constants.IntentResultCode.PublishBack2PersonalCenter:
                        // 从物品发布界面返回到该界面
                        break;
                    case Constants.IntentResultCode.PublishSuccess:
                        // 发布物品信息成功
                        break;
                    default:
                        break;
                }
                break;
            case Constants.IntentRequestCode.PersonalCenter2Buying:
                switch (resultCode) {
                    case Constants.IntentResultCode.BuyingBack2PersonalCenter:
                        // 从物品求购界面返回到该界面
                        break;
                    case Constants.IntentResultCode.BuyingSuccess:
                        // 成功发布物品求购信息
                        break;
                    default:
                        break;
                }
                break;
            case Constants.IntentRequestCode.PersonalCenter2Rent:
                switch (resultCode) {
                    case Constants.IntentResultCode.RentBack2PersonalCenter:
                        //从发布出租信息界面返回到个人信息中心界面
                        break;
                    case Constants.IntentResultCode.RentSuccess:
                        // 发布出租信息成功
                        break;
                    default:
                        break;
                }
                break;
            case Constants.IntentRequestCode.PersonalCenter2Borrow:
                switch (resultCode) {
                    case Constants.IntentResultCode.BorrowBack2PersonalCenter:
                        // 从发布求租信息界面返回到个人信息中心界面
                        break;
                    case Constants.IntentResultCode.BorrowSuccess:
                        // 发布求租信息成功
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
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
