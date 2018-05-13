package com.dalimao.mytaxi.module.home;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dalimao.mytaxi.R;
import com.dalimao.mytaxi.model.NeaberShopModel;
import com.dalimao.mytaxi.model.PictureModel;
import com.dalimao.mytaxi.utils.Common;

import java.util.List;

/**
 * Created by guZhongC on 2018/1/8.
 * describe:
 */

public class OrderAdapter extends BaseQuickAdapter<NeaberShopModel, BaseViewHolder> {
    public OrderAdapter(@Nullable List<NeaberShopModel> data) {
        super(R.layout.item_recycler_hot, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NeaberShopModel item) {
//        helper.setText(R.id.home_recycler_hot_title, item.getDesc());
//        Common.ShowImage(mContext,item.getUrl(),(ImageView) helper.getView(R.id.home_recycler_hot_img));
    }
}
