package com.example.cabbage.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cabbage.R;

public class AutoClearEditText extends androidx.appcompat.widget.AppCompatEditText {
    public AutoClearEditText(@NonNull Context context) {
        super(context);

    }

    public AutoClearEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoClearEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (this.getVisibility() == GONE) {
            this.setText(R.string.string_null);
        }
    }
}
