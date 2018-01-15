package com.example.tuionf.owspacelearn;

import android.app.Application;

import com.example.tuionf.owspacelearn.di.component.DaggerNetComponent;
import com.example.tuionf.owspacelearn.di.component.NetComponent;
import com.example.tuionf.owspacelearn.di.module.NetModule;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * @author tuionf
 * @date 2017/12/22
 * @email 596019286@qq.com
 * @explain
 */

public class MyApplication extends Application {

    private NetComponent netComponent;

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initLogger();
        initNet();
    }

    private void initLogger() {
        // 日志策略
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag("tuionf")
                .build();
        //初始化
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    private void initNet() {
        netComponent = DaggerNetComponent.builder()
                .netModule(new NetModule())
                .build();
    }

    public NetComponent getNetComponent() {
        return netComponent;
    }

    public static MyApplication getInstance(){
        return instance;
    }
}
