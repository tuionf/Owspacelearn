package com.example.tuionf.owspacelearn;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tuionf.owspacelearn.di.component.DaggerMainComponent;
import com.example.tuionf.owspacelearn.di.module.MainModule;
import com.example.tuionf.owspacelearn.model.entity.Event;
import com.example.tuionf.owspacelearn.model.entity.Item;
import com.example.tuionf.owspacelearn.presenter.MainContract;
import com.example.tuionf.owspacelearn.presenter.MainPresenter;
import com.example.tuionf.owspacelearn.util.AppUtil;
import com.example.tuionf.owspacelearn.util.PreferenceUtils;
import com.example.tuionf.owspacelearn.util.RxBus;
import com.example.tuionf.owspacelearn.util.TimeUtil;
import com.example.tuionf.owspacelearn.view.activity.BaseActivity;
import com.example.tuionf.owspacelearn.view.adapter.VerticalPagerAdapter;
import com.example.tuionf.owspacelearn.view.fragment.LeftMenuFragment;
import com.example.tuionf.owspacelearn.view.fragment.RightMenuFragment;
import com.example.tuionf.owspacelearn.view.widget.LunarDialog;
import com.example.tuionf.owspacelearn.view.widget.VerticalViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends BaseActivity implements MainContract.View{

    @BindView(R.id.view_pager)
    VerticalViewPager viewPager;
    private SlidingMenu slidingMenu;
    private LeftMenuFragment leftMenu;
    private RightMenuFragment rightMenu;

    private Subscription subscription;
    private VerticalPagerAdapter pagerAdapter;
    private String deviceId;
    @Inject
    MainPresenter presenter;
    private boolean isLoading = true;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initMenu();
        initPager();
        deviceId = AppUtil.getDeviceId(this);
       String getLunar= PreferenceUtils.getPrefString(this,"getLunar",null);
        if (!TimeUtil.getDate("yyyyMMdd").equals(getLunar)){
            loadRecommend();
        }
        loadData(1, 0, "0", "0");
    }

    private void initMenu() {
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.setFadeEnabled(true);
        slidingMenu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.left_menu);

        leftMenu = new LeftMenuFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.left_menu, leftMenu).commit();

        slidingMenu.setSecondaryMenu(R.layout.right_menu);
        rightMenu = new RightMenuFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.right_menu, rightMenu).commit();

        subscription = RxBus.getInstance().toObservable(Event.class)
                .subscribe(new Action1<Event>() {
                    @Override
                    public void call(Event event) {
                        slidingMenu.showContent();
                    }
                });
    }

    private void initPager() {
        //注入 presenter
        DaggerMainComponent.builder()
                .netComponent(MyApplication.getInstance().getNetComponent())
                .mainModule(new MainModule(this))
                .build()
                .inject(this);

        pagerAdapter = new VerticalPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (pagerAdapter.getCount() <= position + 2 && !isLoading) {
                    if (isLoading){
                        Toast.makeText(MainActivity.this,"正在努力加载...",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Logger.i("page=" + page + ",getLastItemId=" + pagerAdapter.getLastItemId());
                    loadData(page, 0, pagerAdapter.getLastItemId(), pagerAdapter.getLastItemCreateTime());
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void loadData(int page, int mode, String pageId, String createTime) {
        isLoading = true;
        presenter.getListByPage(page, mode, pageId, deviceId, createTime);
    }

    @OnClick({R.id.left_slide,R.id.right_slide})
    public void onClick(View v){
        switch(v.getId()){
            case R.id.left_slide:
                slidingMenu.showMenu();
                break;
            case R.id.right_slide:
                slidingMenu.showSecondaryMenu();
                rightMenu.startAnim();
                break;
            default:
                break;
        }

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
        Toast.makeText(this, "没有更多数据了", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateListUI(List<Item> itemList) {
        isLoading = false;
        pagerAdapter.setDataList(itemList);
        page++;
    }

    @Override
    public void showOnFailure() {
        Toast.makeText(this, "加载数据失败，请检查您的网络", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLunar(String content) {
        Logger.e("showLunar:此处是单向历的展示---"+content);
        PreferenceUtils.setPrefString(this,"getLunar",TimeUtil.getDate("yyyyMMdd"));
        LunarDialog lunarDialog = new LunarDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_lunar,null);
        ImageView imageView = (ImageView)view.findViewById(R.id.image_iv);
        Glide.with(this).load(content).into(imageView);
        lunarDialog.setContentView(view);
        lunarDialog.show();
    }

    private void loadRecommend(){
        presenter.getRecommend(deviceId);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
        super.onDestroy();
    }
}
