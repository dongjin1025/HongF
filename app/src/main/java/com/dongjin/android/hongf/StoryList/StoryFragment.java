package com.dongjin.android.hongf.StoryList;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.StoryList.StoryAdapter.StoryAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoryFragment extends Fragment {
    private StoryAdapter storyAdapter;

    public StoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_story, container, false);
        storyAdapter =new StoryAdapter();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.storyList_Recy);
        recyclerView.setAdapter(storyAdapter);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);



        return view;
    }

}
