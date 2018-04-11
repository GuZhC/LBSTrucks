package com.rongyuan.mingyida.module.goods.goodsdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.rongyuan.mingyida.R;
import com.rongyuan.mingyida.model.NeaberShopModel;
import com.rongyuan.mingyida.module.home.NearbyAdapter;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by guZhongC on 2017/11/13.
 * describe:商品详情页面
 */

public class GoodsDetailTablayoutFragment extends Fragment {
    public static String ONE = "INTRODUCE"; //商品介绍
    public static String TWO = "EVALUATE"; //评价
    Unbinder unbinder;

    @BindView(R.id.recycler_tab_goods)
    RecyclerView recyclerTabGoods;
    private String type;
    private String goodsid;
    List<NeaberShopModel> data;

    public static GoodsDetailTablayoutFragment getInstance(String type, String goodsid) {
        GoodsDetailTablayoutFragment fragment = new GoodsDetailTablayoutFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Type", type);
        bundle.putString("GoodsId", goodsid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        type = getArguments().getString("Type");
        goodsid = getArguments().getString("GoodsId");
        View view = inflater.inflate(R.layout.fragment_goods_details_tablayout, container, false);
        unbinder = ButterKnife.bind(this, view);
        getRecyclerData();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (type == "INTRODUCE") {
            //todo 更换Adapter
            setRecycler(new NearbyAdapter(data));
        } else {
            //todo 更换Adapter
            setRecycler(new NearbyAdapter(data));
        }
    }

    //todo 测试用
    public void getRecyclerData() {
        data = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            NeaberShopModel neaberShopModel = new NeaberShopModel();
            switch (i) {
                case 0:
                    neaberShopModel.setImageUrl("https://ws1.sinaimg.cn/large/610dc034ly1fis7dvesn6j20u00u0jt4.jpg");
                    break;
                case 1:
                    neaberShopModel.setImageUrl("https://ws1.sinaimg.cn/large/610dc034ly1fir1jbpod5j20ip0newh3.jpg");
                    break;
                case 2:
                    neaberShopModel.setImageUrl("https://ws1.sinaimg.cn/large/610dc034ly1fil82i7zsmj20u011hwja.jpg");
                    break;
                case 3:
                    neaberShopModel.setImageUrl("https://ws1.sinaimg.cn/large/610dc034ly1fik2q1k3noj20u00u07wh.jpg");
                    break;
                case 4:
                    neaberShopModel.setImageUrl("https://ws1.sinaimg.cn/large/610dc034ly1fiiiyfcjdoj20u00u0ju0.jpg");
                    break;
                case 5:
                    neaberShopModel.setImageUrl("https://ws1.sinaimg.cn/large/610dc034ly1fiednrydq8j20u011itfz.jpg");
                    break;
                default:
                    neaberShopModel.setImageUrl("https://ws1.sinaimg.cn/large/610dc034ly1fiiiyfcjdoj20u00u0ju0.jpg");
                    break;
            }
            neaberShopModel.setTitle(i + "98 ? ");
            if (type ==GoodsDetailTablayoutFragment.ONE){
                neaberShopModel.setLocation("介绍。");
            }else {
                neaberShopModel.setLocation("评论。");
            }
            neaberShopModel.setDistance(i + "66 米 ");
            data.add(neaberShopModel);
        }
    }

    private void setRecycler(RecyclerView.Adapter adapter) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerTabGoods.setLayoutManager(mLayoutManager);
        recyclerTabGoods.setAdapter(adapter);
        recyclerTabGoods.setNestedScrollingEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
