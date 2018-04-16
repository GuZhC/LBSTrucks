package com.dalimao.mytaxi.module.home;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dalimao.mytaxi.model.NeaberShopModel;
import com.dalimao.mytaxi.utils.Common;
import com.dalimao.mytaxi.R;

import java.util.List;

/**
 * Created by guZhongC on 2018/1/8.
 * describe:
 */

public class NearbyAdapter extends BaseQuickAdapter<NeaberShopModel, BaseViewHolder> {
    public NearbyAdapter(@Nullable List<NeaberShopModel> data) {
        super(R.layout.item_nearby_recycler, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NeaberShopModel item) {
        helper.addOnClickListener(R.id.tv_nearby_recycler_goto);
        helper.setText(R.id.tv_nearby_recycler_title, item.getTitle());
        helper.setText(R.id.tv_nearby_recycler_location, item.getLocation());
        helper.setText(R.id.tv_nearby_recycler_goto, item.getDistance());
        Common.ShowImage(mContext,item.getImageUrl(),(ImageView) helper.getView(R.id.img_nearby_recycler));
    }
}
