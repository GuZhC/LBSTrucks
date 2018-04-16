package com.dalimao.mytaxi.module.mains;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.dalimao.mytaxi.module.me.MeFragment;
import com.dalimao.mytaxi.module.nearby.NearbyFragment;
import com.dalimao.mytaxi.R;
import com.dalimao.mytaxi.module.home.HomeFragment;
import com.dalimao.mytaxi.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    BottomNavigationBar mBottomNavigationBar;
    ViewPager mViewpage;
    @BindView(R.id.main)
    LinearLayout main;
    // 保存用户按返回键的时间
    private long mExitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mBottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        mViewpage = findViewById(R.id.viewPager);
        setmBottomNavigationBar();
        setupViewPager(mViewpage);
    }

    private void setmBottomNavigationBar() {
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigationBar //值得一提，模式跟背景的设置都要在添加tab前面，不然不会有效果。
                .setActiveColor(R.color.colorPrimary)//选中颜色 图标和文字
//                .setInActiveColor("#8e8e8e")//默认未选择颜色
                .setBarBackgroundColor(R.color.white);//默认背景色
        mBottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.main_icon_one, R.string.tab_one).setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.main_icon_two, R.string.tab_two).setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.main_icon_four, R.string.tab_four).setActiveColorResource(R.color.colorPrimary))
                .setFirstSelectedPosition(0)//设置默认选择的按钮
                .initialise();//所有的设置需在调用该方法前完成
        mViewpage.setCurrentItem(0);
        mBottomNavigationBar //设置lab点击事件
                .setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(int position) {
                        mViewpage.setCurrentItem(position);
                    }

                    @Override
                    public void onTabUnselected(int position) {
                    }

                    @Override
                    public void onTabReselected(int position) {
                    }
                });
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mBottomNavigationBar.selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new NearbyFragment());
        adapter.addFragment(new MeFragment());
        viewPager.setAdapter(adapter);
    }

    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtils.showInfo(MainActivity.this, "再次点击退出程序哦~");
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}
