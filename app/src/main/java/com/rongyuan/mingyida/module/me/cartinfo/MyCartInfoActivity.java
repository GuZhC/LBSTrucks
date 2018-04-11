package com.rongyuan.mingyida.module.me.cartinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rongyuan.mingyida.R;
import com.rongyuan.mingyida.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyCartInfoActivity extends BaseActivity implements MyCartContract.IMyCartView {

    MyCartPresenter mMyCartPresenter;
    @BindView(R.id.me_head_image)
    CircleImageView meHeadImage;
    @BindView(R.id.my_cart_go_add)
    Button myCartGoadd;
    @BindView(R.id.my_cart_nohave)
    LinearLayout myCartNohave;
    @BindView(R.id.my_cart_number)
    TextView myCartNumber;
    @BindView(R.id.my_cart_number_chejia)
    TextView myCartNumberChejia;
    @BindView(R.id.my_cart_number_fadonji)
    TextView myCartNumberFadonji;
    @BindView(R.id.my_cart_number_daihao)
    TextView myCartNumberDaihao;
    @BindView(R.id.my_cart_function)
    TextView myCartFunction;
    @BindView(R.id.my_cart_number_brand)
    TextView myCartNumberBrand;
    @BindView(R.id.my_cart_have)
    NestedScrollView myCartHave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart_info);
        ButterKnife.bind(this);
        setBackBtn();
        setTitle("爱车信息");
        mMyCartPresenter = new MyCartPresenter(this);

        mMyCartPresenter.subscribe();
    }

    @Override
    public void showNoHaveCart() {

    }

    @Override
    public void showHaveCart() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMyCartPresenter != null) {
            mMyCartPresenter.unSubscribe();
        }
    }

    @OnClick({R.id.me_head_image, R.id.my_cart_go_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.me_head_image:
                startActivity(new Intent(MyCartInfoActivity.this, AddMyCart.class));
                break;
            case R.id.my_cart_go_add:
                myCartNohave.setVisibility(View.GONE);
                myCartHave.setVisibility(View.VISIBLE);
                break;
        }
    }
}
