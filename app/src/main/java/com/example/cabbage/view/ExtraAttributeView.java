package com.example.cabbage.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.cabbage.R;

public class ExtraAttributeView extends LinearLayout {
    public static final int TYPE_REMARK = 0;
    public static final int TYPE_ATTRIBUTE = 1;
    public static final String STRING_REMARK = "extraAttribute";
    public static final String STRING_ATTRIBUTE = "extraRemark";

    private int mType = TYPE_REMARK;
    private String mKeyName = "";

    private Button btnDelete;
    private EditText edtRemark;
    private TextView txtAttribute;
    private EditText edtAttribute;

    public ExtraAttributeView(Context context, int type, String keyName) {
        super(context);
        initView(context, type, "");
        mKeyName = keyName;
    }

    public ExtraAttributeView(Context context, int type, String name, String keyName) {
        super(context);
        initView(context, type, name);
        mKeyName = keyName;
    }

    public ExtraAttributeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExtraAttributeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ExtraAttributeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initView(Context context, int type, String name) {
        mType = type;
        switch (mType) {
            case TYPE_REMARK:
                View remarkView = LayoutInflater.from(context).inflate(R.layout.view_remark, this, true);
                if (!TextUtils.isEmpty(name)) {
                    txtAttribute.setText(name);
                }
                edtRemark = remarkView.findViewById(R.id.edt_remark);
                btnDelete = remarkView.findViewById(R.id.btn_delete);
                break;
            case TYPE_ATTRIBUTE:
                View attributeView = LayoutInflater.from(context).inflate(R.layout.view_attribute, this, true);
                txtAttribute = attributeView.findViewById(R.id.txt_attribute);
                if (!TextUtils.isEmpty(name)) {
                    txtAttribute.setText(name);
                }
                edtAttribute = attributeView.findViewById(R.id.edt_attribute);
                btnDelete = attributeView.findViewById(R.id.btn_delete);
                break;
            default:
                break;
        }
    }

    public String getTitle() {
        String title = "";
        switch (mType) {
            case TYPE_REMARK:
                title = "备注";
                break;
            case TYPE_ATTRIBUTE:
                title = edtAttribute.getText().toString();
                break;
            default:
                break;
        }
        return title;
    }

    public void setTitle(String title) {
        switch (mType) {
            case TYPE_REMARK:
                break;
            case TYPE_ATTRIBUTE:
                edtAttribute.setText(title);
                break;
            default:
                break;
        }
    }

    public String getContent() {
        String content = "";
        switch (mType) {
            case TYPE_REMARK:
                content = edtRemark.getText().toString();
                break;
            case TYPE_ATTRIBUTE:
                content = edtAttribute.getText().toString();
                break;
            default:
                break;
        }
        return content;
    }

    public void setContent(String content) {
        switch (mType) {
            case TYPE_REMARK:
                edtRemark.setText(content);
                break;
            case TYPE_ATTRIBUTE:
                edtAttribute.setText(content);
                break;
            default:
                break;
        }
    }

    public String getKeyName() {
        return mKeyName;
    }

    private void setDeleteListener(OnClickListener onClickListener) {
        btnDelete.setOnClickListener(onClickListener);
    }

    public void hideDeleteBtn() {
        btnDelete.setVisibility(View.GONE);
    }
}
