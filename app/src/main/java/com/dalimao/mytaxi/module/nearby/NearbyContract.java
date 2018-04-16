package com.dalimao.mytaxi.module.nearby;

import com.dalimao.mytaxi.lbs.LocationInfo;
import com.dalimao.mytaxi.model.Order;
import com.dalimao.mytaxi.base.BaseView;

import java.util.List;

/**
 * Created by guZhongC on 2018/1/5.
 * describe:
 */

public interface NearbyContract {
    interface INearbyView extends IView {
        void showLoginSuc();

        /**
         * 附近司机
         * @param data
         */
        void showNears(List<LocationInfo> data);


        /**
         * 显示位置变化
         * @param locationInfo
         */
        void showLocationChange(LocationInfo locationInfo);

        /**
         *  显示呼叫成功发出
         *
         *
         */
        void showCallDriverSuc(Order order);

        /**
         *  显示呼叫未成功发出
         */
        void showCallDriverFail();




        /**
         * 取消订单成功
         */
        void showCancelSuc();
        /**
         *  显示取消定失败
         *
         */
        void showCancelFail();


        /**
         * 显示司机接单
         * @param mCurrentOrder
         */
        void showDriverAcceptOrder(Order mCurrentOrder);

        /**
         * 司机到达上车地点
         * @param mCurrentOrder
         */
        void showDriverArriveStart(Order mCurrentOrder);

        /**
         * 更新司机到上车点的路径
         * @param locationInfo
         */
        void updateDriver2StartRoute(LocationInfo locationInfo, Order order);

        /**
         * 更新司机到上车点的路径
         * @param order
         */
        void showStartDrive(Order order);

        /**
         *   显示到达终点
         * @param order
         */
        void showArriveEnd(Order order);

        /**
         *  更新司机到终点的路径
         * @param locationInfo
         */
        void updateDriver2EndRoute(LocationInfo locationInfo, Order order);


        /**
         * 支付成功
         * @param mCurrentOrder
         */
        void showPaySuc(Order mCurrentOrder);

        /**
         * 显示支付失败
         */
        void showPayFail();


    }

    interface INearbyPresenter  {
        void loginByToken();

        /**
         * 获取附近司机
         * @param latitude
         * @param longitude
         */
        void fetchNearDrivers(double latitude, double longitude);


        /**
         * 上报当前位置
         * @param locationInfo
         */
        void updateLocationToServer(LocationInfo locationInfo);


        /**
         * 呼叫司机
         * @param cost
         * @param key
         * @param mStartLocation
         * @param mEndLocation
         */
        void callDriver(String key, float cost, LocationInfo mStartLocation, LocationInfo mEndLocation);



        boolean isLogin();

        /**
         * 取消呼叫
         */
        void cancel();

        /**
         * 支付
         */
        void pay();

        /**
         *  获取正在处理中的订单
         */
        void getProcessingOrder();
    }
}
