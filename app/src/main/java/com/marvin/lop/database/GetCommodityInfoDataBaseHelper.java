package com.marvin.lop.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.marvin.lop.config.Constants;

/**
 * Created by Marvin on 2016/6/10.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public class GetCommodityInfoDataBaseHelper extends SQLiteOpenHelper {

    private final String TAG = GetCommodityInfoDataBaseHelper.class.getSimpleName();

    public static final String Create_CommodityInfoData_Table =
            "create table if not exists " + Constants.CommodityInfoDataConfig.TableName + "(_id integer primary key autoincrement," +
                    Constants.CommodityInfoDataConfig.ObjectId + " text," +
                    Constants.CommodityInfoDataConfig.CommodityPrice + " text," +
                    Constants.CommodityInfoDataConfig.CommodityTitle + " text," +
                    Constants.CommodityInfoDataConfig.UserObjectId + " text," +
                    Constants.CommodityInfoDataConfig.CommodityCategory1 + " text," +
                    Constants.CommodityInfoDataConfig.CommodityCategory2 + " text," +
                    Constants.CommodityInfoDataConfig.CommodityDescribe + " text," +
                    Constants.CommodityInfoDataConfig.CommodityOriPrice + " text," +
                    Constants.CommodityInfoDataConfig.CommodityPicPath1 + " text," +
                    Constants.CommodityInfoDataConfig.CommodityPicPath2 + " text," +
                    Constants.CommodityInfoDataConfig.CommodityPicPath3 + " text," +
                    Constants.CommodityInfoDataConfig.CommodityPicPath4 + " text," +
                    Constants.CommodityInfoDataConfig.CommodityPicPath5 + " text," +
                    Constants.CommodityInfoDataConfig.CreateAt + " text," +
                    Constants.CommodityInfoDataConfig.UserPhoneNumber + " text)";
    private Context context;

    public GetCommodityInfoDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                                          int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "CreateSQL:" + Create_CommodityInfoData_Table);
        db.execSQL(Create_CommodityInfoData_Table);
        Log.i(TAG, "创建商品信息表成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
