package com.example.cabbage.monitor;

import com.example.cabbage.BuildConfig;
import com.example.cabbage.base.BaseApplication;
import com.github.moduth.blockcanary.BlockCanaryContext;

/**
 * Author: xiemugan
 * Date: 2020/9/9
 * Description:
 **/
public class AppBlockCanaryContext extends BlockCanaryContext {

    @Override
    public String provideQualifier() {
//        BaseApplication

        return "Cabbage 2.0";
    }

    @Override
    public int provideBlockThreshold() {
        return 500;
    }

    @Override
    public boolean displayNotification() {
        return BuildConfig.DEBUG;
    }

    @Override
    public boolean stopWhenDebugging() {
        return false;
    }

}
