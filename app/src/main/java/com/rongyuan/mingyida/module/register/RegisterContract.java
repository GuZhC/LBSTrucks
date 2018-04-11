package com.rongyuan.mingyida.module.register;

import com.rongyuan.mingyida.base.BasePresenter;
import com.rongyuan.mingyida.base.BaseView;

/**
 * Created by guZhongC on 2018/2/5.
 * describe:
 */

public interface RegisterContract {

    interface IRegisterView extends BaseView {

        void ShowLoading();

        void StopLoading();

    }

    interface IRegisterPresentr extends BasePresenter {

        void Register(String type, String name, String psw);

    }

}
