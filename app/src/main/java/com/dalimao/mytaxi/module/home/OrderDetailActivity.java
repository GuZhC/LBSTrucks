package com.dalimao.mytaxi.module.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dalimao.mytaxi.R;
import com.dalimao.mytaxi.base.BaseActivity;

public class OrderDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        setBackBtn();
        setTitle("订单详情");
    }
}
