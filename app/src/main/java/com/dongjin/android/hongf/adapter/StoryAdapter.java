package com.dongjin.android.hongf.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.dongjin.android.hongf.model.Store;
import com.dongjin.android.hongf.view.CommentActivity;
import com.dongjin.android.hongf.view.LikeDetailActivity;
import com.dongjin.android.hongf.view.StoreDetailActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by kimdongjin on 2017. 1. 16..
 */

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {

    Context context;
    DatabaseReference storyRef= FirebaseDatabase.getInstance().getReference();
    DatabaseReference storeRef= FirebaseDatabase.getInstance().getReference();
    ArrayList<Review> reviews;
    ArrayList<String> keyArray;

    boolean isLiking=false;
    KaKaoInfo kaKaoInfo;

    public void setAdapterData(ArrayList<Review> reivews,ArrayList<String> keyArray){

        this.reviews=reivews;
        this.keyArray=keyArray;
        notifyDataSetChanged();
    }

    public StoryAdapter(final Context context){
        this.context=context;
        reviews=new ArrayList<>();
        keyArray=new ArrayList<>();

        kaKaoInfo=KaKaoInfo.getInstance();


        Log.e("keyArrayTag2",""+keyArray.size());



    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story,parent,false);


        return new StoryAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        final Review review=reviews.get(position);

        holder.setLikeBtn(keyArray.get(position));
        final InterestedUser user=new InterestedUser();
        user.setUserId(kaKaoInfo.read_id_kakao());
        user.setUsername(kaKaoInfo.read_name_kakao());
        user.setProfilepath(kaKaoInfo.read_picture_kakao());


        float rate=review.getRate();

        if(keyArray.size()!=0){
            StoryPhotoAdapter storyPhotoAdapter=new StoryPhotoAdapter(keyArray.get(position),context);
            holder.photosRecy.setAdapter(storyPhotoAdapter);
            holder.photosRecy.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        }


        holder.username.setText(review.getUsername());
        holder.storename.setText("@ "+review.getStoreName());
        holder.storename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storyRef.child("Store").child(review.getStoreId()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Store store=dataSnapshot.getValue(Store.class);
                        Intent intent=new Intent(context, StoreDetailActivity.class);
                        intent.putExtra("Store",store);
                        context.startActivity(intent);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
        holder.content.setText(review.getContent());

        holder.commentCount.setText("댓글 "+ review.getCommentCount()+"개");



        holder.commentCount.setTag(position);
        holder.commentCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag= (int) holder.commentCount.getTag();
                Intent intent =new Intent(context, CommentActivity.class);
                intent.putExtra("key",keyArray.get(tag));
                intent.putExtra("id",reviews.get(tag).getStoreId());
                context.startActivity(intent);


            }
        });
        holder.btnComment.setTag(position);
        holder.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag= (int) holder.btnComment.getTag();
                Intent intent =new Intent(context, CommentActivity.class);
                intent.putExtra("key",keyArray.get(tag));
                intent.putExtra("id",reviews.get(tag).getStoreId());
                context.startActivity(intent);

            }
        });

        if(!review.getUserPicture().equals("")){
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
        holder.likeCount.setText("좋아요 "+review.getLikeCount()+"개");

        holder.likeCount.setTag(position);
        holder.likeCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag= (int) holder.likeCount.getTag();
                Intent intent =new Intent(context, LikeDetailActivity.class);
                intent.putExtra("key",keyArray.get(tag));
                context.startActivity(intent);
            }
        });

        holder.btnLike.setTag(position);
        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isLiking=true;
                int posi= (int) holder.btnLike.getTag();

                storyRef.child("likeReview").child(keyArray.get(posi)).addListenerForSingleValueEvent(new ValueEventListener() {

                    int posi2=(int)holder.btnLike.getTag();
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String key=dataSnapshot.getKey().toString();
                        if (isLiking) {

                            if (!dataSnapshot.hasChild(kaKaoInfo.read_id_kakao())) {

                                storyRef.child("likeReview").child(key).child(kaKaoInfo.read_id_kakao()).setValue(user);
                                holder.btnLike.setImageResource(R.drawable.btn_common_card_like_pressed);

                                HashMap<String, Object> hashMap= new HashMap<>();
                                hashMap.put("likeCount", (int) dataSnapshot.getChildrenCount()+1);
                                review.setLikeCount((int) dataSnapshot.getChildrenCount()+1);
                                holder.likeCount.setText("좋아요 "+review.getLikeCount()+"개");
                                storyRef.child("Story").child(key).updateChildren(hashMap);
                                storyRef.child("story2").child(reviews.get(posi2).getStoreId()).child(key).updateChildren(hashMap);

                                isLiking = false;

                            } else {
                                storyRef.child("likeReview").child(key).child(kaKaoInfo.read_id_kakao()).removeValue();
                                holder.btnLike.setImageResource(R.drawable.btn_unpress_like);

                                HashMap<String, Object> hashMap= new HashMap<>();
                                hashMap.put("likeCount", (int) dataSnapshot.getChildrenCount()-1);
                                review.setLikeCount((int) dataSnapshot.getChildrenCount()-1);
                                holder.likeCount.setText("좋아요 "+review.getLikeCount()+"개");
                                storyRef.child("Story").child(key).updateChildren(hashMap);
                                storyRef.child("story2").child(reviews.get(posi2).getStoreId()).child(key).updateChildren(hashMap);



                                isLiking = false;
                            }


                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




            }
        });




    }



    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        DatabaseReference likeRef;



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
            likeRef=FirebaseDatabase.getInstance()
                    .getReference().child("likeReview");
            likeRef.keepSynced(true);

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
        public void setLikeBtn(final String key){
            likeRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(key).hasChild(kaKaoInfo.read_id_kakao())){
                        btnLike.setImageResource(R.drawable.btn_common_card_like_pressed);
                    }else{
                        btnLike.setImageResource(R.drawable.btn_unpress_like);
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }


}
