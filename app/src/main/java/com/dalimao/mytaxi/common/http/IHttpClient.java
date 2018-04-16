package com.dalimao.mytaxi.common.http;

/**
 * Created by GuZhongCai on 2018/4/12.
 * describe:
 */

public interface IHttpClient {
    IResponse get(IRequest request, boolean forceCache);
    IResponse post(IRequest request, boolean forceCache);
}
