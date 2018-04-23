package com.dalimao.mytaxi.net.api;

import com.dalimao.mytaxi.model.RegisterModel;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by guZhongC on 2018/2/2.
 * describe:
 */

public interface RegisetrApi {
//    @Multipart
    @FormUrlEncoded
    @POST("/")
//    @Headers("Content-Type: application/json")
//    Observable<RegisterModel> Regisetr(@Part("id_card_photo\"; filename=\"id_card_photo.png\"") RequestBody id_card_photo,
//                                       @Part("business_license\"; filename=\"business_license.png\"") RequestBody business_license,
//                                       @Part() List<MultipartBody.Part> params);
    Observable<RegisterModel> Regisetr(@FieldMap() Map<String, String> params);
}
