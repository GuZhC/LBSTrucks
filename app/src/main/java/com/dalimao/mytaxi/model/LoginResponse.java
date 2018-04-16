package com.dalimao.mytaxi.model;


import com.dalimao.mytaxi.common.http.BaseBizResponse;

/**
 * Created by liuguangli on 17/5/6.
 */

public class LoginResponse extends BaseBizResponse {
    Account data;

    public Account getData() {
        return data;
    }

    public void setData(Account data) {
        this.data = data;
    }
}
