package com.rongyuan.mingyida.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by guZhongC on 2018/2/7.
 * describe:
 */

//避免内存泄漏
public  class countDownTimer<T> extends CountDownTimer {
    private TextView mTextView;

    public countDownTimer(T text) {
        super(60000, 1000);
        this.mTextView = ((TextView) text);
        mTextView.setClickable(false);
        start();
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setText(String.format("%s秒", millisUntilFinished / 1000));
    }

    @Override
    public void onFinish() {
        mTextView.setText("获取验证码");
        mTextView.setClickable(true);
    }
}