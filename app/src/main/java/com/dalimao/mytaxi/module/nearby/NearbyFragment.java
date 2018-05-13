package com.dalimao.mytaxi.module.nearby;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.dalimao.mytaxi.App;
import com.dalimao.mytaxi.R;
import com.dalimao.mytaxi.base.BaseFragment;
import com.dalimao.mytaxi.common.databus.RxBus;
import com.dalimao.mytaxi.common.http.API;
import com.dalimao.mytaxi.common.http.IHttpClient;
import com.dalimao.mytaxi.common.http.impl.OkHttpClientImpl;
import com.dalimao.mytaxi.common.storage.SharedPreferencesDao;
import com.dalimao.mytaxi.lbs.GaodeLbsLayerImpl;
import com.dalimao.mytaxi.lbs.ILbsLayer;
import com.dalimao.mytaxi.lbs.LocationInfo;
import com.dalimao.mytaxi.lbs.RouteInfo;
import com.dalimao.mytaxi.model.AddGoodsBean;
import com.dalimao.mytaxi.model.Order;
import com.dalimao.mytaxi.utils.DevUtil;
import com.dalimao.mytaxi.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;

/**
 * Created by guZhongC on 2018/1/5.
 * describe:
 */

public class NearbyFragment extends BaseFragment implements NearbyContract.INearbyView {
    private final static String TAG = "NearbyFragment";
    private static final String LOCATION_END = "10000end";
    @BindView(R.id.btn_neaber_addgoods)
    Button btnNeaberAddgoods;
    Unbinder unbinder;
    private NearbyPresenter mPresenter;
    private ILbsLayer mLbsLayer;
    private Bitmap mDriverBit;
    private String mPushKey;

    //  起点与终点
    private AutoCompleteTextView mStartEdit;
    private AutoCompleteTextView mEndEdit;
    private PoiAdapter mEndAdapter;
    // 标题栏显示当前城市
    private TextView mCity;
    // 记录起点和终点
    private LocationInfo mStartLocation;
    private LocationInfo mEndLocation;
    private Bitmap mStartBit;
    private Bitmap mEndBit;
    //  当前是否登录
    private boolean mIsLogin;
    //  操作状态相关元素
    private View mOptArea;
    private View mLoadingArea;
    private TextView mTips;
    private TextView mLoadingText;
    private Button mBtnCall;
    private Button mBtnCancel;
    private Button mBtnPay;
    private float mCost;
    private Bitmap mLocationBit;
    private boolean mIsLocate;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_nearby;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        IHttpClient httpClient = new OkHttpClientImpl();
        SharedPreferencesDao dao =
                new SharedPreferencesDao(App.getInstance(),
                        SharedPreferencesDao.FILE_ACCOUNT);
        IAccountManager manager = new AccountManagerImpl(httpClient, dao);
        IMainManager mainManager = new MainMangerImpl(httpClient);
        mPresenter = new NearbyPresenter(this, manager, mainManager);
        RxBus.getInstance().register(mPresenter);
//        mPresenter.loginByToken();

        // 地图服务
        mLbsLayer = new GaodeLbsLayerImpl(getContext());
        mLbsLayer.onCreate(savedInstanceState);
        mLbsLayer.setLocationChangeListener(new ILbsLayer.CommonLocationChangeListener() {
            @Override
            public void onLocationChanged(LocationInfo locationInfo) {

            }

            @Override
            public void onLocation(LocationInfo locationInfo) {

                // 记录起点
                mStartLocation = locationInfo;
                Log.e("start", mStartLocation.getLatitude() + "  " + mStartLocation.getLongitude());

                // 设置起点
                mStartEdit.setText(locationInfo.getName());
                // 获取附近司机
                getNearDrivers(locationInfo.getLatitude(),
                        locationInfo.getLongitude());
                // 上报当前位置
                updateLocationToServer(locationInfo);
                // 首次定位，添加当前位置的标记
                addLocationMarker();
                mIsLocate = true;
                //  获取进行中的订单
//                getProcessingOrder();
            }


        });

        // 添加地图到容器
        ViewGroup mapViewContainer = (ViewGroup) mRootView.findViewById(R.id.map_container);
        mapViewContainer.addView(mLbsLayer.getMapView());

        // 推送服务
        // 初始化BmobSDK
        Bmob.initialize(getContext(), API.Config.getAppId());
        // 使用推送服务时的初始化操作
        BmobInstallation installation = BmobInstallation.getCurrentInstallation(getContext());
        installation.save();
        mPushKey = installation.getInstallationId();
        // 启动推送服务
        BmobPush.startWork(getContext());


        //  初始化其他视图元素
        initViews();

        mIsLogin = mPresenter.isLogin();
    }

    public void haveGoodsUI(){
        btnNeaberAddgoods.setText("已添加（点击修改）");
    }

    private void initViews() {
        mStartEdit = (AutoCompleteTextView) mRootView.findViewById(R.id.start);
        mEndEdit = (AutoCompleteTextView) mRootView.findViewById(R.id.end);
        mOptArea = mRootView.findViewById(R.id.optArea);
        mLoadingArea = mRootView.findViewById(R.id.loading_area);
        mLoadingText = (TextView) mRootView.findViewById(R.id.loading_text);
        mBtnCall = (Button) mRootView.findViewById(R.id.btn_call_driver);
        mBtnCancel = (Button) mRootView.findViewById(R.id.btn_cancel);
        mBtnPay = (Button) mRootView.findViewById(R.id.btn_pay);
        mTips = (TextView) mRootView.findViewById(R.id.tips_info);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.btn_call_driver:
                        // 呼叫司机
                        callDriver();
                        break;
                    case R.id.btn_cancel:
                        //  取消
                        cancel();
                        break;
                    case R.id.btn_pay:
                        // 支付
                        pay();
                        break;
                }
            }
        };
        mBtnCall.setOnClickListener(listener);
        mBtnCancel.setOnClickListener(listener);
        mBtnPay.setOnClickListener(listener);
        mEndEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //  关键搜索推荐地点
                mLbsLayer.poiSearch(s.toString(), new ILbsLayer.OnSearchedListener() {
                    @Override
                    public void onSearched(List<LocationInfo> results) {
                        // 更新列表
                        updatePoiList(results);
                    }

                    @Override
                    public void onError(int rCode) {

                    }
                });
            }
        });
    }

    /**
     * 更新 POI 列表
     *
     * @param results
     */
    private void updatePoiList(final List<LocationInfo> results) {

        List<String> listString = new ArrayList<String>();
        for (int i = 0; i < results.size(); i++) {
            listString.add(results.get(i).getName());
        }
        if (mEndAdapter == null) {
            mEndAdapter = new PoiAdapter(getContext(), listString);
            mEndEdit.setAdapter(mEndAdapter);

        } else {

            mEndAdapter.setData(listString);
        }
        mEndEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ToastUtils.showInfo(getContext(), results.get(position).getName());
                DevUtil.closeInputMethod(getActivity());
                //  记录终点
                mEndLocation = results.get(position);
                mEndLocation.setKey(LOCATION_END);
                // 绘制路径
                showRoute(mStartLocation, mEndLocation, new ILbsLayer.OnRouteCompleteListener() {
                    @Override
                    public void onComplete(RouteInfo result) {
//                        LogUtil.d(TAG, "driverRoute: " + result);


                        mLbsLayer.moveCamera(mStartLocation, mEndLocation);
                        // 显示操作区
                        showOptArea();
                        mCost = result.getTaxiCost();
                        String infoString = getString(R.string.route_info);
                        infoString = String.format(infoString,
                                new Float(result.getDistance()).intValue(),
                                mCost,
                                result.getDuration());
                        mTips.setVisibility(View.VISIBLE);
                        mTips.setText(infoString);
                    }
                });
            }
        });
        mEndAdapter.notifyDataSetChanged();
    }


    /**
     * 绘制起点终点路径
     */

    private void showRoute(final LocationInfo mStartLocation,
                           final LocationInfo mEndLocation,
                           ILbsLayer.OnRouteCompleteListener listener) {

        mLbsLayer.clearAllMarkers();
        addStartMarker();
        addEndMarker();
        mLbsLayer.driverRoute(mStartLocation,
                mEndLocation,
                Color.GREEN,
                listener
        );
    }

    private void addStartMarker() {
        if (mStartBit == null || mStartBit.isRecycled()) {
            mStartBit = BitmapFactory.decodeResource(getResources(),
                    R.drawable.start);
        }
        mLbsLayer.addOrUpdateMarker(mStartLocation, mStartBit);
    }

    private void addEndMarker() {
        if (mEndBit == null || mEndBit.isRecycled()) {
            mEndBit = BitmapFactory.decodeResource(getResources(),
                    R.drawable.end);
        }
        mLbsLayer.addOrUpdateMarker(mEndLocation, mEndBit);
    }

    /**
     * 显示操作区
     */
    private void showOptArea() {
        mOptArea.setVisibility(View.VISIBLE);
        mLoadingArea.setVisibility(View.GONE);
        mTips.setVisibility(View.VISIBLE);
        mBtnCall.setEnabled(true);
        mBtnCancel.setEnabled(true);
        mBtnCancel.setVisibility(View.VISIBLE);
        mBtnCall.setVisibility(View.VISIBLE);
        mBtnPay.setVisibility(View.GONE);
    }

    /**
     * 呼叫司机
     */
    private void callDriver() {
        mIsLogin = mPresenter.isLogin();
        if (mIsLogin) {
            // 已登录，直接呼叫
            showCalling();
            //   请求呼叫
            mPresenter.callDriver(mPushKey, mCost, mStartLocation, mEndLocation);
        } else {
            // 未登录，先登录
//            ToastUtil.show(this, getString(R.string.pls_login));
            showPhoneInputDialog();
        }
    }

    /**
     * 取消
     */
    private void cancel() {
        if (!mBtnCall.isEnabled()) {
            // 说明已经点了呼叫
            showCanceling();
            mPresenter.cancel();
        } else {
            // 知识显示了路径信息，还没点击呼叫，恢复 UI 即可
            restoreUI();
        }
    }


    private void pay() {
        mLoadingArea.setVisibility(View.VISIBLE);
        mTips.setVisibility(View.GONE);
        mLoadingText.setText("支付中");
        mPresenter.pay();
    }

    /**
     * 显示取消中
     */
    private void showCanceling() {
        mTips.setVisibility(View.GONE);
        mLoadingArea.setVisibility(View.VISIBLE);
        mLoadingText.setText("订单取消中");
        mBtnCancel.setEnabled(false);
    }

    private void showCalling() {
        mTips.setVisibility(View.GONE);
        mLoadingArea.setVisibility(View.VISIBLE);
        mLoadingText.setText("正在呼叫附近司机");
        mBtnCancel.setEnabled(true);
        mBtnCall.setEnabled(false);
    }

    /**
     * 恢复 UI
     */

    private void restoreUI() {
        // 清楚地图上所有标记：路径信息、起点、终点
        mLbsLayer.clearAllMarkers();
        // 添加定位标记
        addLocationMarker();
        // 恢复地图视野
        mLbsLayer.moveCameraToPoint(mStartLocation, 17);
        //  获取附近司机
        getNearDrivers(mStartLocation.getLatitude(), mStartLocation.getLongitude());
        // 隐藏操作栏
        hideOptArea();
    }

    private void hideOptArea() {
        mOptArea.setVisibility(View.GONE);

    }

    /**
     * 显示手机输入框
     */
    private void showPhoneInputDialog() {


        PhoneInputDialog dialog = new PhoneInputDialog(getContext());
        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                RxBus.getInstance().register(mPresenter);
            }
        });
        RxBus.getInstance().unRegister(mPresenter);
    }

    /**
     * 获取附近司机
     *
     * @param latitude
     * @param longitude
     */
    private void getNearDrivers(double latitude, double longitude) {

        mPresenter.fetchNearDrivers(latitude, longitude);
    }

    /**
     * 上报当前位置
     *
     * @param locationInfo
     */
    private void updateLocationToServer(LocationInfo locationInfo) {
        locationInfo.setKey(mPushKey);
        mPresenter.updateLocationToServer(locationInfo);
    }

    /**
     * 获取正在进行中的订单
     */
    private void getProcessingOrder() {
        /**
         *  满足： 已经登录、已经定位两个条件，执行 getProcessingOrder
         */
        if (mIsLogin && mIsLocate) {
            mPresenter.getProcessingOrder();
        }

    }


    private void addLocationMarker() {
        if (mLocationBit == null || mLocationBit.isRecycled()) {
            mLocationBit = BitmapFactory.decodeResource(getResources(),
                    R.drawable.navi_map_gps_locked);
        }
        mLbsLayer.addOrUpdateMarker(mStartLocation, mLocationBit);
    }


    /**
     * 自动登录成功
     */

    @Override
    public void showLoginSuc() {
        ToastUtils.showInfo(getContext(), getString(R.string.login_suc));
        mIsLogin = true;
        if (mStartLocation != null) {
            updateLocationToServer(mStartLocation);
        }
        // 获取正在进行中的订单
        getProcessingOrder();
    }

    /**
     * 显示附近司机
     *
     * @param data
     */

    @Override
    public void showNears(List<LocationInfo> data) {

        for (LocationInfo locationInfo : data) {
            showLocationChange(locationInfo);
        }
    }

    /**
     * 显示司机的标记
     *
     * @param locationInfo
     */
    @Override
    public void showLocationChange(LocationInfo locationInfo) {
        if (mDriverBit == null || mDriverBit.isRecycled()) {
            mDriverBit = BitmapFactory.decodeResource(getResources(), R.drawable.car);
        }
        mLbsLayer.addOrUpdateMarker(locationInfo, mDriverBit);
    }

    /**
     * 呼叫司机成功发出
     */
    @Override
    public void showCallDriverSuc(Order order) {
        mLoadingArea.setVisibility(View.GONE);
        mTips.setVisibility(View.VISIBLE);
        mTips.setText(getString(R.string.show_call_suc));
        // 显示操作区
        showOptArea();
        mBtnCall.setEnabled(false);
        // 显示路径信息
        if (order.getEndLongitude() != 0 ||
                order.getDriverLatitude() != 0) {
            mEndLocation = new LocationInfo(order.getEndLatitude(), order.getEndLongitude());
            mEndLocation.setKey(LOCATION_END);
            // 绘制路径
            showRoute(mStartLocation, mEndLocation, new ILbsLayer.OnRouteCompleteListener() {
                @Override
                public void onComplete(RouteInfo result) {
//                    LogUtil.d(TAG, "driverRoute: " + result);


                    mLbsLayer.moveCamera(mStartLocation, mEndLocation);
                    mCost = result.getTaxiCost();
                    String infoString = getString(R.string.route_info_calling);
                    infoString = String.format(infoString,
                            new Float(result.getDistance()).intValue(),
                            mCost,
                            result.getDuration());
                    mTips.setVisibility(View.VISIBLE);
                    mTips.setText(infoString);

                }
            });
        }
//        LogUtil.d(TAG,"showCallDriverSuc: " + order);
    }

    @Override
    public void showCallDriverFail() {
        mLoadingArea.setVisibility(View.GONE);
        mTips.setVisibility(View.VISIBLE);
        mTips.setText(getString(R.string.show_call_fail));
        mBtnCall.setEnabled(true);

    }

    /**
     * 取消订单成功
     */
    @Override
    public void showCancelSuc() {
        ToastUtils.showSuccess(getContext(), getString(R.string.order_cancel_suc));
        restoreUI();
    }

    /**
     * 取消订单失败
     */
    @Override
    public void showCancelFail() {
        ToastUtils.showUsual(getContext(), getString(R.string.order_cancel_error));
        mBtnCancel.setEnabled(true);
    }

    /**
     * 司机接单
     *
     * @param order
     */
    @Override
    public void showDriverAcceptOrder(final Order order) {
        // 提示信息
        ToastUtils.showUsual(getContext(), getString(R.string.driver_accept_order));

        // 清除地图标记
        mLbsLayer.clearAllMarkers();
        /**
         * 添加司机标记
         */

        final LocationInfo driverLocation =
                new LocationInfo(order.getDriverLatitude(),
                        order.getDriverLongitude());
        driverLocation.setKey(order.getKey());
        showLocationChange(driverLocation);
        // 显示我的位置
        addLocationMarker();
        /**
         * 显示司机到乘客的路径
         */
        mLbsLayer.driverRoute(driverLocation,
                mStartLocation,
                Color.BLUE,
                new ILbsLayer.OnRouteCompleteListener() {
                    @Override
                    public void onComplete(RouteInfo result) {
                        // 地图聚焦到司机和我的位置
                        mLbsLayer.moveCamera(mStartLocation, driverLocation);
                        // 显示司机、路径信息
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("司机：")
                                .append(order.getDriverName())
                                .append(", 车牌：")
                                .append(order.getCarNo())
                                .append("，预计")
                                .append(result.getDuration())
                                .append("分钟到达");


                        mTips.setText(stringBuilder.toString());
                        // 显示操作区
                        showOptArea();
                        // 呼叫不可点击
                        mBtnCall.setEnabled(false);

                    }
                });

    }

    /**
     * 提示司机到达
     *
     * @param mCurrentOrder
     */
    @Override
    public void showDriverArriveStart(Order mCurrentOrder) {

        showOptArea();
        String arriveTemp = getString(R.string.driver_arrive);
        mTips.setText(String.format(arriveTemp,
                mCurrentOrder.getDriverName(),
                mCurrentOrder.getCarNo()));
        mBtnCall.setEnabled(false);
        mBtnCancel.setEnabled(true);
        // 清除地图标记
        mLbsLayer.clearAllMarkers();
        /**
         * 添加司机标记
         */

        final LocationInfo driverLocation =
                new LocationInfo(mCurrentOrder.getDriverLatitude(),
                        mCurrentOrder.getDriverLongitude());
        driverLocation.setKey(mCurrentOrder.getKey());
        showLocationChange(driverLocation);
        // 显示我的位置
        addLocationMarker();
    }

    /**
     * 司机到上车地点的路径绘制
     *
     * @param locationInfo
     */

    @Override
    public void updateDriver2StartRoute(LocationInfo locationInfo, final Order order) {

        mLbsLayer.clearAllMarkers();
        addLocationMarker();
        showLocationChange(locationInfo);
        mLbsLayer.driverRoute(locationInfo, mStartLocation, Color.BLUE, new ILbsLayer.OnRouteCompleteListener() {
            @Override
            public void onComplete(RouteInfo result) {

                String tipsTemp = getString(R.string.accept_info);
                mTips.setText(String.format(tipsTemp,
                        order.getDriverName(),
                        order.getCarNo(),
                        result.getDistance(),
                        result.getDuration()));
            }
        });
        // 聚焦
        mLbsLayer.moveCamera(locationInfo, mStartLocation);

    }

    /**
     * 显示开始行程
     *
     * @param order
     */
    @Override
    public void showStartDrive(Order order) {


        LocationInfo locationInfo =
                new LocationInfo(order.getDriverLatitude(), order.getDriverLongitude());
        locationInfo.setKey(order.getKey());
        // 路径规划绘制
        updateDriver2EndRoute(locationInfo, order);
        // 隐藏按钮
        mBtnCancel.setVisibility(View.GONE);
        mBtnCall.setVisibility(View.GONE);
    }


    /**
     * 显示到达终点
     *
     * @param order
     */
    @Override
    public void showArriveEnd(Order order) {
        String tipsTemp = getString(R.string.pay_info);
        String tips = String.format(tipsTemp,
                order.getCost(),
                order.getDriverName(),
                order.getCarNo());
        // 显示操作区
        showOptArea();
        mBtnCancel.setVisibility(View.GONE);
        mBtnCall.setVisibility(View.GONE);
        mTips.setText(tips);
        mBtnPay.setVisibility(View.VISIBLE);
    }

    /**
     * 司机到终点的路径绘制或更新
     *
     * @param locationInfo
     */

    @Override
    public void updateDriver2EndRoute(LocationInfo locationInfo, final Order order) {
        // 终点位置从 order 中获取
        if (order.getEndLongitude() != 0 ||
                order.getEndLatitude() != 0) {
            mEndLocation = new LocationInfo(order.getEndLatitude(), order.getEndLongitude());
            mEndLocation.setKey(LOCATION_END);
        }
        mLbsLayer.clearAllMarkers();
        addEndMarker();
        showLocationChange(locationInfo);
        addLocationMarker();
        mLbsLayer.driverRoute(locationInfo, mEndLocation, Color.GREEN, new ILbsLayer.OnRouteCompleteListener() {
            @Override
            public void onComplete(RouteInfo result) {

                String tipsTemp = getString(R.string.driving_info);
                mTips.setText(String.format(tipsTemp,
                        order.getDriverName(),
                        order.getCarNo(),
                        result.getDistance(),
                        result.getDuration()));
                // 显示操作区
                showOptArea();
                mBtnCancel.setEnabled(false);
                mBtnCall.setEnabled(false);
            }
        });
        // 聚焦
        mLbsLayer.moveCamera(locationInfo, mEndLocation);
    }

    /**
     * 显示支付成功
     *
     * @param mCurrentOrder
     */
    @Override
    public void showPaySuc(Order mCurrentOrder) {
        restoreUI();
        ToastUtils.showSuccess(getContext(), getString(R.string.pay_suc));
    }

    /**
     * 显示支付失败
     */
    @Override
    public void showPayFail() {
        restoreUI();
        ToastUtils.showSuccess(getContext(), getString(R.string.pay_fail));
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void showError(int code, String msg) {
        switch (code) {
            case IAccountManager.TOKEN_INVALID:
                // 登录过期
//                ToastUtil.show(this, getString(R.string.token_invalid));
                showPhoneInputDialog();
                mIsLogin = false;
                break;
            case IAccountManager.SERVER_FAIL:
                // 服务器错误
                showPhoneInputDialog();
                break;

        }
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mLbsLayer.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mLbsLayer.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mLbsLayer.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        super.onDestroy();
        RxBus.getInstance().unRegister(mPresenter);
        mLbsLayer.onDestroy();
        unbinder.unbind();
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 200) {
//            btnNeaberAddgoods.setText("已添加（点击修改）");
//        }
//    }

    @OnClick(R.id.btn_neaber_addgoods)
    public void onViewClicked() {
        startActivity(new Intent(getContext(), AddGoodsActivity.class));
    }


}
