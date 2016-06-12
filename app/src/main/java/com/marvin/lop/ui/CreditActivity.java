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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;

import com.marvin.lop.R;
import com.marvin.lop.bean.CreditInfo;
import com.marvin.lop.config.Constants;
import com.marvin.lop.database.GetCommodityInfoDataBaseHelper;
import com.marvin.lop.ui.base.BaseActivity;
import com.marvin.lop.utils.CreateNewBuying;
import com.marvin.lop.utils.CreateNewCredit;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Marvin on 2016/6/11.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public class CreditActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = CreditActivity.class.getSimpleName();

    private ImageButton back;
    private EditText content;
    private RatingBar userRatingBar;
    private RatingBar commodityRatingBar;
    private Button commit;

    private int userStar;
    private int commodityStar;

    SharedPreferences sharedPreferences;

    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper = null;
    private Cursor cursor;
    private Intent mIntent;
    private int id;

    private String sellerObjectid;
    private CreditInfo creditInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);
        sharedPreferences = this.getSharedPreferences(Constants.SharedPreferencesConfig.FILENAME, MODE_PRIVATE);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        back = (ImageButton) findViewById(R.id.credit_back_button);
        content = (EditText) findViewById(R.id.credit_content_et);
        userRatingBar = (RatingBar) findViewById(R.id.credit_users_ratingbar);
        commodityRatingBar = (RatingBar) findViewById(R.id.credit_commodity_ratingbar);
        commit = (Button) findViewById(R.id.credit_commit_btn);
    }

    @Override
    protected void initView() {
        back.setOnClickListener(this);
        commit.setOnClickListener(this);
        userRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                userStar = ratingBar.getNumStars();
            }
        });
        commodityRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                commodityStar = ratingBar.getNumStars();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.credit_back_button:
                CreditActivity.this.finish();
                break;
            case R.id.credit_commit_btn:
                if (!content.getText().toString().equals("")) {
                    // 获取卖家ID和当前用户ID，当前日期，上传到数据库中，数据库中保存的字段有日期，内容，买方，卖方，卖方星等，商品星等
                    String buyerObjectid = sharedPreferences.getString(Constants.SharedPreferencesConfig.USER_OBJECT_ID, null);
                    mIntent = getIntent();
                    id = mIntent.getIntExtra("_id", -1);
                    if (id != -1) {
                        try {
                            openHelper = new GetCommodityInfoDataBaseHelper(CreditActivity.this,
                                    Constants.CommodityInfoDataConfig.DataBaseName, null, 1);
                            database = openHelper.getWritableDatabase();
                            cursor = database.rawQuery("select * from " + Constants.CommodityInfoDataConfig.TableName +
                                    " where _id=" + id, null);
                            if (cursor.moveToFirst()) {
                                sellerObjectid = cursor.getString(cursor.getColumnIndex(Constants.CommodityInfoDataConfig.UserObjectId));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.i(TAG, "positionID error！");
                    }

                    CreateNewCredit cnc = new CreateNewCredit();
                    creditInfo = cnc.createCreditInfoInServer(content.getText().toString(), buyerObjectid, sellerObjectid, userStar,
                            commodityStar);
                    creditInfo.save(CreditActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            DisPlay("用户评价成功");
                            CreditActivity.this.finish();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            DisPlay("用户评价失败!");
                            Log.i(TAG, i + "  " + s);
                        }
                    });
                }
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
