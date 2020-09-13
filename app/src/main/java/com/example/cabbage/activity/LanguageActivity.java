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
import com.example.cabbage.utils.LocalManageUtil;

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
    @BindView(R.id.btn_china)
    Button btnChinese;
    @BindView(R.id.btn_english)
    Button btnEnglish;

    View.OnClickListener toolBarOnClickListener = v -> {
        finish();
    };
    View.OnClickListener settingOnClickListener = v -> {
        switch (v.getId()) {
            case R.id.btn_auto:
                selectLanguage(0);
                break;
            case R.id.btn_china:
                selectLanguage(1);
                break;
            case R.id.btn_english:
                selectLanguage(2);
                break;
            default:
                break;
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
                LocalManageUtil.getSelectLanguage(this)));
        btnAuto.setOnClickListener(settingOnClickListener);
        btnChinese.setOnClickListener(settingOnClickListener);
        btnEnglish.setOnClickListener(settingOnClickListener);
    }


    public static void enter(Context context) {
        Intent intent = new Intent(context, LanguageActivity.class);
        context.startActivity(intent);
    }

    private void selectLanguage(int select) {
        LocalManageUtil.saveSelectLanguage(this, select);
        MainActivity.reStart(this);
    }

}
