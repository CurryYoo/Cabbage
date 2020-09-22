package com.example.cabbage.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * Author:Kang
 * Date:2020/9/12
 * Description:
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //重写，防止当前activity不在栈底时崩溃
    @Override
    public boolean moveTaskToBack(boolean nonRoot) {
        if(!nonRoot){
            if(!isTaskRoot()){
                return false;
            }
        }
        return super.moveTaskToBack(nonRoot);
    }
}
