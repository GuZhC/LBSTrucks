package com.dalimao.mytaxi.common.http.impl;

import com.dalimao.mytaxi.common.http.IRequest;
import com.dalimao.mytaxi.common.http.IResponse;
import com.dalimao.mytaxi.common.http.IHttpClient;

import java.io.IOException;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by GuZhongCai on 2018/4/12.
 * describe:
 */

public class OkHttpClientImpl implements IHttpClient {
    OkHttpClient mOkHttpClient = new OkHttpClient.Builder().build();

    @Override
    public IResponse get(IRequest request, boolean forceCache) {
        request.setMethod(IRequest.GET);
        Map<String, String> header = request.getHeader();
        Request.Builder builder = new Request.Builder();
        for (String key : header.keySet()) {
            builder.header(key,header.get(key));
        }
        String url = request.getUrl();
        builder.url(url).get();
        Request oKRequest = builder.build();
        return execute(oKRequest);
    }

    @Override
    public IResponse post(IRequest request, boolean forceCache) {
        // 指定请求方式
        request.setMethod(IRequest.POST);
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, request.getBody().toString());
        Map<String, String> header = request.getHeader();
        Request.Builder builder = new Request.Builder();
        for (String key : header.keySet()) {

            builder.header(key, header.get(key));

        }
        builder.url(request.getUrl())
                .post(body);
        Request oKRequest = builder.build();
        return  execute(oKRequest);

    }

    private IResponse execute(Request request) {
        BaseResponse commonResponse = new BaseResponse();
        try {
            Response response = mOkHttpClient.newCall(request).execute();
            commonResponse.setCode(response.code());
            String body = response.body().string();
            commonResponse.setData(body);
        } catch (IOException e) {
            e.printStackTrace();
            commonResponse.setCode(commonResponse.STATE_UNKNOWN_ERROR);
            commonResponse.setData(e.getMessage());
        }
        return commonResponse;
    }

}
