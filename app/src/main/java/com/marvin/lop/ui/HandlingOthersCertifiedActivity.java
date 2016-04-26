package com.marvin.lop.ui;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.marvin.lop.R;
import com.marvin.lop.bean.CertifiedUsers;
import com.marvin.lop.config.Constants;
import com.marvin.lop.database.AuthenRequestDataBaseHelper;
import com.marvin.lop.ui.base.BaseActivity;
import com.marvin.lop.utils.CodeMatcher;

import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Marvin on 2016/4/13.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 * 描述： 用来处理其他用户的认证
 */
public class HandlingOthersCertifiedActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = HandlingOthersCertifiedActivity.class.getSimpleName();

    private ImageButton mBack_imgbtn;
    private TextView userName_tv;// 显示姓名
    private TextView userStudentID_tv;// 显示学号
    private TextView userClass_tv;// 显示班级
    private TextView userCollege_tv;// 显示学院
    private TextView userPermission_tv;// 显示身份
    private TextView userEmail_tv;// 显示电子邮箱
    private Button confirm_btn;// 提交认证

    private Intent mIntent;

    private String objectId;

    private int id;

    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper = null;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authen_request_handle);
//        Log.i(TAG, "显示handlingOthersCertifiedActivity界面");
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        mBack_imgbtn = (ImageButton) findViewById(R.id.authen_request_handle_back_button);
        userName_tv = (TextView) findViewById(R.id.authen_request_handle_name);
        userStudentID_tv = (TextView) findViewById(R.id.authen_request_handle_studentid);
        userClass_tv = (TextView) findViewById(R.id.authen_request_handle_class);
        userCollege_tv = (TextView) findViewById(R.id.authen_request_handle_college);
        userPermission_tv = (TextView) findViewById(R.id.authen_request_handle_permission);
        userEmail_tv = (TextView) findViewById(R.id.authen_request_handle_email);
        confirm_btn = (Button) findViewById(R.id.authen_request_handle_confirm);
    }

    @Override
    protected void initView() {
        mBack_imgbtn.setOnClickListener(this);
        confirm_btn.setOnClickListener(this);

        // 获得List界面传给这个界面的List的position值和对象
        mIntent = getIntent();
        id = mIntent.getIntExtra("_id", -1);
        Log.i(TAG, "CursorPosition = " + id);
        if (id != -1) {
            // 初始化数据库
            try {
                openHelper = new AuthenRequestDataBaseHelper(HandlingOthersCertifiedActivity.this,
                        Constants.AuthenRequestDataBaseConfig.DataBaseName, null, 1);
                database = openHelper.getWritableDatabase();
//                Log.i(TAG, "Query语句:" + "select * from " + Constants.AuthenRequestDataBaseConfig.TableName +
//                        " where _id=" + id);
                cursor = database.rawQuery("select * from " + Constants.AuthenRequestDataBaseConfig.TableName +
                        " where _id=" + id, null);
                // android中数据库处理使用cursor时，游标不是放在为0的下标，而是放在为-1的下标处开始的。
                //也就是说返回给cursor查询结果时，不能够马上从cursor中提取值。
                if (cursor.moveToFirst()) {
                    objectId = cursor.getString(cursor.getColumnIndex(Constants.AuthenRequestDataBaseConfig.ObjectID));
                    userName_tv.setText(cursor.getString(cursor.getColumnIndex(Constants.AuthenRequestDataBaseConfig.RealName)));

                    userStudentID_tv.setText(cursor.getString(cursor.getColumnIndex(Constants.AuthenRequestDataBaseConfig.StudentID)));
                    userClass_tv.setText(cursor.getString(cursor.getColumnIndex(Constants.AuthenRequestDataBaseConfig.Class)));
                    // 根据数据库中存储的学院编号，找到对应的学院名称，将学院名称显示在界面上
                    int collegenum = cursor.getInt(cursor.getColumnIndex(Constants.AuthenRequestDataBaseConfig.College));
                    Log.i(TAG, "collegenum = " + collegenum);
                    String collegename = CodeMatcher.CollegeCodeMatcher(collegenum);
                    Log.i(TAG, "collegename = " + collegename);
                    userCollege_tv.setText(collegename);
                    // 根据数据库中存储的身份编号，找到对应的身份名称，将身份名称显示在界面上
                    int permissionnum = cursor.getInt(cursor.getColumnIndex(Constants.AuthenRequestDataBaseConfig.Permission));
                    Log.i(TAG, "permissionnum = " + permissionnum);
                    String permissionname = CodeMatcher.PermissionCodeMatcher(permissionnum);
                    Log.i(TAG, "permissionname = " + permissionname);
                    userPermission_tv.setText(permissionname);
                    // 查看数据库中存储的邮箱
                    String mail = cursor.getString(cursor.getColumnIndex(Constants.AuthenRequestDataBaseConfig.EmailAddress));
                    Log.i(TAG, "mail = " + mail);
                    userEmail_tv.setText(mail);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "positionID error！");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.authen_request_handle_back_button:
                // 返回ListView界面
                setResult(Constants.IntentResultCode.AuthenHandleBackToAuthenList);
                this.finish();
                break;
            case R.id.authen_request_handle_confirm:
                // 确认认证信息之后提交认证结果给相应的用户
                CertifiedUsers cerUser = new CertifiedUsers();
                cerUser.setUserAuthenProgress(Constants.UserAuthenState.CERTIFIED);
                cerUser.setUserAuthenResult(true);
                cerUser.update(this, objectId, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        Log.i(TAG, "用户" + objectId + " 认证成功");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Log.i(TAG, "用户" + objectId + " 认证失败");
                    }
                });
                // 从本地数据库删除掉这条数据
                database.delete(Constants.AuthenRequestDataBaseConfig.TableName, "_id=" + id, null);
                setResult(Constants.IntentResultCode.HandleActivity2AuthenHandleList);
                this.finish();
                break;
            default:
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        database.close();
        openHelper.close();
    }

}
