package com.example.cabbage.base;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.cabbage.BuildConfig;
import com.example.cabbage.data.ObjectBox;
import com.example.cabbage.monitor.AppBlockCanaryContext;
import com.github.moduth.blockcanary.BlockCanary;
import com.hjq.language.LanguagesManager;

import timber.log.Timber;

public class BaseApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        // 国际化适配（绑定语种）
        super.attachBaseContext(LanguagesManager.attach(base));
    }
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        //用户在系统设置页面切换语言时保存系统选择语言(为了选择随系统语言时使用，如果不保存，切换语言后就拿不到了）
//        LocalManageUtil.saveSystemCurrentLanguage(getApplicationContext(), newConfig);
//        MultiLanguage.onConfigurationChanged(getApplicationContext());
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        LanguagesManager.init(this);

//        MultiLanguage.init(new LanguageLocalListener() {
//            @Override
//            public Locale getSetLanguageLocale(Context context) {
//                //返回自己本地保存选择的语言设置
//                return LocalManageUtil.getSetLanguageLocale(context);
//            }
//        });
//        MultiLanguage.setApplicationLanguage(this);

        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(this);

        ObjectBox.init(this);

        BlockCanary.install(this, new AppBlockCanaryContext()).start();

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
