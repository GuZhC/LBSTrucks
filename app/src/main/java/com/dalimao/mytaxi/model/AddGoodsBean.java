package com.dalimao.mytaxi.model;

/**
 * Created by guZhongC on 2018/5/13.
 * describe:
 */

public class AddGoodsBean {
    String etAddgoodsType = "";
    String etAddgoodsNum = "";
    String etAddgoodsWeight = "";
    String etAddgoodsBaozhuangtype = "";
    String etAddgoodsGoodssize = "";
    String etAddgoodsTime = "";
    String etAddgoodsMoney = "";
    String etAddgoodsLocation = "";
    String etAddgoodsLocationtwo = "";
    boolean hasGoods = false;

    private AddGoodsBean() {
    }

    public static final AddGoodsBean getInstance() {
       return Holder.INSTANCE;
    }

    private static class Holder {
            private static final AddGoodsBean INSTANCE  = new AddGoodsBean();
    }

    public boolean isHasGoods() {
        return hasGoods;
    }

    public void setHasGoods(boolean hasGoods) {
        this.hasGoods = hasGoods;
    }

    public String getEtAddgoodsType() {
        return etAddgoodsType;
    }

    public void setEtAddgoodsType(String etAddgoodsType) {
        this.etAddgoodsType = etAddgoodsType;
    }

    public String getEtAddgoodsNum() {
        return etAddgoodsNum;
    }

    public void setEtAddgoodsNum(String etAddgoodsNum) {
        this.etAddgoodsNum = etAddgoodsNum;
    }

    public String getEtAddgoodsWeight() {
        return etAddgoodsWeight;
    }

    public void setEtAddgoodsWeight(String etAddgoodsWeight) {
        this.etAddgoodsWeight = etAddgoodsWeight;
    }

    public String getEtAddgoodsBaozhuangtype() {
        return etAddgoodsBaozhuangtype;
    }

    public void setEtAddgoodsBaozhuangtype(String etAddgoodsBaozhuangtype) {
        this.etAddgoodsBaozhuangtype = etAddgoodsBaozhuangtype;
    }

    public String getEtAddgoodsGoodssize() {
        return etAddgoodsGoodssize;
    }

    public void setEtAddgoodsGoodssize(String etAddgoodsGoodssize) {
        this.etAddgoodsGoodssize = etAddgoodsGoodssize;
    }

    public String getEtAddgoodsTime() {
        return etAddgoodsTime;
    }

    public void setEtAddgoodsTime(String etAddgoodsTime) {
        this.etAddgoodsTime = etAddgoodsTime;
    }

    public String getEtAddgoodsMoney() {
        return etAddgoodsMoney;
    }

    public void setEtAddgoodsMoney(String etAddgoodsMoney) {
        this.etAddgoodsMoney = etAddgoodsMoney;
    }

    public String getEtAddgoodsLocation() {
        return etAddgoodsLocation;
    }

    public void setEtAddgoodsLocation(String etAddgoodsLocation) {
        this.etAddgoodsLocation = etAddgoodsLocation;
    }

    public String getEtAddgoodsLocationtwo() {
        return etAddgoodsLocationtwo;
    }

    public void setEtAddgoodsLocationtwo(String etAddgoodsLocationtwo) {
        this.etAddgoodsLocationtwo = etAddgoodsLocationtwo;
    }
}
