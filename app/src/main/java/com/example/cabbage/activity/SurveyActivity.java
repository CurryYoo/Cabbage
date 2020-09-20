package com.example.cabbage.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.cabbage.R;
import com.example.cabbage.adapter.SurveyPageAdapter;
import com.example.cabbage.base.BaseActivity;
import com.example.cabbage.utils.ARouterPaths;
import com.example.cabbage.view.ScaleTransitionPagerTitleView;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
import timber.log.Timber;

import static com.example.cabbage.utils.StaticVariable.STATUS_NEW;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_FLOWERING;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_GERMINATION;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_HARVEST;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_HEADING;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_ROSETTE;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_SEEDLING;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_SEED_HARVEST;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_STORAGE;
import static com.example.cabbage.utils.UIUtils.getSystemTime;

/**
 * Author:created by Kang on 2020/9/9
 * Email:zyk970512@163.com
 * Annotation:调查页面
 */
@Route(path = ARouterPaths.SURVEY_ACTIVITY)
public class SurveyActivity extends BaseActivity {
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

    //存储最近三个材料信息
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @BindView(R.id.view_pager_indicator)
    MagicIndicator viewPagerIndicator;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.left_one_button)
    ImageView leftOneButton;
    @BindView(R.id.left_one_layout)
    LinearLayout leftOneLayout;
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
    private List<String> mTitleDataList;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_survey);
        ButterKnife.bind(this);

        intent = getIntent();
        materialId = intent.getStringExtra("materialId");
        materialType = intent.getStringExtra("materialType");
        surveyPeriod = intent.getStringExtra("surveyPeriod");

        sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        editor = sp.edit();

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
        SurveyPageAdapter surveyPageAdapter = new SurveyPageAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, intent);
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
    public void initLastMaterial(String surveyPeriod) {
        SharedPreferences sp=getSharedPreferences("lastMaterial",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        JsonObject jsonObject = new JsonObject();

        JsonObject jsonObjectOld = new JsonParser().parse(sp.getString("lastMaterial", jsonObject.toString())).getAsJsonObject();
        if (jsonObjectOld.get("lastMaterialNumber2") != null) {
            if (jsonObjectOld.get("lastMaterialNumber2").getAsString().equals(materialId)) {
                if (jsonObjectOld.get("lastMaterialNumber1") != null) {
                    jsonObject.addProperty("lastMaterialNumber1", jsonObjectOld.get("lastMaterialNumber1").getAsString());
                    jsonObject.addProperty("lastMaterialType1", jsonObjectOld.get("lastMaterialType1").getAsString());
                    jsonObject.addProperty("lastMaterialTime1", jsonObjectOld.get("lastMaterialTime1").getAsString());
                    jsonObject.addProperty("lastMaterialPeriod1", jsonObjectOld.get("lastMaterialPeriod1").getAsString());
                }
                jsonObject.addProperty("lastMaterialNumber2", jsonObjectOld.get("lastMaterialNumber3").getAsString());
                jsonObject.addProperty("lastMaterialType2", jsonObjectOld.get("lastMaterialType3").getAsString());
                jsonObject.addProperty("lastMaterialTime2", jsonObjectOld.get("lastMaterialTime3").getAsString());
                jsonObject.addProperty("lastMaterialPeriod2", jsonObjectOld.get("lastMaterialPeriod3").getAsString());

                jsonObject.addProperty("lastMaterialNumber3", materialId);
                jsonObject.addProperty("lastMaterialType3", materialType);
                jsonObject.addProperty("lastMaterialTime3", getSystemTime());
                jsonObject.addProperty("lastMaterialPeriod3", surveyPeriod);
                editor.putString("lastMaterial", jsonObject.toString());
                editor.apply();
                return;
            }
            jsonObject.addProperty("lastMaterialNumber1", jsonObjectOld.get("lastMaterialNumber2").getAsString());
            jsonObject.addProperty("lastMaterialType1", jsonObjectOld.get("lastMaterialType2").getAsString());
            jsonObject.addProperty("lastMaterialTime1", jsonObjectOld.get("lastMaterialTime2").getAsString());
            jsonObject.addProperty("lastMaterialPeriod1", jsonObjectOld.get("lastMaterialPeriod2").getAsString());
        }
        if (jsonObjectOld.get("lastMaterialNumber3") != null) {
            if (jsonObjectOld.get("lastMaterialNumber3").getAsString().equals(materialId)) {
                jsonObjectOld.addProperty("lastMaterialNumber3", materialId);
                jsonObjectOld.addProperty("lastMaterialType3", materialType);
                jsonObjectOld.addProperty("lastMaterialTime3", getSystemTime());
                jsonObjectOld.addProperty("lastMaterialPeriod3", surveyPeriod);
                editor.putString("lastMaterial", jsonObjectOld.toString());
                editor.apply();
                return;
            }
            jsonObject.addProperty("lastMaterialNumber2", jsonObjectOld.get("lastMaterialNumber3").getAsString());
            jsonObject.addProperty("lastMaterialType2", jsonObjectOld.get("lastMaterialType3").getAsString());
            jsonObject.addProperty("lastMaterialTime2", jsonObjectOld.get("lastMaterialTime3").getAsString());
            jsonObject.addProperty("lastMaterialPeriod2", jsonObjectOld.get("lastMaterialPeriod3").getAsString());
        }

        jsonObject.addProperty("lastMaterialNumber3", materialId);
        jsonObject.addProperty("lastMaterialType3", materialType);
        jsonObject.addProperty("lastMaterialTime3", getSystemTime());
        jsonObject.addProperty("lastMaterialPeriod3", surveyPeriod);

        editor.putString("lastMaterial", jsonObject.toString());
        editor.apply();
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
