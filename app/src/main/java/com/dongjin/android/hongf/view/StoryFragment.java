package com.dongjin.android.hongf.view;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.adapter.StoryAdapter;
import com.dongjin.android.hongf.model.Review;
import com.dongjin.android.hongf.presenter.StoryPresenter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoryFragment extends Fragment implements Story_View {
    private StoryAdapter storyAdapter;
    DatabaseReference storyRef= FirebaseDatabase.getInstance().getReference();

    ArrayList<ArrayList<Uri>> uris;
    ArrayList<Uri> smallUris;
    StoryPresenter presenter;
    ArrayList<Review> reviews;
    ArrayList<String> keyArray;
    RecyclerView recyclerView;




    Context context;
    @Override
    public void onStart() {
        super.onStart();
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
                Collections.reverse(reviews);
                Collections.reverse(keyArray);
                storyAdapter.setAdapterData(reviews,keyArray);

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
        storyRef.child("Story").keepSynced(true);




    }
    public StoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_story, container, false);
        reviews=new ArrayList<>();
        keyArray=new ArrayList<>();

        storyAdapter =new StoryAdapter(getContext() );
        presenter=new StoryPresenter();
        presenter.attachView(this);
        context=getContext();
        uris=new ArrayList<>();
        reviews=new ArrayList<>();

        recyclerView= (RecyclerView) view.findViewById(R.id.storyList_Recy);
        recyclerView.setAdapter(storyAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(manager);






        return view;
    }

    @Override
    public void showReviewList() {

    }

    @Override
    public void showReviewPhotos() {

    }
}
