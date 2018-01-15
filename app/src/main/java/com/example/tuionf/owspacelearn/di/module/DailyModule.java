package com.example.tuionf.owspacelearn.di.module;

import com.example.tuionf.owspacelearn.presenter.ArticalContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author tuionf
 * @date 2018/1/5
 * @email 596019286@qq.com
 * @explain
 */
@Module
public class DailyModule {

    private ArticalContract.View mView;

    public DailyModule(ArticalContract.View mView) {
        this.mView = mView;
    }

    @Provides
    public ArticalContract.View provideView(){
        return mView;
    }
}
