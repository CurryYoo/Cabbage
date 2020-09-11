package com.example.cabbage.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.cabbage.R;
import com.example.cabbage.adapter.SurveyPageAdapter;
import com.example.cabbage.fragment.FloweringPeriodFragment;
import com.example.cabbage.fragment.GerminationPeriodFragment;
import com.example.cabbage.fragment.HarvestPeriodFragment;
import com.example.cabbage.fragment.HeadingPeriodFragment;
import com.example.cabbage.fragment.RosettePeriodFragment;
import com.example.cabbage.fragment.SeedHarvestPeriodFragment;
import com.example.cabbage.fragment.SeedlingPeriodFragment;
import com.example.cabbage.fragment.StoragePeriodFragment;
import com.example.cabbage.utils.ARouterPaths;
import com.example.cabbage.view.ScaleTransitionPagerTitleView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.TriangularPagerIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.cabbage.utils.StaticVariable.STATUS_NEW;
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
 * Annotation:调查页面
 */
@Route(path = ARouterPaths.SURVEY_ACTIVITY)
public class SurveyActivity extends AppCompatActivity {
    @Autowired(name = "materialId")
    public String materialId;
    @Autowired(name = "materialType")
    public String materialType;
    @Autowired(name = "plantId")
    public String plantId;
    @Autowired(name = "investigatingTime")
    public String investigatingTime;
    @Autowired
    public int status = STATUS_NEW;
    @Autowired(name = "surveyId")
    public String surveyId;
    @Autowired(name = "surveyPeriod")
    public String surveyPeriod;

    @BindView(R.id.view_pager_indicator)
    MagicIndicator viewPagerIndicator;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.left_one_button)
    ImageView leftOneButton;
    @BindView(R.id.left_one_layout)
    LinearLayout leftOneLayout;
    View.OnClickListener toolBarOnClickListener = v -> {
        switch (v.getId()) {
            case R.id.left_one_layout:
                finish();
            default:
                break;
        }
    };
    @BindView(R.id.title_text)
    TextView titleText;
    private List<String> mTitleDataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_survey);
        ButterKnife.bind(this);

        surveyPeriod=getIntent().getStringExtra("surveyPeriod");

        mTitleDataList = new ArrayList<String>() {{
            add(getResources().getString(R.string.title_germination_period));
            add(getResources().getString(R.string.title_seedling_period));
            add(getResources().getString(R.string.title_rosette_period));
            add(getResources().getString(R.string.title_heading_period));
            add(getResources().getString(R.string.title_harvest_period));
            add(getResources().getString(R.string.title_storage_period));
            add(getResources().getString(R.string.title_flowering_period));
            add(getResources().getString(R.string.title_seed_harvest_period));
        }};

        initToolbar();
        initViewPager();
    }

    private void initToolbar() {
        leftOneButton.setBackgroundResource(R.mipmap.ic_back);
        leftOneLayout.setBackgroundResource(R.drawable.selector_trans_button);
        titleText.setText(R.string.species_data_pick);
        leftOneLayout.setOnClickListener(toolBarOnClickListener);
    }

    private void initViewPager() {
        //viewpager and fragment初始化
        SurveyPageAdapter surveyPageAdapter = new SurveyPageAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, getIntent());
        viewPager.setAdapter(surveyPageAdapter);

        //indicator初始化
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitleDataList == null ? 0 : mTitleDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                ScaleTransitionPagerTitleView scaleTransitionPagerTitleView = new ScaleTransitionPagerTitleView(context);
                scaleTransitionPagerTitleView.setNormalColor(Color.GRAY);
                scaleTransitionPagerTitleView.setSelectedColor(getResources().getColor(R.color.colorRed));
                scaleTransitionPagerTitleView.setText(mTitleDataList.get(index));
                scaleTransitionPagerTitleView.setOnClickListener(v -> viewPager.setCurrentItem(index));
                return scaleTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                TriangularPagerIndicator indicator = new TriangularPagerIndicator(context);
                indicator.setLineColor(getResources().getColor(R.color.colorRed));
                return indicator;
            }
        });
        viewPagerIndicator.setNavigator(commonNavigator);
        //绑定magic indicator
        ViewPagerHelper.bind(viewPagerIndicator, viewPager);

        //复制粘贴创建数据时自动跳转
        if (surveyPeriod != null) {
            switch (surveyPeriod) {
                case SURVEY_PERIOD_GERMINATION:
                    viewPager.setCurrentItem(0);
                    break;
                case SURVEY_PERIOD_SEEDLING:
                    viewPager.setCurrentItem(1);
                    break;
                case SURVEY_PERIOD_ROSETTE:
                    viewPager.setCurrentItem(2);
                    break;
                case SURVEY_PERIOD_HEADING:
                    viewPager.setCurrentItem(3);
                    break;
                case SURVEY_PERIOD_HARVEST:
                    viewPager.setCurrentItem(4);
                    break;
                case SURVEY_PERIOD_STORAGE:
                    viewPager.setCurrentItem(5);
                    break;
                case SURVEY_PERIOD_FLOWERING:
                    viewPager.setCurrentItem(6);
                    break;
                case SURVEY_PERIOD_SEED_HARVEST:
                    viewPager.setCurrentItem(7);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getSupportFragmentManager().getFragments();
        //调用fragment的onActivityResult
        if (getSupportFragmentManager().getFragments().size() > 0) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment mFragment : fragments) {
                mFragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
