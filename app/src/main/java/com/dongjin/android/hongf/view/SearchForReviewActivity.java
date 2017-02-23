package com.dongjin.android.hongf.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.model.Store;
import com.dongjin.android.hongf.presenter.SearchForReviewPresenter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SearchForReviewActivity extends AppCompatActivity implements SearchForReview_View{

    private ListView listView;
    private SimpleAdapter adapter;
    private ArrayList<Map<String,String>> itemLists;
    private ArrayList<String> list;
    private ArrayList<Store> stores;
    private EditText etSeatch;
    SearchForReviewPresenter presenter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference storeListRefer= database.getReference().child("Store");
    ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_review);

        stores=new ArrayList<>();
        presenter=new SearchForReviewPresenter();
        listView=(ListView)findViewById(R.id.listview);

        initList();


        etSeatch=(EditText) findViewById(R.id.etSearch);
        etSeatch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().equals("")){
                    initList();
                }else{
                    searchItems(s.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




    }
    public void initList() {
        itemLists=new ArrayList<>();
        list=new ArrayList<>();

        for(int i=0;i<stores.size();i++){
            Map<String,String> map=new HashMap<>();
            map.put("title",stores.get(i).getStorename());
            map.put("address",stores.get(i).getStoreaddress());
            map.put("id",stores.get(i).getId());
            itemLists.add(map);
            list.add(stores.get(i).getStorename());
        }

        adapter=new SimpleAdapter(this,itemLists,R.layout.item_search_for_review,new
                String[]{"title","address","id"},new int[]{R.id.tvTitle,R.id.tvAddress,R.id.tvId_search_for_review});

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent =new Intent(SearchForReviewActivity.this,PostReviewActivity.class);
                intent.putExtra("store",stores.get(position));
                startActivity(intent);
            }
        });


    }

    public void searchItems(String textToSearch){
        itemLists.clear();
        for(int i=0;i<stores.size();i++){
            if(stores.get(i).getStorename().contains(textToSearch)){
                Map<String,String> tempoMap=new HashMap<>();
                tempoMap.put("title",stores.get(i).getStorename());
                tempoMap.put("address",stores.get(i).getStorename());
                tempoMap.put("id",stores.get(i).getId());
                itemLists.add(tempoMap);
            }
        }

        adapter.notifyDataSetChanged();
    }
    @Override
    public void onStart() {
        super.onStart();

        childEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Store store=dataSnapshot.getValue(Store.class);
                stores.add(store);

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
    public void onStop() {
        super.onStop();
        storeListRefer.removeEventListener(childEventListener);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
