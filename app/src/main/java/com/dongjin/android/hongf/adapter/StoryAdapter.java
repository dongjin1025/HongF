package com.dongjin.android.hongf.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
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
import com.dongjin.android.hongf.model.InterestedUser;
import com.dongjin.android.hongf.model.KaKaoInfo;
import com.dongjin.android.hongf.model.Review;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by kimdongjin on 2017. 1. 16..
 */

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {

    Context context;
    DatabaseReference storyRef= FirebaseDatabase.getInstance().getReference();
    DatabaseReference likeRef;
    ArrayList<Review> reviews;
    ArrayList<String> keyArray;
    ArrayList<String> userLikeKey;
    InterestedUser user;
    boolean isLiked;
    KaKaoInfo kaKaoInfo;
    String key;
    int keyIndex;

    public StoryAdapter(Context context){
        this.context=context;
        isLiked=false;
        reviews=new ArrayList<>();
        keyArray=new ArrayList<>();
        user=new InterestedUser();
        userLikeKey=new ArrayList<>();
        kaKaoInfo=KaKaoInfo.getInstance();
        storyRef.child("Story").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Review review=dataSnapshot.getValue(Review.class);
                String key=dataSnapshot.getKey();
                keyArray.add(key);
                Log.e("keyArrayTag",""+keyArray.size());
                reviews.add(review);
                notifyDataSetChanged();


                storyRef.child(kaKaoInfo.read_id_kakao()).child("likeReview").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {


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
    public void onBindViewHolder(final ViewHolder holder, int position) {


        Review review=reviews.get(position);
        key=keyArray.get(position);




        user.setUsername(kaKaoInfo.read_name_kakao());
        user.setProfilepath(kaKaoInfo.read_picture_kakao());
        user.setUserId(kaKaoInfo.read_id_kakao());
        user.setComment("");
        float rate=review.getRate();

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

        if(rate==5){
            holder.rate.setImageResource(R.drawable.ic_egmt_review_rating_1_normal);
            holder.rate.setColorFilter(ContextCompat.getColor(context, R.color.black));
        }else if(rate==2.5){
            holder.rate.setImageResource(R.drawable.ic_egmt_review_rating_2_normal);
            holder.rate.setColorFilter(ContextCompat.getColor(context, R.color.black));
        }else if(rate==0){
            holder.rate.setImageResource(R.drawable.ic_egmt_review_rating_3_normal);
            holder.rate.setColorFilter(ContextCompat.getColor(context, R.color.black));
        }
        holder.date.setText(review.getDate());

        if(!isLiked){
            holder.btnLike.setImageResource(R.drawable.btn_unpress_like);
        }else if(isLiked){
            holder.btnLike.setImageResource(R.drawable.btn_common_card_like_pressed);
        }
        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isLiked){
                    holder.btnLike.setImageResource(R.drawable.btn_common_card_like_pressed);
                    HashMap<String,Object> hashMap=new HashMap();
                    hashMap.put(key,key);
                    storyRef.child(kaKaoInfo.read_id_kakao()).child("likeReview").updateChildren(hashMap);
                    isLiked=true;
                }else{
                    holder.btnLike.setImageResource(R.drawable.btn_unpress_like);
                    storyRef.child(kaKaoInfo.read_id_kakao()).child("likeReview").child(key).removeValue();
                    isLiked=false;
                }



            }
        });




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
        ImageView rate;
        TextView date;
        public ViewHolder(View itemView) {
            super(itemView);
            date=(TextView)itemView.findViewById(R.id.Story_tv_date);
            rate=(ImageView)itemView.findViewById(R.id.story_ig_rate);
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
