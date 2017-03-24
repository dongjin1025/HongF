package com.dongjin.android.hongf.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by kimdongjin on 2017. 2. 23..
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {



    DatabaseReference storyRef2;
    DatabaseReference storyRef;

    Context context;
    DatabaseReference commentRef;
    ArrayList<InterestedUser> users;
    ArrayList<String> mCommentIds;
    KaKaoInfo kaKaoInfo;

    public CommentAdapter(Context context,String key,String storeId){

        commentRef= FirebaseDatabase.getInstance().getReference().child("comments").child(key);
        storyRef=FirebaseDatabase.getInstance().getReference().child("Story").child(key);
        storyRef2=FirebaseDatabase.getInstance().getReference().child("story2").child(storeId).child(key);
        commentRef.keepSynced(true);
        users=new ArrayList<>();
        mCommentIds=new ArrayList<>();
        kaKaoInfo=KaKaoInfo.getInstance();

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
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final InterestedUser user= users.get(position);
        Log.e("USERS SIZE",users.size()+"");


        if(!user.getProfilepath().equals("")){
            Glide.with(context).load(user.getProfilepath()).bitmapTransform(new CropCircleTransformation(context)
            ).into(holder.profile);
        }else if(user.getProfilepath().equals("")) {
            holder.profile.setImageResource(R.drawable.kakao_default_profile_image);
        }

        holder.user.setText(user.getUsername());
        holder.date.setText(user.getDate());
        holder.content.setText(user.getComment());
        holder.commentCard.setTag(position);
        holder.commentCard.setOnClickListener(new View.OnClickListener() {

            int posi= (int) holder.commentCard.getTag();

            @Override
            public void onClick(View v) {

                Log.e("posi",posi+"");
                if(users.get(posi).getUserId().equals(kaKaoInfo.read_id_kakao())){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle("삭제");
                    dialog.setMessage("댓글을 삭제 하시겠습니까?");
                    dialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                            dialog.setTitle("삭제");
                            dialog.setMessage("정말로 삭제 하시겠습니까?");
                            dialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    commentRef.child(mCommentIds.get(posi)).removeValue();
                                    users.remove(posi);
                                    int cocount=users.size();
                                    HashMap<String,Object> hashMap= new HashMap<>();
                                    hashMap.put("commentCount",cocount);
                                    storyRef.updateChildren(hashMap);
                                    storyRef2.updateChildren(hashMap);
                                    notifyDataSetChanged();



                                }
                            });
                            dialog.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {


                                }
                            });

                            dialog.show();

                        }
                    });
                    dialog.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                        }
                    });

                    dialog.show();
                }

            }


        });


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView commentCard;
        ImageView profile;
        TextView user;
        TextView content;
        TextView date;
        public ViewHolder(View itemView) {
            super(itemView);
            commentCard=(CardView)itemView.findViewById(R.id.comment_card);
            profile=(ImageView)itemView.findViewById(R.id.item_comment_profile);
            user=(TextView)itemView.findViewById(R.id.item_comment_username);
            content=(TextView)itemView.findViewById(R.id.item_comment_content);
            date=(TextView)itemView.findViewById(R.id.item_comment_date);
        }
    }
}
