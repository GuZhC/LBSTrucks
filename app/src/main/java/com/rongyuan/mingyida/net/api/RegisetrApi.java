package com.rongyuan.mingyida.net.api;

import com.rongyuan.mingyida.model.RegisterModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by guZhongC on 2018/2/2.
 * describe:
 */

public interface RegisetrApi {
    @Multipart
    @POST("rl/register")
//    @Headers("Content-Type: application/json")
    Observable<RegisterModel> Regisetr(@Part("id_card_photo\"; filename=\"id_card_photo.png\"") RequestBody id_card_photo,
                                       @Part("business_license\"; filename=\"business_license.png\"") RequestBody business_license,
                                       @Part() List<MultipartBody.Part> params);
}
