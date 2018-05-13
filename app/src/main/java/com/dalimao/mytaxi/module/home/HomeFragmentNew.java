package com.dalimao.mytaxi.module.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dalimao.mytaxi.R;
import com.dalimao.mytaxi.base.BaseFragment;
import com.dalimao.mytaxi.model.NeaberShopModel;
import com.dalimao.mytaxi.module.store.StoreDetailsActivity;
import com.dalimao.mytaxi.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by guZhongC on 2018/5/13.
 * describe:
 */

public class HomeFragmentNew extends BaseFragment {
    @BindView(R.id.home_recycler_order)
    RecyclerView homeRecyclerOrder;
    Unbinder unbinder;
    OrderAdapter orderAdapter;
    private List<NeaberShopModel> itemdata;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_hone_new;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        itemdata = new ArrayList<>();
        for (int i = 0;i<3;i++){
            NeaberShopModel data = new NeaberShopModel();
            itemdata.add(data);
        }
        orderAdapter = new OrderAdapter(itemdata);
        orderAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        homeRecyclerOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        homeRecyclerOrder.setAdapter(orderAdapter);
        orderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
