package com.dalimao.mytaxi.module.me.cartinfo;

import android.os.Bundle;
import com.dalimao.mytaxi.R;
import com.dalimao.mytaxi.base.BaseActivity;

public class AddMyCart extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_cart);
        setBackBtn();
        setTitle("完善爱车信息");
    }

}
