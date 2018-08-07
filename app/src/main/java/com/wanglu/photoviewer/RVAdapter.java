package com.wanglu.photoviewer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by WangLu on 2018/7/18.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {

    private List<String> mData;
    private Context mContext;
    private OnClickListener l;
    public View itemView;
    private ViewHolder mViewHolder;

    public void setOnClickListener(OnClickListener l) {
        this.l = l;
    }

    public RVAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<String> data) {
        mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img, parent, false);
        mViewHolder = new ViewHolder(view);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Glide.with(mContext).load(mData.get(position)).into(holder.iv);
        itemView = holder.itemView;
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (l != null){
                    int[] location = new int[2];
                    holder.itemView.getLocationInWindow(location);
                    l.onClick(position, location);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView iv;

        public ViewHolder(View itemView) {
            super(itemView);

            iv = itemView.findViewById(R.id.itemIv);
        }
    }


    interface OnClickListener{
        void onClick(int position, int[] location);
    }
}
