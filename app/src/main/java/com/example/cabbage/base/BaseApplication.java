package com.example.cabbage.base;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.cabbage.data.ObjectBox;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(this);

        ObjectBox.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }
}
