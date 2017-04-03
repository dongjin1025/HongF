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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by kimdongjin on 2017. 2. 17..
 */

public class StoryPhotoAdapter extends RecyclerView.Adapter<StoryPhotoAdapter.ViewHolder> {

    ArrayList<Uri> images;
    ArrayList<String> urls;
    Context context;
    String ur;
    DatabaseReference storyRef= FirebaseDatabase.getInstance().getReference();

    public StoryPhotoAdapter(String key,Context context){
        this.context=context;
        images=new ArrayList<>();
        urls=new ArrayList<>();

        ChildEventListener childEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ur = dataSnapshot.getValue(String.class);
                Log.e("ur TAG1", ur);

                if (ur != null) {
                    if (!images.contains(ur)) {
                        urls.add(ur);
                        images.add(Uri.parse(ur));
                        Collections.reverse(images);
                        notifyDataSetChanged();
                    }
                }


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
        };
        storyRef.child("Story_Urls").child(key).addChildEventListener(childEventListener);

    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_adapter,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.pb.setVisibility(View.VISIBLE);
        if(images.size()==0||images.get(0).equals("")){
            holder.ig_detail.setVisibility(View.GONE);

        }else{
            if(!images.get(0).equals("")){


                Glide.with(context).load(images.get(position)).listener(new RequestListener<Uri, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                        holder.pb.setVisibility(View.GONE);
                        return false;

                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.pb.setVisibility(View.GONE);
                        return false;
                    }
                }).into(holder.ig_detail);
//                //holder.ig_detail.setTag(position);
//                holder.ig_detail.setOnClickListener(new View.OnClickListener() {
//                    //int posi= (int) holder.ig_detail.getTag();
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent=new Intent(context,Detail_photo_Activity.class);
//                        intent.putExtra("url",urls);
//                        context.startActivity(intent);
//                    }
//                });
            }



        }




    }

    @Override
    public int getItemCount() {


        return images.size();



    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ig_detail;
        ProgressBar pb;
        public ViewHolder(View itemView) {
            super(itemView);
            pb=(ProgressBar)itemView.findViewById(R.id.pb_photo);
            ig_detail=(ImageView)itemView.findViewById(R.id.ig_detail);
        }

    }
}
