package com.dalimao.mytaxi.module.goods;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dalimao.mytaxi.model.AllGoodsListModel;
import com.dalimao.mytaxi.R;
import com.dalimao.mytaxi.utils.Common;

import java.util.List;

/**
 * Created by guZhongC on 2018/2/1.
 * describe:
 */

public class AllGoodsDetailAdapter extends BaseQuickAdapter<AllGoodsListModel,BaseViewHolder> {

    public AllGoodsDetailAdapter(int layoutResId, @Nullable List<AllGoodsListModel> data) {
        super(layoutResId, data);
    }

    public AllGoodsDetailAdapter( @Nullable List<AllGoodsListModel> data) {
        super(R.layout.item_allgoods_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AllGoodsListModel item) {
        Common.ShowImage(mContext,item.getImagUrl(),(ImageView) helper.getView(R.id.img_allgods));
        helper.setText(R.id.tv_allgoods_recycler_price,"￥："+item.getPrice());
        if (!item.isPromotion()){
            helper.getView(R.id.iv_allgoods_recycler_promotion).setVisibility(View.GONE);
        }else {
            helper.getView(R.id.iv_allgoods_recycler_promotion).setVisibility(View.VISIBLE);
        }
        helper.setText(R.id.tv_allgoods_recycler_market_price,"市场￥:"+item.getMarketPrice());
        helper.setText(R.id.tv_allgoods_recycler_buy_number,"销量"+item.getBuyNumber());
        helper.setText(R.id.tv_allgoods_recycler_name,item.getTitle());

    }
}
