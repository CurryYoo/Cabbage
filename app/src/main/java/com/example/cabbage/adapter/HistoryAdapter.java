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
        helper.setText(R.id.txt_obsPeriod, "观测时期："+item.getObsPeriod());
        helper.setText(R.id.txt_materialType, "材料类型："+item.getMaterialType());
        helper.setText(R.id.txt_materialNumber, "材料编号："+item.getMaterialNumber());
        helper.setText(R.id.txt_plantNumber, "单株编号："+item.getPlantNumber());
        helper.setText(R.id.txt_investigatingTime,"调查时间："+ item.getInvestigatingTime());
        helper.setText(R.id.txt_investigator, "调查人员："+item.getInvestigator());
//        helper.setText(R.id.txt_location, "调查位置："+item.getLocation());
    }
}
