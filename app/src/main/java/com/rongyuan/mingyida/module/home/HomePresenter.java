package com.rongyuan.mingyida.module.home;

import com.rongyuan.mingyida.model.CategoryResult;
import com.rongyuan.mingyida.model.NeaberShopModel;
import com.rongyuan.mingyida.model.PictureModel;
import com.rongyuan.mingyida.net.NetWork;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by guZhongC on 2018/1/8.
 * describe:
 */

public class HomePresenter implements HomeContract.IHomePresenter {

    private Subscription mSubscription;

    private HomeContract.IHomeView mHomeView;

    private List<PictureModel> mModels;

    HomePresenter(HomeContract.IHomeView homeView) {
        this.mHomeView = homeView;
        mModels = new ArrayList<>();
    }

    @Override
    public void subscribe() {
        getBannerData();
        getRecyclerDataHot();
        getRecyclerDataAll();
    }

    public List<PictureModel> getBannerModel() {
        return this.mModels;
    }

    @Override
    public void unSubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void getRecyclerDataHot() {
        // todo hot商品数据请求  下面的测试用

        mSubscription = NetWork.getGankApi()
                .getCategoryData("福利", 35, 2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        mHomeView.showBannerFail("Banner 图加载失败");
                        for (int i = 0; i < 8; i++) {
                            PictureModel model = new PictureModel();
                            model.desc = "加载失败";
                            model.url = "errorview";
                            mModels.add(model);

                        }
                        mHomeView.setRecyclerHot(mModels);
                    }

                    @Override
                    public void onNext(CategoryResult categoryResult) {
                        if (categoryResult != null && categoryResult.results != null
                                && categoryResult.results.size() > 0) {
                            List<String> imagUrls = new ArrayList<>();
                            for (CategoryResult.ResultsBean result : categoryResult.results) {
                                if (!result.url.isEmpty()) {
                                    imagUrls.add(result.url);
                                }
                                PictureModel model = new PictureModel();
                                model.desc = "福利!福利";
                                model.url = result.url;
                                mModels.add(model);
//                                Log.e( "image",model.url+"\n");
                            }
                            mHomeView.setRecyclerHot(mModels);
                        } else {
//                            mHomeView.showBannerFail("Banner 图加载失败");
                            for (int i = 0; i < 8; i++) {
                                PictureModel model = new PictureModel();
                                model.desc = "加载失败";
                                model.url = "errorview";
                                mModels.add(model);
                            }
                            mHomeView.setRecyclerHot(mModels);
                        }
                    }
                });
    }


    @Override
    public void getRecyclerDataAll() {

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
        mHomeView.setRecyclerall(data);
    }

    @Override
    public void getBannerData() {
        mSubscription = NetWork.getGankApi()
                .getCategoryData("福利", 5, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        mHomeView.showBannerFail("Banner 图加载失败");
                        List<String> imagUrls = new ArrayList<>();
                        imagUrls.add("errorview");
                        imagUrls.add("errorview");
                        mHomeView.setBanner(imagUrls);
                    }

                    @Override
                    public void onNext(CategoryResult categoryResult) {
                        if (categoryResult != null && categoryResult.results != null
                                && categoryResult.results.size() > 0) {
                            List<String> imagUrls = new ArrayList<>();
                            for (CategoryResult.ResultsBean result : categoryResult.results) {
                                if (!result.url.isEmpty()) {
                                    imagUrls.add(result.url);
                                }
                                PictureModel model = new PictureModel();
                                model.desc = result.desc;
                                model.url = result.url;
                                mModels.add(model);
                            }
                            mHomeView.setBanner(imagUrls);
                        } else {
//                            mHomeView.showBannerFail("Banner 图加载失败");
                            List<String> imagUrls = new ArrayList<>();
                            imagUrls.add("errorview");
                            imagUrls.add("errorview");
                            mHomeView.setBanner(imagUrls);
                        }
                    }
                });
    }
}
