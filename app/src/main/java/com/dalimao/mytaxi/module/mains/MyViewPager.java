package com.dalimao.mytaxi.module.mains;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.dalimao.mytaxi.R;

/**
 * Created by guZhongC on 2018/1/11.
 * describe: 处理地图和Viewpage冲突
 */

public class MyViewPager extends ViewPager {

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if(v.getId()== R.id.map_container||v.getClass().getName().equals("com.amap.api.maps.MapView")) {
            return true;
        }
        //if(v instanceof MapView){
        //    return true;
        //}
        return super.canScroll(v, checkV, dx, x, y);
    }
}