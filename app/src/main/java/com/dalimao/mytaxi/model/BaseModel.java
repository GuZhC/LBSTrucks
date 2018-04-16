package com.dalimao.mytaxi.model;

/**
 * Created by guZhongC on 2018/2/5.
 * describe:
 */

public class BaseModel<T> {
     public int code;
     public String hint;
     public T data;

     public T getData() {
          return data;
     }

     public void setData(T data) {
          this.data = data;
     }
}
