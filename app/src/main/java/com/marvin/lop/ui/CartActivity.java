package com.marvin.lop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.marvin.lop.R;
import com.marvin.lop.ui.base.BaseActivity;

/**
 * Created by Marvin on 2016/3/20.
 * Email:1576925704@qq.com
 * Project Name :  Lop
 */
public class CartActivity extends BaseActivity implements OnClickListener {

    private Button cart_login,cart_market;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        cart_login=(Button)this.findViewById(R.id.cart_login);
        cart_market=(Button)  this.findViewById(R.id.cart_market);
    }

    @Override
    protected void initView() {
        cart_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cart_login:
                mIntent=new Intent(this, LoginActivity.class);
                startActivity(mIntent);

                break;

            case R.id.cart_market:

                break;

            default:
                break;
        }

    }

}

