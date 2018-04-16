package com.dalimao.mytaxi.module.classify;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dalimao.mytaxi.model.GoodsModel;
import com.dalimao.mytaxi.utils.Common;
import com.dalimao.mytaxi.R;

import java.util.List;

/**
 * Created by guZhongC on 2018/1/18.
 * describe:
 */

public class ClassifyGoodsAdapter extends BaseQuickAdapter<GoodsModel, BaseViewHolder> {

    public ClassifyGoodsAdapter(@Nullable List<GoodsModel> data) {
        super(R.layout.item_classify_goods, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsModel item) {
        helper.setText(R.id.tv_classify_goods_name, item.getTitle());
        Common.ShowImage(mContext, item.getImgUrl(), (ImageView) helper.getView(R.id.iv_classify_goods_img));

    }
}
