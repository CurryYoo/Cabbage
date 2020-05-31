package com.example.cabbage.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.cabbage.R;

/**
 * Author: xiemugan
 * Date: 2020/5/24
 * Description:
 **/
public class PasswordEditText extends LinearLayout {

    private ViewGroup mRootView;
    private EditText mContentEditText;
    private ImageView mClearImageView;
    private ImageView mShowPasswordImageView;
    private OnPasswordHideShowListener onPasswordHideShowListener;
    private Context mContext;

    public PasswordEditText(Context context) {
        super(context);
        mContext = context;
        init(context);
    }

    public PasswordEditText(Context context,  AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(context);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(context);
    }

    interface OnPasswordHideShowListener {
        void hideShowPassword(boolean isShowPwd);
    }

    private void init(Context mContext) {
        mRootView = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.layout_clearable_edittext, this, true);
        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setGravity(Gravity.CENTER_VERTICAL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViews();
        setViewListener();
    }

    private void findViews() {
        mContentEditText = mRootView.findViewById(R.id.et_content);
        mClearImageView = mRootView.findViewById(R.id.iv_clear);
        mShowPasswordImageView = mRootView.findViewById(R.id.iv_show_pwd);
    }

    private void setViewListener() {
        mContentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setClearIconVisibility(hasFocus() && !TextUtils.isEmpty(s));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mContentEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    setClearIconVisibility(hasFocus && mContentEditText.length() > 0);
                }
            }
        });

        mClearImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mContentEditText.setText("");
            }
        });

        mShowPasswordImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                restorePasswordIconStatus((Boolean) mShowPasswordImageView.getTag());
                onPasswordHideShowListener.hideShowPassword((Boolean)mShowPasswordImageView.getTag());
            }
        });
    }

    private void setClearIconVisibility(boolean visibility) {
        mClearImageView.setVisibility(visibility? View.VISIBLE: View.GONE);
    }

    // 设置密码指示器的状态
    private void restorePasswordIconStatus(boolean isShowPwd) {
        if (!isShowPwd) {
            // 可视密码输入
            mContentEditText.setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            mShowPasswordImageView.setImageResource(R.drawable.icon_show_password);
        } else {
            // 非可视密码状态
            mContentEditText.setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
            mShowPasswordImageView.setImageResource(R.drawable.icon_hide_password);
        }
        mShowPasswordImageView.setTag(!isShowPwd);

        // 移动光标
        mContentEditText.setSelection(mContentEditText.getText().length());
    }

    public String getText(){
        return mContentEditText.getText().toString();
    }

    public void setContentEditTextTextChangedListener(TextWatcher watcher){
        mContentEditText.addTextChangedListener(watcher);
    }

    public void setText(String value){
        mContentEditText.setText(value);
    }
}

