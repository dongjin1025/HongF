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
import com.dongjin.android.hongf.adapter.StoreListAdapter;
import com.dongjin.android.hongf.model.Store;
import com.dongjin.android.hongf.presenter.StoreListPresenter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoreListFragment extends android.support.v4.app.Fragment implements StoreList_View{


    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private StoreListPresenter presenter;
    private StoreListAdapter adapter;
    private ArrayList<Store> stores;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference storeListRefer= database.getReference().child("Store");
    ChildEventListener childEventListener;


    public StoreListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        stores=new ArrayList<>();
        childEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Store store=dataSnapshot.getValue(Store.class);
                stores.add(store);
                presenter.setSotreListData();
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
        storeListRefer.addChildEventListener(childEventListener);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_list, container, false);
        presenter=new StoreListPresenter();
        presenter.attachView(this);


        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview);
        adapter = new StoreListAdapter(getActivity());
        recyclerView.setAdapter(adapter);



        RecyclerView.LayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
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

    @Override
    public void showStoreList() {
        adapter.setAdapterData(stores);
    }

    @Override
    public void onStop() {
        super.onStop();
        storeListRefer.removeEventListener(childEventListener);
    }
}
