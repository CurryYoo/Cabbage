package com.example.cabbage.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.cabbage.utils.ARouterPaths;

@Route(path = ARouterPaths.HISTORY_DETAIL_ACTIVITY)
public class HistoryDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
