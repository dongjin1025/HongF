package com.dongjin.android.hongf.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.adapter.StoryAdapter;
import com.dongjin.android.hongf.model.Review;

import java.util.ArrayList;

public class Detail_ReviwList extends AppCompatActivity {
    private ArrayList<Review> reviews;
    private ArrayList<String> keyArray;
    private StoryAdapter adapter;
    private RecyclerView recyclerView;
    private View view;
    private ImageButton btnBack;
    private TextView storeName_tb;

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        keyArray=bundle.getStringArrayList("keyarray");
        reviews=bundle.getParcelableArrayList("reviews");
        adapter.setAdapterData(reviews,keyArray);
        if(reviews.size()!=0){
            storeName_tb.setText(reviews.get(0).getStoreName());
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__reviw_list);

        reviews=new ArrayList<>();
        keyArray=new ArrayList<>();
        adapter=new StoryAdapter(this);

        recyclerView=(RecyclerView)findViewById(R.id.recy);
        LinearLayoutManager manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        view = findViewById(R.id.tb_detail);
        storeName_tb=(TextView)view.findViewById(R.id.list_tv_orderfilter);
        storeName_tb.setText("리뷰");
        btnBack = (ImageButton) view.findViewById(R.id.ib_back_toolbar);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }
}
