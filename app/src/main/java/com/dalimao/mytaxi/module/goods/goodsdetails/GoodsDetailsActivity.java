package com.dalimao.mytaxi.module.goods.goodsdetails;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dalimao.mytaxi.R;
import com.dalimao.mytaxi.base.BaseActivity;
import com.dalimao.mytaxi.ui.WrapContentHeightViewPager;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodsDetailsActivity extends BaseActivity {

    @BindView(R.id.banner_goods_details)
    Banner bannerGoodsDetails;
    @BindView(R.id.tv_goods_details_title)
    TextView tvGoodsDetailsTitle;
    @BindView(R.id.tv_goods_details_introduce)
    TextView tvGoodsDetailsIntroduce;
    @BindView(R.id.tv_goods_details_money)
    TextView tvGoodsDetailsMoney;
    @BindView(R.id.tv_goods_details_market_money)
    TextView tvGoodsDetailsMarketMoney;
    @BindView(R.id.tag_goods_details_specification)
    TagFlowLayout tagGoodsDetailsSpecification;
    @BindView(R.id.tag_goods_promotion)
    TextView tagGoodsPromotion;
    @BindView(R.id.tag_goods_details_promotion)
    TagFlowLayout tagGoodsDetailsPromotion;
    @BindView(R.id.ll_shops_details)
    LinearLayout llShopsDetails;
    @BindView(R.id.tv_goods_details_tablayout)
    TabLayout tvGoodsDetailsTablayout;
    @BindView(R.id.tv_goods_details_viewpage)
    WrapContentHeightViewPager tvGoodsDetailsViewpage;
    @BindView(R.id.tv_goods_details_allmoney)
    TextView tvGoodsDetailsAllmoney;
    @BindView(R.id.tv_goods_details_addcart)
    TextView tvGoodsDetailsAddcart;
    @BindView(R.id.tv_goods_details_buy)
    TextView tvGoodsDetailsBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        ButterKnife.bind(this);
        setBackBtn();
        setTitle("商品详情");
        bannerGoodsDetails.setBannerStyle(BannerConfig.NUM_INDICATOR);
        initTablayout();
    }

    private void initTablayout() {
        final String[] mTitle = new String[]{"商品介绍", "商品评价"};
        tvGoodsDetailsViewpage.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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
        tvGoodsDetailsTablayout.setupWithViewPager(tvGoodsDetailsViewpage);
    }

    @OnClick({R.id.ll_shops_details, R.id.tv_goods_details_addcart, R.id.tv_goods_details_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_shops_details:
                break;
            case R.id.tv_goods_details_addcart:
                break;
            case R.id.tv_goods_details_buy:
                break;
        }
    }
}
