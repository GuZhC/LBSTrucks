package com.dalimao.mytaxi.module.goods;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dalimao.mytaxi.R;
import com.dalimao.mytaxi.base.BaseActivity;
import com.dalimao.mytaxi.model.AllGoodsListModel;
import com.dalimao.mytaxi.module.goods.goodsdetails.GoodsDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AllGoodsActivity extends BaseActivity {

    @BindView(R.id.tv_allgoods_chose_default)
    TextView tvAllgoodsChoseDefault;
    @BindView(R.id.tv_allgoods_chose_sales)
    TextView tvAllgoodsChoseSales;
    @BindView(R.id.tv_allgoods_chose_price)
    TextView tvAllgoodsChosePrice;
    @BindView(R.id.tv_allgoods_chose_goodsreputation)
    TextView tvAllgoodsChoseGoodsreputation;
    @BindView(R.id.recycler_allgoods_list)
    RecyclerView recyclerAllgoodsList;

    AllGoodsDetailAdapter adapter;
    List<AllGoodsListModel> datas;
    private boolean mdefault = false;
    private boolean sales = false;
    private boolean price = false;
    private boolean reputation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_goods);
        ButterKnife.bind(this);
        setBackBtn();
        Intent intent = getIntent();
        setTitle(intent.getStringExtra("toolbarTitle"));
        getData();
        setRecycler();
    }

    private void setRecycler() {
        recyclerAllgoodsList.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new AllGoodsDetailAdapter(datas);
        adapter.openLoadAnimation();
        recyclerAllgoodsList.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(AllGoodsActivity.this, GoodsDetailsActivity.class));
            }
        });
    }

    @OnClick({R.id.tv_allgoods_chose_default, R.id.tv_allgoods_chose_sales, R.id.tv_allgoods_chose_price, R.id.tv_allgoods_chose_goodsreputation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_allgoods_chose_default:
                setTopTextStyle(tvAllgoodsChoseDefault, mdefault);
                if (mdefault) {
                    mdefault = false;
                } else {
                    mdefault = true;
                }
                break;
            case R.id.tv_allgoods_chose_sales:
                setTopTextStyle(tvAllgoodsChoseSales, sales);
                if (sales) {
                    sales = false;
                } else {
                    sales = true;
                }
                break;
            case R.id.tv_allgoods_chose_price:
                setTopTextStyle(tvAllgoodsChosePrice, price);
                if (price) {
                    price = false;
                } else {
                    price = true;
                }
                break;
            case R.id.tv_allgoods_chose_goodsreputation:
                setTopTextStyle(tvAllgoodsChoseGoodsreputation, reputation);
                if (reputation) {
                    reputation = false;
                } else {
                    reputation = true;
                }
                break;
        }
    }

    private void setTopTextStyle(TextView tv, boolean ischose) {
        if (ischose) {
            tv.setTextColor(getResources().getColor(R.color.textBlack));
        } else {
            tv.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    public void getData() {
        datas = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            AllGoodsListModel data = new AllGoodsListModel();
            data.setPrice("882.00");
            data.setMarketPrice("998.00");
            if (i % 2 == 0) {
                data.setImagUrl("https://ws1.sinaimg.cn/large/610dc034ly1fir1jbpod5j20ip0newh3.jpg");
                data.setPromotion(false);
            } else {
                data.setImagUrl("https://ws1.sinaimg.cn/large/610dc034ly1fil82i7zsmj20u011hwja.jpg");
                data.setPromotion(true);
            }
            data.setBuyNumber("334");
            data.setTitle("美滋滋~");
            datas.add(data);
        }
    }
}
