package com.rongyuan.mingyida.module.servingproduct;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rongyuan.mingyida.R;
import com.rongyuan.mingyida.model.AllServiceListModel;
import com.rongyuan.mingyida.utils.Common;

import java.util.List;

/**
 * Created by guZhongC on 2018/2/1.
 * describe:
 */

public class AllServicesRecyclerAdapter extends BaseQuickAdapter<AllServiceListModel, BaseViewHolder> {


    public AllServicesRecyclerAdapter(@Nullable List<AllServiceListModel> data) {
        super(R.layout.item_allservice_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AllServiceListModel item) {
        Common.ShowImage(mContext, item.getImagUrl(), (ImageView) helper.getView(R.id.img_allservice));
        helper.setText(R.id.tv_allservice_recycler_price, "￥：" + item.getPrice());
        if (!item.isPromotion()) {
            helper.getView(R.id.tv_allservice_recycler_promotion).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.tv_allservice_recycler_promotion).setVisibility(View.VISIBLE);
        }
        helper.setText(R.id.tv_allservice_recycler_market_price, "门市￥:" + item.getMarketPrice());
        helper.setText(R.id.tv_allservice_recycler_title, item.getTitle());
        helper.setText(R.id.tv_allservice_recycler_shopname, item.getShopsname());
        helper.setText(R.id.tv_allservice_recycler_buy_distance, item.getDistance()+"米");

    }
}
