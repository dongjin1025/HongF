package com.dongjin.android.hongf.view;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoreListActivity extends AppCompatActivity implements StoreList_View{


    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private StoreListPresenter presenter;
    private StoreListAdapter adapter;
    private ArrayList<Store> stores;
    private String tag;
    private LinearLayout filter_lay;
    private TextView list_tv_orderfilter;
    private ImageView list_ig_foodfilter;
    private TextView tv_fliter_order;
    private View subView;
    private ImageButton btnBack;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference storeListRefer;
    DatabaseReference storeListRefer2;


    public StoreListActivity() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent intent=getIntent();

        Bundle bundle=intent.getExtras();
        tag=bundle.getString("tag");

        Log.e("LIST TAGTAGTAG",tag);


        storeListRefer= database.getReference().child("Store");
        storeListRefer2= database.getReference().child("Store2");
        storeListRefer.keepSynced(true);
        storeListRefer2.keepSynced(false);
        if(tag!="all"){
            setFilterOnList(tag,"date");

        }else{
            setFilterOnList2(tag,"date");

        }
        presenter=new StoreListPresenter();
        presenter.attachView(this);
        stores=new ArrayList<>();
        subView=findViewById(R.id.tb_comment);
        list_ig_foodfilter=(ImageView)subView.findViewById(R.id.list_ig_foodfilter);
        list_tv_orderfilter=(TextView)subView.findViewById(R.id.list_tv_orderfilter);
        tv_fliter_order=(TextView)subView.findViewById(R.id.tv_fliter_order);
        btnBack=(ImageButton)subView.findViewById(R.id.ib_back_toolbar);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        filter_lay=(LinearLayout)findViewById(R.id.lay_filterOrder);
        filter_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StoreListActivity.this,Dialog_StoreList_Filter.class);
                startActivityForResult(intent,990);

            }
        });
        setFilterInfo(tag);




        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        resetAdapter();

        RecyclerView.LayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            String orderTag=data.getStringExtra("orderby");
            Log.e("ListActivity Result tag",orderTag);
            switch (orderTag){
                case "bookmarkcount":
                    tv_fliter_order.setText("즐겨찾기순");
                    break;
                case "averagerating":
                    tv_fliter_order.setText("평점순");
                    break;
                case "reviewcount":
                    tv_fliter_order.setText("리뷰순");
                    break;
            }
            setFilterOnList(tag,orderTag);
        }
    }

    @Override
    public void showStoreList() {
        adapter.setAdapterData(stores);
    }

    public void resetAdapter(){
        adapter = new StoreListAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        super.onStop();
    }
    public void setFilterOnList(String tag,String orderby){

        stores=new ArrayList<>();
        storeListRefer2.child(tag).orderByChild(orderby).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Store store=dataSnapshot.getValue(Store.class);


                stores.add(store);
                Collections.reverse(stores);
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
        });

    }
    public void setFilterOnList2(String tag,String orderby){

        stores=new ArrayList<>();
        storeListRefer.orderByChild(orderby).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Store store=dataSnapshot.getValue(Store.class);


                stores.add(store);
                Collections.reverse(stores);
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
        });

    }

    @Override
    public Context getContext() {
        return this;
    }
    public void setFilterInfo(String tag){
        switch (tag) {
            case "korean":
                list_ig_foodfilter.setImageResource(R.drawable.foodicon1);
                list_tv_orderfilter.setText("한식");
                break;
            case "japanease":
                list_ig_foodfilter.setImageResource(R.drawable.foodicon3);
                list_tv_orderfilter.setText("일식");

                break;
            case "chinease":
                list_ig_foodfilter.setImageResource(R.drawable.foodicon4);
                list_tv_orderfilter.setText("중식");
                break;
            case "wastern":
                list_ig_foodfilter.setImageResource(R.drawable.foodicon2);
                list_tv_orderfilter.setText("양식");
                break;
            case "world":
                list_ig_foodfilter.setImageResource(R.drawable.foodicon5);
                list_tv_orderfilter.setText("세계음식");
                break;
            case "cafe":
                list_ig_foodfilter.setImageResource(R.drawable.foodicon8);
                list_tv_orderfilter.setText("까페");
                break;
            case "bar":
                list_ig_foodfilter.setImageResource(R.drawable.foodicon10);
                list_tv_orderfilter.setText("Bar");
                break;
            case "hope":
                list_ig_foodfilter.setImageResource(R.drawable.foodicon9);
                list_tv_orderfilter.setText("술집");
                break;
            case "fastfood":
                list_ig_foodfilter.setImageResource(R.drawable.foodicon7);
                list_tv_orderfilter.setText("패스트푸드");
                break;
            case "koreansnack":
                list_ig_foodfilter.setImageResource(R.drawable.foodicon6);
                list_tv_orderfilter.setText("분식");
                break;
            case "null":
                list_ig_foodfilter.setImageResource(R.drawable.places_ic_clear);
                list_tv_orderfilter.setText("All");
        }

    }
}
