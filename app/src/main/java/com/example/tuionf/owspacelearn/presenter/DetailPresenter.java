package com.example.tuionf.owspacelearn.presenter;

import com.example.tuionf.owspacelearn.model.api.ApiService;
import com.example.tuionf.owspacelearn.model.entity.DetailEntity;
import com.example.tuionf.owspacelearn.model.entity.Result;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author tuionf
 * @date 2017/12/27
 * @email 596019286@qq.com
 * @explain
 */

public class DetailPresenter implements DetailContract.Present{

    private DetailContract.View view;
    private ApiService apiService;

    @Inject
    public DetailPresenter(DetailContract.View view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }


    @Override
    public void getDetail(String itemId) {
        apiService.getDetail("api", "getPost",itemId,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result.Data<DetailEntity>>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("-----详情页数据请求完毕------");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("-----详情页数据请求报错------"+e.getMessage());
                    }

                    @Override
                    public void onNext(Result.Data<DetailEntity> detailEntityData) {
                        view.updateListUI(detailEntityData.getDatas());
                    }
                });
    }
}
