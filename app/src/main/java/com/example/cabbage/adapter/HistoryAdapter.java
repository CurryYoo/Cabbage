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
        helper.setText(R.id.txt_obsPeriod, mContext.getString(R.string.observation_period) + item.getObsPeriod());
        helper.setText(R.id.txt_materialType, mContext.getString(R.string.material_type) + item.getMaterialType());
        helper.setText(R.id.txt_materialNumber, mContext.getString(R.string.material_number) + item.getMaterialNumber());
        helper.setText(R.id.txt_plantNumber, mContext.getString(R.string.plant_number) + item.getPlantNumber());
        helper.setText(R.id.txt_investigatingTime, mContext.getString(R.string.investigating_time) + item.getInvestigatingTime());
        helper.setText(R.id.txt_investigator, mContext.getString(R.string.investigator) + item.getInvestigator());
        helper.addOnClickListener(R.id.btn_copy);
        helper.addOnClickListener(R.id.btn_create);
    }
}
