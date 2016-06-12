package com.marvin.lop.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.marvin.lop.config.Constants;

/**
 * Created by Marvin on 2016/6/11.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public class GetSearchResultDataBaseHelper extends SQLiteOpenHelper {

    private final String TAG = GetSearchResultDataBaseHelper.class.getSimpleName();

    public static final String Create_SearchResultData_Table =
            "create table if not exists " + Constants.SearchResultDataConfig.TableName + "(_id integer primary key autoincrement," +
                    Constants.SearchResultDataConfig.ObjectId + " text," +
                    Constants.SearchResultDataConfig.CommodityPrice + " text," +
                    Constants.SearchResultDataConfig.CommodityTitle + " text," +
                    Constants.SearchResultDataConfig.UserObjectId + " text," +
                    Constants.SearchResultDataConfig.CommodityCategory1 + " text," +
                    Constants.SearchResultDataConfig.CommodityCategory2 + " text," +
                    Constants.SearchResultDataConfig.CommodityDescribe + " text," +
                    Constants.SearchResultDataConfig.CommodityOriPrice + " text," +
                    Constants.SearchResultDataConfig.CommodityPicPath1 + " text," +
                    Constants.SearchResultDataConfig.CommodityPicPath2 + " text," +
                    Constants.SearchResultDataConfig.CommodityPicPath3 + " text," +
                    Constants.SearchResultDataConfig.CommodityPicPath4 + " text," +
                    Constants.SearchResultDataConfig.CommodityPicPath5 + " text," +
                    Constants.SearchResultDataConfig.CreateAt + " text," +
                    Constants.SearchResultDataConfig.UserPhoneNumber + " text)";
    private Context context;

    public GetSearchResultDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                                         int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "CreateSQL:" + Create_SearchResultData_Table);
        db.execSQL(Create_SearchResultData_Table);
        Log.i(TAG, "创建查询结果信息表成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
