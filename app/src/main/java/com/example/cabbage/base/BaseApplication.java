package com.example.cabbage.base;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.cabbage.BuildConfig;
import com.example.cabbage.data.ObjectBox;
import com.example.cabbage.utils.LogTree;

import timber.log.Timber;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(this);

        ObjectBox.init(this);

        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }
}
