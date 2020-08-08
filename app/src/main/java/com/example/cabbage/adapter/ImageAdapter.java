package com.example.cabbage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.cabbage.R;

import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mList;
    private LayoutInflater inflater;

    public ImageAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.mList = list;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        int count = mList == null ? 1 : mList.size() + 1;
        if (count > 8) {
            return mList.size();
        } else {
            return count;
        }
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_nine_image, parent, false);
        ImageView imageView = convertView.findViewById(R.id.imageUpload);
        if (position < mList.size()) {
            String picUrl = mList.get(position);
            Glide.with(mContext).load(picUrl).into(imageView);
        } else {
            imageView.setImageResource(R.mipmap.ic_default_add_img);
        }
        return convertView;
    }
}
