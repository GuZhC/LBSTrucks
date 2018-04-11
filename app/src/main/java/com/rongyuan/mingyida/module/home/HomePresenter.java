package com.rongyuan.mingyida.module.home;

import android.util.Log;

import com.rongyuan.mingyida.R;
import com.rongyuan.mingyida.model.CategoryResult;
import com.rongyuan.mingyida.model.ClassifyBeans;
import com.rongyuan.mingyida.model.HomeAllModel;
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
        getRecyclerDataClassify();
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
    public void getRecyclerDataClassify() {
        String[] text = {"洗车", "保养", "改装", "汽车用品", "分类"};
        String[] image = {String.valueOf(R.mipmap.home_classify_wash), String.valueOf(R.mipmap.home_classify_maintain),
                String.valueOf(R.mipmap.home_classify_refit), String.valueOf(R.mipmap.home_classify_other), String.valueOf(R.mipmap.home_classify_all)};
        List<ClassifyBeans> datas = new ArrayList<>();
        for (int i = 0; i < text.length; i++) {
            ClassifyBeans classifyBeans = new ClassifyBeans();
            classifyBeans.setImgUrl(image[i]);
            classifyBeans.setTitle(text[i]);
            datas.add(classifyBeans);
        }
        mHomeView.setRecyclerClassify(datas);
    }

    @Override
    public void getRecyclerDataAll() {
        List<HomeAllModel> datas = new ArrayList<>();
        for (int i = 0 ; i<3; i++){
        HomeAllModel data = new HomeAllModel();
        data.setTitleA("岁月丶竟好？");
        data.setTitleB("岁月丶静好？");
        data.setTitleC("岁月丶禁好？");
        data.setCotentA("岁月丶竟好");
        data.setCotentB("岁月丶静好");
        data.setCotentC("岁月丶禁好");
        data.setImageA("https://ws1.sinaimg.cn/large/610dc034ly1fjgfyxgwgnj20u00gvgmt.jpg");
        data.setImageB("https://ws1.sinaimg.cn/large/610dc034ly1fj3w0emfcbj20u011iabm.jpg");
        data.setImageC("https://ws1.sinaimg.cn/large/610dc034ly1fhj5228gwdj20u00u0qv5.jpg");
        datas.add(data);
        }
        mHomeView.setRecyclerall(datas);
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
