package com.rongyuan.mingyida.module.nearby;

import com.rongyuan.mingyida.base.BasePresenter;
import com.rongyuan.mingyida.base.BaseView;
import com.rongyuan.mingyida.lbs.LocationInfo;
import com.rongyuan.mingyida.model.ClassifyBeans;
import com.rongyuan.mingyida.model.HomeAllModel;
import com.rongyuan.mingyida.model.NeaberShopModel;
import com.rongyuan.mingyida.model.PictureModel;

import java.util.List;

/**
 * Created by guZhongC on 2018/1/5.
 * describe:
 */

public interface NearbyContract {
    interface INearbyView extends BaseView {

        /**
         * 附近
         *
         * @param data
         */
        void showNears(List<LocationInfo> data);

        /**
         * 显示位置变化
         *
         * @param locationInfo
         */
        void showLocationChange(LocationInfo locationInfo);

    }

    interface INearbyPresenter extends BasePresenter {
        /**
         * 获取附近店铺
         *
         * @param latitude
         * @param longitude
         */
        void fetchNearShops(double latitude, double longitude);

    }
}
