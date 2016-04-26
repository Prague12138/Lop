package com.marvin.lop.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.marvin.lop.config.Constants;

/**
 * Created by Marvin on 2016/4/26.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 * 如果用户通过认证，把用户的认证信息从服务器获取下来，保存在数据库中
 */
public class GetUserCertifiedInfoDataBaseHelper extends SQLiteOpenHelper{

    private static final String TAG = GetUserCertifiedInfoDataBaseHelper.class.getSimpleName();

    // 创建存储用户认证信息的表
    public static final String Create_UserCertifiedInfoData_Table =
            "create table if not exists "+ Constants.UserCertifiedInfoDataConfig.TableName +"(_id integer primary key autoincrement," +
                    Constants.UserCertifiedInfoDataConfig.PhoneNumber + " text," +
                    Constants.UserCertifiedInfoDataConfig.Permission+ " integer," +
                    Constants.UserCertifiedInfoDataConfig.College + " integer," +
                    Constants.UserCertifiedInfoDataConfig.Class + " text," +
                    Constants.UserCertifiedInfoDataConfig.StudentID + " text," +
                    Constants.UserCertifiedInfoDataConfig.RealName + " text," +
                    Constants.UserCertifiedInfoDataConfig.EmailAddress + " text," +
                    Constants.UserCertifiedInfoDataConfig.ObjectID + " text)";

    private Context context;

    public GetUserCertifiedInfoDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                                              int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "Create SQL:" + Create_UserCertifiedInfoData_Table);
        db.execSQL(Create_UserCertifiedInfoData_Table);
        Log.i(TAG, "创建用户认证信息数据表成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
