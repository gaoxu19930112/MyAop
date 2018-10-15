package com.android.gaoxu.aoplibrary.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 *     author : gaoxu
 *     e-mail : 511527070@qq.com
 *     time   : 2018/05/30
 *     desc   : 切面编程帮助类
 *     version: 1.0
 * </pre>
 */
public class MyAOPHelper {
    private static volatile MyAOPHelper mInstance;
    private List<Method> mMethodList = new ArrayList<>();
    private HashMap<String, Object[]> mMethodArgs = new HashMap<>();
    private HashMap<String, Object> mTargets = new HashMap<>();
    private Application mApplication;

    public static MyAOPHelper getInstance(){
        if (mInstance ==null){
            synchronized (MyAOPHelper.class){
                if (mInstance ==null){
                    mInstance = new MyAOPHelper();
                }
            }
        }
        return mInstance;
    }

    public void init(Application application){
        if (application==null){
            throw new NullPointerException("MyAOP init() Application is null");
        }
        mApplication = application;
        mApplication.registerActivityLifecycleCallbacks(sLifecycleCallbacks);
    }

    public Application getApplication() {
        if (mApplication==null){
            throw new NullPointerException("The MaikAOP is not initialized");
        }
        return mApplication;
    }

    public HashMap<String, Object[]> getMethodArgs() {
        return mMethodArgs;
    }

    public HashMap<String, Object> getTargets() {
        return mTargets;
    }

    public void clear(){
        mMethodList.clear();
        mMethodArgs.clear();
        mTargets.clear();
    }

    private static Application.ActivityLifecycleCallbacks sLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            ActivityManager.getInstance().addActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            ActivityManager.getInstance().removeActivity(activity);
        }
    };
}
