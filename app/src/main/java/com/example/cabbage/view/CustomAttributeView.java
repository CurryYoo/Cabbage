package com.example.cabbage.view;

import android.content.Context;
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

    private EditText txtRemark;
    private Button btnDelete;
    private EditText edtAttribute;
    private EditText txtAttribute;

    public CustomAttributeView(Context context, int type, String... name) {
        super(context);
        initView(context, type, name);
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

    private void initView(Context context, int i, String... name) {
        btnDelete = findViewById(R.id.btn_delete);
        switch (i) {
            case TEXT_REMARK:
                LayoutInflater.from(context).inflate(R.layout.view_remark, this, true);
                break;
            case TEXT_ATTRIBUTE:
                View view = LayoutInflater.from(context).inflate(R.layout.view_attribute, this, true);
                if (name.length != 0) {
                    EditText editText = view.findViewById(R.id.edt_attribute);
                    editText.setText(name[0]);
                }
                break;
            default:
                break;
        }
    }

    private void setDeleteListener(OnClickListener onClickListener) {
        btnDelete.setOnClickListener(onClickListener);
    }
}
