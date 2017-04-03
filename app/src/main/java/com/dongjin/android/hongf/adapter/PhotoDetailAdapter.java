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
 * Created by kimdongjin on 2017. 4. 1..
 */

public class PhotoDetailAdapter extends RecyclerView.Adapter<PhotoDetailAdapter.ViewHolder> {

    private ArrayList<String> urls;
    private Context context;
    public PhotoDetailAdapter(ArrayList<String> urls,Context context){
        this.urls=urls;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_photo,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Uri uri= Uri.parse(urls.get(position));
        Glide.with(context).load(uri).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.photo_detail_ig);
        }
    }
}
