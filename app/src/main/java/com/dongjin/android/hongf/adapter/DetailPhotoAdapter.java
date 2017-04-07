package com.dongjin.android.hongf.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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
        Log.e("IMAGES TAG",images.get(0).toString());

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_adapter,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.progressBar.setVisibility(View.VISIBLE);
        Glide.with(context).load(images.get(position)).listener(new RequestListener<Uri, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.ig_detail);

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ig_detail;
        ProgressBar progressBar;
        public ViewHolder(View itemView) {
            super(itemView);
            progressBar=(ProgressBar)itemView.findViewById(R.id.pb_photo);
            ig_detail=(ImageView)itemView.findViewById(R.id.ig_detail);
        }

    }
}
