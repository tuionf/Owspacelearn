package com.example.tuionf.owspacelearn.di.component;

import com.example.tuionf.owspacelearn.di.module.DailyModule;
import com.example.tuionf.owspacelearn.di.scopes.UserScope;
import com.example.tuionf.owspacelearn.view.activity.DailyActivity;

import dagger.Component;

/**
 * @author tuionf
 * @date 2018/1/5
 * @email 596019286@qq.com
 * @explain
 */

@UserScope
@Component ( modules = DailyModule.class,dependencies = NetComponent.class)
public interface DailyComponent {
    void inject(DailyActivity dailyActivity);
}
