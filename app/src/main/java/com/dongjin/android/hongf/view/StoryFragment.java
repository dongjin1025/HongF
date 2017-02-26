package com.dongjin.android.hongf.view;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.adapter.StoryAdapter;
import com.dongjin.android.hongf.model.Review;
import com.dongjin.android.hongf.presenter.StoryPresenter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoryFragment extends Fragment implements Story_View {
    private StoryAdapter storyAdapter;
    DatabaseReference storyRef= FirebaseDatabase.getInstance().getReference();
    ArrayList<Review> reviews;
    ArrayList<ArrayList<Uri>> uris;
    ArrayList<Uri> smallUris;
    StoryPresenter presenter;

    Context context;
    @Override
    public void onStart() {
        super.onStart();


    }
    public StoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_story, container, false);
        storyAdapter =new StoryAdapter(getContext() );
        presenter=new StoryPresenter();
        presenter.attachView(this);
        context=getContext();
        uris=new ArrayList<>();
        reviews=new ArrayList<>();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.storyList_Recy);
        recyclerView.setAdapter(storyAdapter);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
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
