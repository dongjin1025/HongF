package com.dongjin.android.hongf.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dongjin.android.hongf.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by kimdongjin on 2017. 2. 17..
 */

public class StoryPhotoAdapter extends RecyclerView.Adapter<StoryPhotoAdapter.ViewHolder> {

    ArrayList<Uri> images;
    Context context;
    String ur;
    DatabaseReference storyRef= FirebaseDatabase.getInstance().getReference();

    public StoryPhotoAdapter(String key,Context context){
        this.context=context;
        images=new ArrayList<>();
        storyRef.child("Story_Urls").child(key).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ur= dataSnapshot.getValue(String.class);
                Log.e("ur TAG1",ur);
                if(ur!="no") {
                    images.add(Uri.parse(ur));
                }
                else{
                    images=new ArrayList<Uri>();
                }
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

    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_adapter,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        Log.e("images size Tag",images.size()+"");
        if(images.get(0)!=null){
            Glide.with(context).load(images.get(position)).into(holder.ig_detail);
        }


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
