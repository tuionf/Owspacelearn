package com.example.tuionf.owspacelearn.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * @author tuionf
 * @date 2017/12/23
 * @email 596019286@qq.com
 * @explain
 */

public class NetUtil {
    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null) {
            return false;
        }

        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }
}
