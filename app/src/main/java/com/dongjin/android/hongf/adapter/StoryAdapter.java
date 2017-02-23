package com.dongjin.android.hongf.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.model.Review;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by kimdongjin on 2017. 1. 16..
 */

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {

    Context context;
    DatabaseReference storyRef= FirebaseDatabase.getInstance().getReference();
    ArrayList<Review> reviews;
    ArrayList<String> keyArray;

    public StoryAdapter(Context context){
        this.context=context;
        reviews=new ArrayList<>();
        keyArray=new ArrayList<>();
        storyRef.child("Story").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Review review=dataSnapshot.getValue(Review.class);
                String key=dataSnapshot.getKey();
                keyArray.add(key);
                Log.e("keyArrayTag",""+keyArray.size());
                reviews.add(review);
                notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


        Log.e("keyArrayTag2",""+keyArray.size());



    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story,parent,false);


        return new StoryAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Review review=reviews.get(position);

        if(keyArray.size()!=0){
            StoryPhotoAdapter storyPhotoAdapter=new StoryPhotoAdapter(keyArray.get(position),context);
            holder.photosRecy.setAdapter(storyPhotoAdapter);
            holder.photosRecy.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        }

        holder.username.setText(review.getUsername());
        holder.storename.setText(review.getStoreName());
        holder.content.setText(review.getContent());

        if(review.getUserPicture()!=""){
            Uri uri= Uri.parse(review.getUserPicture());
            Glide.with(context).load(uri).bitmapTransform(new CropCircleTransformation(context)).into(holder.profile);
        }else
        {
            holder.profile.setImageResource(R.drawable.kakao_default_profile_image);
        }




    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profile;
        TextView username;
        TextView storename;
        RecyclerView photosRecy;
        ImageView btnLike;
        ImageView btnComment;
        TextView likeCount;
        TextView content;
        TextView commentCount;

        public ViewHolder(View itemView) {
            super(itemView);
            profile=(ImageView) itemView.findViewById(R.id.story_ig_profile);
            username=(TextView)itemView.findViewById(R.id.story_tv_username);
            storename=(TextView)itemView.findViewById(R.id.story_tv_storename);
            photosRecy=(RecyclerView)itemView.findViewById(R.id.story_photos_recy);
            btnLike=(ImageView)itemView.findViewById(R.id.story_ig_likeBtn);
            btnComment=(ImageView)itemView.findViewById(R.id.story_ig_commentBtn);
            likeCount=(TextView)itemView.findViewById(R.id.story_tv_likeCount);
            content=(TextView)itemView.findViewById(R.id.story_tv_content);
            commentCount=(TextView)itemView.findViewById(R.id.story_tv_commentCount);
        }
    }
}
