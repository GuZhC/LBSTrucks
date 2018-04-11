package com.rongyuan.mingyida.module.me.cartinfo;

import com.rongyuan.mingyida.module.me.cartinfo.MyCartContract.IMyCartPresenter;

import rx.Subscription;

/**
 * Created by guZhongC on 2018/1/8.
 * describe:
 */

public class MyCartPresenter implements IMyCartPresenter {

    private Subscription mSubscription;

    private MyCartContract.IMyCartView mMyCartView;


    MyCartPresenter(MyCartContract.IMyCartView mMyCartView) {
        this.mMyCartView = mMyCartView;
    }

    @Override
    public void getCartinfo() {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
