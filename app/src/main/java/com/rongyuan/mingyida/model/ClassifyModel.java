package com.rongyuan.mingyida.model;

import java.util.List;

/**
 * Created by guZhongC on 2018/1/16.
 * describe:
 */

public class ClassifyModel {
    private String classify;

    private boolean isChose;

    private List<GoodsModel> GoodsData;

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public boolean isChose() {
        return isChose;
    }

    public void setChose(boolean chose) {
        isChose = chose;
    }

    public List<GoodsModel> getGoodsData() {
        return GoodsData;
    }

    public void setGoodsData(List<GoodsModel> goodsData) {
        GoodsData = goodsData;
    }
}
