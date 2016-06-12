package com.marvin.lop.ui;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.marvin.lop.R;
import com.marvin.lop.adapter.CommodityInfoCursorAdapter;
import com.marvin.lop.config.Constants;
import com.marvin.lop.database.GetCommodityInfoDataBaseHelper;
import com.marvin.lop.task.LoadingCommodityInfoDataTask;
import com.marvin.lop.ui.base.BaseActivity;
import com.marvin.lop.ui.linstener.OnDataLoadSuccessLinstener;

/**
 * Created by Marvin on 2016/3/20.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 * 首页
 */

public class IndexActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    public static final String TAG = IndexActivity.class.getSimpleName();

    private ImageButton buying_imgbtn;
    private ImageButton rent_imgbtn;
    private ImageButton borrow_imgbtn;
    private ListView listview;
    private ImageView refresh_img;

    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper = null;
    private Cursor cursor;
    private CommodityInfoCursorAdapter commodityInfoCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        buying_imgbtn = (ImageButton) findViewById(R.id.index_buying_btn);
        rent_imgbtn = (ImageButton) findViewById(R.id.index_rent_btn);
        borrow_imgbtn = (ImageButton) findViewById(R.id.index_borrow_btn);
        listview = (ListView) findViewById(R.id.index_listview);
        refresh_img = (ImageView) findViewById(R.id.index_refresh_imgview);
    }

    @Override
    protected void initView() {
        refresh_img.setOnClickListener(this);
        // 使用异步加载去获取服务器数据
        Log.i(TAG, "使用异步加载去获取服务器数据......");
        LoadingCommodityInfoDataTask loadingCommodityInfoDataTask = new LoadingCommodityInfoDataTask(this);
        loadingCommodityInfoDataTask.execute();
        loadingCommodityInfoDataTask.setOnDataLoadSuccessLinstener(new OnDataLoadSuccessLinstener() {
            @Override
            public void onSuccess(Boolean isSuccess) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.index_refresh_imgview:
                Log.i(TAG, "加载完成，准备给List设置适配器");
                openHelper = new GetCommodityInfoDataBaseHelper(IndexActivity.this,
                        Constants.CommodityInfoDataConfig.DataBaseName, null, 1);
                database = openHelper.getWritableDatabase();
                cursor = database.rawQuery("select * from " + Constants.CommodityInfoDataConfig.TableName, null);
                commodityInfoCursorAdapter = new CommodityInfoCursorAdapter(IndexActivity.this, cursor, true);
                Log.i(TAG, "1");
                if (listview != null) {
                    Log.i(TAG, "2");
                    listview.setAdapter(commodityInfoCursorAdapter);
                    listview.setOnItemClickListener(this);
                }

                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "OnItemClickListener++++++++++++++++");
        Intent mIntent = new Intent();
        mIntent.putExtra("_id", cursor.getInt(cursor.getColumnIndex("_id")));
        mIntent.putExtra("activity", "index");
        mIntent.setClass(IndexActivity.this, ProductDetailsActivity.class);
        startActivityForResult(mIntent, Constants.IntentRequestCode.Index2ProductDetails);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.IntentRequestCode.Index2ProductDetails) {
            switch (resultCode) {
                case Constants.IntentResultCode.Product2Index:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null) {
            cursor.close();
        }
        if (database != null) {
            database.close();
        }
        if (openHelper != null) {
            openHelper.close();
        }
    }

}
