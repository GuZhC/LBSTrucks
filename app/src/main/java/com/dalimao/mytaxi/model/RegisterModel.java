package com.dalimao.mytaxi.model;

/**
 * Created by guZhongC on 2018/2/2.
 * describe:
 */

public class RegisterModel {


    /**
     * code : 0
     * hint : 登陆成功
     */

    private int result;
    private String hint;

    public int getCode() {
        return result;
    }

    public void setCode(int code) {
        this.result = code;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

}
