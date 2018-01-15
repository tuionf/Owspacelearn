package com.example.tuionf.owspacelearn.di.module;

import com.example.tuionf.owspacelearn.presenter.ArticalContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author tuionf
 * @date 2018/1/3
 * @email 596019286@qq.com
 * @explain
 */
@Module
public class ArtModule {
    private ArticalContract.View view;

    public ArtModule(ArticalContract.View view) {
        this.view = view;
    }

    @Provides
    public ArticalContract.View provideView(){
        return view;
    }
}
