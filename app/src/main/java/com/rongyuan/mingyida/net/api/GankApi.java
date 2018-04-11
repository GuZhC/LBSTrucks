package com.rongyuan.mingyida.net.api;


import com.rongyuan.mingyida.model.CategoryResult;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 代码家的gank.io接口
 * <p>
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-14  11:23
 */

public interface GankApi {

    /**
     * 根据category获取Android、iOS等干货数据
     *
     * @param category 类别
     * @param count    条目数目
     * @param page     页数
     */
    @GET("data/{category}/{count}/{page}")
    Observable<CategoryResult> getCategoryData(@Path("category") String category, @Path("count") int count, @Path("page") int page);

}
