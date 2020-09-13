package com.example.cabbage.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.cabbage.R;
import com.example.cabbage.base.BaseActivity;
import com.example.cabbage.network.HttpRequest;
import com.example.cabbage.network.UserInfo;
import com.example.cabbage.utils.ARouterPaths;
import com.example.cabbage.view.PasswordEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author:created by Kang on 2020/9/9
 * Email:zyk970512@163.com
 * Annotation:登录页
 */
@Route(path = ARouterPaths.LOGIN_ACTIVITY)
public class LoginActivity extends BaseActivity {
    private Context context = this;

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_title_tip)
    TextView tvTitleTip;
    @BindView(R.id.space_top)
    Space spaceTop;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_password)
    PasswordEditText etPassword;
    @BindView(R.id.space_bottom)
    Space spaceBottom;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_fast_login)
    Button btnFastLogin;
    @BindView(R.id.tv_register_account)
    TextView tvRegisterAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        sp = getSharedPreferences("userInfo", MODE_PRIVATE);
    }

    @OnClick({R.id.btn_login, R.id.btn_fast_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_fast_login:
                fastLogin();
                break;
        }
    }

    public void login() {
        String username = etAccount.getText().toString();
        String password = etPassword.getText();
        HttpRequest.requestLogin(username, password, new HttpRequest.IUserInfoCallback() {
            @Override
            public void onResponse(UserInfo userInfo) {
                if (userInfo.getCode() == 200 && userInfo.getMessage().equals("操作成功")) {
                    editor = sp.edit();
                    editor.putInt("userId", userInfo.getId());
                    editor.putString("nickname", userInfo.getNickname());
                    editor.putString("username",userInfo.getUsername());
                    editor.putString("password",userInfo.getPassword());
                    editor.putString("headImgUrl", userInfo.getHeadImgUrl());
                    editor.putString("token", userInfo.getToken());
                    editor.apply();
                    ARouter.getInstance().build(ARouterPaths.MAIN_ACTIVITY).navigation();
                    Toast.makeText(context, getString(R.string.log_in_success), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(context, getString(R.string.log_in_fail), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure() {
                Toast.makeText(context, getString(R.string.log_in_fail), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void fastLogin() {
        etAccount.setText("admin");
        etPassword.setText("admin");
    }


}
