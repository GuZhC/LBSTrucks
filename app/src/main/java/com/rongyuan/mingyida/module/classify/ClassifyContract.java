package com.rongyuan.mingyida.module.classify;


import com.rongyuan.mingyida.base.BasePresenter;
import com.rongyuan.mingyida.base.BaseView;
import com.rongyuan.mingyida.model.ClassifyModel;

import java.util.List;

/**
 * Created by guZhongC on 2018/1/15.
 * describe:
 */

public interface ClassifyContract  {
    interface IClassifyView extends BaseView{
        void setLiftRecycler(List<ClassifyModel> data);

        void setRightRecycler(List<ClassifyModel> data);

    }

    interface IClassifyPresenter extends BasePresenter{
        void getRecyclerData();
    }
}
