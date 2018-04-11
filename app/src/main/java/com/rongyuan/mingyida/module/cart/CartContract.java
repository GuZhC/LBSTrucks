package com.rongyuan.mingyida.module.cart;


import com.rongyuan.mingyida.base.BasePresenter;
import com.rongyuan.mingyida.base.BaseView;
import com.rongyuan.mingyida.model.CartGoodsModel;
import com.rongyuan.mingyida.model.RecommendGodds;

import java.util.List;

/**
 * Created by guZhongC on 2018/1/12.
 * describe:
 */

public interface CartContract {
    interface ICartNiew extends BaseView{
        void ShowRecyclerView(List<CartGoodsModel> data, List<RecommendGodds> recommendGoddsdatas);
    }

    interface ICartPresenter extends BasePresenter{
        void addGoodsNum(int postion);
        void subGoosdNum(int postion);
        void getRecyclerData();
        void choseGoods(int postion);
        int getChoseNum();
        double getChoseMoney();
    }
}
