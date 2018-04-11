package com.rongyuan.mingyida.module.home;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rongyuan.mingyida.R;
import com.rongyuan.mingyida.model.PictureModel;
import com.rongyuan.mingyida.utils.Common;

import java.util.List;

/**
 * Created by guZhongC on 2018/1/8.
 * describe:
 */

public class HotAdapter extends BaseQuickAdapter<PictureModel, BaseViewHolder> {
    public HotAdapter(@Nullable List<PictureModel> data) {
        super(R.layout.item_recycler_hot, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PictureModel item) {
        helper.setText(R.id.home_recycler_hot_title, item.getDesc());
        Common.ShowImage(mContext,item.getUrl(),(ImageView) helper.getView(R.id.home_recycler_hot_img));
    }
}
