package com.dongjin.android.hongf.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongjin.android.hongf.R;

import java.util.ArrayList;

/**
 * Created by kimdongjin on 2017. 2. 17..
 */

public class DetailPhotoAdapter extends RecyclerView.Adapter<DetailPhotoAdapter.ViewHolder> {

    ArrayList<Uri> Images;

    public DetailPhotoAdapter(){
        Images=new ArrayList<>();
    }
    public void setImageDatas(ArrayList<Uri> images){
        this.Images=images;
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_review_photos,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return Images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
