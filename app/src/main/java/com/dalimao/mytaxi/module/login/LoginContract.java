package com.dalimao.mytaxi.module.login;

import com.dalimao.mytaxi.base.BasePresenter;
import com.dalimao.mytaxi.base.BaseView;

/**
 * Created by guZhongC on 2018/2/5.
 * describe:
 */

public interface LoginContract {

    interface ILoginView extends BaseView {
        void InitNamePsw();

        void ShowLoading();

        void StopLoading();

        void activityFinish();

    }

    interface ILoginPresentr extends BasePresenter {
        String getName();

        String getPsw();

        boolean isRemener();

        boolean isMember();

        void Login(String type, String name, String psw);

        void RememberPsw(boolean isRemember, String name, String psw, boolean isMember) throws Exception;
    }

}
