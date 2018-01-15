package com.example.tuionf.owspacelearn.di.module;

import com.example.tuionf.owspacelearn.presenter.MainContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author tuionf
 * @date 2017/12/26
 * @email 596019286@qq.com
 * @explain
 */

@Module
public class MainModule {
    private MainContract.View view;

    public MainModule(MainContract.View view) {
        this.view = view;
    }

    @Provides
    public MainContract.View provideMainView(){
        return view;
    }
}
