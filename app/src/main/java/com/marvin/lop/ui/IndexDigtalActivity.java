package com.marvin.lop.ui;

import android.os.Bundle;
import android.view.Window;

import com.marvin.lop.R;
import com.marvin.lop.ui.base.BaseActivity;

/**
 * Created by Marvin on 2016/3/31.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public class IndexDigtalActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_digital);
    }

    @Override
    protected void findViewById() {

    }

    @Override
    protected void initView() {

    }

}
