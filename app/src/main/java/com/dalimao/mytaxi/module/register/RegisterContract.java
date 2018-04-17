package com.dalimao.mytaxi.module.register;

import com.dalimao.mytaxi.base.BasePresenter;
import com.dalimao.mytaxi.base.BaseView;

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
