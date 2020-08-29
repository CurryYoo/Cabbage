package com.example.cabbage.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.cabbage.R;

import java.util.Arrays;

public class CustomAttributeView extends LinearLayout {
    private static final int TEXT_REMARK = 0;
    private static final int TEXT_ATTRIBUTE = 1;

    private int mType = TEXT_REMARK;
    private String mKeyName = "";

    private EditText txtRemark;
    private Button btnDelete;
    private EditText edtAttribute;
    private EditText txtAttribute;

    public CustomAttributeView(Context context, int type, String keyName) {
        super(context);
        initView(context, type, "");
        mKeyName = keyName;
    }

    public CustomAttributeView(Context context, int type, String name, String keyName) {
        super(context);
        initView(context, type, name);
        mKeyName = keyName;
    }

    public CustomAttributeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomAttributeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomAttributeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initView(Context context, int i, String name) {
        btnDelete = findViewById(R.id.btn_delete);
        mType = i;
        switch (mType) {
            case TEXT_REMARK:
                View remarkView = LayoutInflater.from(context).inflate(R.layout.view_remark, this, true);
                txtRemark = remarkView.findViewById(R.id.txt_remark);
                break;
            case TEXT_ATTRIBUTE:
                View attributeView = LayoutInflater.from(context).inflate(R.layout.view_attribute, this, true);
                if (!TextUtils.isEmpty(name)) {
                    edtAttribute = attributeView.findViewById(R.id.edt_attribute);
                    edtAttribute.setText(name);
                }
                txtAttribute = attributeView.findViewById(R.id.txt_attribute);
                break;
            default:
                break;
        }
    }

    public String getTitle() {
        String title = "";
        switch (mType) {
            case TEXT_REMARK:
                title = "备注";
                break;
            case TEXT_ATTRIBUTE:
                title = edtAttribute.getText().toString();
                break;
            default:
                break;
        }
        return title;
    }

    public String getContent() {
        String content = "";
        switch (mType) {
            case TEXT_REMARK:
                content = txtRemark.getText().toString();
                break;
            case TEXT_ATTRIBUTE:
                content = txtAttribute.getText().toString();
                break;
            default:
                break;
        }
        return content;
    }

    public String getKeyName() {
        return mKeyName;
    }

    private void setDeleteListener(OnClickListener onClickListener) {
        btnDelete.setOnClickListener(onClickListener);
    }
}
