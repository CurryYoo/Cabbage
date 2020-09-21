package com.example.cabbage.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.cabbage.R;
import com.example.cabbage.adapter.HistoryDetailAdapter;
import com.example.cabbage.base.BaseActivity;
import com.example.cabbage.network.HistoryInfo;
import com.example.cabbage.network.HttpRequest;
import com.example.cabbage.utils.ARouterPaths;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.cabbage.utils.StaticVariable.STATUS_COPY;

/**
 * Author:created by Kang on 2020/9/9
 * Email:zyk970512@163.com
 * Annotation:详细历史调查采集页面
 */
@Route(path = ARouterPaths.HISTORY_DETAIL_ACTIVITY2)
public class HistoryDetailActivity2 extends BaseActivity {


    @BindView(R.id.left_one_button)
    ImageView leftOneButton;
    @BindView(R.id.left_one_layout)
    LinearLayout leftOneLayout;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.view_pager_history)
    ViewPager2 viewPagerHistory;
    @BindView(R.id.right_one_button)
    ImageView rightOneButton;
    @BindView(R.id.right_one_layout)
    LinearLayout rightOneLayout;
    @BindView(R.id.right_two_button)
    ImageView rightTwoButton;
    @BindView(R.id.right_two_layout)
    LinearLayout rightTwoLayout;

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

    //    private int mStartIndex = 1;
//    private int mEndIndex = 1;
    private HistoryDetailAdapter historyDetailAdapter;
    private List<HistoryInfo.data.Info> mHistoryInfoList;
    //    private List<HistoryInfo.data.Info> mAdapterList = new ArrayList<>();
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
                rightOneLayout.setOnClickListener(v -> {
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    Intent dataIntent = new Intent();
                    // 创建普通字符型ClipData
                    dataIntent.putExtra("surveyId", mHistoryInfoList.get(viewPagerHistory.getCurrentItem()).observationId);
                    dataIntent.putExtra("surveyPeriod", mHistoryInfoList.get(viewPagerHistory.getCurrentItem()).obsPeriod);
                    dataIntent.putExtra("materialId", mHistoryInfoList.get(viewPagerHistory.getCurrentItem()).materialNumber);
                    dataIntent.putExtra("materialType", mHistoryInfoList.get(viewPagerHistory.getCurrentItem()).materialType);
                    ClipData mClipData1 = ClipData.newIntent("copyData", dataIntent);
                    // 将ClipData内容放到系统剪贴板里
                    cm.setPrimaryClip(mClipData1);
                    Toast.makeText(getApplicationContext(), R.string.copy_data_success, Toast.LENGTH_SHORT).show();
                });
                rightTwoLayout.setOnClickListener(v ->{
                    ARouter.getInstance().build(ARouterPaths.SURVEY_ACTIVITY)
                            .withString("surveyId", mHistoryInfoList.get(viewPagerHistory.getCurrentItem()).observationId)
                            .withString("surveyPeriod", mHistoryInfoList.get(viewPagerHistory.getCurrentItem()).obsPeriod)
                            .withString("materialId", mHistoryInfoList.get(viewPagerHistory.getCurrentItem()).materialNumber)
                            .withString("materialType", mHistoryInfoList.get(viewPagerHistory.getCurrentItem()).materialType)
                            .withInt("status", STATUS_COPY)
                            .navigation();
                });
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void initToolbar() {
        leftOneButton.setBackgroundResource(R.mipmap.ic_back);
        leftOneLayout.setBackgroundResource(R.drawable.selector_trans_button);
        rightOneButton.setBackgroundResource(R.mipmap.ic_copy);
        rightOneLayout.setBackgroundResource(R.drawable.selector_trans_button);
        rightTwoButton.setBackgroundResource(R.mipmap.ic_create);
        rightTwoLayout.setBackgroundResource(R.drawable.selector_trans_button);
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
