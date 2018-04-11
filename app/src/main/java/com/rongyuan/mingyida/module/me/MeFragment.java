package com.rongyuan.mingyida.module.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rongyuan.mingyida.R;
import com.rongyuan.mingyida.base.BaseFragment;
import com.rongyuan.mingyida.module.login.LoginActivity;
import com.rongyuan.mingyida.module.me.cartinfo.MyCartInfoActivity;
import com.rongyuan.mingyida.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by guZhongC on 2018/1/5.
 * describe:
 */

public class MeFragment extends BaseFragment {
    @BindView(R.id.me_toolbar_set)
    ImageView meToolbarSet;
    @BindView(R.id.me_head_image)
    CircleImageView meHeadImage;
    @BindView(R.id.me_user_name)
    TextView meUserName;
    @BindView(R.id.me_cart_info)
    TextView meCartInfo;
    @BindView(R.id.me_me_code)
    TextView meMeCode;
    @BindView(R.id.tv_me_lookall_order)
    TextView tvMeLookallOrder;
    @BindView(R.id.me_nopay_order)
    TextView meNopayOrder;
    @BindView(R.id.me_noserve_order)
    TextView meNoserveOrder;
    @BindView(R.id.me_complete_order)
    TextView meCompleteOrder;
    @BindView(R.id.me_backmoney_order)
    TextView meBackmoneyOrder;
    @BindView(R.id.l_me_location)
    LinearLayout lMeLocation;
    @BindView(R.id.l_me_safety)
    LinearLayout lMeSafety;
    @BindView(R.id.l_me_news)
    LinearLayout lMeNews;
    @BindView(R.id.l_me_aboutme)
    LinearLayout lMeAboutme;
    Unbinder unbinder;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout. fragment_me;
    }

    @Override
    protected void init(Bundle savedInstanc) {
        Glide.with(getContext())
                .load("https://ws1.sinaimg.cn/large/610dc034ly1fiz4ar9pq8j20u010xtbk.jpg")
//                .placeholder(R.drawable.lodingview)
                .into(meHeadImage);
        meUserName.setText("大脸狗");
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

    @OnClick({R.id.me_cart_info, R.id.me_me_code, R.id.tv_me_lookall_order, R.id.me_nopay_order,
            R.id.me_noserve_order, R.id.me_complete_order, R.id.me_backmoney_order, R.id.l_me_location,
            R.id.l_me_safety, R.id.l_me_news, R.id.l_me_aboutme,R.id.me_toolbar_set})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.me_toolbar_set:
                startActivity(new Intent(getContext(),LoginActivity.class));
                break;
                case R.id.me_cart_info:
               startActivity(new Intent(getContext(),MyCartInfoActivity.class));
                break;
            case R.id.me_me_code:
                ToastUtils.showInfo(getContext(), "click");
                break;
            case R.id.tv_me_lookall_order:
                ToastUtils.showInfo(getContext(), "click");
                break;
            case R.id.me_nopay_order:
                ToastUtils.showInfo(getContext(), "click");
                break;
            case R.id.me_noserve_order:
                ToastUtils.showInfo(getContext(), "click");
                break;
            case R.id.me_complete_order:
                ToastUtils.showInfo(getContext(), "click");
                break;
            case R.id.me_backmoney_order:
                ToastUtils.showInfo(getContext(), "click");
                break;
            case R.id.l_me_location:
                ToastUtils.showInfo(getContext(), "click");
                break;
            case R.id.l_me_safety:
                ToastUtils.showInfo(getContext(), "click");
                break;
            case R.id.l_me_news:
                ToastUtils.showInfo(getContext(), "click");
                break;
            case R.id.l_me_aboutme:
                ToastUtils.showInfo(getContext(), "click");
                break;
        }
    }
}
