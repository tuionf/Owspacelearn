package com.example.tuionf.owspacelearn.view.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tuionf.owspacelearn.MyApplication;
import com.example.tuionf.owspacelearn.R;
import com.example.tuionf.owspacelearn.di.component.DaggerDetailComponent;
import com.example.tuionf.owspacelearn.di.module.DetailModule;
import com.example.tuionf.owspacelearn.model.entity.DetailEntity;
import com.example.tuionf.owspacelearn.model.entity.Item;
import com.example.tuionf.owspacelearn.presenter.DetailContract;
import com.example.tuionf.owspacelearn.presenter.DetailPresenter;
import com.example.tuionf.owspacelearn.util.AnalysisHTML;
import com.example.tuionf.owspacelearn.util.AppUtil;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailActivity extends BaseActivity implements ObservableScrollViewCallbacks,DetailContract.View{

    @BindView(R.id.scrollView)
    ObservableScrollView scrollView;
    @BindView(R.id.image) ImageView image;
    @BindView(R.id.news_top) LinearLayout newsTop;
    @BindView(R.id.news_top_img_under_line) View newsTopImgUnderLine;
    @BindView(R.id.news_top_type) TextView newsTopType;
    @BindView(R.id.news_top_date) TextView newsTopDate;
    @BindView(R.id.news_top_title) TextView newsTopTitle;
    @BindView(R.id.news_top_author) TextView newsTopAuthor;
    @BindView(R.id.news_top_lead) TextView newsTopLead;
    @BindView(R.id.news_top_lead_line) View newsTopLeadLine;
    @BindView(R.id.news_parse_web) LinearLayout newsParseWeb;
    @BindView(R.id.webView) WebView webView;
    @BindView(R.id.toolBar) Toolbar toolBar;
    @BindView(R.id.favorite) ImageView favorite;
    @BindView(R.id.write) ImageView write;
    @BindView(R.id.share) ImageView share;
    private int mParallaxImageHeight;
    @Inject
    DetailPresenter detailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        DaggerDetailComponent.builder()
                .netComponent(MyApplication.getInstance().getNetComponent())
                .detailModule(new DetailModule(this))
                .build()
                .inject(this);

        Logger.e("detailPresenter--"+detailPresenter.toString());
        initView();

        getIntentData();
    }

    private void getIntentData() {
        Item item = getIntent().getParcelableExtra("item");
        if (item != null) {
            //详情页的最上面的图片
            Glide.with(this).load(item.getThumbnail()).centerCrop().into(image);
            newsTopLeadLine.setVisibility(View.VISIBLE);
            newsTopImgUnderLine.setVisibility(View.VISIBLE);
            newsTopType.setText("文 字");
            newsTopDate.setText(item.getUpdate_time());
            newsTopTitle.setText(item.getTitle());
            newsTopAuthor.setText(item.getAuthor());
            newsTopLead.setText(item.getLead());
            newsTopLead.setLineSpacing(1.5f,1.8f);
            detailPresenter.getDetail(item.getId());
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        // 给左上角图标的左边加上一个返回的图标
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolBar.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.primary)));
        scrollView.setScrollViewCallbacks(this);
        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);
    }

    private void initWebViewSetting() {
        WebSettings localWebSettings = this.webView.getSettings();
        localWebSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        localWebSettings.setJavaScriptEnabled(true);
        localWebSettings.setSupportZoom(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        localWebSettings.setUseWideViewPort(true);
        localWebSettings.setLoadWithOverviewMode(true);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void updateListUI(DetailEntity detailEntity) {
        if (detailEntity.getParseXML() == 1) {
            int i = detailEntity.getLead().trim().length();
            AnalysisHTML analysisHTML = new AnalysisHTML();
            analysisHTML.loadHtml(this, detailEntity.getContent(), analysisHTML.HTML_STRING, newsParseWeb, i);
            newsTopType.setText("文 字");
        }

//        else {
//            initWebViewSetting();
//            newsParseWeb.setVisibility(View.GONE);
//            image.setVisibility(View.GONE);
//            webView.setVisibility(View.VISIBLE);
//            newsTop.setVisibility(View.GONE);
//            webView.loadUrl(addParams2WezeitUrl(detailEntity.getHtml5(), false));
//        }

    }

    @Override
    public void showOnFailure() {

    }

    @Override
    public void onScrollChanged(int i, boolean b, boolean b1) {
        int baseColor = getResources().getColor(R.color.primary);
        float alpha = Math.min(1, (float) i / mParallaxImageHeight);
        toolBar.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
//        ViewHelper.setTranslationY(image, i / 2);
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    public String addParams2WezeitUrl(String url, boolean paramBoolean) {
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append(url);
        localStringBuffer.append("?client=android");
        localStringBuffer.append("&device_id=" + AppUtil.getDeviceId(this));
        localStringBuffer.append("&version=" + "1.3.0");
        if (paramBoolean)
            localStringBuffer.append("&show_video=0");
        else {
            localStringBuffer.append("&show_video=1");
        }
        return localStringBuffer.toString();
    }

}
