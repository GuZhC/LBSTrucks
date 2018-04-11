package com.rongyuan.mingyida.module.cart;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rongyuan.mingyida.R;
import com.rongyuan.mingyida.base.BaseFragment;
import com.rongyuan.mingyida.model.CartGoodsModel;
import com.rongyuan.mingyida.model.RecommendGodds;
import com.rongyuan.mingyida.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by guZhongC on 2018/1/5.
 * describe:
 */

public class CartFragment extends BaseFragment implements CartContract.ICartNiew {
    @BindView(R.id.nearby_toolbar_editer)
    ImageView nearbyToolbarEditer;
    @BindView(R.id.nearby_mygoods_cart)
    RecyclerView nearbyMygoodsCart;
    @BindView(R.id.cb_cart_betoom)
    CheckBox cbNearby;
    @BindView(R.id.tv_nearby_allmoney)
    TextView tvNearbyAllmoney;
    @BindView(R.id.tv_nearby_allnum)
    TextView tvNearbyAllnum;
    Unbinder unbinder;

    public static final int CHOSEALL = -1;
    public static final int NOCHOSEALL = -2;
    boolean isDelete = false;
    CartPresenter mCartPresenter;
    CartAdapter mCartAdapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_cart;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mCartPresenter = new CartPresenter(this);
        mCartPresenter.subscribe();
    }


    @Override
    public void ShowRecyclerView(List<CartGoodsModel> data, List<RecommendGodds> recommendGoddsdatas) {
        nearbyMygoodsCart.setLayoutManager(new LinearLayoutManager(getContext()));
          mCartAdapter = new CartAdapter(data, recommendGoddsdatas, getContext());
        mCartAdapter.setNotDoAnimationCount(3);
        nearbyMygoodsCart.setAdapter(mCartAdapter);
        mCartAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.cb_item_cart:
                        mCartPresenter.choseGoods(position);
                        mCartAdapter.notifyDataSetChanged();
                        cbNearby.setChecked(false);
                        setMoneyAddNum(isDelete);
                        break;
                    case R.id.tv_nearby_item_gooddsub:
                        mCartPresenter.subGoosdNum(position);
                        mCartAdapter.notifyDataSetChanged();
                        setMoneyAddNum(isDelete);
                        break;
                    case R.id.tv_nearby_item_goodsadd:
                        mCartPresenter.addGoodsNum(position);
                        mCartAdapter.notifyDataSetChanged();
                        setMoneyAddNum(isDelete);
                        break;
                }
            }
        });

    }

    @OnClick({R.id.nearby_toolbar_editer, R.id.cb_cart_betoom, R.id.tv_nearby_allnum})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.nearby_toolbar_editer:
                if (!isDelete){
                    nearbyToolbarEditer.setImageResource(R.mipmap.cart_editer_ok);
                    tvNearbyAllnum.setTextColor (getResources().getColor( R.color.colorPrimary));
                    tvNearbyAllnum.setBackgroundColor(getResources().getColor( R.color.white));
                    tvNearbyAllmoney.setVisibility(View.GONE);
                    isDelete = true;
                }else {
                    nearbyToolbarEditer.setImageResource(R.mipmap.cart_editer);
                    tvNearbyAllnum.setTextColor (getResources().getColor( R.color.white));
                    tvNearbyAllnum.setBackgroundColor(getResources().getColor( R.color.colorPrimary));
                    tvNearbyAllmoney.setVisibility(View.VISIBLE);
                    isDelete = false;
                }
                setMoneyAddNum(isDelete);
                break;
            case R.id.cb_cart_betoom:
                if (cbNearby.isChecked()){
                    mCartPresenter.choseGoods(CHOSEALL);
                    mCartAdapter.notifyDataSetChanged();
                    cbNearby.setChecked(true);
                }else {
                    mCartPresenter.choseGoods(NOCHOSEALL);
                    mCartAdapter.notifyDataSetChanged();
                    cbNearby.setChecked(false);
                }
                setMoneyAddNum(isDelete);
                break;
            case R.id.tv_nearby_allnum:
                if (isDelete){
                    ToastUtils.showInfo(getContext(),"删除");
                }else {
                    ToastUtils.showInfo(getContext(),"结算");
                }
                break;
        }
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
        if (mCartPresenter != null) {
            mCartPresenter.unSubscribe();
        }
        unbinder.unbind();
    }

    public void setMoneyAddNum( boolean isDelete){
        tvNearbyAllmoney.setText("¥ "+mCartPresenter.getChoseMoney());
        if (isDelete){
            tvNearbyAllnum.setText("删除（"+mCartPresenter.getChoseNum()+"）");
        }else  {
            tvNearbyAllnum.setText("去结算（ "+mCartPresenter.getChoseNum()+"）");
        }
    }
}
