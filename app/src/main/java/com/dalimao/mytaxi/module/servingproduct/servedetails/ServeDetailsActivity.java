package com.dalimao.mytaxi.module.servingproduct.servedetails;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dalimao.mytaxi.R;
import com.dalimao.mytaxi.base.BaseActivity;
import com.dalimao.mytaxi.module.goods.goodsdetails.GoodsDetailTablayoutFragment;
import com.dalimao.mytaxi.ui.WrapContentHeightViewPager;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServeDetailsActivity extends BaseActivity {

    @BindView(R.id.banner_serve_details)
    Banner bannerServeDetails;
    @BindView(R.id.tv_serve_details_title)
    TextView tvServeDetailsTitle;
    @BindView(R.id.tv_serve_details_introduce)
    TextView tvServeDetailsIntroduce;
    @BindView(R.id.tv_serve_details_distance)
    TextView tvServeDetailsDistance;
    @BindView(R.id.tv_serve_details_money)
    TextView tvServeDetailsMoney;
    @BindView(R.id.tv_serve_details_market_money)
    TextView tvServeDetailsMarketMoney;
    @BindView(R.id.ll_serve_details_gostore)
    LinearLayout llServeDetailsGostore;
    @BindView(R.id.tv_serve_details_store_name)
    TextView tvServeDetailsStoreName;
    @BindView(R.id.tv_serve_details_store_phone)
    TextView tvServeDetailsStorePhone;
    @BindView(R.id.tv_serve_details_store_time)
    TextView tvServeDetailsStoreTime;
    @BindView(R.id.tv_serve_details_store_location)
    TextView tvServeDetailsStoreLocation;
    @BindView(R.id.tv_serve_details_store_callphone)
    TextView tvServeDetailsStoreCallphone;
    @BindView(R.id.tag_goods_promotion)
    TextView tagGoodsPromotion;
    @BindView(R.id.recycler_serve_details_other)
    RecyclerView recyclerServeDetailsOther;
    @BindView(R.id.tv_serve_details_store_tablayout)
    TabLayout tvServeDetailsStoreTablayout;
    @BindView(R.id.tv_serve_details_store_viewpage)
    WrapContentHeightViewPager tvServeDetailsStoreViewpage;
    @BindView(R.id.tv_serve_details_store_allmoney)
    TextView tvServeDetailsStoreAllmoney;
    @BindView(R.id.tv_serve_details_store_buy)
    TextView tvServeDetailsStoreBuy;
    @BindView(R.id.tag_goods_hint)
    TextView tagGoodsHint;
    @BindView(R.id.tag_goods_hint_content)
    TextView tagGoodsHintContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serve_details);
        ButterKnife.bind(this);
        setBackBtn();
        setTitle("服务详情");
        initTablayout();
    }

    private void initTablayout() {
        final String[] mTitle = new String[]{"商品介绍", "商品评价"};
        tvServeDetailsStoreViewpage.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle[position % mTitle.length];
            }

            @Override
            public Fragment getItem(int i) {
                List fragments = new ArrayList<>();
                fragments.add(GoodsDetailTablayoutFragment.getInstance(GoodsDetailTablayoutFragment.ONE, "1"));
                // Log.e("bodyb",escapedUsername);
                fragments.add(GoodsDetailTablayoutFragment.getInstance(GoodsDetailTablayoutFragment.TWO, "1"));
                return (Fragment) fragments.get(i);
            }

            @Override
            public int getCount() {
                return mTitle.length;
            }
        });
        tvServeDetailsStoreTablayout.setupWithViewPager(tvServeDetailsStoreViewpage);
    }

    @OnClick({R.id.tv_serve_details_store_callphone, R.id.tv_serve_details_store_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_serve_details_store_callphone:
                break;
            case R.id.tv_serve_details_store_buy:
                break;
        }
    }
}
