package com.marvin.lop.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.marvin.lop.R;
import com.marvin.lop.bean.CommodityInfo;
import com.marvin.lop.bean.HistoryInfo;
import com.marvin.lop.config.Constants;
import com.marvin.lop.database.GetCommodityInfoDataBaseHelper;
import com.marvin.lop.ui.base.BaseActivity;
import com.marvin.lop.utils.CreateNewHistoryInfo;
import com.marvin.lop.zxing.decoding.Intents;

import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Marvin on 2016/6/11.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public class BargainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = BargainActivity.class.getSimpleName();

    private ImageButton back;
    private TextView phone;
    private Button success;
    private Button cancel;

    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper = null;
    private Cursor cursor;
    private Intent mIntent;
    private int id;
    private String commodityObjectid;
    private String commodityCategory;
    private String commodityTitle;
    private String sellerPhone;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bargain);
        sharedPreferences = this.getSharedPreferences(Constants.SharedPreferencesConfig.FILENAME, MODE_PRIVATE);

        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        back = (ImageButton) findViewById(R.id.bargain_back_button);
        phone = (TextView) findViewById(R.id.bargain_phonenumber);
        success = (Button) findViewById(R.id.bargain_success_btn);
        cancel = (Button) findViewById(R.id.bargain_cancel_btn);
    }

    @Override
    protected void initView() {
        back.setOnClickListener(this);
        success.setOnClickListener(this);
        cancel.setOnClickListener(this);

        mIntent = getIntent();
        id = mIntent.getIntExtra("_id", -1);
        if (id != -1) {
            try {
                openHelper = new GetCommodityInfoDataBaseHelper(BargainActivity.this,
                        Constants.CommodityInfoDataConfig.DataBaseName, null, 1);
                database = openHelper.getWritableDatabase();
                cursor = database.rawQuery("select * from " + Constants.CommodityInfoDataConfig.TableName +
                        " where _id=" + id, null);
                if (cursor.moveToFirst()) {
                    phone.setText(cursor.getString(cursor.getColumnIndex(Constants.CommodityInfoDataConfig.UserObjectId)));
                    commodityObjectid = cursor.getString(cursor.getColumnIndex(Constants.CommodityInfoDataConfig.ObjectId));
                    commodityCategory = cursor.getString(cursor.getColumnIndex(Constants.CommodityInfoDataConfig.CommodityCategory1));
                    commodityTitle = cursor.getString(cursor.getColumnIndex(Constants.CommodityInfoDataConfig.CommodityTitle));
                    sellerPhone = cursor.getString(cursor.getColumnIndex(Constants.CommodityInfoDataConfig.UserPhoneNumber));
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
            case R.id.bargain_back_button:
                BargainActivity.this.finish();
                break;
            case R.id.bargain_success_btn:
                // 进入评价界面,修改商品状态
                CommodityInfo commodityInfo = new CommodityInfo();
                commodityInfo.setCommodityState(true);
                commodityInfo.update(this, commodityObjectid, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        Log.i(TAG, "商品状态" + commodityObjectid + " 修改成功");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Log.i(TAG, "商品状态" + commodityObjectid + " 修改失败");
                    }
                });
                // 创建用户历史记录
                CreateNewHistoryInfo cnhi = new CreateNewHistoryInfo();
                String objectid = sharedPreferences.getString(Constants.SharedPreferencesConfig.USER_OBJECT_ID, null);
                String userPhone = sharedPreferences.getString(Constants.SharedPreferencesConfig.PHONE_NUMBER, null);
                HistoryInfo historyInfo;
                if (objectid != null) {
                    historyInfo = cnhi.createHistoryInfoInServer(objectid, commodityCategory,commodityObjectid, commodityTitle,
                            userPhone, sellerPhone);
                    historyInfo.save(BargainActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            Log.i(TAG, "用户 历史 记录保存成功");
                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                }

                Intent mIntent = new Intent();
                mIntent.putExtra("_id", cursor.getInt(cursor.getColumnIndex("_id")));
                mIntent.setClass(BargainActivity.this, CreditActivity.class);
                startActivity(mIntent);
                break;
            case R.id.bargain_cancel_btn:
                // 结束该界面
                BargainActivity.this.finish();
                break;
            default:
                break;
        }
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
