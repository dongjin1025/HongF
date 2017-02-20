package com.dongjin.android.hongf.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dongjin.android.hongf.R;

import java.util.ArrayList;

/**
 * Created by kimdongjin on 2017. 2. 17..
 */

public class DetailPhotoAdapter extends RecyclerView.Adapter<DetailPhotoAdapter.ViewHolder> {

    ArrayList<Uri> images;
    Context context;
    public DetailPhotoAdapter(Context context){
        this.context=context;
        images=new ArrayList<>();
    }
    public void setImageDatas(ArrayList<Uri> images){
        this.images=images;
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_adapter,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        Glide.with(context).load(images.get(position)).into(holder.ig_detail);

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ig_detail;
        public ViewHolder(View itemView) {
            super(itemView);
            ig_detail=(ImageView)itemView.findViewById(R.id.ig_detail);
        }

    }
}
