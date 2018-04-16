package com.dalimao.mytaxi.module.home;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dalimao.mytaxi.R;
import com.dalimao.mytaxi.model.ClassifyBeans;

import java.util.List;

/**
 * Created by guZhongC on 2018/1/8.
 * describe:
 */

public class ClassifyAdapter extends BaseQuickAdapter<ClassifyBeans,BaseViewHolder> {
    public ClassifyAdapter(@Nullable List<ClassifyBeans> data) {
        super(R.layout.item_recycler_classify, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassifyBeans item) {
        helper.setText(R.id.home_recycler_classify_title,item.getTitle());
        Glide.with(mContext)
                .load(Integer.valueOf(item.getImgUrl()))
                .into((ImageView) helper.getView(R.id.home_recycler_classify_img));
    }
}
