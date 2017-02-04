package com.dongjin.android.hongf.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.adapter.SearchResultAdapter;
import com.dongjin.android.hongf.model.Item;
import com.dongjin.android.hongf.present.SearchPresenter;

import java.util.ArrayList;

public class SearchActivity extends Activity implements Search_View {

    private SearchPresenter presenter;
    private RecyclerView searchRecyclerview;
    private SearchResultAdapter adapter;
    private TextView tvNoResult;
    private Button btnSearch;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);
        btnSearch=(Button)findViewById(R.id.btnSearch);
        tvNoResult=(TextView)findViewById(R.id.tvNoResult);
        etSearch=(EditText)findViewById(R.id.etSearch);

        presenter=new SearchPresenter();
        presenter.attachView(this);
        searchRecyclerview=(RecyclerView)findViewById(R.id.search_recyclerview);
        adapter=new SearchResultAdapter(this);
        searchRecyclerview.setAdapter(adapter);

        searchRecyclerview.setLayoutManager(new LinearLayoutManager(this));


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query =etSearch.getText().toString();
                presenter.loadPlace(query);
            }
        });



    }

    @Override
    public Context getContext() {
        return SearchActivity.this;
    }



    @Override
    public void showSearchedPlaces(ArrayList<Item> items) {
        adapter.setAdapterData(items);
        tvNoResult.setVisibility(View.INVISIBLE);

    }

    @Override
    public void showErrorToGetData() {
        tvNoResult.setVisibility(View.VISIBLE);
    }
}
