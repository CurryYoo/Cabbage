package com.example.cabbage.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.example.cabbage.utils.StaticVariable.COUNT_REPEAT;

public class CountButton extends androidx.appcompat.widget.AppCompatButton {
    public int count;

    public CountButton(@NonNull Context context) {
        super(context);
        count= COUNT_REPEAT;
    }

    public CountButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        count= COUNT_REPEAT;
    }

    public CountButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        count= COUNT_REPEAT;
    }


    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void subtractCount() {
        count--;
        if (count <= 0) {
            this.setEnabled(false);
        }
    }

    public void addCount() {
        count++;
        if (count > 0) {
            this.setEnabled(true);
        }
    }
}
