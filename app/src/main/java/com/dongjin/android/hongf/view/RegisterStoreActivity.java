package com.dongjin.android.hongf.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.model.Item;
import com.dongjin.android.hongf.model.Store;
import com.dongjin.android.hongf.presenter.RegisterPresenter;

public class RegisterStoreActivity extends AppCompatActivity implements Register_View,View.OnClickListener {

    private CheckBox priceRb0;
    private CheckBox priceRb1;
    private CheckBox priceRb2;
    private CheckBox priceRb3;
    private CheckBox clickedCh;
    private RegisterPresenter presenter;
    private ImageView igKoreanF;
    private ImageView chinease;
    private ImageView japanease;
    private ImageView wastern;
    private ImageView bar;
    private ImageView koreansnack;
    private ImageView hope;
    private ImageView world;
    private ImageView cafe;
    private ImageView fastfood;
    private ImageView clickedIg;
    private Button btnRegister;
    String priceTag;
    String foodTag;
    Item item;
    Boolean checkChecked= false;
    Store store;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_store);
        this.setFinishOnTouchOutside(false);

        store = new Store();
        presenter=new RegisterPresenter();
        presenter.attachView(this);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        item=bundle.getParcelable("item");


        priceRb0=(CheckBox)findViewById(R.id.priceRb0);
        priceRb0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                priceTag=priceRb0.getText().toString();
                Log.e("PRICE TAG",priceTag);
                clickCheckbox(priceRb0);
            }
        });
        priceRb1=(CheckBox)findViewById(R.id.priceRb1);
        priceRb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                priceTag=priceRb1.getText().toString();
                Log.e("PRICE TAG",priceTag);
                clickCheckbox(priceRb1);
            }
        });

        priceRb2=(CheckBox)findViewById(R.id.priceRb2);
        priceRb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                priceTag=priceRb2.getText().toString();
                Log.e("PRICE TAG",priceTag);
                clickCheckbox(priceRb2);
            }
        });
        priceRb3=(CheckBox)findViewById(R.id.priceRb3);
        priceRb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                priceTag=priceRb3.getText().toString();
                Log.e("PRICE TAG",priceTag);
                clickCheckbox(priceRb3);
            }
        });


        setImages();

        btnRegister=(Button) findViewById(R.id.btnRegister);



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(foodTag!=""){
                    if(!checkChecked){
                        store.setStorename(item.getTitle());
                        store.setStoreaddress(item.getAddress());
                        store.setStorefood(foodTag);
                        store.setStoreprice(priceTag);
                        store.setId(item.getId());
                        store.setImageUrl(item.getImageUrl());
                        store.setPhone(item.getPhone());
                        store.setLatit(item.getLatitude());
                        store.setLongni(item.getLongitude());
                        presenter.writeNewStore(store);

                        Toast.makeText(RegisterStoreActivity.this,"등록 되었습니다",Toast.LENGTH_LONG).show();
                        finish();
                        
                    }else{
                        Toast.makeText(RegisterStoreActivity.this,"가격대를 선택해주세요!",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(RegisterStoreActivity.this,"어떤 종류의 가게인지 선택해주세요!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onStop() {
        super.onStop();
        item=null;
    }

    public void setImages(){
        igKoreanF=(ImageView) findViewById(R.id.register_ig_korean);
        igKoreanF.setOnClickListener(this);
        japanease=(ImageView) findViewById(R.id.register_ig_japanease);
        japanease.setOnClickListener(this);
        chinease=(ImageView) findViewById(R.id.register_ig_chinease);
        chinease.setOnClickListener(this);
        wastern=(ImageView) findViewById(R.id.register_ig_wastern);
        wastern.setOnClickListener(this);
        world=(ImageView) findViewById(R.id.register_ig_world);
        world.setOnClickListener(this);
        bar=(ImageView) findViewById(R.id.register_ig_bar);
        bar.setOnClickListener(this);
        hope=(ImageView) findViewById(R.id.register_ig_hope);
        hope.setOnClickListener(this);
        koreansnack=(ImageView) findViewById(R.id.register_ig_koreansnack);
        koreansnack.setOnClickListener(this);
        fastfood=(ImageView) findViewById(R.id.register_ig_fastfood);
        fastfood.setOnClickListener(this);
        cafe=(ImageView) findViewById(R.id.register_ig_cafe);
        cafe.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_ig_korean:
                foodTag="korean";
                clickImageView(igKoreanF);
                break;
            case R.id.register_ig_japanease:
                foodTag="japanease";
                clickImageView(japanease);
                break;
            case R.id.register_ig_chinease:
                foodTag="chinease";
                clickImageView(chinease);
                break;
            case R.id.register_ig_wastern:
                foodTag="wastern";
                clickImageView(wastern);
                break;
            case R.id.register_ig_world:
                foodTag="world";
                clickImageView(world);
                break;
            case R.id.register_ig_cafe:
                foodTag="cafe";
                clickImageView(cafe);
                break;
            case R.id.register_ig_bar:
                foodTag="bar";
                clickImageView(bar);
                break;
            case R.id.register_ig_hope:
                foodTag="hope";
                clickImageView(hope);
                break;
            case R.id.register_ig_fastfood:
                foodTag="fastfood";
                clickImageView(fastfood);
                break;
            case R.id.register_ig_koreansnack:
                foodTag="koreansnack";
                clickImageView(koreansnack);
                break;


        }
    }

    public void clickImageView (ImageView imageView){
        if(clickedIg==null){
            imageView.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
            clickedIg=imageView;
        }else if(clickedIg==imageView){
            imageView.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            clickedIg=null;
            foodTag=null;
        }else if(clickedIg!=null && clickedIg!=imageView){
            clickedIg.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            imageView.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
            clickedIg=imageView;
        }
    }

    public void clickCheckbox(CheckBox v){

        if(clickedCh==null){
            clickedCh=v;
            v.setChecked(true);
        }else if(clickedCh==v){
            v.setChecked(false);
            clickedCh=null;
            priceTag=null;
        }else if(clickedCh!=null&& clickedCh!=v){
            clickedCh.setChecked(false);
            v.setChecked(true);
            clickedCh=v;
        }


    }

}
