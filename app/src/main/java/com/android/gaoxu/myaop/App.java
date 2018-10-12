package com.android.gaoxu.myaop;

import android.app.Application;

import com.android.gaoxu.aoplibrary.utils.MyAOPHelper;


/**
 * <pre>
 *     author : Mark
 *     e-mail : makun.cai@aorise.org
 *     time   : 2018/05/30
 *     desc   : TODO
 *     version: 1.0
 * </pre>
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MyAOPHelper.getInstance().init(this);
    }
}
