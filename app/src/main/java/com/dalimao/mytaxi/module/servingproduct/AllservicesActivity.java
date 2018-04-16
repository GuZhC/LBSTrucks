package com.dalimao.mytaxi.module.servingproduct;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dalimao.mytaxi.R;
import com.dalimao.mytaxi.base.BaseActivity;
import com.dalimao.mytaxi.model.AllServiceListModel;
import com.dalimao.mytaxi.module.servingproduct.servedetails.ServeDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AllservicesActivity extends BaseActivity {


    @BindView(R.id.tv_allgoods_chose_distance)
    TextView tvAllgoodsChoseDistance;
    @BindView(R.id.tv_allservice_chose_default)
    TextView tvAllserviceChoseDefault;
    @BindView(R.id.tv_allservice_chose_sales)
    TextView tvAllserviceChoseSales;
    @BindView(R.id.tv_allservice_chose_price)
    TextView tvAllserviceChosePrice;
    @BindView(R.id.recycler_allservice_list)
    RecyclerView recyclerAllserviceList;
    private boolean mdefault = false;
    private boolean sales = false;
    private boolean price = false;
    private boolean distance = false;

    List<AllServiceListModel> datas;
    AllServicesRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_services);
        ButterKnife.bind(this);
        setBackBtn();
        Intent intent = getIntent();
        setTitle(intent.getStringExtra("toolbarTitle"));
        getData();
        setRecycler();
    }

    private void setRecycler() {
        recyclerAllserviceList.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new AllServicesRecyclerAdapter(datas);
        adapter.openLoadAnimation();
        recyclerAllserviceList.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(AllservicesActivity.this, ServeDetailsActivity.class));
            }
        });
    }

    @OnClick({R.id.tv_allgoods_chose_distance, R.id.tv_allservice_chose_default, R.id.tv_allservice_chose_sales, R.id.tv_allservice_chose_price})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_allservice_chose_default:
                setTopTextStyle(tvAllserviceChoseDefault, mdefault);
                if (mdefault) {
                    mdefault = false;
                } else {
                    mdefault = true;
                }
                break;
            case R.id.tv_allservice_chose_sales:
                setTopTextStyle(tvAllserviceChoseSales, sales);
                if (sales) {
                    sales = false;
                } else {
                    sales = true;
                }
                break;
            case R.id.tv_allservice_chose_price:
                setTopTextStyle(tvAllserviceChosePrice, price);
                if (price) {
                    price = false;
                } else {
                    price = true;
                }
                break;
            case R.id.tv_allgoods_chose_distance:
                setTopTextStyle(tvAllgoodsChoseDistance, distance);
                if (distance) {
                    distance = false;
                } else {
                    distance = true;
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
            AllServiceListModel data = new AllServiceListModel();
            data.setPrice("882.00");
            data.setMarketPrice("998.00");
            if (i % 2 == 0) {
                data.setImagUrl("https://ws1.sinaimg.cn/large/610dc034ly1fir1jbpod5j20ip0newh3.jpg");
                data.setPromotion(false);
            } else {
                data.setImagUrl("https://ws1.sinaimg.cn/large/610dc034ly1fil82i7zsmj20u011hwja.jpg");
                data.setPromotion(true);
            }
            data.setDistance("334");
            data.setTitle("美滋滋~");
            data.setShopsname("The Japanese club");
            datas.add(data);
        }
    }
}
