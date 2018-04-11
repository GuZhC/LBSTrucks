package com.rongyuan.mingyida.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.rongyuan.mingyida.R;

/**
 * Created by guZhongC on 2018/1/29.
 * describe:
 */

public class Common {
    public static void ShowImage(Context context , String url, ImageView imageView){
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.lodingview)
                .error(R.drawable.errorview)
                .priority(Priority.HIGH);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public static void ShouwError(Context context){
        ToastUtils.showError(context,"服务器繁忙，稍后再试");
    }
}
