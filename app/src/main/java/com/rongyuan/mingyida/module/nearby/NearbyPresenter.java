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
        getRecyclerData();
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

    @Override
    public void getRecyclerData() {
        List<NeaberShopModel> data = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            NeaberShopModel neaberShopModel = new NeaberShopModel();
            switch (i) {
                case 0:
                    neaberShopModel.setImageUrl("https://ws1.sinaimg.cn/large/610dc034ly1fis7dvesn6j20u00u0jt4.jpg");
                    break;
                case 1:
                    neaberShopModel.setImageUrl("https://ws1.sinaimg.cn/large/610dc034ly1fir1jbpod5j20ip0newh3.jpg");
                    break;
                case 2:
                    neaberShopModel.setImageUrl("https://ws1.sinaimg.cn/large/610dc034ly1fil82i7zsmj20u011hwja.jpg");
                    break;
                case 3:
                    neaberShopModel.setImageUrl("https://ws1.sinaimg.cn/large/610dc034ly1fik2q1k3noj20u00u07wh.jpg");
                    break;
                case 4:
                    neaberShopModel.setImageUrl("https://ws1.sinaimg.cn/large/610dc034ly1fiiiyfcjdoj20u00u0ju0.jpg");
                    break;
                case 5:
                    neaberShopModel.setImageUrl("https://ws1.sinaimg.cn/large/610dc034ly1fiednrydq8j20u011itfz.jpg");
                    break;
                default:
                    neaberShopModel.setImageUrl("https://ws1.sinaimg.cn/large/610dc034ly1fiiiyfcjdoj20u00u0ju0.jpg");
                    break;
            }
            neaberShopModel.setTitle(i + "98 ? ");
            neaberShopModel.setLocation("不收定金，货到付款。");
            neaberShopModel.setDistance(i + "66 米 ");
            data.add(neaberShopModel);
        }
        mNearbyView.setRecycler(data);
    }
}
