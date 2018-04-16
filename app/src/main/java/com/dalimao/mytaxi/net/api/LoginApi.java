package com.dalimao.mytaxi.net.api;

import com.dalimao.mytaxi.model.BaseModel;
import com.dalimao.mytaxi.model.LoginModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by guZhongC on 2018/2/2.
 * describe:
 */

public interface LoginApi {
    @FormUrlEncoded
//    @Headers("Content-Type: application/json")
    @POST("Rl/login")
    Observable<BaseModel<LoginModel>> getLoginData(@Field("which") String which, @Field("username") String username, @Field("password") String password);
}
