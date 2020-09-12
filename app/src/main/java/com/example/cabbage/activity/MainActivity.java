package com.example.cabbage.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.example.cabbage.R;
import com.example.cabbage.base.BaseActivity;
import com.example.cabbage.fragment.MainFragment;
import com.example.cabbage.fragment.UserFragment;
import com.example.cabbage.utils.ARouterPaths;
import com.example.cabbage.utils.FragmentUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = ARouterPaths.MAIN_ACTIVITY)
public class MainActivity extends BaseActivity {
    @BindView(R.id.main_frame)
    FrameLayout mainFrame;
    @BindView(R.id.bottomBar)
    BottomBarLayout bottomBar;


    private List<Fragment> mFragmentList = new ArrayList<>();
    private MainFragment mainFragment;
    private UserFragment userFragment;
    private List<Integer> mTitles;
    private int currentPosition=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_main);
        ButterKnife.bind(this);
        initData(savedInstanceState);
    }

    private void initData(Bundle savedInstanceState) {
        if (mTitles == null) {
            mTitles = new ArrayList<>();
            mTitles.add(R.string.home_page);
            mTitles.add(R.string.user_page);
        }
        if(savedInstanceState==null) {
            mainFragment = MainFragment.newInstance();
            userFragment = UserFragment.newInstance();
        }else {
            FragmentManager fm=getSupportFragmentManager();
            mainFragment =(MainFragment) FragmentUtils.findFragment(fm, MainFragment.class);
            userFragment=(UserFragment)FragmentUtils.findFragment(fm,UserFragment.class);
        }

        mFragmentList.add(mainFragment);
        mFragmentList.add(userFragment);
        initView();
    }

    private void initView(){
        showFragment(0);
        bottomBar.setOnItemSelectedListener((bottomBarItem, previousPosition, currentPosition) -> changeFragment(currentPosition));
    }
    private void changeFragment(int fg) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        //如果之前没有添加过
        if (!mFragmentList.get(fg).isAdded()) {
            transaction
                    .hide(mFragmentList.get(currentPosition))
                    .add(R.id.main_frame, mFragmentList.get(fg));
        } else {
            transaction
                    .hide(mFragmentList.get(currentPosition))
                    .show(mFragmentList.get(fg));
        }

        //全局变量，记录当前显示的fragment
        currentPosition = fg;
        transaction.commit();
    }
    private void showFragment(int currentPosition) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame, mFragmentList.get(currentPosition));
        transaction.commit();
    }
    //语言设置后重启
    public static void reStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
