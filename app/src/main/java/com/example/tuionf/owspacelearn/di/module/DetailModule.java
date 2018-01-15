package com.example.tuionf.owspacelearn.di.module;

import com.example.tuionf.owspacelearn.presenter.DetailContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author tuionf
 * @date 2017/12/28
 * @email 596019286@qq.com
 * @explain
 */
@Module
public class DetailModule {

    private DetailContract.View view;

    public DetailModule(DetailContract.View view) {
        this.view = view;
    }

    @Provides
    public DetailContract.View provideDetailView(){
        return view;
    }
}
