package com.dongjin.android.hongf.view;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.StoreListAdapter;
import com.melnykov.fab.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoreListFragment extends android.support.v4.app.Fragment{


    private RecyclerView recyclerView;
    private FloatingActionButton fab;


    public StoreListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_list, container, false);


        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview);
        StoreListAdapter adapter = new StoreListAdapter(getActivity());
        recyclerView.setAdapter(adapter);



        RecyclerView.LayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);


        fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setBackgroundColor(getResources().getColor(R.color.color_Bar));
        fab.attachToRecyclerView(recyclerView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationForTest();
            }
        });


        final CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) view.findViewById(R.id.toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#ff000000"));
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#ff000000"));




        return view;
    }
    public void navigationForTest(){
        Intent intent =new Intent(getContext(),SearchActivity.class);
        startActivity(intent);
    }

}
