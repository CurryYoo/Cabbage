package com.example.cabbage.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cabbage.R;
/**
 * Author:created by Kang on 2020/9/9
 * Email:zyk970512@163.com
 * Annotation:莲座期
 */
public class RosettePeriodFragment extends Fragment {

    public static RosettePeriodFragment newInstance() {
        return new RosettePeriodFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rosette_period, container, false);
        return view;
    }
}
