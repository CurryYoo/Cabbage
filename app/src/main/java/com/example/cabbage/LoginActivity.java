package com.example.cabbage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Space;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cabbage.view.PasswordEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_title_tip)
    TextView tvTitleTip;
    @Bind(R.id.space_top)
    Space spaceTop;
    @Bind(R.id.et_account)
    EditText etAccount;
    @Bind(R.id.et_password)
    PasswordEditText etPassword;
    @Bind(R.id.space_bottom)
    Space spaceBottom;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.btn_fast_login)
    Button btnFastLogin;
    @Bind(R.id.tv_register_account)
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
