package com.example.cabbage.activity;

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
import com.example.cabbage.fragment.MainFragment;
import com.example.cabbage.fragment.UserFragment;
import com.example.cabbage.utils.ARouterPaths;
import com.example.cabbage.utils.FragmentUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = ARouterPaths.MAIN_ACTIVITY)
public class MainActivity extends AppCompatActivity {
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
        bottomBar.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int previousPosition, int currentPosition) {
                changeFragment(currentPosition);
            }
        });
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

//        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
//        if (homeFragment!= null&& homeFragment.isAdded()){
//            transaction.hide(homeFragment);
//        }
//        if (momentFragment!= null&& momentFragment.isAdded()){
//            transaction.hide(momentFragment);
//        }
//        if (messageFragment!= null&& messageFragment.isAdded()){
//            transaction.hide(messageFragment);
//        }
//        if (meFragment!= null&& meFragment.isAdded()){
//            transaction.hide(meFragment);
//        }
//        transaction.add(R.id.main_frame, mFragmentList.get(currentPosition));
//        transaction.commit();

//        transaction.replace(R.id.main_frame, mFragmentList.get(currentPosition));
//        transaction.commitAllowingStateLoss();
//        homeFragment= null;
//        momentFragment= null;
//        messageFragment= null;
//        meFragment= null;
    }

}
