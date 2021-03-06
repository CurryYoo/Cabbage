package com.example.cabbage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cabbage.R;

import java.util.List;

public class SingleImageAdapter extends RecyclerView.Adapter<SingleImageHolder> {
    private Context mContext;
    private List<String> mList;
    private LayoutInflater inflater;

    private OnItemClickListener mOnItemClickListener;

    public SingleImageAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.mList = list;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public SingleImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_signle_image, parent, false);

        SingleImageHolder singleImageHolder = new SingleImageHolder(view);
        if (parent.getChildCount() < mList.size()) {
            String picUrl = mList.get(parent.getChildCount());
            Glide.with(mContext).load(picUrl).into(singleImageHolder.imageView);
        } else {
            singleImageHolder.imageView.setImageResource(R.mipmap.ic_add_photos);
        }
        return singleImageHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SingleImageHolder holder, int position) {
        if (position < mList.size()) {
            String picUrl = mList.get(position);
            Glide.with(mContext).load(picUrl).into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.mipmap.ic_add_photos);
        }
        // item click
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(view -> mOnItemClickListener.onItemClick(holder.itemView, position));
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        int count = mList == null ? 1 : mList.size() + 1;
        if (count > 4) {
            return mList.size();
        } else {
            return count;
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
