package com.example.tuionf.owspacelearn.di.component;

import com.example.tuionf.owspacelearn.di.module.NetModule;
import com.example.tuionf.owspacelearn.model.api.ApiService;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @author tuionf
 * @date 2017/12/23
 * @email 596019286@qq.com
 * @explain
 */
@Singleton
@Component (modules = NetModule.class)
public interface NetComponent {
    OkHttpClient getClient();
    Retrofit getRetrofit();
    ApiService getApiService();
}
