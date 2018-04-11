package com.rongyuan.mingyida.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by guZhongC on 2018/1/12.
 * describe:
 */

public class CartGoodsModel implements MultiItemEntity {
    public static final int CART_GOODS = 1;
    public static final int CART_RECOMMEND = 2;
    public int Type;

    private boolean isChose;
    private String shopName;
    private String goodsImgUrl;
    private String goodsTname;
    private String goodsDetail;
    private String goodsNum;
    private String goodsMoney;

    public boolean isChose() {
        return isChose;
    }

    public void setChose(boolean chose) {
        isChose = chose;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getGoodsImgUrl() {
        return goodsImgUrl;
    }

    public void setGoodsImgUrl(String goodsImgUrl) {
        this.goodsImgUrl = goodsImgUrl;
    }

    public String getGoodsTname() {
        return goodsTname;
    }

    public void setGoodsTname(String goodsTname) {
        this.goodsTname = goodsTname;
    }

    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsMoney() {
        return goodsMoney;
    }

    public void setGoodsMoney(String goodsMoney) {
        this.goodsMoney = goodsMoney;
    }

    public CartGoodsModel(final int type) {
        Type = type;
    }

    @Override
    public int getItemType() {
        return Type;
    }
}
