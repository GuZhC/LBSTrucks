package com.dalimao.mytaxi.module.home;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dalimao.mytaxi.utils.Common;
import com.dalimao.mytaxi.R;
import com.dalimao.mytaxi.model.PictureModel;

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
