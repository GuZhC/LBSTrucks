package com.rongyuan.mingyida.module.nearby;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.rongyuan.mingyida.R;
import com.rongyuan.mingyida.base.BaseFragment;
import com.rongyuan.mingyida.lbs.GaodeLbsLayerImpl;
import com.rongyuan.mingyida.lbs.ILbsLayer;
import com.rongyuan.mingyida.lbs.LocationInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by guZhongC on 2018/1/5.
 * describe:
 */

public class NearbyFragment extends BaseFragment implements NearbyContract.INearbyView {
    @BindView(R.id.map_container)
    FrameLayout mapContainer;
    Unbinder unbinder;

    NearbyPresenter mNearbyPresenter;
    private LocationInfo mStartLocation;
    private Bitmap mLocationBit;
    private Bitmap mShopBit;
    private ILbsLayer mLbsLayer;
    private boolean mIsLocate;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_nearby;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mNearbyPresenter = new NearbyPresenter(this);
//        ToastUtils.showSuccess(getContext(),"nearby");
        setMap(savedInstanceState);

        mNearbyPresenter.subscribe();
    }

    private void setMap(Bundle savedInstanceState) {
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
//                // 获取附近
                getNearDrivers(locationInfo.getLatitude(),
                        locationInfo.getLongitude());
                Log.e("lat,lon" , String.valueOf(locationInfo.getLatitude()) + "  " + String.valueOf(locationInfo.getLongitude()));
                // 首次定位，添加当前位置的标记
                addLocationMarker();
                mIsLocate = true;
            }
        });
        // 添加地图到容器
        mapContainer.addView(mLbsLayer.getMapView());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void addLocationMarker() {
        if (mLocationBit == null || mLocationBit.isRecycled()) {
            mLocationBit = BitmapFactory.decodeResource(getResources(),
                    R.mipmap.navi_map_gps_locked);
        }
        mLbsLayer.addOrUpdateMarker(mStartLocation, mLocationBit);
    }



    private void getNearDrivers(double latitude, double longitude) {

        mNearbyPresenter.fetchNearShops(latitude, longitude);
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
        if (outState !=null){
            mLbsLayer.onSaveInstanceState(outState);
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mLbsLayer.onDestroy();
        if (mNearbyPresenter != null) {
            mNearbyPresenter.unSubscribe();
        }
    }

    @Override
    public void showNears(List<LocationInfo> data) {
        for (LocationInfo locationInfo : data) {
            showLocationChange(locationInfo);
        }
    }

    /**
     * 显示店铺的标记
     *
     * @param locationInfo
     */
    @Override
    public void showLocationChange(LocationInfo locationInfo) {
        if (mShopBit == null || mShopBit.isRecycled()) {
            mShopBit = BitmapFactory.decodeResource(getResources(), R.mipmap.nearby_city);
        }
        mLbsLayer.addOrUpdateMarker(locationInfo, mShopBit);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }
}
