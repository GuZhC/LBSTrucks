package com.rongyuan.mingyida.module.nearby;

import com.rongyuan.mingyida.lbs.LocationInfo;
import com.rongyuan.mingyida.model.NeaberShopModel;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Created by guZhongC on 2018/1/11.
 * describe:
 */

public class NearbyPresenter implements NearbyContract.INearbyPresenter {
    private Subscription mSubscription;

    private NearbyContract.INearbyView mNearbyView;

    private List<LocationInfo> mModels;

    NearbyPresenter(NearbyContract.INearbyView nearbyView) {
        this.mNearbyView = nearbyView;
        mModels = new ArrayList<>();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void fetchNearShops(double latitude, double longitude) {
        List<LocationInfo> data = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            LocationInfo locationInfo = new LocationInfo(30.54577 + i * 0.001, 104.066691 - i * 0.001);
            locationInfo.setKey("key" + i);
            locationInfo.setName("name" + i);
            data.add(locationInfo);
        }
        mNearbyView.showNears(data);
    }


}
