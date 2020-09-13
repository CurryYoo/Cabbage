package com.example.cabbage.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.cabbage.BuildConfig;
import com.example.cabbage.data.ObjectBox;
import com.example.cabbage.monitor.AppBlockCanaryContext;
import com.example.cabbage.utils.LocalManageUtil;
import com.github.jokar.multilanguages.library.LanguageLocalListener;
import com.github.jokar.multilanguages.library.MultiLanguage;
import com.github.moduth.blockcanary.BlockCanary;

import java.util.Locale;

import timber.log.Timber;

public class BaseApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        //Save the system language selection when entering the app for the first time.
        LocalManageUtil.saveSystemCurrentLanguage(base);
        super.attachBaseContext(MultiLanguage.setLocal(base));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        /**
         The user saves the system selection language when switching languages on the system settings page (in order to select when the system language is used, if it is not saved, it will not be available after switching languages)
         **/
        LocalManageUtil.saveSystemCurrentLanguage(getApplicationContext(), newConfig);
        MultiLanguage.onConfigurationChanged(getApplicationContext());
    }


    @Override
    public void onCreate() {
        super.onCreate();

        MultiLanguage.init(context -> {
            //return your local settings
            return LocalManageUtil.getSetLanguageLocale(context);
        });
        MultiLanguage.setApplicationLanguage(this);

        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(this);

        ObjectBox.init(this);

        BlockCanary.install(this, new AppBlockCanaryContext()).start();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }
}
