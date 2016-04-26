package com.marvin.lop.ui;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.marvin.lop.R;
import com.marvin.lop.config.Constants;
import com.marvin.lop.database.GetUserCertifiedInfoDataBaseHelper;
import com.marvin.lop.ui.base.BaseActivity;
import com.marvin.lop.utils.CodeMatcher;

/**
 * Created by Marvin on 2016/4/26.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 * 描述： 当用户通过认证，显示该认证信息界面
 */
public class ShowCertifiedInfoActivity extends BaseActivity{

    private static final String TAG = ShowCertifiedInfoActivity.class.getSimpleName();

    private ImageButton mBack_imgbtn;
    private TextView name_tv;
    private TextView studentid_tv;
    private TextView class_tv;
    private TextView college_tv;
    private TextView permission_tv;
    private TextView email_tv;

    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper = null;
    private Cursor cursor = null;

    private String objectid;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_certified_info);
        sharedPreferences = this.getSharedPreferences(Constants.SharedPreferencesConfig.FILENAME, MODE_PRIVATE);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        mBack_imgbtn = (ImageButton) findViewById(R.id.show_certified_info_back_button);
        name_tv = (TextView) findViewById(R.id.show_certified_info_name);
        studentid_tv = (TextView) findViewById(R.id.show_certified_info_studentid);
        class_tv = (TextView) findViewById(R.id.show_certified_info_class);
        college_tv = (TextView) findViewById(R.id.show_certified_info_college);
        permission_tv = (TextView) findViewById(R.id.show_certified_info_permission);
        email_tv = (TextView) findViewById(R.id.show_certified_info_email);

    }

    @Override
    protected void initView() {
        mBack_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Constants.IntentResultCode.ShowCertifiedInfo2PersonalCenter);
                ShowCertifiedInfoActivity.this.finish();
            }
        });

        objectid = sharedPreferences.getString(Constants.SharedPreferencesConfig.USER_OBJECT_ID, null);
        // 初始化数据库
        if (objectid != null) {
            try{
                openHelper = new GetUserCertifiedInfoDataBaseHelper(ShowCertifiedInfoActivity.this,
                        Constants.UserCertifiedInfoDataConfig.DataBaseName, null, 1);
                database = openHelper.getWritableDatabase();
                Log.i(TAG, "select * from " + Constants.UserCertifiedInfoDataConfig.TableName +
                        " where " + Constants.UserCertifiedInfoDataConfig.ObjectID + "=" + objectid);
                cursor = database.rawQuery("select * from " + Constants.UserCertifiedInfoDataConfig.TableName +
                        " where " + Constants.UserCertifiedInfoDataConfig.ObjectID + "='" + objectid + "'", null);
                if (cursor.getCount() == 1) {
                    // 数据库返回数量为1说明存在数据
//                    Log.i(TAG, "存在该数据+++++++++++++++++++++++++++++");
                    if (cursor.moveToFirst()) {
                        name_tv.setText(cursor.getString(cursor.getColumnIndex(Constants.UserCertifiedInfoDataConfig.RealName)));
                        studentid_tv.setText(cursor.getString(cursor.getColumnIndex(Constants.UserCertifiedInfoDataConfig.StudentID)));
                        class_tv.setText(cursor.getString(cursor.getColumnIndex(Constants.UserCertifiedInfoDataConfig.Class)));
                        int collegenum = cursor.getInt(cursor.getColumnIndex(Constants.UserCertifiedInfoDataConfig.College));
                        String collegeName = CodeMatcher.CollegeCodeMatcher(collegenum);
                        college_tv.setText(collegeName);
                        int permissionnum = cursor.getInt(cursor.getColumnIndex(Constants.UserCertifiedInfoDataConfig.Permission));
//                        Log.i(TAG, "PermissionNum = " + permissionnum);
                        String permissionname = CodeMatcher.PermissionCodeMatcher(permissionnum);
//                        Log.i(TAG, "PermissionName = " + permissionname);
                        permission_tv.setText(permissionname);
                        String mail = cursor.getString(cursor.getColumnIndex(Constants.UserCertifiedInfoDataConfig.EmailAddress));
                        email_tv.setText(mail);
                    }
                } else {
                    Log.i(TAG, "数据库中不存在该数据");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                database.close();
                openHelper.close();
            }
        }

    }


}
