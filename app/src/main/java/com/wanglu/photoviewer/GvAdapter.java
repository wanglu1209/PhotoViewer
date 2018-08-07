package com.wanglu.photoviewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

public class GvAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mData;

    public GvAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<String> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        if (convertView == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_img, parent, false);
            holder = new ViewHolder();
            holder.mPhotoView = view.findViewById(R.id.itemIv);
            view.setTag(holder);
        }else{
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        Glide.with(mContext).load(mData.get(position)).into(holder.mPhotoView);
        return view;
    }

    class ViewHolder{
        ImageView mPhotoView;
    }
}
