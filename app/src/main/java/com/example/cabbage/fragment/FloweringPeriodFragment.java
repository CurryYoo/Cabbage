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
 * Author:Kang
 * Date:2020/9/10
 * Description:现蕾开花期
 */
public class FloweringPeriodFragment extends Fragment {

    public static FloweringPeriodFragment newInstance() {
        return new FloweringPeriodFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flowering_period, container, false);
        return view;
    }


}
