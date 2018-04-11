package com.rongyuan.mingyida.lbs;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

/**
 * Created by guZhongC on 2018/1/11.
 * describe:
 */

public interface ILbsLayer {

    /**
     *  获取地图
     */
    View getMapView();

    /**
     *  设置位置变化监听
     */
    void setLocationChangeListener(CommonLocationChangeListener locationChangeListener);

    /**
     *  设置定位图标
     */
    void setLocationRes(int res);

    /**
     *  添加，更新标记点，包括位置、角度（通过 id 识别）
     */
    void addOrUpdateMarker(LocationInfo locationInfo, Bitmap bitmap);

    /**
     *   获取当前城市
     */
    String getCity();


    /**
     *  生命周期函数
     */

    void onCreate(Bundle state);
    void onResume();
    void onSaveInstanceState(Bundle outState);
    void onPause();
    void onDestroy();

    void clearAllMarkers();
    /**
     *  移动动相机到某个点，
     * @param locationInfo
     * @param scale 缩放系数
     */
    void moveCameraToPoint(LocationInfo locationInfo, int scale);

    interface CommonLocationChangeListener {
        void onLocationChanged(LocationInfo locationInfo);

        void onLocation(LocationInfo locationInfo);
    }
}