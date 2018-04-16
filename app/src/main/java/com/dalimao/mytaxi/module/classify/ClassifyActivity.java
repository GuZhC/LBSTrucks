package com.dalimao.mytaxi.module.classify;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dalimao.mytaxi.base.BaseActivity;
import com.dalimao.mytaxi.R;
import com.dalimao.mytaxi.model.ClassifyModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ClassifyActivity extends BaseActivity implements ClassifyContract.IClassifyView {


    @BindView(R.id.iv_classify_back)
    ImageView ivClassifyBack;
    @BindView(R.id.tv_classify_search)
    TextView tvClassifySearch;
    @BindView(R.id.recycler_classify_lift)
    RecyclerView recyclerClassifyLift;
    @BindView(R.id.recycler_classify_Right)
    RecyclerView recyclerClassifyRight;

    ClassifyLiftAdapter liftAdapter;
    ClassifyRightAdapter rightAdapter;
    LinearLayoutManager manager = new LinearLayoutManager(this);

    private int NowChose = 0;

    ClassifyContract.IClassifyPresenter mClassifyPesenter;
    private boolean move = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify);
        ButterKnife.bind(this);
       hideToolbar();
        mClassifyPesenter = new ClassifyPresenter(this);
        mClassifyPesenter.subscribe();
    }

    @OnClick({R.id.iv_classify_back, R.id.tv_classify_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_classify_back:
                finish();
                break;
            case R.id.tv_classify_search:
                break;
        }
    }

    @Override
    public void setLiftRecycler(final List<ClassifyModel> data) {
        liftAdapter = new ClassifyLiftAdapter(data, this);
        recyclerClassifyLift.setLayoutManager(new LinearLayoutManager(this));
        recyclerClassifyLift.setAdapter(liftAdapter);
        liftAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                data.get(NowChose).setChose(false);
                data.get(position).setChose(true);
                NowChose = position;
                liftAdapter.notifyDataSetChanged();
                manager.scrollToPositionWithOffset(position, 0);
                manager.setStackFromEnd(true);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void setRightRecycler(final List<ClassifyModel> data) {
        rightAdapter = new ClassifyRightAdapter(data);
        recyclerClassifyRight.setLayoutManager(manager);
        recyclerClassifyRight.setAdapter(rightAdapter);
        recyclerClassifyRight.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//                //判断是当前layoutManager是否为LinearLayoutManager
//                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
//                if (layoutManager instanceof LinearLayoutManager) {
//                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
//                    //获取最后一个可见view的位置
////                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
//                    //获取第一个可见view的位置
//                    int firstItemPosition = linearManager.findFirstVisibleItemPosition();
//                    if (firstItemPosition!= NowChose){
//                        data.get(firstItemPosition).setChose(true);
//                        data.get(NowChose).setChose(false);
//                        NowChose = firstItemPosition;
//                        liftAdapter.notifyDataSetChanged();
//                    }
//                }
//            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int distance = 0;
                if (Math.abs(distance - Math.abs(dy)) > 16) {
                    distance = Math.abs(dy);
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    //判断是当前layoutManager是否为LinearLayoutManager
                    // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
//                    if (layoutManager instanceof LinearLayoutManager) {
//                        LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取最后一个可见view的位置
//                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
                    //获取第一个可见view的位置
                    int firstItemPosition = manager.findFirstVisibleItemPosition();
                    if (firstItemPosition != NowChose) {
                        data.get(firstItemPosition).setChose(true);
                        data.get(NowChose).setChose(false);
                        NowChose = firstItemPosition;
                        liftAdapter.notifyDataSetChanged();
                    }
                }
//                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mClassifyPesenter != null) {
            mClassifyPesenter.unSubscribe();
        }
    }
}
