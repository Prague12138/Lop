package com.marvin.lop.ui;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.marvin.lop.R;
import com.marvin.lop.adapter.IndexGalleryAdapter;
import com.marvin.lop.entity.IndexGalleryItemData;
import com.marvin.lop.ui.base.BaseActivity;
import com.marvin.lop.utils.CommonTools;
import com.marvin.lop.widgets.HomeSearchBarPopupWindow;
import com.marvin.lop.widgets.jazzviewpager.JazzyViewPager;
import com.marvin.lop.widgets.jazzviewpager.OutlineContainer;
import com.marvin.lop.zxing.CaptureActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marvin on 2016/3/20.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public class IndexActivity extends BaseActivity implements View.OnClickListener,
        HomeSearchBarPopupWindow.onSearchBarItemClickListener {

    public static final String TAG = IndexActivity.class.getSimpleName();

    // TODO: 2016/3/21 重写秒杀部分
    private ImageView mMiaoShaImage = null;
    private TextView mIndexHour = null;
    private TextView mIndexMin = null;
    private TextView mIndexSeconds = null;
    private TextView mIndexPrice = null;
    private TextView mIndexRawPrice = null;

    // =============中部导航栏模块==================
    private ImageButton shake;
    private Intent mIntent;

    // ==============广告切换=======================
    private JazzyViewPager mViewPager = null;

    /**
     * 装入指引用的ImageView数组
     */
    private ImageView[] mIndicators;

    /**
     * 装ViewPager中ImageView的数组
     */
    private ImageView[] mImageViews;
    private List<String> mImageUrls = new ArrayList<>();
    private LinearLayout mIndicator = null;
    private String mImageUrl = null;
    private static final int MSG_CHANGE_PHOTO = 1;
    /**
     * 图片自动切换时间长度
     */
    private static final int PHOTO_CHANGE_TIME = 3000;
    //==================广告切换=======================

    private Gallery mStormGallery = null;
    private Gallery mPromotionGallery = null;
    private IndexGalleryAdapter mStormAdapter = null;
    private IndexGalleryAdapter mPromotionAdapter = null;
    private List<IndexGalleryItemData> mStormListData = new ArrayList<>();
    private List<IndexGalleryItemData> mPromotionListData = new ArrayList<>();
    private IndexGalleryItemData mItemData = null;
    private HomeSearchBarPopupWindow mBarPopupWindow = null;
    private EditText mSearchBox = null;
    private ImageButton mCameraButton = null;
    private LinearLayout mTopLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        mHandler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case MSG_CHANGE_PHOTO:
                        int index = mViewPager.getCurrentItem();
                        if (index == mImageUrls.size() - 1) {
                            index = -1;
                        }
                        mViewPager.setCurrentItem(index + 1);
                        mHandler.sendEmptyMessageDelayed(MSG_CHANGE_PHOTO, PHOTO_CHANGE_TIME);
                }

            }
        };

        initData();
        findViewById();
        initView();


    }

    @Override
    protected void findViewById() {
        mIndexHour = (TextView) findViewById(R.id.index_miaosha_hour);
        mIndexMin = (TextView) findViewById(R.id.index_miaosha_min);
        mIndexSeconds = (TextView) findViewById(R.id.index_miaosha_seconds);
        mIndexPrice = (TextView) findViewById(R.id.index_miaosha_price);
        mIndexRawPrice = (TextView) findViewById(R.id.index_miaosha_raw_price);

        mMiaoShaImage = (ImageView) findViewById(R.id.index_miaosha_image);
        mViewPager = (JazzyViewPager) findViewById(R.id.index_product_images_container);
        mIndicator = (LinearLayout) findViewById(R.id.index_product_images_indicator);

        mStormGallery = (Gallery) findViewById(R.id.index_jingqiu_gallery);
        mPromotionGallery = (Gallery) findViewById(R.id.index_tehui_gallery);

        mSearchBox = (EditText) findViewById(R.id.index_search_edit);
        mCameraButton = (ImageButton) findViewById(R.id.index_camer_button);
        mTopLayout = (LinearLayout) findViewById(R.id.index_top_layout);

        shake = (ImageButton) findViewById(R.id.index_shake);

        // 添加事件监听
        shake.setOnClickListener(indexClickListener);

    }

    private View.OnClickListener indexClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.index_shake:
                    mIntent = new Intent(IndexActivity.this, IndexShakeActivity.class);
                    startActivity(mIntent);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void initView() {
        ImageLoader.getInstance().displayImage("drawable://" + R.drawable.miaosha, mMiaoShaImage);

        mIndexHour.setText("00");
        mIndexMin.setText("53");
        mIndexSeconds.setText("49");
        mIndexPrice.setText("¥ 269.99");
        mIndexRawPrice.setText("¥ 459.99");
        mIndexRawPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

        // ========================初始化ViewPager==========================
        mIndicators = new ImageView[mImageUrls.size()];
        if (mImageUrls.size() <= 1) {
            mIndicator.setVisibility(View.GONE);
        }

        for (int i = 0; i < mIndicators.length; i++) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            if (i != 0) {
                params.leftMargin = 5;
            }
            imageView.setLayoutParams(params);
            mIndicators[i] = imageView;
            if (i == 0) {
                mIndicators[i].setBackgroundResource(R.drawable.android_activities_cur);
            } else {
                mIndicators[i].setBackgroundResource(R.drawable.android_activities_bg);
            }
            mIndicator.addView(imageView);
        }

        mImageViews = new ImageView[mImageUrls.size()];

        for (int i = 0; i < mImageViews.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageViews[i] = imageView;
        }
        mViewPager.setTransitionEffect(JazzyViewPager.TransitionEffect.CubeOut);
        mViewPager.setCurrentItem(0);
        mHandler.sendEmptyMessageDelayed(MSG_CHANGE_PHOTO, PHOTO_CHANGE_TIME);

        mViewPager.setAdapter(new MyAdapter());
        mViewPager.setOnPageChangeListener(new MyPageChangeListener());
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mImageUrls.size() == 0 || mImageUrls.size() == 1)
                    return true;
                else
                    return false;
            }
        });

        // =========================初始化ViewPager=============================
        mStormAdapter = new IndexGalleryAdapter(this, R.layout.activity_index_gallery_item,
                mStormListData, new int[]{R.id.index_gallery_item_image, R.id.index_gallery_item_text});
        mStormGallery.setAdapter(mStormAdapter);

        mPromotionAdapter = new IndexGalleryAdapter(this, R.layout.activity_index_gallery_item,
                mPromotionListData, new int[]{R.id.index_gallery_item_image, R.id.index_gallery_item_text});

        mPromotionGallery.setAdapter(mPromotionAdapter);

        mStormGallery.setSelection(3);
        mPromotionGallery.setSelection(3);

        mBarPopupWindow = new HomeSearchBarPopupWindow(this, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mBarPopupWindow.setOnSearchBarItemClickListener(this);

        mCameraButton.setOnClickListener(this);
        mSearchBox.setOnClickListener(this);
        mSearchBox.setInputType(InputType.TYPE_NULL);
    }

    private void initData() {
        mImageUrl = "drawable://" + R.drawable.image01;
        mImageUrls.add(mImageUrl);

        mImageUrl = "drawable://" + R.drawable.image02;
        mImageUrls.add(mImageUrl);

        mImageUrl = "drawable://" + R.drawable.image03;
        mImageUrls.add(mImageUrl);

        mImageUrl = "drawable://" + R.drawable.image04;
        mImageUrls.add(mImageUrl);

        mImageUrl = "drawable://" + R.drawable.image05;
        mImageUrls.add(mImageUrl);

        mImageUrl = "drawable://" + R.drawable.image06;
        mImageUrls.add(mImageUrl);

        mImageUrl = "drawable://" + R.drawable.image07;
        mImageUrls.add(mImageUrl);

        mImageUrl = "drawable://" + R.drawable.image08;
        mImageUrls.add(mImageUrl);


        mItemData = new IndexGalleryItemData();
        mItemData.setId(1);
        mItemData.setImageUrl("drawable://" + R.drawable.index_gallery_01);
        mItemData.setPrice("¥ 79.00");
        mStormListData.add(mItemData);

        mItemData = new IndexGalleryItemData();
        mItemData.setId(2);
        mItemData.setImageUrl("drawable://" + R.drawable.index_gallery_02);
        mItemData.setPrice("¥ 89.00");
        mStormListData.add(mItemData);

        mItemData = new IndexGalleryItemData();
        mItemData.setId(3);
        mItemData.setImageUrl("drawable://" + R.drawable.index_gallery_03);
        mItemData.setPrice("¥ 99.00");
        mStormListData.add(mItemData);

        mItemData = new IndexGalleryItemData();
        mItemData.setId(4);
        mItemData.setImageUrl("drawable://" + R.drawable.index_gallery_04);
        mItemData.setPrice("¥ 109.00");
        mStormListData.add(mItemData);

        mItemData = new IndexGalleryItemData();
        mItemData.setId(5);
        mItemData.setImageUrl("drawable://" + R.drawable.index_gallery_05);
        mItemData.setPrice("¥ 119.00");
        mStormListData.add(mItemData);

        mItemData = new IndexGalleryItemData();
        mItemData.setId(6);
        mItemData.setImageUrl("drawable://" + R.drawable.index_gallery_06);
        mItemData.setPrice("¥ 129.00");
        mStormListData.add(mItemData);

        mItemData = new IndexGalleryItemData();
        mItemData.setId(7);
        mItemData.setImageUrl("drawable://" + R.drawable.index_gallery_07);
        mItemData.setPrice("¥ 139.00");
        mStormListData.add(mItemData);

        mItemData = new IndexGalleryItemData();
        mItemData.setId(8);
        mItemData.setImageUrl("drawable://" + R.drawable.index_gallery_08);
        mItemData.setPrice("¥ 149.00");
        mStormListData.add(mItemData);

        mItemData = new IndexGalleryItemData();
        mItemData.setId(9);
        mItemData.setImageUrl("drawable://" + R.drawable.index_gallery_09);
        mItemData.setPrice("¥ 159.00");
        mStormListData.add(mItemData);

        mItemData = new IndexGalleryItemData();
        mItemData.setId(10);
        mItemData.setImageUrl("drawable://" + R.drawable.index_gallery_10);
        mItemData.setPrice("¥ 169.00");
        mStormListData.add(mItemData);

        mItemData = new IndexGalleryItemData();
        mItemData.setId(11);
        mItemData.setImageUrl("drawable://" + R.drawable.index_gallery_11);
        mItemData.setPrice("¥ 179.00");
        mStormListData.add(mItemData);

        mItemData = new IndexGalleryItemData();
        mItemData.setId(12);
        mItemData.setImageUrl("drawable://" + R.drawable.index_gallery_12);
        mItemData.setPrice("¥ 189.00");
        mStormListData.add(mItemData);

        mItemData = new IndexGalleryItemData();
        mItemData.setId(13);
        mItemData.setImageUrl("drawable://" + R.drawable.index_gallery_13);
        mItemData.setPrice("¥ 199.00");
        mStormListData.add(mItemData);

        mItemData = new IndexGalleryItemData();
        mItemData.setId(14);
        mItemData.setImageUrl("drawable://" + R.drawable.index_gallery_14);
        mItemData.setPrice("¥ 209.00");
        mStormListData.add(mItemData);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageViews.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            if (view instanceof OutlineContainer) {
                return ((OutlineContainer) view).getChildAt(0) == object;
            } else {
                return view == object;
            }

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(mViewPager.findViewFromObject(position));
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ImageLoader.getInstance().displayImage(mImageUrls.get(position),
                    mImageViews[position]);
            ((ViewPager) container).addView(mImageViews[position], 0);
            mViewPager.setObjectForPosition(mImageViews[position], position);
            return mImageViews[position];
        }
    }

    /**
     * 当ViewPager中页面的状态发生改变时调用
     *
     * @author Marvin
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        /**
         * 这个方法在当一个新的页面被选中时调用
         *
         * @param position 被选中的新的页面的索引位置
         */
        public void onPageSelected(int position) {
            setImageBackground(position);
        }

        public void onPageScrollStateChanged(int arg0) {

        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
    }

    /**
     * 设置选中的tip的背景
     *
     * @param selectItemsIndex
     */
    private void setImageBackground(int selectItemsIndex) {
        for (int i = 0; i < mIndicators.length; i++) {
            if (i == selectItemsIndex) {
                mIndicators[i].
                        setBackgroundResource(R.drawable.android_activities_cur);
            } else {
                mIndicators[i].
                        setBackgroundResource(R.drawable.android_activities_bg);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.index_camer_button:
                int height = mTopLayout.getHeight() + CommonTools.getStatusBarHeight(this);
                mBarPopupWindow.showAtLocation(mTopLayout, Gravity.TOP, 0, height);
                break;
            case R.id.index_search_edit:
                openActivity(SearchActivity.class);
                break;
            default:
                break;
        }
    }

    // TODO: 2016/3/21 添加onBarCodeButtonClick()  onCameraButtonClick()  onColorButtonClick()的重载 方法


    @Override
    public void onBarCodeButtonClick() {
        CommonTools.showShortToast(this, "条码购");
        mIntent = new Intent(IndexActivity.this, CaptureActivity.class);
        startActivity(mIntent);
    }

    @Override
    public void onCameraButtonClick() {
        CommonTools.showShortToast(this, "拍照购");
    }

    @Override
    public void onColorButtonClick() {
        CommonTools.showShortToast(this, "颜色购");
    }

}
