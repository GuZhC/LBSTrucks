package com.dalimao.mytaxi.module.classify;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dalimao.mytaxi.R;
import com.dalimao.mytaxi.model.ClassifyModel;
import com.dalimao.mytaxi.utils.ToastUtils;

import java.util.List;

/**
 * Created by guZhongC on 2018/1/16.
 * describe:
 */

public class ClassifyRightAdapter extends BaseQuickAdapter<ClassifyModel, BaseViewHolder> {

    private ClassifyGoodsAdapter mClassifyGoodsAdapter;

    public ClassifyRightAdapter( @Nullable List<ClassifyModel> data) {
        super(R.layout.item_classify_right_recycler, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassifyModel item) {
        final RecyclerView recyclerView = helper.getView(R.id.recycler_classify_Right);
        recyclerView.setLayoutManager(new GridLayoutManager(helper.itemView.getContext(), 3));
        recyclerView.setHasFixedSize(true);
        mClassifyGoodsAdapter = new ClassifyGoodsAdapter(item.getGoodsData());
        mClassifyGoodsAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showInfo(mContext, "click" + position);
            }
        });
        recyclerView.setNestedScrollingEnabled(false);
        View top = LayoutInflater.from(mContext).inflate(R.layout.classify_goods_top_view, (ViewGroup) recyclerView.getParent(), false);
        TextView tv = top.findViewById(R.id.tv_classify_goods_head);
        tv.setText(item.getClassify().toString());
        mClassifyGoodsAdapter.addHeaderView(top);
        recyclerView.setAdapter(mClassifyGoodsAdapter);
    }
}
