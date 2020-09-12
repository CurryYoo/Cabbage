package com.example.cabbage.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.cabbage.R;
import com.example.cabbage.base.BaseActivity;
import com.example.cabbage.utils.ARouterPaths;
import com.hjq.language.LanguagesManager;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:Kang
 * Date:2020/9/12
 * Description:
 */
@Route(path = ARouterPaths.LANGUAGE_ACTIVITY)
public class LanguageActivity extends BaseActivity {
    @BindView(R.id.left_one_button)
    ImageView leftOneButton;
    @BindView(R.id.left_one_layout)
    LinearLayout leftOneLayout;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.txt_choose_language)
    TextView txtChooseLanguage;
    @BindView(R.id.btn_auto)
    Button btnAuto;
    @BindView(R.id.btn_chinese)
    Button btnChinese;
    @BindView(R.id.btn_english)
    Button btnEnglish;

    View.OnClickListener toolBarOnClickListener = v -> {
        finish();
    };
    View.OnClickListener settingOnClickListener = v -> {
        // 是否需要重启
        boolean restart = true;
        switch (v.getId()) {
            case R.id.btn_auto:
                restart = LanguagesManager.setSystemLanguage(this);
                break;
            case R.id.btn_chinese:
                restart = LanguagesManager.setAppLanguage(this, Locale.CHINESE);
                break;
            case R.id.btn_english:
                restart = LanguagesManager.setAppLanguage(this, Locale.ENGLISH);
                break;
        }
        if (restart) {
            // 我们可以充分运用 Activity 跳转动画，在跳转的时候设置一个渐变的效果
            MainActivity.reStart(this);
            finish();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        ButterKnife.bind(this);

        initToolBar();
        initView();
    }

    private void initToolBar() {
        leftOneButton.setBackgroundResource(R.mipmap.ic_back);
        leftOneLayout.setBackgroundResource(R.drawable.selector_trans_button);
        titleText.setText(R.string.language_setting);
        leftOneLayout.setOnClickListener(toolBarOnClickListener);
    }

    private void initView() {
        txtChooseLanguage.setText(getString(R.string.choose_language,
                LanguagesManager.getAppLanguage(this)));
        btnAuto.setOnClickListener(settingOnClickListener);
        btnChinese.setOnClickListener(settingOnClickListener);
        btnEnglish.setOnClickListener(settingOnClickListener);
    }

    public static void enter(Context context) {
        Intent intent = new Intent(context, LanguageActivity.class);
        context.startActivity(intent);
    }
}
