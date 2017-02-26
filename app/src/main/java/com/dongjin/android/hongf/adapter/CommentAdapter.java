package com.dongjin.android.hongf.adapter;

import android.content.Context;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by kimdongjin on 2017. 2. 23..
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {



    Context context;
    DatabaseReference commentRef;
    ArrayList<InterestedUser> users;
    ArrayList<String> mCommentIds;

    public CommentAdapter(Context context,String key){

        commentRef= FirebaseDatabase.getInstance().getReference().child("comments").child(key);
        commentRef.keepSynced(true);
        users=new ArrayList<>();
        mCommentIds=new ArrayList<>();

        this.context=context;

        commentRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                InterestedUser user=dataSnapshot.getValue(InterestedUser.class);
                mCommentIds.add(dataSnapshot.getKey());

                users.add(user);
                notifyDataSetChanged();
                //notifyItemInserted(users.size()-1);



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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment,parent,false);


        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        InterestedUser user= users.get(position);
        Log.e("USERS SIZE",users.size()+"");


        if(user.getProfilepath()!=""){
            Glide.with(context).load(user.getProfilepath()).bitmapTransform(new CropCircleTransformation(context)
            ).into(holder.profile);
        }else{
            holder.profile.setImageResource(R.drawable.kakao_default_profile_image);
        }

        holder.user.setText(user.getUsername());
        holder.date.setText(user.getDate());
        holder.content.setText(user.getComment());


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView user;
        TextView content;
        TextView date;
        public ViewHolder(View itemView) {
            super(itemView);
            profile=(ImageView)itemView.findViewById(R.id.item_comment_profile);
            user=(TextView)itemView.findViewById(R.id.item_comment_username);
            content=(TextView)itemView.findViewById(R.id.item_comment_content);
            date=(TextView)itemView.findViewById(R.id.item_comment_date);
        }
    }
}
