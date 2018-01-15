package com.example.tuionf.owspacelearn.view.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tuionf.owspacelearn.MyApplication;
import com.example.tuionf.owspacelearn.R;
import com.example.tuionf.owspacelearn.di.component.DaggerArtComponent;
import com.example.tuionf.owspacelearn.di.module.ArtModule;
import com.example.tuionf.owspacelearn.model.entity.Item;
import com.example.tuionf.owspacelearn.presenter.ArticalContract;
import com.example.tuionf.owspacelearn.presenter.ArticalPresenter;
import com.example.tuionf.owspacelearn.util.AppUtil;
import com.example.tuionf.owspacelearn.view.adapter.ArtRecycleViewAdapter;
import com.example.tuionf.owspacelearn.view.widget.CustomPtrHeader;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class ArtActivity extends BaseActivity implements ArticalContract.View{

    @BindView(R.id.toolBar)
    Toolbar toolbar;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.ptrFrameLayout)
    PtrClassicFrameLayout mPtrFrame;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    private int page = 1;
    private int mode = 1;
    private boolean isRefresh;
    private boolean hasMore=true;
    private String deviceId;
    private int lastVisibleItem;
    private ArtRecycleViewAdapter recycleViewAdapter;

    @Inject
    ArticalPresenter presenter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art);
        ButterKnife.bind(this);
        mode = getIntent().getIntExtra("mode", 1);
        DaggerArtComponent.builder()
                .netComponent(MyApplication.getInstance().getNetComponent())
                .artModule(new ArtModule(this))
                .build()
                .inject1(this);
        Logger.e(presenter.toString()+"======");
        initView();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        String tt = getIntent().getStringExtra("title");
        title.setText(tt);
        deviceId = AppUtil.getDeviceId(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page=1;
                isRefresh=true;
                hasMore = true;
                loadData(page, mode, "0", deviceId, "0");
            }
        });
        mPtrFrame.setOffsetToRefresh(200);
        mPtrFrame.autoRefresh(true);
        CustomPtrHeader header = new CustomPtrHeader(this,mode);
        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);

        recycleViewAdapter = new ArtRecycleViewAdapter(this);
        recycleView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recycleView.addItemDecoration(new com.example.tuionf.owspacelearn.view.widget.DividerItemDecoration(this));
        recycleView.setAdapter(recycleViewAdapter);
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !isRefresh && hasMore && (lastVisibleItem+1  == recycleViewAdapter.getItemCount())){
                    loadData(page, mode, recycleViewAdapter.getLastItemId(),deviceId, recycleViewAdapter.getLastItemCreateTime());
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
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
        hasMore = false;
        if (!isRefresh){
            //显示没有更多
            recycleViewAdapter.setHasMore(false);
            recycleViewAdapter.notifyItemChanged(recycleViewAdapter.getItemCount()-1);
        }
    }

    @Override
    public void showNoMore() {

    }

    @Override
    public void updateListUI(List<Item> itemList) {
        mPtrFrame.refreshComplete();
        page++;
        if (isRefresh) {
            recycleViewAdapter.setHasMore(true);
            recycleViewAdapter.setError(false);
            isRefresh = false;
            recycleViewAdapter.replaceAllData(itemList);
        } else {
            recycleViewAdapter.setArtList(itemList);
        }
    }

    @Override
    public void showOnFailure() {
        if (!isRefresh){
            //显示失败
            recycleViewAdapter.setError(true);
            recycleViewAdapter.notifyItemChanged(recycleViewAdapter.getItemCount()-1);
        }else{
            Toast.makeText(this,"~~~~(>_<)~~~~刷新失败",Toast.LENGTH_SHORT).show();
        }
    }

    private void loadData(int page, int mode, String pageId, String deviceId, String createTime) {
        presenter.getListByPage(page, mode, pageId, deviceId, createTime);
    }
}
