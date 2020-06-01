package com.example.cabbage.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.cabbage.R;
import com.example.cabbage.utils.ARouterPaths;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = ARouterPaths.PWD_ACTIVITY)
public class PwdActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.img_left_one)
    ImageView imgLeftOne;
    @BindView(R.id.ll_left_one)
    LinearLayout llLeftOne;
    @BindView(R.id.edt_old_pwd)
    EditText edtOldPwd;
    @BindView(R.id.edt_new_pwd)
    EditText edtNewPwd;
    @BindView(R.id.edt_new_pwd2)
    EditText edtNewPwd2;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.txt_title)
    TextView txtTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        imgLeftOne.setImageResource(R.mipmap.ic_back);
        txtTitle.setText("修改密码");
        llLeftOne.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left_one:
                finish();
                break;
            case R.id.btn_confirm:
                break;
        }
    }
}
