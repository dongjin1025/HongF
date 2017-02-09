package com.dongjin.android.hongf.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.presenter.MapPresenter;

public class StoreDetail extends AppCompatActivity {

    private Toolbar toolbar;
    private LinearLayout linearLayout_addReview;
    MapPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);
        presenter=new MapPresenter();
        toolbar=(Toolbar)findViewById(R.id.toolbar2);
        toolbar.setTitle("Store Name");
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        linearLayout_addReview=(LinearLayout)findViewById(R.id.addReview);



    }

}
