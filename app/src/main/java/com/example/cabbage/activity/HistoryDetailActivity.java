package com.example.cabbage.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.cabbage.R;
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

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;
import static com.example.cabbage.utils.StaticVariable.STATUS_READ;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_FLOWERING;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_GERMINATION;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_HARVEST;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_HEADING;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_ROSETTE;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_SEEDLING;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_SEED_HARVEST;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_STORAGE;
import static com.example.cabbage.utils.UIUtils.checkPeriod;

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
    public int pointPosition;
    private int mStartIndex = 1;
    private int mEndIndex = 1;
    private HistoryPageAdapter historyPageAdapter;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<HistoryInfo.data.Info> mHistoryInfoList = new ArrayList<>();

    private String token;
    private Context self = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_history_datail);
        ButterKnife.bind(this);

        SharedPreferences sp = self.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        token = sp.getString("token", "");

        pointPosition = getIntent().getIntExtra("pointPosition", 0);
        mStartIndex = pointPosition - 2;
        mEndIndex = pointPosition + 2;

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
        titleText.setText(R.string.species_data_pick);
        leftOneLayout.setOnClickListener(toolBarOnClickListener);
    }

    private void initView() {
        if (pointPosition != 0) {
            addFragmentToEndList(mHistoryInfoList.get(pointPosition - 1));
        }
        addFragmentToEndList(mHistoryInfoList.get(pointPosition));
        addFragmentToEndList(mHistoryInfoList.get(pointPosition + 1));

        historyPageAdapter = new HistoryPageAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mFragmentList);

        viewPagerHistory.setAdapter(historyPageAdapter);

        viewPagerHistory.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //滑动到两端时延迟加载fragment
                if (state == SCROLL_STATE_IDLE) {
                   if (viewPagerHistory.getCurrentItem() == mFragmentList.size() - 1) {
                        addFragmentToEndList(mHistoryInfoList.get(mEndIndex));
                        historyPageAdapter.notifyDataSetChanged();
                        mEndIndex++;
                   } else if (viewPagerHistory.getCurrentItem() == 0 && mStartIndex >= 0) {
                        addFragmentToStartList(mHistoryInfoList.get(mStartIndex));
                       historyPageAdapter.notifyDataSetChanged();
//                        historyPageAdapter = new HistoryPageAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mFragmentList);
//                        viewPagerHistory.setAdapter(historyPageAdapter);
//                        viewPagerHistory.setCurrentItem(1);
                        mStartIndex--;
                     }}
            }
        });

        if (pointPosition == 0) {
            viewPagerHistory.setCurrentItem(pointPosition);
        } else {
            viewPagerHistory.setCurrentItem(1);
        }
    }

    private void addFragmentToEndList(HistoryInfo.data.Info info) {
        switch (info.obsPeriod) {
            case SURVEY_PERIOD_GERMINATION:
                GerminationPeriodFragment germinationPeriodFragment = GerminationPeriodFragment.newInstance();
                germinationPeriodFragment.setInitValue(info.materialNumber, info.materialType, info.plantNumber, info.investigatingTime,
                        STATUS_READ, info.observationId);
                mFragmentList.add(germinationPeriodFragment);
                break;
            case SURVEY_PERIOD_SEEDLING:
                SeedlingPeriodFragment seedlingPeriodFragment = SeedlingPeriodFragment.newInstance();
                seedlingPeriodFragment.setInitValue(info.materialNumber, info.materialType, info.plantNumber, info.investigatingTime,
                        STATUS_READ, info.observationId);
                mFragmentList.add(seedlingPeriodFragment);
                break;
            case SURVEY_PERIOD_ROSETTE:
                RosettePeriodFragment rosettePeriodFragment = RosettePeriodFragment.newInstance();
                rosettePeriodFragment.setInitValue(info.materialNumber, info.materialType, info.plantNumber, info.investigatingTime,
                        STATUS_READ, info.observationId);
                mFragmentList.add(rosettePeriodFragment);
                break;
            case SURVEY_PERIOD_HEADING:
                HeadingPeriodFragment headingPeriodFragment = HeadingPeriodFragment.newInstance();
                headingPeriodFragment.setInitValue(info.materialNumber, info.materialType, info.plantNumber, info.investigatingTime,
                        STATUS_READ, info.observationId);
                mFragmentList.add(headingPeriodFragment);
                break;
            case SURVEY_PERIOD_HARVEST:
                HarvestPeriodFragment harvestPeriodFragment = HarvestPeriodFragment.newInstance();
                harvestPeriodFragment.setInitValue(info.materialNumber, info.materialType, info.plantNumber, info.investigatingTime,
                        STATUS_READ, info.observationId);
                mFragmentList.add(harvestPeriodFragment);
                break;
            case SURVEY_PERIOD_STORAGE:
                StoragePeriodFragment storagePeriodFragment = StoragePeriodFragment.newInstance();
                storagePeriodFragment.setInitValue(info.materialNumber, info.materialType, info.plantNumber, info.investigatingTime,
                        STATUS_READ, info.observationId);
                mFragmentList.add(storagePeriodFragment);
            case SURVEY_PERIOD_FLOWERING:
                FloweringPeriodFragment floweringPeriodFragment = FloweringPeriodFragment.newInstance();
                floweringPeriodFragment.setInitValue(info.materialNumber, info.materialType, info.plantNumber, info.investigatingTime,
                        STATUS_READ, info.observationId);
                mFragmentList.add(floweringPeriodFragment);
            case SURVEY_PERIOD_SEED_HARVEST:
                SeedHarvestPeriodFragment seedHarvestPeriodFragment = SeedHarvestPeriodFragment.newInstance();
                seedHarvestPeriodFragment.setInitValue(info.materialNumber, info.materialType, info.plantNumber, info.investigatingTime,
                        STATUS_READ, info.observationId);
                mFragmentList.add(seedHarvestPeriodFragment);
                break;
        }
    }

    private void addFragmentToStartList(HistoryInfo.data.Info info) {
        switch (info.obsPeriod) {
            case SURVEY_PERIOD_GERMINATION:
                GerminationPeriodFragment germinationPeriodFragment = GerminationPeriodFragment.newInstance();
                germinationPeriodFragment.setInitValue(info.materialNumber, info.materialType, info.plantNumber, info.investigatingTime,
                        STATUS_READ, info.observationId);
                mFragmentList.add(0, germinationPeriodFragment);
                break;
            case SURVEY_PERIOD_SEEDLING:
                SeedlingPeriodFragment seedlingPeriodFragment = SeedlingPeriodFragment.newInstance();
                seedlingPeriodFragment.setInitValue(info.materialNumber, info.materialType, info.plantNumber, info.investigatingTime,
                        STATUS_READ, info.observationId);
                mFragmentList.add(0, seedlingPeriodFragment);
                break;
            case SURVEY_PERIOD_ROSETTE:
                RosettePeriodFragment rosettePeriodFragment = RosettePeriodFragment.newInstance();
                rosettePeriodFragment.setInitValue(info.materialNumber, info.materialType, info.plantNumber, info.investigatingTime,
                        STATUS_READ, info.observationId);
                mFragmentList.add(0, rosettePeriodFragment);
                break;
            case SURVEY_PERIOD_HEADING:
                HeadingPeriodFragment headingPeriodFragment = HeadingPeriodFragment.newInstance();
                headingPeriodFragment.setInitValue(info.materialNumber, info.materialType, info.plantNumber, info.investigatingTime,
                        STATUS_READ, info.observationId);
                mFragmentList.add(0, headingPeriodFragment);
                break;
            case SURVEY_PERIOD_HARVEST:
                HarvestPeriodFragment harvestPeriodFragment = HarvestPeriodFragment.newInstance();
                harvestPeriodFragment.setInitValue(info.materialNumber, info.materialType, info.plantNumber, info.investigatingTime,
                        STATUS_READ, info.observationId);
                mFragmentList.add(0, harvestPeriodFragment);
                break;
            case SURVEY_PERIOD_STORAGE:
                StoragePeriodFragment storagePeriodFragment = StoragePeriodFragment.newInstance();
                storagePeriodFragment.setInitValue(info.materialNumber, info.materialType, info.plantNumber, info.investigatingTime,
                        STATUS_READ, info.observationId);
                mFragmentList.add(0, storagePeriodFragment);
            case SURVEY_PERIOD_FLOWERING:
                FloweringPeriodFragment floweringPeriodFragment = FloweringPeriodFragment.newInstance();
                floweringPeriodFragment.setInitValue(info.materialNumber, info.materialType, info.plantNumber, info.investigatingTime,
                        STATUS_READ, info.observationId);
                mFragmentList.add(0,floweringPeriodFragment);
            case SURVEY_PERIOD_SEED_HARVEST:
                SeedHarvestPeriodFragment seedHarvestPeriodFragment = SeedHarvestPeriodFragment.newInstance();
                seedHarvestPeriodFragment.setInitValue(info.materialNumber, info.materialType, info.plantNumber, info.investigatingTime,
                        STATUS_READ, info.observationId);
                mFragmentList.add(0,seedHarvestPeriodFragment);
                break;
        }
    }

}
