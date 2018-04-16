package com.dalimao.mytaxi.module.home;

import com.dalimao.mytaxi.base.BaseView;
import com.dalimao.mytaxi.model.NeaberShopModel;
import com.dalimao.mytaxi.base.BasePresenter;
import com.dalimao.mytaxi.model.PictureModel;

import java.util.List;

/**
 * Created by guZhongC on 2018/1/5.
 * describe:
 */

public interface HomeContract {
    interface IHomeView extends BaseView {
        void showBannerFail(String failMessage);

        void setBanner(List<String> imgUrls);


        void setRecyclerHot(List<PictureModel> itemdata);

        void setRecyclerall(List<NeaberShopModel> itemdata);
    }

    interface IHomePresenter extends BasePresenter{

        void getRecyclerDataHot();


        void getRecyclerDataAll();

        void getBannerData();
    }
}
