package com.example.cabbage.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.cabbage.R;
import com.example.cabbage.network.HistoryInfo;

import java.util.List;

public class HistoryAdapter extends BaseQuickAdapter<HistoryInfo.data.Info, BaseViewHolder> {
    public HistoryAdapter(int layoutResId, List<HistoryInfo.data.Info> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HistoryInfo.data.Info item) {
//        helper.setText(R.id.txt_observationId, "观测Id："+item.getObservationId());
        helper.setText(R.id.txt_obsPeriod, R.string.observation_period+item.getObsPeriod());
        helper.setText(R.id.txt_materialType, R.string.material_type+item.getMaterialType());
        helper.setText(R.id.txt_materialNumber, R.string.material_number+item.getMaterialNumber());
        helper.setText(R.id.txt_plantNumber, R.string.plant_number+item.getPlantNumber());
        helper.setText(R.id.txt_investigatingTime,R.string.investigating_time+ item.getInvestigatingTime());
        helper.setText(R.id.txt_investigator, R.string.investigator+item.getInvestigator());
    }
}
