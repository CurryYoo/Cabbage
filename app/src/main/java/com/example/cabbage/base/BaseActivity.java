package com.example.cabbage.base;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.hjq.language.LanguagesManager;

/**
 * Author:Kang
 * Date:2020/9/12
 * Description:
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        // 国际化适配（绑定语种）
        super.attachBaseContext(LanguagesManager.attach(newBase));
    }
}
