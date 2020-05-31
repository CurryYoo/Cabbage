package com.example.cabbage.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cabbage.R;

/**
 * Author: xiemugan
 * Date: 2020/5/31
 * Description:
 **/
public class InfoItemBar extends LinearLayout {
    private LinearLayout mLinearLayout, mItemBar;
    private ImageView Image;

    public InfoItemBar(Context mContext, String title)
    {
        super(mContext);
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_bar, this);
        this.mLinearLayout = view.findViewById(R.id.itemBar_content);
        this.mItemBar = view.findViewById(R.id.itembar);
        this.Image =  view.findViewById(R.id.item_img_title);
        TextView titleTextView =  view.findViewById(R.id.itemBar_title);
        titleTextView.setText(title);

        mItemBar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLinearLayout.getVisibility() == View.GONE) {
                    mLinearLayout.setVisibility(View.VISIBLE);
                    Image.setImageResource(R.drawable.item_pressed);
                } else {
                    mLinearLayout.setVisibility(View.GONE);
                    Image.setImageResource(R.drawable.item_unpressed);
                }
            }

        });
    }
    /**
     * 设置 项目集 是否展开
     *
     * @param isShow
     */
    public void setShow(boolean isShow) {
        if (isShow) {
            mLinearLayout.setVisibility(View.VISIBLE);
            Image.setImageResource(R.drawable.item_pressed);
        } else {
            mLinearLayout.setVisibility(View.GONE);
            Image.setImageResource(R.drawable.item_unpressed);
        }
    }
    /**
     * 添加 infoItem项目
     *
     * @param item
     */
    public void addView(View item) {

        mLinearLayout.addView(item);

    }

    /**
     * 改变itemBar颜色
     *
     * @param drawable
     */
    public void setColor(Drawable drawable) {

        mItemBar.setBackground(drawable);

    }
}
