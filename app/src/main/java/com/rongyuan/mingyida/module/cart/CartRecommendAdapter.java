package com.rongyuan.mingyida.module.cart;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rongyuan.mingyida.R;
import com.rongyuan.mingyida.model.RecommendGodds;
import com.rongyuan.mingyida.utils.Common;

import java.util.List;

/**
 * Created by guZhongC on 2018/1/12.
 * describe:
 */

public class CartRecommendAdapter extends BaseQuickAdapter<RecommendGodds, BaseViewHolder> {


    public CartRecommendAdapter(List<RecommendGodds> recommendGoddsdatas) {
        super(R.layout.item_cart_recommend, recommendGoddsdatas);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendGodds item) {
        helper.setText(R.id.tv_cart_title, item.getTitle()).setText(R.id.tv_cart_price, "¥ " + item.getPrice())
                .setText(R.id.tv_cart_price_out, "门市价：" + item.getPriceOut());
        Common.ShowImage(mContext, item.getImagUrl(), (ImageView) helper.getView(R.id.img_cart_commend));


    }
}
