package com.rongyuan.mingyida.module.classify;

import com.rongyuan.mingyida.model.ClassifyModel;
import com.rongyuan.mingyida.model.GoodsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guZhongC on 2018/1/15.
 * describe:
 */

public class ClassifyPresenter implements ClassifyContract.IClassifyPresenter {

    public ClassifyContract.IClassifyView mClassifyView;
    List<ClassifyModel> datas;

    ClassifyPresenter(ClassifyContract.IClassifyView mClassifyView) {
        this.mClassifyView = mClassifyView;
    }

    public void subscribe() {
        getRecyclerData();
    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void getRecyclerData() {
        datas = new ArrayList<>();
        for (int j = 0; j < 11; j++) {
            ClassifyModel classifyModel = new ClassifyModel();
            List<GoodsModel> goodsModelList = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                GoodsModel goodsModel = new GoodsModel();
//                goodsModel.setImgUrl("https://ws1.sinaimg.cn/large/610dc034ly1fir1jbpod5j20ip0newh3.jpg");
                goodsModel.setImgUrl("https://ws1.sinaimg.cn/large/610dc034ly1fis7dvesn6j20u00u0jt4.jpg");
                goodsModel.setTitle("for free|" + i);
                goodsModelList.add(goodsModel);
            }
            classifyModel.setChose(false);
            classifyModel.setGoodsData(goodsModelList);
            switch (j) {
                case 0:
                    classifyModel.setChose(true);
                    classifyModel.setClassify("美容清洗");
                    break;
                case 1:
                    classifyModel.setClassify("常规保养");
                    break;
                case 2:
                    classifyModel.setClassify("常用配件");
                    break;
                case 3:
                    classifyModel.setClassify("车载电器");
                    break;
                case 4:
                    classifyModel.setClassify("空调养护");
                    break;
                case 5:
                    classifyModel.setClassify("清洗养护");
                    break;
                case 6:
                    classifyModel.setClassify("汽车内饰");
                    break;
                case 7:
                    classifyModel.setClassify("户外用品");
                    break;
                case 8:
                    classifyModel.setClassify("功能小件");
                    break;
                case 9:
                    classifyModel.setClassify("道路救援");
                    break;
                case 10:
                    classifyModel.setClassify("为你推荐");
                    break;

            }
            datas.add(classifyModel);
        }
        mClassifyView.setLiftRecycler(datas);
        mClassifyView.setRightRecycler(datas);
    }
}
