package com.example.cabbage.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.cabbage.fragment.FloweringPeriodFragment;
import com.example.cabbage.fragment.GerminationPeriodFragment;
import com.example.cabbage.fragment.HarvestPeriodFragment;
import com.example.cabbage.fragment.HeadingPeriodFragment;
import com.example.cabbage.fragment.RosettePeriodFragment;
import com.example.cabbage.fragment.SeedHarvestPeriodFragment;
import com.example.cabbage.fragment.SeedlingPeriodFragment;
import com.example.cabbage.fragment.StoragePeriodFragment;
import com.example.cabbage.network.HistoryInfo;

import java.util.List;

import static com.example.cabbage.utils.StaticVariable.STATUS_READ;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_FLOWERING;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_GERMINATION;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_HARVEST;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_HEADING;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_ROSETTE;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_SEEDLING;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_SEED_HARVEST;
import static com.example.cabbage.utils.StaticVariable.SURVEY_PERIOD_STORAGE;

/**
 * Author:Kang
 * Date:2020/9/12
 * Description:
 */
public class HistoryDetailAdapter extends FragmentStateAdapter {
    private Fragment fragment;
    private List<HistoryInfo.data.Info> infoList;

    public HistoryDetailAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<HistoryInfo.data.Info> infoList) {
        super(fragmentManager, lifecycle);
        this.infoList=infoList;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        fragment = chooseFragment(infoList.get(position).obsPeriod, infoList.get(position));
        return fragment;
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    private Fragment chooseFragment(String period, HistoryInfo.data.Info info) {
        Fragment fragment = null;
        switch (period) {
            case SURVEY_PERIOD_GERMINATION:
                fragment = GerminationPeriodFragment.newInstance(info.materialNumber, info.materialType, info.plantNumber, info.investigatingTime,
                        info.observationId, STATUS_READ);
                break;
            case SURVEY_PERIOD_SEEDLING:
                fragment = SeedlingPeriodFragment.newInstance(info.materialNumber, info.materialType, info.plantNumber, info.investigatingTime,
                        info.observationId, STATUS_READ);
                break;
            case SURVEY_PERIOD_ROSETTE:
                fragment = RosettePeriodFragment.newInstance(info.materialNumber, info.materialType, info.plantNumber, info.investigatingTime,
                        info.observationId, STATUS_READ);
                break;
            case SURVEY_PERIOD_HEADING:
                fragment = HeadingPeriodFragment.newInstance(info.materialNumber, info.materialType, info.plantNumber, info.investigatingTime,
                        info.observationId, STATUS_READ);
                break;
            case SURVEY_PERIOD_HARVEST:
                fragment = HarvestPeriodFragment.newInstance(info.materialNumber, info.materialType, info.plantNumber, info.investigatingTime,
                        info.observationId, STATUS_READ);
                break;
            case SURVEY_PERIOD_STORAGE:
                fragment = StoragePeriodFragment.newInstance(info.materialNumber, info.materialType, info.plantNumber, info.investigatingTime,
                        info.observationId, STATUS_READ);
                break;
            case SURVEY_PERIOD_FLOWERING:
                fragment = FloweringPeriodFragment.newInstance(info.materialNumber, info.materialType, info.plantNumber, info.investigatingTime,
                        info.observationId, STATUS_READ);
                break;
            case SURVEY_PERIOD_SEED_HARVEST:
                fragment = SeedHarvestPeriodFragment.newInstance(info.materialNumber, info.materialType, info.plantNumber, info.investigatingTime,
                        info.observationId, STATUS_READ);
                break;
        }
        return fragment;
    }

}
