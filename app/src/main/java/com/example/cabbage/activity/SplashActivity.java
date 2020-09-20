package com.example.cabbage.activity;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.cabbage.R;
import com.example.cabbage.utils.ARouterPaths;

/**
 * Author:Kang
 * Date:2020/9/20
 * Description:
 */
public class SplashActivity extends AppCompatActivity {


    private SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        sp = getSharedPreferences("userInfo", MODE_PRIVATE);

        new Thread(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (sp.getString("token", "") != null && sp.getString("token", "").length() != 0) {
                ARouter.getInstance().build(ARouterPaths.MAIN_ACTIVITY).navigation();
            }else {
                ARouter.getInstance().build(ARouterPaths.LOGIN_ACTIVITY).navigation();
            }
            finish();
        }).start();
    }
}
