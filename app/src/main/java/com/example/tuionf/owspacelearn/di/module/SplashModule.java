package com.example.tuionf.owspacelearn.di.module;

import com.example.tuionf.owspacelearn.presenter.SplashContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author tuionf
 * @date 2017/12/25
 * @email 596019286@qq.com
 * @explain
 */

@Module
public class SplashModule {

    private SplashContract.View view;

    public SplashModule(SplashContract.View view) {
        this.view = view;
    }

    @Provides
    public SplashContract.View provideView(){
        return view;
    }
}
