package com.dalimao.mytaxi.module.nearby;


import com.dalimao.mytaxi.lbs.LocationInfo;
import com.dalimao.mytaxi.common.http.BaseBizResponse;

import java.util.List;

/**
 * Created by liuguangli on 17/5/31.
 */

public class NearDriversResponse extends BaseBizResponse {
    List<LocationInfo> data;

    public List<LocationInfo> getData() {
        return data;
    }

    public void setData(List<LocationInfo> data) {
        this.data = data;
    }
}
