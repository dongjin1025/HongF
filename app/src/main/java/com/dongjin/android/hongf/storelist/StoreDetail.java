package com.dongjin.android.hongf.storelist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.dongjin.android.hongf.R;

public class StoreDetail extends AppCompatActivity {

    private Toolbar toolbar;
    private LinearLayout linearLayout_addReview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);
        toolbar=(Toolbar)findViewById(R.id.toolbar2);
        toolbar.setTitle("Store Name");
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));

        linearLayout_addReview=(LinearLayout)findViewById(R.id.addReview);



    }
}
