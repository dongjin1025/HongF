package com.dongjin.android.hongf.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dongjin.android.hongf.R;

import java.util.ArrayList;

/**
 * Created by kimdongjin on 2017. 1. 30..
 */

public class PostReivewPhotosAdapter extends RecyclerView.Adapter<PostReivewPhotosAdapter.ViewHolder> {


    ArrayList<Bitmap> bitmaps;
    public PostReivewPhotosAdapter(){
        bitmaps=new ArrayList<>();
    }

    public void setReviewPhotos(ArrayList<Bitmap> bitmaps){

        this.bitmaps=bitmaps;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_review_photos,parent,false);


        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.igReviewPhoto.setImageBitmap(bitmaps.get(position));

    }

    @Override
    public int getItemCount() {
        return bitmaps.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView igReviewPhoto;
        public ViewHolder(View itemView) {
            super(itemView);

            igReviewPhoto=(ImageView)itemView.findViewById(R.id.igReviewPhoto);
        }
    }
}
