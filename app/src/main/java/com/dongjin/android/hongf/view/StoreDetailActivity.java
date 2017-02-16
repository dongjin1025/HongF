package com.dongjin.android.hongf.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.adapter.DetailPhotoAdapter;
import com.dongjin.android.hongf.model.Store;
import com.dongjin.android.hongf.presenter.DetailPresenter;

import java.util.ArrayList;

public class StoreDetailActivity extends AppCompatActivity implements StoreDetail_View {

    private Toolbar toolbar;
    private LinearLayout linearLayout_addReview;
    private TextView tvAddress;
    private TextView tvType;
    private TextView tvPrice;
    private TextView tvTell;
    private TextView tvStoreName;
    private TextView tvInfo;
    private RecyclerView recyclerView;
    DetailPresenter presenter;
    private String detail_id;
    Store store;
    DetailPhotoAdapter photoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);
        presenter=new DetailPresenter();
        presenter.attachView(this);

        Intent intent=getIntent();
        store=intent.getParcelableExtra("Store");

        photoAdapter=new DetailPhotoAdapter();

        detail_id=store.getId();
        recyclerView=(RecyclerView)findViewById(R.id.detail_recycler);
        recyclerView.setAdapter(photoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        tvAddress=(TextView)findViewById(R.id.detail_tv_address);
        tvPrice=(TextView)findViewById(R.id.detail_tv_price);
        tvTell=(TextView)findViewById(R.id.detail_tv_tell);
        tvType=(TextView)findViewById(R.id.detail_tv_type);
        tvStoreName=(TextView)findViewById(R.id.detail_tv_storename);
        tvInfo=(TextView)findViewById(R.id.detail_tv_info);

        tvAddress.setText(store.getStoreaddress());
        tvTell.setText(store.getPhone());
        tvType.setText(store.getStorefood());
        tvStoreName.setText(store.getStorename());



        toolbar=(Toolbar)findViewById(R.id.toolbar2);
        toolbar.setTitle(store.getStorename());
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        linearLayout_addReview=(LinearLayout)findViewById(R.id.addReview);



    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showStorePhotos(ArrayList<Uri> uris) {
        photoAdapter.setImageDatas(uris);
    }
}
