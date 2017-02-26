package com.dongjin.android.hongf.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.model.Store;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by kimdongjin on 2017. 2. 26..
 */

public class MapPagerAdapter extends PagerAdapter {

    LayoutInflater inflater;
    ArrayList<Store> stores;
    ArrayList<Uri> uris;
    String url;
    Context context;
    DatabaseReference storeRef;
    public MapPagerAdapter(LayoutInflater inflater,Context context) {
        // TODO Auto-generated constructor stub
        this.context=context;
        this.inflater = inflater;
        stores=new ArrayList<>();
        uris=new ArrayList<>();
        storeRef= FirebaseDatabase.getInstance().getReference().child("Store");
        storeRef.keepSynced(true);


        storeRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Store store=dataSnapshot.getValue(Store.class);
                stores.add(store);

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
    public int getCount() {
        // TODO Auto-generated method stub
        return stores.size();
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub
        Store store =stores.get(position);

        View view = null;
        view = inflater.inflate(R.layout.item_map, null);

        CardView cardView = (CardView)view.findViewById(R.id.cardView);
        ImageView imageView=(ImageView)view.findViewById(R.id.imageView4);
        TextView tvStoreName=(TextView)view.findViewById(R.id.tvStoreName);
        TextView tvRating=(TextView)view.findViewById(R.id.tvRating);
        TextView tvAddress=(TextView)view.findViewById(R.id.tvAddress);



        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        if(store.getImageUrl()=="") {
            imageView.setImageResource(R.drawable.cast_expanded_controller_seekbar_thumb);
        }else{


            Glide.with(context).load(store.getImageUrl()).into(imageView);
        }

        tvStoreName.setText(store.getStorename());
        tvRating.setText(store.getStoreaddress());
        tvAddress.setText(store.getStoreaddress());



        container.addView(view);

        return view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        container.removeView((View) object);
    }
    @Override
    public boolean isViewFromObject(View v, Object obj) {
        // TODO Auto-generated method stub
        return v == obj;
    }
}

