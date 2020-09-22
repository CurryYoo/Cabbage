package com.example.cabbage.activity;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.cabbage.R;
import com.example.cabbage.base.BaseActivity;
import com.example.cabbage.network.HttpRequest;
import com.example.cabbage.network.UserInfo;
import com.example.cabbage.utils.ARouterPaths;

/**
 * Author:Kang
 * Date:2020/9/20
 * Description:
 */
public class SplashActivity extends BaseActivity {


    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        editor = sp.edit();


        new Thread(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String username = sp.getString("username", "");
            if (username != null && username.length() != 0) {
                login();
            } else {
                ARouter.getInstance().build(ARouterPaths.LOGIN_ACTIVITY).navigation();
                finish();
            }

        }).start();
    }


    public void login() {
        String username = sp.getString("username", "");
        String password = sp.getString("passwordShort", "");
        HttpRequest.requestLogin(username, password, new HttpRequest.IUserInfoCallback() {
            @Override
            public void onResponse(UserInfo userInfo) {
                if (userInfo.getCode() == 200 && userInfo.getMessage().equals("操作成功")) {
                    editor = sp.edit();
                    editor.putInt("userId", userInfo.getId());
                    editor.putString("nickname", userInfo.getNickname());
                    editor.putString("username", userInfo.getUsername());
                    editor.putString("password", userInfo.getPassword());
                    editor.putString("passwordShort", password);
                    editor.putString("headImgUrl", userInfo.getHeadImgUrl());
                    editor.putString("token", userInfo.getToken());
                    editor.apply();
                    ARouter.getInstance().build(ARouterPaths.MAIN_ACTIVITY).navigation();
                    finish();
                } else {
                    ARouter.getInstance().build(ARouterPaths.LOGIN_ACTIVITY).navigation();
                    finish();
                }
            }

            @Override
            public void onFailure() {
                ARouter.getInstance().build(ARouterPaths.LOGIN_ACTIVITY).navigation();
                finish();
            }
        });
    }
}
