package com.dalimao.mytaxi.module.home;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dalimao.mytaxi.model.HomeAllModel;
import com.dalimao.mytaxi.utils.Common;
import com.dalimao.mytaxi.R;

import java.util.List;

/**
 * Created by guZhongC on 2018/1/10.
 * describe:
 */

public class AllAdapter extends BaseQuickAdapter<HomeAllModel, BaseViewHolder> {
    public AllAdapter(@Nullable List<HomeAllModel> data) {
        super(R.layout.item_recycler_all, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeAllModel item) {
        helper.addOnClickListener(R.id.layout_home_one).addOnClickListener(R.id.layout_home_two).addOnClickListener(R.id.layout_home_three);
        int position = helper.getAdapterPosition();
        if (position == 0) {
            Glide.with(mContext)
                    .load(R.mipmap.home_maintain_img)
                    .into((ImageView) helper.getView(R.id.img_home_title_image));
        } else if (position == 1) {
            Glide.with(mContext)
                    .load(R.mipmap.home_wash_img)
                    .into((ImageView) helper.getView(R.id.img_home_title_image));
        } else {
            Glide.with(mContext)
                    .load(R.mipmap.home_refit_img)
                    .into((ImageView) helper.getView(R.id.img_home_title_image));
        }
        helper.setText(R.id.tv_home_title_one, item.getTitleA());
        helper.setText(R.id.tv_home_title_two, item.getTitleB());
        helper.setText(R.id.tv_home_title_three, item.getTitleC());
        helper.setText(R.id.tv_home_content_one, item.getCotentA());
        helper.setText(R.id.tv_home_content_two, item.getCotentB());
        helper.setText(R.id.tv_home_content_three, item.getCotentC());
        Common.ShowImage(mContext, item.getImageA(), (ImageView) helper.getView(R.id.img_home_img_one));
        Common.ShowImage(mContext, item.getImageB(), (ImageView) helper.getView(R.id.img_home_img_two));
        Common.ShowImage(mContext, item.getImageC(), (ImageView) helper.getView(R.id.img_home_img_three));
    }
}
