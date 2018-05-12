package com.dalimao.mytaxi.module.me;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dalimao.mytaxi.R;
import com.dalimao.mytaxi.base.BaseActivity;

public class AboutMeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        setBackBtn();
        setTitle("关于我们");
    }
}
