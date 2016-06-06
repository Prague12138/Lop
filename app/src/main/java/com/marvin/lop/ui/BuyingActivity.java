package com.marvin.lop.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marvin.lop.R;
import com.marvin.lop.bean.BuyingInfo;
import com.marvin.lop.config.Constants;
import com.marvin.lop.ui.base.BaseActivity;
import com.marvin.lop.utils.CreateNewBuying;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Marvin on 2016/6/4.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 * 发布求购信息的界面
 */

public class BuyingActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = BuyingActivity.class.getSimpleName();

    private ImageButton mBackbtn;
    private EditText mTitle;
    private EditText mDes;
    private EditText mPrice;
    private TextView mCategory;
    private RelativeLayout mCategoryClick;
    private EditText mDeadlineDate;
    private EditText mDeadlineTime;
    private Button mBuying;

    private String itemTitle;
    private String itemDes;
    private String itemPrice;
    private String categoryName1;
    private String categoryName2;
    private String itemDeadlineDate;
    private String itemDeadlineTime;

    private boolean isCategory = false;

    private BuyingInfo buyingInfo;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buying);

        sharedPreferences = this.getSharedPreferences(Constants.SharedPreferencesConfig.FILENAME, MODE_PRIVATE);

        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        mBackbtn = (ImageButton) findViewById(R.id.buying_back_button);
        mTitle = (EditText) findViewById(R.id.buying_item_title_et);
        mDes = (EditText) findViewById(R.id.buying_item_description_et);
        mPrice = (EditText) findViewById(R.id.buying_price_default);
        mCategory = (TextView) findViewById(R.id.buying_classification);
        mCategoryClick = (RelativeLayout) findViewById(R.id.buying_classification_layout_click);
        mDeadlineDate = (EditText) findViewById(R.id.buying_deadline_date_et);
        mDeadlineTime = (EditText) findViewById(R.id.buying_deadline_time_et);
        mBuying = (Button) findViewById(R.id.buying_btn);
    }


    @Override
    protected void initView() {
        buyingInfo = new BuyingInfo();

        mBackbtn.setOnClickListener(this);
        mCategoryClick.setOnClickListener(this);
        mBuying.setOnClickListener(this);
//        mDeadline.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buying_back_button:
                // 返回个人信息中心界面
                setResult(Constants.IntentResultCode.BuyingBack2PersonalCenter);
                this.finish();
                break;
            case R.id.buying_classification_layout_click:
                // 选择物品所在分类
                Intent mIntent = new Intent(BuyingActivity.this, PublishCategoryListActivity.class);
                startActivityForResult(mIntent, Constants.IntentRequestCode.Publish2PublishCategoryList);
                break;
//            case R.id.buying_deadline_time_tv:
//                 选择截止时间
//                mDialogAll.show(getSupportFragmentManager(), "all");
//                break;
            case R.id.buying_btn:
                // 提交物品信息之后 返回到个人信息界面并将物品信息在后台上传到服务器
                // TODO: 2016/5/7  另外，需要把上传物品信息作为异步操作来处理，当上传成功，上传的时候在通知里面显示一个进度条用来表示上传的进度
                if (!mTitle.getText().toString().equals("")) {
                    if (!mDes.getText().toString().equals("")) {
                        if (!mPrice.getText().toString().equals("")) {
                            if (!mDeadlineDate.getText().toString().equals("")) {
                                if (!mDeadlineTime.getText().toString().equals("")) {
                                    if (isCategory) {
                                        itemTitle = mTitle.getText().toString();
                                        itemDes = mDes.getText().toString();
                                        itemPrice = mPrice.getText().toString();
                                        itemDeadlineDate = mDeadlineDate.getText().toString();
                                        itemDeadlineTime = mDeadlineTime.getText().toString();

                                        // 将求购信息上传到数据库
                                        CreateNewBuying cnb = new CreateNewBuying();
//                                BmobDate bd = BmobDate.createBmobDate("yyyy-MM-dd HH:mm:ss", itemDeadline);
                                        String userObjectId = sharedPreferences.getString(Constants.SharedPreferencesConfig.USER_OBJECT_ID, null);
                                        buyingInfo = cnb.createBuyingInfoInServer(itemTitle, itemDes, itemPrice, itemDeadlineDate, itemDeadlineTime,
                                                categoryName1, categoryName2, userObjectId, false);
                                        buyingInfo.save(BuyingActivity.this, new SaveListener() {
                                            @Override
                                            public void onSuccess() {
                                                DisPlay("求购信息发布成功!");
                                                // TODO: 2016/6/4 在发布商品信息的时候讲操作放到后台运行，就行发微博一样，先显示一个通知，显示正在发送，
                                                // 发布成功后就提示发布成功
                                                setResult(Constants.IntentResultCode.BuyingSuccess);
                                                BuyingActivity.this.finish();
                                            }

                                            @Override
                                            public void onFailure(int i, String s) {
                                                DisPlay("求购信息发布失败!");
                                                Log.i(TAG, i + "  " + s);
                                            }
                                        });
                                    } else {
                                        DisPlay("请选择物品分类");
                                    }
                                } else {
                                    DisPlay("请输入求购截止时间");
                                }
                            } else {
                                DisPlay("请输入求购截止日期");
                            }
                        } else {
                            DisPlay("请输入价格");
                        }
                    } else {
                        DisPlay("请输入物品描述");
                    }
                } else {
                    DisPlay("请输入标题");
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.IntentRequestCode.Publish2PublishCategoryList:
                switch (resultCode) {
                    case Constants.IntentResultCode.PublishCategoryListBack2Buying:
                        // 从物品分类界面返回该界面，没有操作
                        break;
                    case Constants.IntentResultCode.CategoryName:
                        Bundle bundle = data.getExtras();
                        categoryName1 = bundle.getString("categoryName1");
                        categoryName2 = bundle.getString("categoryName2");
                        Log.i(TAG, "CategoryName = " + categoryName1 + "--" + categoryName2);
                        mCategory.setText(categoryName1 + "--" + categoryName2);
                        isCategory = true;
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
