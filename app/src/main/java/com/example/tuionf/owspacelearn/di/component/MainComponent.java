package com.example.tuionf.owspacelearn.di.component;

import com.example.tuionf.owspacelearn.MainActivity;
import com.example.tuionf.owspacelearn.di.module.MainModule;
import com.example.tuionf.owspacelearn.di.scopes.UserScope;

import dagger.Component;

/**
 * @author tuionf
 * @date 2017/12/26
 * @email 596019286@qq.com
 * @explain
 */

@UserScope
@Component( modules = MainModule.class,dependencies = NetComponent.class)
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
