package com.rongyuan.mingyida.module.me.cartinfo;

import android.os.Bundle;

import com.rongyuan.mingyida.R;
import com.rongyuan.mingyida.base.BaseActivity;

public class AddMyCart extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_cart);
        setBackBtn();
        setTitle("完善爱车信息");
    }

}
