package com.example.tuionf.owspacelearn.di.component;

import com.example.tuionf.owspacelearn.di.module.SplashModule;
import com.example.tuionf.owspacelearn.di.scopes.UserScope;
import com.example.tuionf.owspacelearn.view.activity.SplashActivity;

import dagger.Component;

/**
 * @author tuionf
 * @date 2017/12/25
 * @email 596019286@qq.com
 * @explain
 */
@UserScope
@Component (modules = SplashModule.class,dependencies = NetComponent.class)
public interface SplashComponent {
    void inject(SplashActivity splashActivity);
}
