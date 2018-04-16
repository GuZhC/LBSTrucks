package com.dalimao.mytaxi.utils;


import android.content.Context;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

/**
 * Created by guZhongC on 2017/12/26.
 * describe:
 */

public class ToastUtils {


    /**
     * 错误Toast：
     */
    public static void showError(Context context, String tost) {
        Toasty.error(context, tost, Toast.LENGTH_SHORT, true).show();
    }

    /**
     * 成功Toast：
     */
    public static void showSuccess(Context context, String tost) {
        Toasty.success(context, tost, Toast.LENGTH_SHORT, true).show();
    }

    /**
     * 信息Toast：
     */
    public static void showInfo(Context context, String tost) {
        Toasty.info(context, tost, Toast.LENGTH_SHORT, true).show();
    }

    /**
     * 警告Toast：
     */
    public static void showWarning(Context context, String tost) {
        Toasty.warning(context, tost, Toast.LENGTH_SHORT, true).show();
    }

    /***
     *通常的Toast
     */
    public static void showUsual(Context context, String tost) {
        Toasty.normal(context, tost, Toast.LENGTH_SHORT).show();
    }

    /**
     * 带有图标的常用Toast：
     */
    public static void showIcon(Context context, String tost, int icon) {
        Toasty.normal(context, tost, icon).show();
    }

    /**
     * 创建自定义Toasts ：
     */
    public static void showCustom(Context context, String tost, int icon, int color, int colorBg) {
        Toasty.custom(context, tost, icon, color, Toast.LENGTH_SHORT, true, true).show();
    }
}
