package com.example.cabbage.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.cabbage.R;
import com.example.cabbage.network.MaterialData;

import java.util.List;

/**
 * Author:Kang
 * Date:2020/9/19
 * Description:
 */
public class LastMaterialAdapter extends BaseQuickAdapter<MaterialData, BaseViewHolder> {
    private Context mContext;

    public LastMaterialAdapter(Context context, int layoutResId, @Nullable List<MaterialData> data) {
        super(layoutResId, data);
        mContext = context;
    }

    public LastMaterialAdapter(Context context, @Nullable List<MaterialData> data) {
        super(data);
        mContext = context;
    }

    public LastMaterialAdapter(Context context, int layoutResId) {
        super(layoutResId);
        mContext = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MaterialData item) {
        helper.setText(R.id.txt_materialNumber, mContext.getResources().getString(R.string.material_number)
                + item.materialNumber
                + "   "
                + mContext.getResources().getString(R.string.material_type)
                + item.materialType
                + "   "
                + mContext.getResources().getString(R.string.investigating_time)
                + item.year);
    }
}
