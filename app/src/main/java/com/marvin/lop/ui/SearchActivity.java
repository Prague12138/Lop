package com.marvin.lop.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.marvin.lop.R;
import com.marvin.lop.adapter.CommodityInfoCursorAdapter;
import com.marvin.lop.bean.BeanConstants;
import com.marvin.lop.bean.CommodityInfo;
import com.marvin.lop.config.Constants;
import com.marvin.lop.database.GetSearchResultDataBaseHelper;
import com.marvin.lop.ui.base.BaseActivity;
import com.marvin.lop.widgets.AutoClearEditText;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Marvin on 2016/3/20.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public class SearchActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private AutoClearEditText mEditText;
    private ImageButton mImageButton;
    private TextView search_null;
    private ListView listView;


    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper = null;
    private Cursor cursor;
    private CommodityInfoCursorAdapter commodityInfoCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        mEditText = (AutoClearEditText) findViewById(R.id.search_edit);
        mImageButton = (ImageButton) findViewById(R.id.search_button);
        search_null = (TextView) findViewById(R.id.search_null_msg);
        listView = (ListView) findViewById(R.id.search_listview);
    }

    @Override
    protected void initView() {
        mEditText.requestFocus();
        mImageButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!mEditText.getText().toString().equals("")) {
                    // 根据输入的关键字查询
                    BmobQuery<CommodityInfo> query = new BmobQuery<>();
                    query.addWhereContains(BeanConstants.CommodityInfoProperties.CommodityTitle, mEditText.getText().toString());
                    query.findObjects(SearchActivity.this, new FindListener<CommodityInfo>() {
                        @Override
                        public void onSuccess(List<CommodityInfo> list) {
                            if (list.size() > 0) {
                                Log.i(TAG, "共获取" + list.size() + "条商品信息");
                                GetSearchResultDataBaseHelper dbHelper = new GetSearchResultDataBaseHelper(SearchActivity.this,
                                        Constants.SearchResultDataConfig.DataBaseName, null, 1);
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                final ContentValues values = new ContentValues();
                                Cursor cursor = db.rawQuery("select * from " + Constants.SearchResultDataConfig.TableName, null);
                                if (cursor.getCount() < 1) {
                                    Log.i(TAG, "数据库为空");
                                    for (CommodityInfo commodity : list) {
                                        values.put(Constants.SearchResultDataConfig.ObjectId, commodity.getObjectId());
                                        values.put(Constants.SearchResultDataConfig.CommodityPrice, commodity.getCommodityPrice());
                                        values.put(Constants.SearchResultDataConfig.CommodityTitle, commodity.getCommodityTitle());
                                        values.put(Constants.SearchResultDataConfig.UserObjectId, commodity.getUserObjectId());
                                        values.put(Constants.SearchResultDataConfig.UserPhoneNumber, commodity.getUserPhoneNumber());
                                        values.put(Constants.SearchResultDataConfig.CommodityCategory1, commodity.getCommodityCategory1());
                                        values.put(Constants.SearchResultDataConfig.CommodityCategory2, commodity.getCommodityCategory2());
                                        values.put(Constants.SearchResultDataConfig.CommodityDescribe, commodity.getCommodityDescribe());
                                        values.put(Constants.SearchResultDataConfig.CommodityOriPrice, commodity.getCommodityOriPrice());
                                        values.put(Constants.SearchResultDataConfig.CommodityPicPath1, commodity.getCommodityPicPath1());
                                        values.put(Constants.SearchResultDataConfig.CommodityPicPath2, commodity.getCommodityPicPath2());
                                        values.put(Constants.SearchResultDataConfig.CommodityPicPath3, commodity.getCommodityPicPath3());
                                        values.put(Constants.SearchResultDataConfig.CommodityPicPath4, commodity.getCommodityPicPath4());
                                        values.put(Constants.SearchResultDataConfig.CommodityPicPath5, commodity.getCommodityPicPath5());
                                        values.put(Constants.SearchResultDataConfig.CreateAt, commodity.getCreatedAt());
                                        db.insert(Constants.SearchResultDataConfig.TableName, null, values);// 插入数据
                                        values.clear();//清空
                                    }
                                } else {
                                    //如果数据库不为空，就清空数据库，重新写入
                                    Log.i(TAG, "数据库不为空");
                                    db.execSQL("delete from " + Constants.SearchResultDataConfig.TableName);
                                    for (CommodityInfo commodity1 : list) {
                                        values.put(Constants.SearchResultDataConfig.ObjectId, commodity1.getObjectId());
                                        values.put(Constants.SearchResultDataConfig.CommodityPrice, commodity1.getCommodityPrice());
                                        values.put(Constants.SearchResultDataConfig.CommodityTitle, commodity1.getCommodityTitle());
                                        values.put(Constants.SearchResultDataConfig.UserObjectId, commodity1.getUserObjectId());
                                        values.put(Constants.SearchResultDataConfig.UserPhoneNumber, commodity1.getUserPhoneNumber());
                                        values.put(Constants.SearchResultDataConfig.CommodityCategory1, commodity1.getCommodityCategory1());
                                        values.put(Constants.SearchResultDataConfig.CommodityCategory2, commodity1.getCommodityCategory2());
                                        values.put(Constants.SearchResultDataConfig.CommodityDescribe, commodity1.getCommodityDescribe());
                                        values.put(Constants.SearchResultDataConfig.CommodityOriPrice, commodity1.getCommodityOriPrice());
                                        values.put(Constants.SearchResultDataConfig.CommodityPicPath1, commodity1.getCommodityPicPath1());
                                        values.put(Constants.SearchResultDataConfig.CommodityPicPath2, commodity1.getCommodityPicPath2());
                                        values.put(Constants.SearchResultDataConfig.CommodityPicPath3, commodity1.getCommodityPicPath3());
                                        values.put(Constants.SearchResultDataConfig.CommodityPicPath4, commodity1.getCommodityPicPath4());
                                        values.put(Constants.SearchResultDataConfig.CommodityPicPath5, commodity1.getCommodityPicPath5());
                                        values.put(Constants.SearchResultDataConfig.CreateAt, commodity1.getCreatedAt());
                                        db.insert(Constants.SearchResultDataConfig.TableName, null, values);// 插入数据
                                        values.clear();//清空
                                    }
                                }
                                cursor.close();
                                db.close();
                                dbHelper.close();
                                switchSearchState(true);
                            } else {
                                DisPlay("没有对应的商品信息");
                                switchSearchState(false);
                            }
                        }

                        @Override
                        public void onError(int i, String s) {

                        }
                    });
                } else {
                    DisPlay("请输入搜索内容");
                    mEditText.setText("");
                }
            }
        });
    }

    private void switchSearchState(boolean isSuccess) {
        if (isSuccess) {
            // 如果返回搜索结果
            search_null.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            // 加载数据库数据到ListView上
            loadData();
        } else {
            // 如果没有返回数据
            search_null.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
    }

    private void loadData() {
        openHelper = new GetSearchResultDataBaseHelper(SearchActivity.this,
                Constants.SearchResultDataConfig.DataBaseName, null, 1);
        database = openHelper.getWritableDatabase();
        cursor = database.rawQuery("select * from " + Constants.SearchResultDataConfig.TableName, null);
        commodityInfoCursorAdapter = new CommodityInfoCursorAdapter(SearchActivity.this, cursor, true);
        if (listView != null) {
            listView.setAdapter(commodityInfoCursorAdapter);
            listView.setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent mIntent = new Intent();
        mIntent.putExtra("_id", cursor.getInt(cursor.getColumnIndex("_id")));
        mIntent.putExtra("activity", "search");
        mIntent.setClass(SearchActivity.this, ProductDetailsActivity.class);
        startActivityForResult(mIntent, Constants.IntentRequestCode.Search2ProductDetails);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.IntentRequestCode.Search2ProductDetails) {
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

