package com.dalimao.mytaxi.module.nearby;

import android.os.Handler;

import com.dalimao.mytaxi.common.databus.RegisterBus;
import com.dalimao.mytaxi.lbs.LocationInfo;
import com.dalimao.mytaxi.model.LoginResponse;
import com.dalimao.mytaxi.model.Order;
import com.dalimao.mytaxi.common.http.BaseBizResponse;

/**
 * Created by guZhongC on 2018/1/11.
 * describe:
 */

public class NearbyPresenter implements NearbyContract.INearbyPresenter {


    private NearbyContract.INearbyView view;
    private IAccountManager accountManager;
    private IMainManager mainManager;
    // 当前的订单
    private Order mCurrentOrder;



    @RegisterBus
    public void onLoginResponse(LoginResponse loginResponse) {
        switch (loginResponse.getCode()) {


            case IAccountManager.LOGIN_SUC:
                // 登录成功
                view.showLoginSuc();
                break;
            case IAccountManager.TOKEN_INVALID:
                // 登录过期
                view.showError(IAccountManager.TOKEN_INVALID, "");
                break;
            case IAccountManager.SERVER_FAIL:
                // 服务器错误
                view.showError(IAccountManager.SERVER_FAIL, "");
                break;
        }
    }
    @RegisterBus
    public void onNearDriversResponse(NearDriversResponse response){
        if (response.getCode() == BaseBizResponse.STATE_OK) {
            view.showNears(response.getData());
        }
    }

    @RegisterBus
    public void onLocationInfo(LocationInfo locationInfo) {
        if (mCurrentOrder != null
                &&( mCurrentOrder.getState() ==
                OrderStateOptResponse.ORDER_STATE_ACCEPT)) {
            // 更新司机到上车点的路径信息
            view.updateDriver2StartRoute(locationInfo, mCurrentOrder);
        } else if (mCurrentOrder != null
                && mCurrentOrder.getState() ==
                OrderStateOptResponse.ORDER_STATE_START_DRIVE){
            // 更新司机到终点的路径信息
            view.updateDriver2EndRoute(locationInfo, mCurrentOrder);
        } else {
            view.showLocationChange(locationInfo);
        }

    }
    // 订单状态响应
    @RegisterBus
    public void onOrderOptResponse(OrderStateOptResponse response) {
        if (response.getState() == OrderStateOptResponse.ORDER_STATE_CREATE) {
            // 呼叫司机
            if (response.getCode() == BaseBizResponse.STATE_OK) {
                // 保存当前的订单
                mCurrentOrder = response.getData();
                // 接口增加参数
                view.showCallDriverSuc(mCurrentOrder);
            } else {
                view.showCallDriverFail();
            }
        } else if (response.getState() == OrderStateOptResponse.ORDER_STATE_CANCEL) {
            // 取消订单
            if (response.getCode() == BaseBizResponse.STATE_OK) {
                mCurrentOrder = null;
                view.showCancelSuc();

            } else {
                view.showCancelFail();
            }
        } else if (response.getState() == OrderStateOptResponse.ORDER_STATE_ACCEPT) {
            // 司机接单
            mCurrentOrder = response.getData();
            view.showDriverAcceptOrder(mCurrentOrder);
        } else if (response.getState() ==
                OrderStateOptResponse.ORDER_STATE_ARRIVE_START) {
            // 司机到达上车点
            mCurrentOrder = response.getData();
            view.showDriverArriveStart(mCurrentOrder);
        } else if (response.getState() ==
                OrderStateOptResponse.ORDER_STATE_START_DRIVE) {
            // 开始行程
            mCurrentOrder = response.getData();
            view.showStartDrive(mCurrentOrder);
        } else if (response.getState() ==
                OrderStateOptResponse.ORDER_STATE_ARRIVE_END) {
            // 到达终点
            mCurrentOrder = response.getData();
            view.showArriveEnd(mCurrentOrder);
        } else if (response.getState() ==
                OrderStateOptResponse.PAY) {
            //  支付
            if (response.getCode() == BaseBizResponse.STATE_OK) {
                view.showPaySuc(mCurrentOrder);
            } else {
                view.showPayFail();
            }
        }
//        LogUtil.d("MainPresenterImpl", "getProcessingOrder" + mCurrentOrder);
    }


    public NearbyPresenter(NearbyContract.INearbyView  view,
                           IAccountManager accountManager,
                           IMainManager mainManager) {
        this.view = view;
        this.accountManager = accountManager;
        this.mainManager = mainManager;

    }

    @Override
    public void loginByToken() {
        accountManager.loginByToken();
    }

    @Override
    public void fetchNearDrivers(double latitude, double longitude) {

        mainManager.fetchNearDrivers(latitude, longitude);

    }

    @Override
    public void updateLocationToServer(LocationInfo locationInfo) {
        mainManager.updateLocationToServer(locationInfo);
    }

    @Override
    public void callDriver(String key, float cost, LocationInfo startLocation, LocationInfo endLocation) {
        mainManager.callDriver(key, cost, startLocation, endLocation);
    }

    @Override
    public boolean isLogin() {
        return accountManager.isLogin();
    }

    /**
     *  取消呼叫
     */
    @Override
    public void cancel() {
        if (mCurrentOrder != null) {
            mainManager.cancelOrder(mCurrentOrder.getOrderId());
        } else {
            view.showCancelSuc();
        }
    }

    /**
     *  支付
     */
    @Override
    public void pay() {
        if (mCurrentOrder != null) {
            mainManager.pay(mCurrentOrder.getOrderId());
        }
    }

    /**
     * 获取正在处理中的订单
     */
    @Override
    public void getProcessingOrder() {
        mainManager.getProcessingOrder();
//        LogUtil.d("MainPresenterImpl", "getProcessingOrder");
    }


}
