package com.rongyuan.mingyida.net.api;

import com.rongyuan.mingyida.model.BaseModel;
import com.rongyuan.mingyida.model.LoginModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by guZhongC on 2018/2/2.
 * describe:
 */

public interface GetCodeApi {
    @FormUrlEncoded
//    @Headers("Content-Type: application/json")
    @POST("auth/auth")
    Observable<BaseModel> getCode(@Field("phone") String which, @Field("type") String username);
}
