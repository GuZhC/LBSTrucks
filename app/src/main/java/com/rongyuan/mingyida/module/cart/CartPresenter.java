package com.rongyuan.mingyida.module.cart;

import com.rongyuan.mingyida.model.CartGoodsModel;
import com.rongyuan.mingyida.model.RecommendGodds;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Created by guZhongC on 2018/1/12.
 * describe:
 */

public class CartPresenter implements CartContract.ICartPresenter {
    private final CartContract.ICartNiew careView;
    private Subscription mSubscription;
    List<CartGoodsModel> data;
    List<RecommendGodds> recommendGoddsdatas;

    CartPresenter(CartContract.ICartNiew careView) {
        this.careView = careView;
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
    public void addGoodsNum(int postion) {
        int nowNum =Integer.valueOf(data.get(postion).getGoodsNum());
        //todo 网络请求
        data.get(postion).setGoodsNum(String.valueOf(nowNum+1));
    }

    @Override
    public void subGoosdNum(int postion) {
        int nowNum =Integer.valueOf(data.get(postion).getGoodsNum());
        //todo 网络请求
        if (nowNum>1){
            data.get(postion).setGoodsNum(String.valueOf(nowNum-1));
        }
    }


    @Override
    public void choseGoods(int postion) {
        if (postion == CartFragment.CHOSEALL){
            for (int i = 0 ; i<data.size()-1;i++){
                data.get(i).setChose(true);
            }
        }else if (postion == CartFragment.NOCHOSEALL){
            for (int i = 0 ; i<data.size()-1;i++){
                data.get(i).setChose(false);
            }
        } else {
            if (data.get(postion).isChose()){
                data.get(postion).setChose(false);
            }else {
                data.get(postion).setChose(true);
            }
        }
    }

    @Override
    public int getChoseNum() {
        int num = 0;
        for (int i = 0 ; i<data.size()-1;i++){
            if ( data.get(i).isChose()){
                num = num +1;
            }
        }
        return num;
    }

    @Override
    public double getChoseMoney() {
        double money = 0.00;
        for (int i = 0 ; i<data.size()-1;i++){
            if ( data.get(i).isChose()){
                money = money + (Double.valueOf(data.get(i).getGoodsMoney())*Integer.valueOf(data.get(i).getGoodsNum()));
            }
        }
        return money;
    }

    @Override
    public void getRecyclerData() {
        data = new ArrayList<>();
        recommendGoddsdatas = new ArrayList<>();
        for (int i = 0; i<3;i++){
            CartGoodsModel cartGoodsModel = new CartGoodsModel(CartGoodsModel.CART_GOODS);
            cartGoodsModel.setShopName("国际连锁");
            cartGoodsModel.setGoodsImgUrl("https://ws1.sinaimg.cn/large/610dc034ly1fiiiyfcjdoj20u00u0ju0.jpg");
            cartGoodsModel.setGoodsTname("清纯唯美");
            cartGoodsModel.setGoodsDetail("现货直发，需要的快来哦！");
            cartGoodsModel.setGoodsNum("1");
            cartGoodsModel.setGoodsMoney("348.0");
            data.add(cartGoodsModel);
        }
        for (int i = 0; i<3;i++){

            RecommendGodds recommendGodds = new RecommendGodds();
            recommendGodds.setImagUrl("https://ws1.sinaimg.cn/large/610dc034ly1fj3w0emfcbj20u011iabm.jpg");
            recommendGodds.setPrice("998");
            recommendGodds.setPriceOut("1230");
            recommendGodds.setTitle("天真无邪");
            recommendGoddsdatas.add(recommendGodds);
        }

        //这里多加了一个，所以上面用到循环需要减1
        data.add(new CartGoodsModel(CartGoodsModel.CART_RECOMMEND));
        careView.ShowRecyclerView(data,recommendGoddsdatas);
    }

}
