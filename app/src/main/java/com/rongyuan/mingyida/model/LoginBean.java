package com.rongyuan.mingyida.model;

/**
 * Created by guZhongC on 2018/2/2.
 * describe:
 */

public class LoginBean {


    /**
     * code : 200
     * data : {"id":"d1d1a14e-3c8b-11e8-b371-00ac4c8e9c0f","is_login":1}
     * mes :
     */

    private int code;
    private DataBean data;
    private String mes;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public static class DataBean {
        /**
         * id : d1d1a14e-3c8b-11e8-b371-00ac4c8e9c0f
         * is_login : 1
         */

        private String id;
        private int is_login;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getIs_login() {
            return is_login;
        }

        public void setIs_login(int is_login) {
            this.is_login = is_login;
        }
    }
}
