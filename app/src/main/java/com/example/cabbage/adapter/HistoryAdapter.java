package com.example.cabbage.adapter;

import android.graphics.ColorSpace;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.cabbage.R;
import com.example.cabbage.entity.HistoryEntity;

import java.util.List;

public class HistoryAdapter extends BaseQuickAdapter<HistoryEntity, BaseViewHolder> {
    public HistoryAdapter(int layoutResId, List<HistoryEntity> data){
        super(layoutResId, data);
    }
    @Override
    protected void convert(@NonNull BaseViewHolder helper, HistoryEntity item) {
        helper.setText(R.id.txt_history_Id,item.getID());
    }
}
