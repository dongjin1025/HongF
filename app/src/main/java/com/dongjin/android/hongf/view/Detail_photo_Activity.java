package com.dongjin.android.hongf.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.adapter.PhotoDetailAdapter;

import java.util.ArrayList;

public class Detail_photo_Activity extends AppCompatActivity {

    private ArrayList<String> urls;
    private PhotoDetailAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_photo_);


        urls=new ArrayList<>();
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        urls=bundle.getStringArrayList("url");
        adapter=new PhotoDetailAdapter(urls,this);

        recyclerView=(RecyclerView)findViewById(R.id.recy_dt_photo);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);



    }
}
