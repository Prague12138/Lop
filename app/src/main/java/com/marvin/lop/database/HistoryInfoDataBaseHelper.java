package com.marvin.lop.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.marvin.lop.bean.HistoryInfo;
import com.marvin.lop.config.Constants;

/**
 * Created by Marvin on 2016/6/12.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public class HistoryInfoDataBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = HistoryInfoDataBaseHelper.class.getSimpleName();

    public static final String Create_HistoryInfoData_Table =
            "create table if not exists " + Constants.HistoryInfoDataConfig.TableName + "(_id integer primary key autoincrement," +
                    Constants.HistoryInfoDataConfig.ObjectId + " text," +
                    Constants.HistoryInfoDataConfig.UserObjectid + " text," +
                    Constants.HistoryInfoDataConfig.CommodityCategory1 + " text," +
                    Constants.HistoryInfoDataConfig.CommodityObjectid + " text," +
                    Constants.HistoryInfoDataConfig.CommodityTitle + " text," +
                    Constants.HistoryInfoDataConfig.UserPhoneNumber + " text," +
                    Constants.HistoryInfoDataConfig.SellerPhoneNumber + " text," +
                    Constants.HistoryInfoDataConfig.CreateAt + " text)";

    private Context context;

    public HistoryInfoDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                                     int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "Create SQL:" + Create_HistoryInfoData_Table);
        db.execSQL(Create_HistoryInfoData_Table);
        Log.i(TAG, "创建数据表成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
