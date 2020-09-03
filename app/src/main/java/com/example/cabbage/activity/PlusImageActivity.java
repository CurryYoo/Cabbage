package com.example.cabbage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.cabbage.R;
import com.example.cabbage.adapter.PlusViewPagerAdapter;
import com.example.cabbage.utils.MainConstant;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class PlusImageActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {


    private ViewPager viewPager; //展示图片的ViewPager
    private TextView positionTv; //图片的位置，第几张图片
    private ArrayList<String> imgList; //图片的数据源
    private int mPosition; //第几张图片
    private PlusViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus_image);
        ButterKnife.bind(this);
        initData(savedInstanceState);
    }
//    public int initView(@Nullable Bundle savedInstanceState) {
//        return R.layout.activity_plus_image; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
//    }
    public void initData(@Nullable Bundle savedInstanceState) {
        imgList = getIntent().getStringArrayListExtra(MainConstant.IMG_LIST);
        mPosition = getIntent().getIntExtra(MainConstant.POSITION, 0);
        initImageView();
    }


    private void initImageView() {
        viewPager = findViewById(R.id.viewPager);
        positionTv = findViewById(R.id.position_tv);
        findViewById(R.id.back_iv).setOnClickListener(this);
        findViewById(R.id.delete_iv).setOnClickListener(this);
        viewPager.addOnPageChangeListener(this);

        mAdapter = new PlusViewPagerAdapter(this, imgList);
        viewPager.setAdapter(mAdapter);
        positionTv.setText(mPosition + 1 + "/" + imgList.size());
        viewPager.setCurrentItem(mPosition);
    }

    //删除图片
    private void deletePic() {
        //TODO 删除图片时弹出确认框确认

        imgList.remove(mPosition); //从数据源移除删除的图片
        setPosition();

    }

    //设置当前位置
    private void setPosition() {
        if (imgList.size() == 0) {
            positionTv.setText(mPosition + 0 + "/" + imgList.size());
        } else {
            positionTv.setText(mPosition + 1 + "/" + imgList.size());
        }
        viewPager.setCurrentItem(mPosition);
        mAdapter.notifyDataSetChanged();
    }

    //返回上一个页面
    private void back() {
        Intent intent = getIntent();
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, imgList);
        setResult(MainConstant.RESULT_CODE_VIEW_IMG, intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                //返回
                back();
                break;
            case R.id.delete_iv:
                //删除图片
                deletePic();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //按下了返回键
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPosition = position;
        positionTv.setText(position + 1 + "/" + imgList.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
