package com.marvin.lop.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.marvin.lop.config.Constants;

/**
 * Created by Marvin on 2016/4/17.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 * 把认证请求的数据保存在本地数据库中
 */
public class AuthenRequestDataBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = AuthenRequestDataBaseHelper.class.getSimpleName();

    // 创建存储用户认证信息的表
    public static final String CREATE_AuthenRequestUserData_TABLE =
            "create table if not exists "+ Constants.AuthenRequestDataBaseConfig.TableName +"(_id integer primary key autoincrement," +
                    Constants.AuthenRequestDataBaseConfig.PhoneNumber + " text," +
                    Constants.AuthenRequestDataBaseConfig.Permission+ " integer," +
                    Constants.AuthenRequestDataBaseConfig.College + " integer," +
                    Constants.AuthenRequestDataBaseConfig.Class + " text," +
                    Constants.AuthenRequestDataBaseConfig.StudentID + " text," +
                    Constants.AuthenRequestDataBaseConfig.RealName + " text," +
                    Constants.AuthenRequestDataBaseConfig.EmailAddress + " text," +
                    Constants.AuthenRequestDataBaseConfig.ObjectID + " text)";

    private Context context;

    /**
     * 构造方法
     * @param context 上下文
     * @param name 数据库名称
     * @param factory
     * @param version 数据库版本号
     */
    public AuthenRequestDataBaseHelper(Context context, String name,
                                       SQLiteDatabase.CursorFactory factory,
                                       int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "Create SQL:" + CREATE_AuthenRequestUserData_TABLE);
        db.execSQL(CREATE_AuthenRequestUserData_TABLE);
        Log.i(TAG, "创建认证请求用户的数据表成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
