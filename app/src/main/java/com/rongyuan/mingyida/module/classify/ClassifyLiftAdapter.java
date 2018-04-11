package com.rongyuan.mingyida.module.classify;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rongyuan.mingyida.R;
import com.rongyuan.mingyida.model.ClassifyModel;

import java.util.List;

/**
 * Created by guZhongC on 2018/1/16.
 * describe:
 */

public class ClassifyLiftAdapter extends BaseQuickAdapter<ClassifyModel,BaseViewHolder> {

    Context context;
    public ClassifyLiftAdapter( @Nullable List<ClassifyModel> data,Context context) {
        super(R.layout.item_classify_left_recycler , data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassifyModel item) {

        helper.setText(R.id.tv_classify_lift_recycler,item.getClassify());
        if (item.isChose()){
            helper.setBackgroundColor(R.id.tv_classify_lift_recycler,context.getResources().getColor( R.color.lightGray));
            helper.setTextColor(R.id.tv_classify_lift_recycler,context.getResources().getColor( R.color.colorPrimary));
        }else {
            helper.setBackgroundColor(R.id.tv_classify_lift_recycler,context.getResources().getColor( R.color.white));
            helper.setTextColor(R.id.tv_classify_lift_recycler,context.getResources().getColor( R.color.textBlack));
        }
    }
}
