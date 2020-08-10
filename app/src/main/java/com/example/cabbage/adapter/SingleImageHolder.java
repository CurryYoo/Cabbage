package com.example.cabbage.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cabbage.R;

public class SingleImageHolder extends RecyclerView.ViewHolder {

    ImageView imageView;

    public SingleImageHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.imageUpload);
    }
}
