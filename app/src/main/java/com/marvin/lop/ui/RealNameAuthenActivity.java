package com.marvin.lop.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.marvin.lop.R;
import com.marvin.lop.bean.BeanConstants;
import com.marvin.lop.bean.CertifiedUsers;
import com.marvin.lop.config.Constants;
import com.marvin.lop.ui.base.BaseActivity;
import com.marvin.lop.utils.CodeMatcher;
import com.marvin.lop.utils.CommonTools;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Marvin on 2016/4/9.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 * 描述： 实名认证界面
 */
public class RealNameAuthenActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    public static final String TAG = RealNameAuthenActivity.class.getSimpleName();

    private ImageButton mBack_imgbtn;
    private Spinner mPermission_spinner;
    private Spinner mCollege_spinner;
    private EditText mClass_et;
    private EditText mStudentID_et;
    private EditText mStudentName_et;
    private EditText mEmailAddress_et;
    private Button mCommit_btn;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    // 获取本地保存的用户手机号码
    private String phoneNumber;
    // 获得本地保存的用户密码
    private String userPassword;
    private String objectId;

    // 从Spinner获取的选项
    private Integer mPermissionSpinnerItem;
    private Integer mCollegeSpinnerItem;

    private CertifiedUsers certifiedUsers;

    // 从控件中获取的输入数据
    private String mClass = null,
            mStudentId = null,
            mStudentName = null,
            mEmailAddress = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_real_name_authen);

        sharedPreferences = this.getSharedPreferences(Constants.SharedPreferencesConfig.FILENAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        mBack_imgbtn = (ImageButton) findViewById(R.id.real_name_authen_back_button);
        mPermission_spinner = (Spinner) findViewById(R.id.real_name_authen_permission_spinner);
        mCollege_spinner = (Spinner) findViewById(R.id.real_name_authen_college_spinner);
        mClass_et = (EditText) findViewById(R.id.real_name_authen_class_number_edittext);
        mStudentID_et = (EditText) findViewById(R.id.real_name_authen_student_id_edittext);
        mStudentName_et = (EditText) findViewById(R.id.real_name_authen_student_name_edittext);
        mEmailAddress_et = (EditText) findViewById(R.id.real_name_authen_email_number_edittext);
        mCommit_btn = (Button) findViewById(R.id.real_name_authen_commit_button);
    }

    @Override
    protected void initView() {
        // 获取SharedPreferences中的userPhoneNumber
        phoneNumber = sharedPreferences.getString(Constants.SharedPreferencesConfig.PHONE_NUMBER, null);
        // 获取本地保存的用户密码
        userPassword = sharedPreferences.getString(Constants.SharedPreferencesConfig.USER_PASSWORD, null);
        // 获取本地保存的ObjectID
        objectId = sharedPreferences.getString(Constants.SharedPreferencesConfig.USER_OBJECT_ID, null);

        certifiedUsers = new CertifiedUsers();

        mBack_imgbtn.setOnClickListener(this);
        mCommit_btn.setOnClickListener(this);

        // 为PermissionSpinner绑定数据和监听器
        if (mPermission_spinner != null) {
            mPermission_spinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter mPermissionSpinnerAdapter = ArrayAdapter.createFromResource(RealNameAuthenActivity.this,
                R.array.permission, R.layout.custom_spinner_text_item);
        mPermissionSpinnerAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        mPermission_spinner.setAdapter(mPermissionSpinnerAdapter);

        // 为CollegeSpinner绑定数据和监听器
        if (mCollege_spinner != null) {
            mCollege_spinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter mCollegeSpinnerAdapter = ArrayAdapter.createFromResource(RealNameAuthenActivity.this,
                R.array.college_names, R.layout.custom_spinner_text_item);
        mCollegeSpinnerAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        mCollege_spinner.setAdapter(mCollegeSpinnerAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.real_name_authen_back_button:
                // 如果是直接在实名认证界面返回个人信息界面并没有进行认证，返回未认证的响应码
                Log.i(TAG, "点击返回按钮");
                setResult(Constants.IntentResultCode.Authen2PersonalCenter);
                RealNameAuthenActivity.this.finish();
                break;
            case R.id.real_name_authen_commit_button:
                // 提交实名认证信息
                // 根据本地登录的用户信息到服务器端查询用户是否存在，如果存在就将该用户信息添加到新的认证用户信息表中
                BmobQuery<CertifiedUsers> userQuery = new BmobQuery<>();
                Log.i(TAG, "已登录的用户objectId:" + objectId);
                if (objectId != null) {
                    userQuery.addWhereEqualTo(BeanConstants.CertifiedUsersProperties.UserObjectId,
                            objectId);
                    userQuery.findObjects(RealNameAuthenActivity.this,
                            new FindListener<CertifiedUsers>() {
                                @Override
                                public void onSuccess(List<CertifiedUsers> list) {
                                    boolean isPermission = false,
                                            isCollege = false,
                                            isClass = false,
                                            isId = false,
                                            isName = false,
                                            isEmail = false;
                                    if (list.size() > 0) { // 用户存在
                                        Log.i(TAG, "服务器返回数据：用户存在");
                                        if (mPermissionSpinnerItem != null) {
                                            isPermission = true;
                                        }
                                        if (mCollegeSpinnerItem != null) {
                                            isCollege = true;
                                        }
                                        if (!mClass_et.getText().toString().equals("")) {
                                            // TODO: 2016/4/11 验证是不是班级号码
                                            isClass = true;
                                            mClass = mClass_et.getText().toString();
                                        } else {
                                            DisPlay("请输入班级");
                                            mClass_et.setText("");
                                        }
                                        if (!mStudentID_et.getText().toString().equals("")) {
                                            // TODO: 2016/4/11 验证是不是学号
                                            isId = true;
                                            mStudentId = mStudentID_et.getText().toString();
                                        } else {
                                            DisPlay("请输入学号");
                                            mStudentID_et.setText("");
                                        }
                                        if (!mStudentName_et.getText().toString().equals("")) {
                                            isName = true;
                                            mStudentName = mStudentName_et.getText().toString();
                                        } else {
                                            DisPlay("请输入姓名");
                                        }
                                        if (!mEmailAddress_et.getText().toString().equals("")) {
                                            if (CommonTools.checkEmail(mEmailAddress_et.getText().toString())) {
                                                isEmail = true;
                                                mEmailAddress = mEmailAddress_et.getText().toString();
                                            } else {
                                                DisPlay("请输入正确的电子邮箱");
                                            }
                                        } else {
                                            DisPlay("请输入电子邮箱");
                                        }
                                        if (isPermission && isCollege && isClass && isId && isName && isEmail) {
                                            certifiedUsers.setUserPermission(mPermissionSpinnerItem);
                                            certifiedUsers.setUserCollege(mCollegeSpinnerItem);
                                            certifiedUsers.setUserClass(mClass);
                                            certifiedUsers.setUserStudentId(mStudentId);
                                            certifiedUsers.setUserRealName(mStudentName);
                                            certifiedUsers.setUserEmailAddress(mEmailAddress);
                                            // 设置认证结果为false
                                            certifiedUsers.setUserAuthenResult(false);
                                            // 设置认证进度为Being
                                            certifiedUsers.setUserAuthenProgress(Constants.UserAuthenState.BEING_CERTIFIED);
                                            certifiedUsers.update(RealNameAuthenActivity.this, objectId, new UpdateListener() {
                                                @Override
                                                public void onSuccess() {
                                                    Log.i(TAG, "提交认证信息成功");
                                                    // 将认证状态改变为正在认证
                                                    editor.putString(Constants.SharedPreferencesConfig.USER_AUTHEN_PROGRESS,
                                                            Constants.UserAuthenState.BEING_CERTIFIED);
                                                    // 将用户的认证结果设置为未认证
                                                    editor.putBoolean(Constants.SharedPreferencesConfig.USER_AUTHEN_RESULT, false);
                                                    editor.commit();
                                                    // 跳转到个人信息界面
                                                    setResult(Constants.IntentResultCode.AuthenCommitButton);
                                                    // 将该Activity移除
                                                    RealNameAuthenActivity.this.finish();
                                                }

                                                @Override
                                                public void onFailure(int i, String s) {
                                                    Log.i(TAG, "提交认证信息失败" + i + s);
                                                }
                                            });
                                        }
                                    } else {
                                        Log.i(TAG, "服务器返回数据：用户不存在");
                                        DisPlay("服务器返回数据：用户不存在 ");
                                    }
                                }

                                @Override
                                public void onError(int i, String s) {
                                    Log.i(TAG, "服务器返回数据：用户不存在onError()");
                                    DisPlay("服务器返回数据：用户不存在onError()");
                                }
                            });
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.real_name_authen_permission_spinner) {
            String item = mPermission_spinner.getItemAtPosition(position).toString();
            // 根据权限名称获取相应的代号
            mPermissionSpinnerItem = CodeMatcher.PermissionCodeMatcher(item);
            // 把权限代号存入SharedPreferences
            editor.putInt(Constants.SharedPreferencesConfig.USER_PERMISSION, mPermissionSpinnerItem);
            editor.commit();
        } else if (parent.getId() == R.id.real_name_authen_college_spinner) {
            String item = mCollege_spinner.getItemAtPosition(position).toString();
            // 根据学院名称获取相应的代号
            mCollegeSpinnerItem = CodeMatcher.CollegeCodeMatcher(item);
            // 把学院代号存入SharedPreferences
            editor.putInt(Constants.SharedPreferencesConfig.USER_COLLEGE, mCollegeSpinnerItem);
            editor.commit();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
