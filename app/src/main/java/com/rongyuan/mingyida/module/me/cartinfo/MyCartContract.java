package com.rongyuan.mingyida.module.me.cartinfo;

import com.rongyuan.mingyida.base.BasePresenter;
import com.rongyuan.mingyida.base.BaseView;

/**
 * Created by guZhongC on 2018/1/5.
 * describe:
 */

public interface MyCartContract {
    interface IMyCartView extends BaseView {
        void showNoHaveCart();

        void showHaveCart();


    }

    interface IMyCartPresenter extends BasePresenter{

        void getCartinfo();

    }
}
