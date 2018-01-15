package com.example.tuionf.owspacelearn.presenter;

import com.example.tuionf.owspacelearn.MyApplication;
import com.example.tuionf.owspacelearn.model.api.ApiService;
import com.example.tuionf.owspacelearn.model.entity.SplashEntity;
import com.example.tuionf.owspacelearn.util.NetUtil;
import com.example.tuionf.owspacelearn.util.OkHttpImageDownloader;
import com.example.tuionf.owspacelearn.util.TimeUtil;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * @author tuionf
 * @date 2017/12/23
 * @email 596019286@qq.com
 * @explain
 */

public class SplashPresenter implements SplashContract.Presenter{

    private SplashContract.View view;
    private ApiService apiService;

    @Inject
    public SplashPresenter(SplashContract.View view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    @Override
    public void getSplash(String deviceId) {
        Long time = TimeUtil.getCurrentSeconds();
        apiService.getSplash("android","1.3.0",time,deviceId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<SplashEntity>() {
                    @Override
                    public void onCompleted() {
                        Logger.e("load splash onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("load splash Error"+e.toString());
                    }

                    @Override
                    public void onNext(SplashEntity splashEntity) {
                        if (NetUtil.isWifi(MyApplication.getInstance().getApplicationContext())) {
                            if (splashEntity != null){
                                List<String> urlist = splashEntity.getImages();
                                for (String url:urlist) {
                                    OkHttpImageDownloader.download(url);
                                }
                            }
                        }else {
                            Logger.d("不是WIFI环境,不下载图片");
                        }
                    }
                });
    }

}
