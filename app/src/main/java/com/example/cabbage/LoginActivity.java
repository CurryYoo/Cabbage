package com.example.cabbage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Space;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cabbage.view.PasswordEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

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

    }

    public void fastLogin() {

    }
}
