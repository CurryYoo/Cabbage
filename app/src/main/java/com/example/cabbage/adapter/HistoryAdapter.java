package com.example.cabbage.adapter;

import android.graphics.ColorSpace;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.cabbage.R;
import com.example.cabbage.entity.HistoryEntity;
import com.example.cabbage.network.HistoryInfo;

import java.util.List;

public class HistoryAdapter extends BaseQuickAdapter<HistoryInfo.data, BaseViewHolder> {
    public HistoryAdapter(int layoutResId, List<HistoryInfo.data> data){
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HistoryInfo.data item) {
        helper.setText(R.id.txt_materialType,item.getMaterialType());
        helper.setText(R.id.txt_materialNumber,item.getMaterialNumber());
        helper.setText(R.id.txt_plantNumber,item.getPlantNumber());
        helper.setText(R.id.txt_investigatingTime,item.getInvestigatingTime());
        helper.setText(R.id.txt_investigator,item.getInvestigator());
        helper.setText(R.id.txt_obsPeriod,item.getObsPeriod());
    }
}
