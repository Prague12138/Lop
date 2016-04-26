package com.marvin.lop.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.marvin.lop.R;
import com.marvin.lop.ui.base.BaseActivity;
import com.marvin.lop.utils.CommonTools;
import com.marvin.lop.widgets.AutoClearEditText;

/**
 * Created by Marvin on 2016/3/20.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public class SearchActivity extends BaseActivity {

    private AutoClearEditText mEditText = null;
    private ImageButton mImageButton = null;

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
    }

    @Override
    protected void initView() {
        mEditText.requestFocus();
        mImageButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                CommonTools.showShortToast(SearchActivity.this, "亲，该功能暂未开放");
            }
        });
    }

}

