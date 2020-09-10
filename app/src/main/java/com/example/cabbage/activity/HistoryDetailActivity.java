package com.example.cabbage.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.cabbage.R;
import com.example.cabbage.adapter.HistoryAdapter;
import com.example.cabbage.adapter.HistoryPageAdapter;
import com.example.cabbage.fragment.FloweringPeriodFragment;
import com.example.cabbage.fragment.GerminationPeriodFragment;
import com.example.cabbage.fragment.HarvestPeriodFragment;
import com.example.cabbage.fragment.HeadingPeriodFragment;
import com.example.cabbage.fragment.RosettePeriodFragment;
import com.example.cabbage.fragment.SeedHarvestPeriodFragment;
import com.example.cabbage.fragment.SeedlingPeriodFragment;
import com.example.cabbage.fragment.StoragePeriodFragment;
import com.example.cabbage.network.HistoryInfo;
import com.example.cabbage.network.HttpRequest;
import com.example.cabbage.utils.ARouterPaths;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.example.cabbage.utils.StaticVariable.STATUS_READ;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_GERMINATION;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_HARVEST;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_HEADING;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_ROSETTE;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_SEEDLING;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_STORAGE;

/**
 * Author:created by Kang on 2020/9/9
 * Email:zyk970512@163.com
 * Annotation:详细历史调查采集页面
 */
@Route(path = ARouterPaths.HISTORY_DETAIL_ACTIVITY)
public class HistoryDetailActivity extends AppCompatActivity {
    @BindView(R.id.left_one_button)
    ImageView leftOneButton;
    @BindView(R.id.left_one_layout)
    LinearLayout leftOneLayout;
    @BindView(R.id.view_pager_history)
    ViewPager viewPagerHistory;
    @BindView(R.id.title_text)
    TextView titleText;

    View.OnClickListener toolBarOnClickListener = v -> {
        switch (v.getId()) {
            case R.id.left_one_layout:
                finish();
            default:
                break;
        }
    };

    private Fragment germinationPeriodFragment = GerminationPeriodFragment.newInstance();
    private Fragment seedlingPeriodFragment = SeedlingPeriodFragment.newInstance();
    private Fragment rosettePeriodFragment = RosettePeriodFragment.newInstance();
    private Fragment headingPeriodFragment = HeadingPeriodFragment.newInstance();
    private Fragment harvestPeriodFragment = HarvestPeriodFragment.newInstance();
    private Fragment storagePeriodFragment = StoragePeriodFragment.newInstance();
    private Fragment floweringPeriodFragment = FloweringPeriodFragment.newInstance();
    private Fragment seedHarvestPeriodFragment = SeedHarvestPeriodFragment.newInstance();

    @Autowired(name = "position")
    public int position;
    private HistoryPageAdapter historyPageAdapter;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<HistoryInfo.data.Info> mHistoryInfoList =new ArrayList<>();

    private String token;
    private Context self = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_history_datail);
        ButterKnife.bind(this);


        SharedPreferences sp = self.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        token = sp.getString("token", "");

        position=getIntent().getIntExtra("position",0);

        initToolbar();
        initData();
    }

    private void initData() {
        HttpRequest.getHistorySurveyData(token, new HttpRequest.IHistoryCallback() {
            @Override
            public void onResponse(HistoryInfo historyInfo) {
                mHistoryInfoList=historyInfo.data.list;
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
        titleText.setText(R.string.species_data_pick);
        leftOneLayout.setOnClickListener(toolBarOnClickListener);
    }

    private void initView(){

        for(HistoryInfo.data.Info info:mHistoryInfoList){
            switch (info.obsPeriod){
                case SURVEY_PERIOD_GERMINATION:
                    GerminationPeriodFragment germinationPeriodFragment=new GerminationPeriodFragment();
                    germinationPeriodFragment.setInitValue(info.materialNumber,info.materialType,info.plantNumber,info.investigatingTime,
                            STATUS_READ,info.observationId,info.obsPeriod);
                    mFragmentList.add(germinationPeriodFragment);
                    break;
                case SURVEY_PERIOD_SEEDLING:
                    SeedlingPeriodFragment seedlingPeriodFragment=new SeedlingPeriodFragment();
                    seedlingPeriodFragment.setInitValue(info.materialNumber,info.materialType,info.plantNumber,info.investigatingTime,
                            STATUS_READ,info.observationId,info.obsPeriod);
                    mFragmentList.add(seedlingPeriodFragment);
                    break;
                case SURVEY_PERIOD_ROSETTE:
                    RosettePeriodFragment rosettePeriodFragment=new RosettePeriodFragment();
                    rosettePeriodFragment.setInitValue(info.materialNumber,info.materialType,info.plantNumber,info.investigatingTime,
                            STATUS_READ,info.observationId,info.obsPeriod);
                    mFragmentList.add(rosettePeriodFragment);
                    break;
                case SURVEY_PERIOD_HEADING:
                    HeadingPeriodFragment headingPeriodFragment=new HeadingPeriodFragment();
                    headingPeriodFragment.setInitValue(info.materialNumber,info.materialType,info.plantNumber,info.investigatingTime,
                            STATUS_READ,info.observationId,info.obsPeriod);
                    mFragmentList.add(headingPeriodFragment);
                    break;
                case SURVEY_PERIOD_HARVEST:
                    HarvestPeriodFragment harvestPeriodFragment=new HarvestPeriodFragment();
                    harvestPeriodFragment.setInitValue(info.materialNumber,info.materialType,info.plantNumber,info.investigatingTime,
                            STATUS_READ,info.observationId,info.obsPeriod);
                    mFragmentList.add(harvestPeriodFragment);
                    break;
            }
        }
        historyPageAdapter = new HistoryPageAdapter(getSupportFragmentManager(), 0, mFragmentList);
        viewPagerHistory.setOffscreenPageLimit(2);
        viewPagerHistory.setAdapter(historyPageAdapter);
        viewPagerHistory.setCurrentItem(position);
    }
}
