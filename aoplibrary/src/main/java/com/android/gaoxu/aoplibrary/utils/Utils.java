package com.android.gaoxu.aoplibrary.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * <pre>
 *     author : gaoxu
 *     e-mail : 511527070@qq.com
 *     time   : 2018/05/30
 *     desc   : 工具类
 *     version: 1.0
 * </pre>
 */
public class Utils {
    /**
     * 打开网络设置界面
     */
    public static void openWirelessSettings() {
        Utils.getApp().startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    /**
     * 获取活动网络信息
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}</p>
     *
     * @return NetworkInfo
     */
    @SuppressLint("MissingPermission")
    private static NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager manager =
                (ConnectivityManager) Utils.getApp().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) return null;
        return manager.getActiveNetworkInfo();
    }

    private static Context getApp() {
        return MyAOPHelper.getInstance().getApplication();
    }

    /**
     * 判断网络是否连接
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}</p>
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isConnected() {
        NetworkInfo info = getActiveNetworkInfo();
        return info != null && info.isConnected();
    }
}
