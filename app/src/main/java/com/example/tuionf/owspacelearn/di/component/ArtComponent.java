package com.example.tuionf.owspacelearn.di.component;

import com.example.tuionf.owspacelearn.di.module.ArtModule;
import com.example.tuionf.owspacelearn.di.scopes.UserScope;
import com.example.tuionf.owspacelearn.view.activity.ArtActivity;

import dagger.Component;

/**
 * @author tuionf
 * @date 2018/1/4
 * @email 596019286@qq.com
 * @explain
 */
@UserScope
@Component( modules = ArtModule.class,dependencies = NetComponent.class)
public interface ArtComponent {
    void inject1(ArtActivity artActivity);
}
