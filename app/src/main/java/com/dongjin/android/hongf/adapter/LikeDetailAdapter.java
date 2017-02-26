package com.dongjin.android.hongf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.model.InterestedUser;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by kimdongjin on 2017. 2. 25..
 */

public class LikeDetailAdapter extends RecyclerView.Adapter<LikeDetailAdapter.ViewHolder> {


    ArrayList<InterestedUser> users;
    Context context;
    public LikeDetailAdapter(Context context){
        users=new ArrayList<>();
        this.context=context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_like_detail,parent,false);

        return new ViewHolder(view);
    }
    public void setDatas(ArrayList<InterestedUser> users){
        this.users=users;
        notifyDataSetChanged();

    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        InterestedUser user=users.get(position);

        holder.username.setText(user.getUsername());
        if(user.getProfilepath()!=""){
            Glide.with(context).load(user.getProfilepath()).bitmapTransform(new CropCircleTransformation(context)
            ).into(holder.profile);
        }else{
            holder.profile.setImageResource(R.drawable.kakao_default_profile_image);
        }

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView username;
        public ViewHolder(View itemView) {
            super(itemView);
            profile=(ImageView)itemView.findViewById(R.id.likedetail_ig_profile);
            username=(TextView) itemView.findViewById(R.id.likedetail_tv_username);
        }
    }
}
