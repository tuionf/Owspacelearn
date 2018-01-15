package com.example.tuionf.owspacelearn.di.component;

import com.example.tuionf.owspacelearn.di.module.DetailModule;
import com.example.tuionf.owspacelearn.di.scopes.UserScope;
import com.example.tuionf.owspacelearn.view.activity.AudioDetailActivity;
import com.example.tuionf.owspacelearn.view.activity.DetailActivity;
import com.example.tuionf.owspacelearn.view.activity.VideoDetailActivity;

import dagger.Component;

/**
 * @author tuionf
 * @date 2017/12/28
 * @email 596019286@qq.com
 * @explain
 */
@UserScope
@Component (modules = DetailModule.class,dependencies = NetComponent.class)
public interface DetailComponent {
    void inject(DetailActivity detailActivity);
    void inject(VideoDetailActivity videoDetailActivity);
    void inject(AudioDetailActivity audioDetailActivity);
}
