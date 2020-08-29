package com.example.cabbage.view;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.cabbage.R;

import me.shaohui.bottomdialog.BaseBottomDialog;

public class InfoBottomDialog extends BaseBottomDialog {
    private TextView txtInfo;
    private String info;
    @Override
    public int getLayoutRes() {
        return R.layout.dialog_bottom_info;
    }

    @Override
    public void bindView(View v) {
        txtInfo=v.findViewById(R.id.txt_info);
        txtInfo.setText(info);
    }
    public void setTxtInfo(String info){
        this.info=info;
    }

    //修改背景透明度
    @Override
    public float getDimAmount() {
        return 0.8f;
    }

    @Override
    public int getHeight() {
        return 500;
    }
}
