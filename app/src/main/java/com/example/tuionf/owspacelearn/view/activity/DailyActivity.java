package com.example.tuionf.owspacelearn.view.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tuionf.owspacelearn.MyApplication;
import com.example.tuionf.owspacelearn.R;
import com.example.tuionf.owspacelearn.di.component.DaggerDailyComponent;
import com.example.tuionf.owspacelearn.di.module.DailyModule;
import com.example.tuionf.owspacelearn.model.entity.Item;
import com.example.tuionf.owspacelearn.presenter.ArticalContract;
import com.example.tuionf.owspacelearn.presenter.ArticalPresenter;
import com.example.tuionf.owspacelearn.util.AppUtil;
import com.example.tuionf.owspacelearn.view.adapter.DailyViewPagerAdapter;
import com.example.tuionf.owspacelearn.view.widget.VerticalViewPager;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DailyActivity extends BaseActivity implements ArticalContract.View{

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.view_pager)
    VerticalViewPager viewPager;
    private String deviceId;
    @Inject
    ArticalPresenter presenter;
    private static final int MODE = 4;
    private int page = 1;
    private DailyViewPagerAdapter pagerAdapter;
    private boolean isLoading=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
        ButterKnife.bind(this);
        initPresenter();
        initView();
        deviceId = AppUtil.getDeviceId(this);
    }

    private void initPresenter() {
        DaggerDailyComponent.builder()
                .dailyModule(new DailyModule(this))
                .netComponent(MyApplication.getInstance().getNetComponent())
                .build()
                .inject(this);
        presenter.getListByPage(page,MODE,"0", deviceId,"0");
    }

    private void initView() {
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        pagerAdapter = new DailyViewPagerAdapter(getSupportFragmentManager(),this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (pagerAdapter.getCount() <= position +2 && !isLoading){
                    presenter.getListByPage(page, 0, pagerAdapter.getLastItemId(),deviceId,pagerAdapter.getLastItemCreateTime());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showNoData() {
    }

    @Override
    public void showNoMore() {
        Toast.makeText(this,"没有更多数据了",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateListUI(List<Item> itemList) {
        isLoading = false;
        pagerAdapter.setArtList(itemList);
        page++;
    }

    @Override
    public void showOnFailure() {
        if (pagerAdapter.getCount()==0){
            showNoData();
        }else{
            Toast.makeText(this,"加载数据失败，请检查您的网络",Toast.LENGTH_SHORT).show();
        }
    }
}
