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
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.marvin.lop.R;
import com.marvin.lop.config.Constants;
import com.marvin.lop.database.GetCommodityInfoDataBaseHelper;
import com.marvin.lop.ui.base.BaseActivity;

import java.lang.ref.SoftReference;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConversationListener;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by Marvin on 2016/6/10.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public class ProductDetailsActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = ProductDetailsActivity.class.getSimpleName();

    private ImageButton back;
    private TextView nickname;
    private TextView publishTime;
    private TextView price;
    private TextView oriPrice;
    private TextView title;
    private TextView describe;
    private TextView category;
    private SimpleDraweeView img1;
    private SimpleDraweeView img2;
    private SimpleDraweeView img3;
    private SimpleDraweeView img4;
    private SimpleDraweeView img5;
    private Button buy;
    private ImageView chat;

    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper = null;
    private Cursor cursor;
    private Intent mIntent;
    private int id;

    private String sellerObjectid;
    private String currentObjectid;

    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_product_details);
        //初始化Fresco开源库
        sharedPreferences = this.getSharedPreferences(Constants.SharedPreferencesConfig.FILENAME, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(Constants.SharedPreferencesConfig.USER_LOGIN_STATE, false) != false) {
            currentObjectid = sharedPreferences.getString(Constants.SharedPreferencesConfig.USER_OBJECT_ID, null);
        }
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        back = (ImageButton) findViewById(R.id.product_details_back_button);
        nickname = (TextView) findViewById(R.id.product_details_nickname);
        publishTime = (TextView) findViewById(R.id.product_details_publishtime);
        price = (TextView) findViewById(R.id.product_details_price);
        oriPrice = (TextView) findViewById(R.id.product_details_oripirce);
        title = (TextView) findViewById(R.id.product_details_title);
        describe = (TextView) findViewById(R.id.product_details_describe);
        category = (TextView) findViewById(R.id.product_details_commodityinfo_index_category);
        img1 = (SimpleDraweeView) findViewById(R.id.product_details_commodityinfo_index_img1);
        img2 = (SimpleDraweeView) findViewById(R.id.product_details_commodityinfo_index_img2);
        img3 = (SimpleDraweeView) findViewById(R.id.product_details_commodityinfo_index_img3);
        img4 = (SimpleDraweeView) findViewById(R.id.product_details_commodityinfo_index_img4);
        img5 = (SimpleDraweeView) findViewById(R.id.product_details_commodityinfo_index_img5);
        buy = (Button) findViewById(R.id.buy_btn);
        chat = (ImageView) findViewById(R.id.buy_chat_img);
    }

    @Override
    protected void initView() {
        back.setOnClickListener(this);
        buy.setOnClickListener(this);
        chat.setOnClickListener(this);

        mIntent = getIntent();
        id = mIntent.getIntExtra("_id", -1);
        String activity = mIntent.getStringExtra("activity");
        switch (activity) {
            case "search":
                if (id != -1) {
                    try {
                        openHelper = new GetCommodityInfoDataBaseHelper(ProductDetailsActivity.this,
                                Constants.SearchResultDataConfig.DataBaseName, null, 1);
                        database = openHelper.getWritableDatabase();
                        cursor = database.rawQuery("select * from " + Constants.SearchResultDataConfig.TableName +
                                " where _id=" + id, null);
                        if (cursor.moveToFirst()) {
                            sellerObjectid = cursor.getString(cursor.getColumnIndex(Constants.SearchResultDataConfig.UserObjectId));
                            nickname.setText(cursor.getString(cursor.getColumnIndex(Constants.SearchResultDataConfig.UserPhoneNumber)));
                            publishTime.setText(cursor.getString(cursor.getColumnIndex(Constants.SearchResultDataConfig.CreateAt)));
                            price.setText(cursor.getString(cursor.getColumnIndex(Constants.SearchResultDataConfig.CommodityPrice)));
                            oriPrice.setText(cursor.getString(cursor.getColumnIndex(Constants.SearchResultDataConfig.CommodityOriPrice)));
                            title.setText(cursor.getString(cursor.getColumnIndex(Constants.SearchResultDataConfig.CommodityTitle)));
                            describe.setText(cursor.getString(cursor.getColumnIndex(Constants.SearchResultDataConfig.CommodityDescribe)));
                            category.setText(cursor.getString(cursor.getColumnIndex(Constants.SearchResultDataConfig.CommodityCategory1)));
                            Uri uri1 = Uri.parse(cursor.getString(cursor.getColumnIndex(Constants.SearchResultDataConfig.CommodityPicPath1)));
                            Uri uri2 = Uri.parse(cursor.getString(cursor.getColumnIndex(Constants.SearchResultDataConfig.CommodityPicPath2)));
                            Uri uri3 = Uri.parse(cursor.getString(cursor.getColumnIndex(Constants.SearchResultDataConfig.CommodityPicPath3)));
                            Uri uri4 = Uri.parse(cursor.getString(cursor.getColumnIndex(Constants.SearchResultDataConfig.CommodityPicPath4)));
                            Uri uri5 = Uri.parse(cursor.getString(cursor.getColumnIndex(Constants.SearchResultDataConfig.CommodityPicPath5)));
                            img1.setImageURI(uri1);
                            img2.setImageURI(uri2);
                            img3.setImageURI(uri3);
                            img4.setImageURI(uri4);
                            img5.setImageURI(uri5);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "index":
                if (id != -1) {
                    try {
                        openHelper = new GetCommodityInfoDataBaseHelper(ProductDetailsActivity.this,
                                Constants.CommodityInfoDataConfig.DataBaseName, null, 1);
                        database = openHelper.getWritableDatabase();
                        cursor = database.rawQuery("select * from " + Constants.CommodityInfoDataConfig.TableName +
                                " where _id=" + id, null);
                        if (cursor.moveToFirst()) {
                            sellerObjectid = cursor.getString(cursor.getColumnIndex(Constants.CommodityInfoDataConfig.UserObjectId));
                            nickname.setText(cursor.getString(cursor.getColumnIndex(Constants.CommodityInfoDataConfig.UserPhoneNumber)));
                            publishTime.setText(cursor.getString(cursor.getColumnIndex(Constants.CommodityInfoDataConfig.CreateAt)));
                            price.setText(cursor.getString(cursor.getColumnIndex(Constants.CommodityInfoDataConfig.CommodityPrice)));
                            oriPrice.setText(cursor.getString(cursor.getColumnIndex(Constants.CommodityInfoDataConfig.CommodityOriPrice)));
                            title.setText(cursor.getString(cursor.getColumnIndex(Constants.CommodityInfoDataConfig.CommodityTitle)));
                            describe.setText(cursor.getString(cursor.getColumnIndex(Constants.CommodityInfoDataConfig.CommodityDescribe)));
                            category.setText(cursor.getString(cursor.getColumnIndex(Constants.CommodityInfoDataConfig.CommodityCategory1)));
                            Uri uri1 = Uri.parse(cursor.getString(cursor.getColumnIndex(Constants.CommodityInfoDataConfig.CommodityPicPath1)));
                            Uri uri2 = Uri.parse(cursor.getString(cursor.getColumnIndex(Constants.CommodityInfoDataConfig.CommodityPicPath2)));
                            Uri uri3 = Uri.parse(cursor.getString(cursor.getColumnIndex(Constants.CommodityInfoDataConfig.CommodityPicPath3)));
                            Uri uri4 = Uri.parse(cursor.getString(cursor.getColumnIndex(Constants.CommodityInfoDataConfig.CommodityPicPath4)));
                            Uri uri5 = Uri.parse(cursor.getString(cursor.getColumnIndex(Constants.CommodityInfoDataConfig.CommodityPicPath5)));
                            img1.setImageURI(uri1);
                            img2.setImageURI(uri2);
                            img3.setImageURI(uri3);
                            img4.setImageURI(uri4);
                            img5.setImageURI(uri5);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "push":
                // 来自通知栏的商品推荐消息
                SharedPreferences s = this.getSharedPreferences(Constants.PushInfoSharePreferencesConfig.FileName, MODE_PRIVATE);
                sellerObjectid = s.getString(Constants.PushInfoSharePreferencesConfig.userObjectId, null);
                nickname.setText(s.getString(Constants.PushInfoSharePreferencesConfig.userPhoneNumber, null));
                publishTime.setText(s.getString(Constants.PushInfoSharePreferencesConfig.createAt, null));
                price.setText(s.getString(Constants.PushInfoSharePreferencesConfig.commodityPrice, null));
                oriPrice.setText(s.getString(Constants.PushInfoSharePreferencesConfig.commodityOriPrice, null));
                title.setText(s.getString(Constants.PushInfoSharePreferencesConfig.CommodityTitle, null));
                describe.setText(s.getString(Constants.PushInfoSharePreferencesConfig.ComodityDescribe, null));
                category.setText(s.getString(Constants.PushInfoSharePreferencesConfig.commodityCategory1, null));
                Uri uri1 = Uri.parse(s.getString(Constants.PushInfoSharePreferencesConfig.commodityPicPath1, null));
                Uri uri2 = Uri.parse(s.getString(Constants.PushInfoSharePreferencesConfig.commodityPicPath2, null));
                Uri uri3 = Uri.parse(s.getString(Constants.PushInfoSharePreferencesConfig.commodityPicPath3, null));
                Uri uri4 = Uri.parse(s.getString(Constants.PushInfoSharePreferencesConfig.commodityPicPath4, null));
                Uri uri5 = Uri.parse(s.getString(Constants.PushInfoSharePreferencesConfig.commodityPicPath5, null));
                img1.setImageURI(uri1);
                img2.setImageURI(uri2);
                img3.setImageURI(uri3);
                img4.setImageURI(uri4);
                img5.setImageURI(uri5);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.product_details_back_button:
                setResult(Constants.IntentResultCode.Product2Index);
                this.finish();
                break;
            case R.id.buy_btn:
                if (sharedPreferences.getBoolean(Constants.SharedPreferencesConfig.USER_LOGIN_STATE, false) != false) {
                    if (sellerObjectid != currentObjectid) {
                        Intent mIntent = new Intent();
                        mIntent.putExtra("_id", cursor.getInt(cursor.getColumnIndex("_id")));
                        mIntent.setClass(ProductDetailsActivity.this, BargainActivity.class);
                        startActivity(mIntent);
                    } else {
                        DisPlay("亲这是你自己发布的商品啊");
                    }
                } else {
                    DisPlay("请登录后再进行购买!");
                }
                break;
            case R.id.buy_chat_img:
                if (sharedPreferences.getBoolean(Constants.SharedPreferencesConfig.USER_LOGIN_STATE, false) != false) {
                    if (sellerObjectid != currentObjectid) {
                        if (currentObjectid != null) {
                            // 连接服务器
                            BmobIM.connect(currentObjectid, new ConnectListener() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null) {
                                        Log.i(TAG, "连接服务器成功!");
                                    } else {
                                        Log.i(TAG, e.getErrorCode() + "----" + e.getMessage());
                                    }
                                }
                            });

                            BmobIMUserInfo info = new BmobIMUserInfo(sellerObjectid, "卖家", null);
                            // 创建常态会话
                            BmobIM.getInstance().startPrivateConversation(info, new ConversationListener() {
                                @Override
                                public void done(BmobIMConversation bmobIMConversation, BmobException e) {
                                    if (e == null) {
                                        //在此跳转到聊天页面
                                        Intent i1 = new Intent();
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("chat", bmobIMConversation);
                                        i1.putExtras(bundle);
                                        i1.setClass(ProductDetailsActivity.this, ChatActivity.class);
                                        startActivity(i1);
                                    } else {
                                        Log.i(TAG, e.getMessage() + "----" + e.getErrorCode());
                                    }
                                }
                            });
                        } else {
                            DisPlay("用户没有登录");
                        }
                    }
                } else {
                    DisPlay("请登录后再进行聊天!");
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
            cursor = null;
        }
        if (database != null) {
            database.close();
            database = null;
        }
        if (openHelper != null) {
            openHelper.close();
            openHelper = null;
        }
        // 释放资源
        sharedPreferences = null;
        sellerObjectid = null;
        currentObjectid = null;
        mIntent = null;
        chat = null;
        buy = null;
        img1 = null;
        img2 = null;
        img3 = null;
        img4 = null;
        img5 = null;
        category = null;
        describe = null;
        title = null;
        oriPrice = null;
        price = null;
        publishTime = null;
        nickname = null;
        back = null;
    }
}
