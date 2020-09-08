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
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.cabbage.R;
import com.example.cabbage.adapter.SurveyPageAdapter;
import com.example.cabbage.fragment.DiseasePeriodFragment;
import com.example.cabbage.fragment.GSRPeriodFragment;
import com.example.cabbage.fragment.HeadingPeriodFragment;
import com.example.cabbage.fragment.SFSPeriodFragment;
import com.example.cabbage.utils.ARouterPaths;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.cabbage.utils.StaticVariable.STATUS_NEW;

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
    private SFSPeriodFragment sfsPeriodFragment = SFSPeriodFragment.newInstance();
    private Fragment headingPeriodFragment = HeadingPeriodFragment.newInstance();
    private Fragment gsrPeriodFragment = GSRPeriodFragment.newInstance();
    private Fragment diseasePeriodFragment = DiseasePeriodFragment.newInstance();
    private List<Fragment> mFragmentList = new ArrayList<Fragment>() {
        {
            add(sfsPeriodFragment);
            add(headingPeriodFragment);
            add(gsrPeriodFragment);
            add(diseasePeriodFragment);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_survey);
        ButterKnife.bind(this);

        materialId = getIntent().getStringExtra("materialId");
        materialType = getIntent().getStringExtra("materialType");
        status = getIntent().getIntExtra("status", STATUS_NEW);
        plantId = getIntent().getStringExtra("plantId");
        surveyId = getIntent().getStringExtra("surveyId");
        surveyPeriod = getIntent().getStringExtra("surveyPeriod");
        investigatingTime = getIntent().getStringExtra("investigatingTime");
        mTitleDataList = new ArrayList<String>() {{
            add(getResources().getString(R.string.title_germination_period));
            add(getResources().getString(R.string.title_heading_period));
            add(getResources().getString(R.string.title_storage_period));
            add(getResources().getString(R.string.disease_resistance));
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
        SurveyPageAdapter surveyPageAdapter = new SurveyPageAdapter(getSupportFragmentManager(), 0, mFragmentList);
        viewPager.setAdapter(surveyPageAdapter);
        //向Fragment传参
        SFSPeriodFragment sfsPeriodFragment1 = (SFSPeriodFragment) surveyPageAdapter.instantiateItem(viewPager,0);
        sfsPeriodFragment1.setInitValue(materialId, materialType, plantId, investigatingTime, status, surveyId, surveyPeriod);

        HeadingPeriodFragment headingPeriodFragment1 = (HeadingPeriodFragment) surveyPageAdapter.instantiateItem(viewPager,1);
        headingPeriodFragment1.setInitValue(materialId, materialType, plantId, investigatingTime, status, surveyId, surveyPeriod);

        //indicator初始化
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitleDataList == null ? 0 : mTitleDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.GRAY);
                colorTransitionPagerTitleView.setSelectedColor(getResources().getColor(R.color.colorRed));
                colorTransitionPagerTitleView.setText(mTitleDataList.get(index));
                colorTransitionPagerTitleView.setOnClickListener(v -> viewPager.setCurrentItem(index));
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(getResources().getColor(R.color.colorRed));
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                return indicator;
            }
        });
        commonNavigator.setAdjustMode(true);
        viewPagerIndicator.setNavigator(commonNavigator);
        //绑定magic indicator
        ViewPagerHelper.bind(viewPagerIndicator, viewPager);
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
