package com.example.tuionf.owspacelearn.presenter;

import com.example.tuionf.owspacelearn.model.api.ApiService;
import com.example.tuionf.owspacelearn.model.entity.Item;
import com.example.tuionf.owspacelearn.model.entity.Result;
import com.example.tuionf.owspacelearn.util.TimeUtil;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author tuionf
 * @date 2018/1/4
 * @email 596019286@qq.com
 * @explain
 */

public class ArticalPresenter implements ArticalContract.Presenter {

    private ArticalContract.View view;
    private ApiService apiService;

    @Inject
    public ArticalPresenter(ArticalContract.View view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    @Override
    public void getListByPage(int page, int model, String pageId, String deviceId, String createTime) {
        apiService.getList("api","getList",page,model,pageId,createTime,"android","1.3.0", TimeUtil.getCurrentSeconds(), deviceId,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Result.Data<List<Item>>>() {
                    @Override
                    public void call(Result.Data<List<Item>> listData) {
                        int size = listData.getDatas().size();
                        if(size>0){
                            view.updateListUI(listData.getDatas());
                        }else {
                            view.showNoMore();
                        }
                    }
                });
    }
}
