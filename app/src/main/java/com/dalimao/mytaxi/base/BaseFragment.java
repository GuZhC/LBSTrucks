package com.dalimao.mytaxi.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;



public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;
    private static final String TAG = "aaa";

   protected View mRootView;
    /**
     * 获取布局ID
     */
    protected abstract int getContentViewLayoutID();

    /**
     * 界面初始化
     */
    protected abstract void init( Bundle savedInstanceState);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //ViewPage + Fragment 防止Fragment 重复加载问题
        if (mRootView == null) {
            if (getContentViewLayoutID() != 0) {
                mRootView = inflater.inflate(getContentViewLayoutID(), container, false);
                unbinder = ButterKnife.bind(this, mRootView);
                init( savedInstanceState);//初始化要放这儿
            } else {
                return super.onCreateView(inflater, container, savedInstanceState);
            }
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        unbinder = ButterKnife.bind(this, mRootView);
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
