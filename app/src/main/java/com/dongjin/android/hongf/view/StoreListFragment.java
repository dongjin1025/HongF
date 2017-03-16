package com.dongjin.android.hongf.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dongjin.android.hongf.R;


public class StoreListFragment extends Fragment implements View.OnClickListener {


    ImageView list_ig_korean;
    ImageView list_ig_japan;
    ImageView list_ig_china;
    ImageView list_ig_western;
    ImageView list_ig_world;
    ImageView list_ig_bar;
    ImageView list_ig_koreanSnack;
    ImageView list_ig_fastfood;
    ImageView list_ig_cafe;
    ImageView list_ig_pub;

    public StoreListFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_store_list, container, false);
        list_ig_korean=(ImageView)view.findViewById(R.id.list_ig_korean);
        list_ig_japan=(ImageView)view.findViewById(R.id.list_ig_japan);
        list_ig_china=(ImageView)view.findViewById(R.id.list_ig_china);
        list_ig_western=(ImageView)view.findViewById(R.id.list_ig_western);
        list_ig_world=(ImageView)view.findViewById(R.id.list_ig_world);
        list_ig_bar=(ImageView)view.findViewById(R.id.list_ig_bar);
        list_ig_koreanSnack=(ImageView)view.findViewById(R.id.list_ig_koreanSnack);
        list_ig_fastfood=(ImageView)view.findViewById(R.id.list_ig_fastfood);
        list_ig_cafe=(ImageView)view.findViewById(R.id.list_ig_cafe);
        list_ig_pub=(ImageView)view.findViewById(R.id.list_ig_pub);

        list_ig_korean.setOnClickListener(this);
        list_ig_japan.setOnClickListener(this);
        list_ig_china.setOnClickListener(this);
        list_ig_western.setOnClickListener(this);
        list_ig_world.setOnClickListener(this);
        list_ig_bar.setOnClickListener(this);
        list_ig_koreanSnack.setOnClickListener(this);
        list_ig_cafe.setOnClickListener(this);
        list_ig_fastfood.setOnClickListener(this);
        list_ig_pub=(ImageView)view.findViewById(R.id.list_ig_pub);
        return view;

    }


    @Override
    public void onClick(View v) {
        String tag="";
        switch (v.getId()){
            case R.id.list_ig_korean:
                tag="korean";
                break;
            case R.id.list_ig_japan:
                tag="japanease";
                break;
            case R.id.list_ig_china:
                tag="chinease";
                break;
            case R.id.list_ig_western:
                tag="wastern";
                break;
            case R.id.list_ig_world:
                tag="world";
                break;
            case R.id.list_ig_bar:
                tag="bar";
                break;
            case R.id.list_ig_pub:
                tag="hope";
                break;
            case R.id.list_ig_cafe:
                tag="cafe";
                break;
            case R.id.list_ig_fastfood:
                tag="fastfood";
                break;
            case R.id.list_ig_koreanSnack:
                tag="koreansnack";
                break;
        }
        Intent intent=new Intent(getContext(),StoreListActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
    }
}
