package com.example.tuionf.owspacelearn.model.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import org.joda.time.DateTime;

/**
 * @author tuionf
 * @date 2017/12/23
 * @email 596019286@qq.com
 * @explain
 */

public final class EntityUtils {
    private EntityUtils() {}

    public static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(DateTime.class,new DateTypeAdapter())
            .create();
}
