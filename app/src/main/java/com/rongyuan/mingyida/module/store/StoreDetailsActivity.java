package com.rongyuan.mingyida.module.store;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.rongyuan.mingyida.R;
import com.rongyuan.mingyida.base.BaseActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StoreDetailsActivity extends BaseActivity {

    @BindView(R.id.banner_store_details)
    Banner bannerStoreDetails;
    @BindView(R.id.tv_store_details_salesvolume)
    TextView tvStoreDetailsSalesvolume;
    @BindView(R.id.tv_store_details_evaluatenumber)
    TextView tvStoreDetailsEvaluatenumber;
    @BindView(R.id.tv_store_details_phone)
    TextView tvStoreDetailsPhone;
    @BindView(R.id.tv_store_details_location)
    TextView tvStoreDetailsLocation;
    @BindView(R.id.tv_store_details_distance)
    TextView tvStoreDetailsDistance;
    @BindView(R.id.tv_store_details_serve)
    RecyclerView tvStoreDetailsServe;
    @BindView(R.id.tv_store_details_serve_more)
    TextView tvStoreDetailsServeMore;
    @BindView(R.id.tv_store_details_evaluate)
    RecyclerView tvStoreDetailsEvaluate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_details);
        ButterKnife.bind(this);
        setBackBtn();
        setTitle("店铺详情");
        bannerStoreDetails.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
    }

    @OnClick({R.id.tv_store_details_distance, R.id.tv_store_details_serve_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_store_details_distance:
                break;
            case R.id.tv_store_details_serve_more:
                break;
        }
    }
}
