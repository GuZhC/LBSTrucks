package com.dalimao.mytaxi.module.classify;


import com.dalimao.mytaxi.base.BasePresenter;
import com.dalimao.mytaxi.base.BaseView;
import com.dalimao.mytaxi.model.ClassifyModel;

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
