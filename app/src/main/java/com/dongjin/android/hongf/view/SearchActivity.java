package com.dongjin.android.hongf.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.model.Item;
import com.dongjin.android.hongf.present.SearchPresenter;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements Search_View {

    private SearchPresenter presenter;
    private TextView tvTest;
    private Button btnTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        tvTest=(TextView)findViewById(R.id.tvTest);
        btnTest=(Button)findViewById(R.id.btnTest);
        presenter=new SearchPresenter();
        presenter.attachView(this);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loadPlace("홍익대학교");
            }
        });
    }

    @Override
    public Context getContext() {
        return SearchActivity.this;
    }



    @Override
    public void showSearchedPlaces(ArrayList<Item> items) {

    }
}
