package com.dalimao.mytaxi.module.me.cartinfo;

import com.dalimao.mytaxi.base.BasePresenter;
import com.dalimao.mytaxi.base.BaseView;

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
