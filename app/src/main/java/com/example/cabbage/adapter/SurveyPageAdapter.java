package com.example.cabbage.adapter;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.cabbage.fragment.FloweringPeriodFragment;
import com.example.cabbage.fragment.GerminationPeriodFragment;
import com.example.cabbage.fragment.HarvestPeriodFragment;
import com.example.cabbage.fragment.HeadingPeriodFragment;
import com.example.cabbage.fragment.RosettePeriodFragment;
import com.example.cabbage.fragment.SeedHarvestPeriodFragment;
import com.example.cabbage.fragment.SeedlingPeriodFragment;
import com.example.cabbage.fragment.StoragePeriodFragment;

import timber.log.Timber;

import static com.example.cabbage.utils.StaticVariable.STATUS_NEW;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_FLOWERING;
import static com.example.cabbage.utils.UIUtils.checkPeriod;

public class SurveyPageAdapter extends FragmentStatePagerAdapter {
    private Fragment fragment;
    private Intent intent;
    private String materialId;
    private String materialType;
    private String plantId;
    private String investigatingTime;
    private String surveyId;
    private String surveyPeriod;
    private int status = STATUS_NEW;


    public SurveyPageAdapter(@NonNull FragmentManager fm, int behavior, Intent intent) {
        super(fm, behavior);
        this.intent = intent;
        this.materialId = intent.getStringExtra("materialId");
        this.materialType = intent.getStringExtra("materialType");
        this.plantId = intent.getStringExtra("plantId");
        this.investigatingTime = intent.getStringExtra("investigatingTime");
        this.surveyId = intent.getStringExtra("surveyId");
        this.surveyPeriod=intent.getStringExtra("surveyPeriod");
        this.status = intent.getIntExtra("status", STATUS_NEW);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                fragment = GerminationPeriodFragment.newInstance(materialId, materialType, plantId, investigatingTime, surveyId, status);
                break;
            case 1:
                fragment = SeedlingPeriodFragment.newInstance(materialId, materialType, plantId, investigatingTime, surveyId, status);
                break;
            case 2:
                fragment = RosettePeriodFragment.newInstance(materialId, materialType, plantId, investigatingTime, surveyId, status);
                break;
            case 3:
                fragment = HeadingPeriodFragment.newInstance(materialId, materialType, plantId, investigatingTime, surveyId, status);
                break;
            case 4:
                fragment = HarvestPeriodFragment.newInstance(materialId, materialType, plantId, investigatingTime, surveyId, status);
                break;
            case 5:
                fragment = StoragePeriodFragment.newInstance(materialId, materialType, plantId, investigatingTime, surveyId, status);
                break;
            case 6:
                fragment = FloweringPeriodFragment.newInstance(materialId, materialType, plantId, investigatingTime,surveyId, status);
                break;
            case 7:
                fragment = SeedHarvestPeriodFragment.newInstance(materialId, materialType, plantId, investigatingTime, checkPeriod(SURVEY_PERIOD_FLOWERING, surveyPeriod, surveyId), status);
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 8;
    }
}
