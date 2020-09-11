package com.example.cabbage.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class HistoryPageAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList;

    public HistoryPageAdapter(@NonNull FragmentManager fm, int behavior, List<Fragment> list) {
        super(fm, behavior);
        fragmentList = list;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
