package com.dongjin.android.hongf.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.adapter.LikeDetailAdapter;
import com.dongjin.android.hongf.model.InterestedUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LikeDetailActivity extends AppCompatActivity {

    DatabaseReference likePeople;
    String key;
    ArrayList<InterestedUser> users;
    LikeDetailAdapter adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_detail);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        key = bundle.getString("key");
        Log.e("TAG LIKE KEY", key);
        users = new ArrayList<>();
        adapter = new LikeDetailAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.likedetail_recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        likePeople = FirebaseDatabase.getInstance().getReference().child("likeReview").child(key);
        likePeople.keepSynced(true);
        likePeople.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                InterestedUser user = dataSnapshot.getValue(InterestedUser.class);
                users.add(user);
                adapter.setDatas(users);

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

}
