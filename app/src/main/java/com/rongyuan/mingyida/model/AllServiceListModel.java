package com.rongyuan.mingyida.model;

/**
 * Created by guZhongC on 2018/2/1.
 * describe:
 */

public class AllServiceListModel {
    private String imagUrl;
    private String title;
    private boolean isPromotion;
    private String price;
    private String marketPrice;
    private String distance;
    private String shopsname;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getShopsname() {
        return shopsname;
    }

    public void setShopsname(String shopsname) {
        this.shopsname = shopsname;
    }

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public boolean isPromotion() {
        return isPromotion;
    }

    public void setPromotion(boolean promotion) {
        isPromotion = promotion;
    }

    public String getImagUrl() {
        return imagUrl;
    }

    public void setImagUrl(String imagUrl) {
        this.imagUrl = imagUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
