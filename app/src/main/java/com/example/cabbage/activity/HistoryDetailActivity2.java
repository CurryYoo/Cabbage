package com.example.cabbage.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.cabbage.R;
import com.example.cabbage.adapter.HistoryDetailAdapter;
import com.example.cabbage.network.HistoryInfo;
import com.example.cabbage.network.HttpRequest;
import com.example.cabbage.utils.ARouterPaths;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:created by Kang on 2020/9/9
 * Email:zyk970512@163.com
 * Annotation:详细历史调查采集页面
 */
@Route(path = ARouterPaths.HISTORY_DETAIL_ACTIVITY2)
public class HistoryDetailActivity2 extends AppCompatActivity {


    @BindView(R.id.left_one_button)
    ImageView leftOneButton;
    @BindView(R.id.left_one_layout)
    LinearLayout leftOneLayout;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.view_pager_history)
    ViewPager2 viewPagerHistory;
    @Autowired(name = "position")
    public int pointPosition;
    private String token;
    private Context self = this;
    View.OnClickListener toolBarOnClickListener = v -> {
        switch (v.getId()) {
            case R.id.left_one_layout:
                finish();
            default:
                break;
        }
    };

    private int mStartIndex = 1;
    private int mEndIndex = 1;
    private HistoryDetailAdapter historyDetailAdapter;
    private List<HistoryInfo.data.Info> mHistoryInfoList = new ArrayList<>();
    private List<HistoryInfo.data.Info> mAdapterList = new ArrayList<>();
    private float MINALPHA = 0.1f;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_history_datail2);
        ButterKnife.bind(this);

        SharedPreferences sp = self.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        token = sp.getString("token", "");

        pointPosition = getIntent().getIntExtra("pointPosition", 0);

        initToolbar();
        initData();
    }

    private void initData() {
        HttpRequest.getHistorySurveyData(token, new HttpRequest.IHistoryCallback() {
            @Override
            public void onResponse(HistoryInfo historyInfo) {
                mHistoryInfoList = historyInfo.data.list;
                initView();
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void initToolbar() {
        leftOneButton.setBackgroundResource(R.mipmap.ic_back);
        leftOneLayout.setBackgroundResource(R.drawable.selector_trans_button);
        titleText.setText(R.string.species_data_pick_history);
        leftOneLayout.setOnClickListener(toolBarOnClickListener);
    }


    private void initView() {
        historyDetailAdapter = new HistoryDetailAdapter(getSupportFragmentManager(), new Lifecycle() {
            @Override
            public void addObserver(@NonNull LifecycleObserver observer) {

            }

            @Override
            public void removeObserver(@NonNull LifecycleObserver observer) {

            }

            @NonNull
            @Override
            public State getCurrentState() {
                return null;
            }
        }, mHistoryInfoList);
        viewPagerHistory.setAdapter(historyDetailAdapter);
        viewPagerHistory.setCurrentItem(pointPosition, false);
        viewPagerHistory.setPageTransformer((page, position) -> {
            if (position < -1 || position > 1) {
                page.setAlpha(MINALPHA);
            } else {
                //不透明->半透明
                if (position < 0) {//[0,-1]
                    page.setAlpha(MINALPHA + (1 + position) * (1 - MINALPHA));
                } else {//[1,0]
                    //半透明->不透明
                    page.setAlpha(MINALPHA + (1 - position) * (1 - MINALPHA));
                }
            }
        });
    }
}
