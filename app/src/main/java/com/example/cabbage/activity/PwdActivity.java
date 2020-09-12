package com.example.cabbage.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.cabbage.R;
import com.example.cabbage.base.BaseActivity;
import com.example.cabbage.network.HttpRequest;
import com.example.cabbage.network.NormalInfo;
import com.example.cabbage.utils.ARouterPaths;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:created by Kang on 2020/9/9
 * Email:zyk970512@163.com
 * Annotation:修改密码页面
 */
@Route(path = ARouterPaths.PWD_ACTIVITY)
public class PwdActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.left_one_button)
    ImageView imgLeftOne;
    @BindView(R.id.left_one_layout)
    LinearLayout llLeftOne;
    @BindView(R.id.edt_old_pwd)
    EditText edtOldPwd;
    @BindView(R.id.edt_new_pwd)
    EditText edtNewPwd;
    @BindView(R.id.edt_new_pwd2)
    EditText edtNewPwd2;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.title_text)
    TextView txtTitle;
    private Context context = this;

    private String token;
    private String username;
    private String password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd);
        ButterKnife.bind(this);
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        token = sp.getString("token", "");
        username = sp.getString("username", "");
        password = sp.getString("password", "");
        edtOldPwd.setText(password);
        initView();
    }

    private void initView() {
        imgLeftOne.setImageResource(R.mipmap.ic_back);
        txtTitle.setText(R.string.pwd_change);
        llLeftOne.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_one_layout:
                finish();
                break;
            case R.id.btn_confirm:
                if (edtOldPwd.length() > 0 & edtNewPwd.length() > 0 & edtNewPwd2.length() > 0) {
                    if (edtOldPwd.getText().toString().equals(password)) {
                        if (edtNewPwd.getText().toString().equals(edtNewPwd2.getText().toString())) {
                            HttpRequest.requestChangePassword(token, username, edtOldPwd.getText().toString(), edtNewPwd.getText().toString(), new HttpRequest.INormalCallback() {
                                @Override
                                public void onResponse(NormalInfo normalInfo) {

                                }

                                @Override
                                public void onFailure() {

                                }
                            });
                        } else {
                            Toast.makeText(this, R.string.pwd_no_same, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, R.string.pwd_old_error, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
