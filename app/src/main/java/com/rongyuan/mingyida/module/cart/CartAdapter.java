package com.rongyuan.mingyida.module.cart;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rongyuan.mingyida.R;
import com.rongyuan.mingyida.model.CartGoodsModel;
import com.rongyuan.mingyida.model.RecommendGodds;
import com.rongyuan.mingyida.utils.Common;
import com.rongyuan.mingyida.utils.ToastUtils;

import java.util.List;

/**
 * Created by guZhongC on 2018/1/12.
 * describe:
 */

public class CartAdapter extends BaseMultiItemQuickAdapter<CartGoodsModel, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    private CartRecommendAdapter mCartRecommendAdapter;
    List<RecommendGodds> recommendGoddsdatas;
    Context context;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public CartAdapter(List<CartGoodsModel> data, List<RecommendGodds> recommendGoddsdatas, Context context) {
        super(data);
        this.recommendGoddsdatas = recommendGoddsdatas;
        this.context = context;
        addItemType(CartGoodsModel.CART_GOODS, R.layout.item_newrby_cart);
        addItemType(CartGoodsModel.CART_RECOMMEND, R.layout.item_newrby_goods);
    }

    @Override
    protected void convert(BaseViewHolder helper, CartGoodsModel item) {
        switch (helper.getItemViewType()) {
            case CartGoodsModel.CART_GOODS:
                helper.addOnClickListener(R.id.cb_item_cart).addOnClickListener(R.id.tv_nearby_item_gooddsub)
                        .addOnClickListener(R.id.tv_nearby_item_goodsadd);
                helper.setText(R.id.tv_nearby_storename, item.getShopName()).setText(R.id.tv_nearby_item_goodsname, item.getGoodsTname())
                        .setText(R.id.tv_nearby_item_gooddetail, item.getGoodsDetail()).setText(R.id.tv_nearby_item_goodsnum, item.getGoodsNum())
                        .setText(R.id.tv_nearby_item_price, "¥ " + item.getGoodsMoney());
                if (item.isChose()){
                    helper.setChecked(R.id.cb_item_cart,true);
                }else {
                    helper.setChecked(R.id.cb_item_cart,false);
                }
                Common.ShowImage(mContext,item.getGoodsImgUrl(),(ImageView) helper.getView(R.id.img_nearby_item_goods));

                break;
            case CartGoodsModel.CART_RECOMMEND:
                helper.setNestView(R.id.item_newrby_goods); // u can set nestview id
                final RecyclerView recyclerView = helper.getView(R.id.item_recycler_recommend);
                recyclerView.setLayoutManager(new GridLayoutManager(helper.itemView.getContext(), 2));
                recyclerView.setHasFixedSize(true);
                mCartRecommendAdapter = new CartRecommendAdapter(recommendGoddsdatas);
                mCartRecommendAdapter.setOnItemClickListener(this);
                mCartRecommendAdapter.setNotDoAnimationCount(3);
                View top = LayoutInflater.from(context).inflate(R.layout.top_view, (ViewGroup) recyclerView.getParent(), false);
                mCartRecommendAdapter.addHeaderView(top);
                recyclerView.setAdapter(mCartRecommendAdapter);

        }

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ToastUtils.showUsual(context, "click" + position);
    }
}
