package com.dongjin.android.hongf.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.model.Item;
import com.dongjin.android.hongf.model.Store;
import com.dongjin.android.hongf.presenter.RegisterPresenter;

public class RegisterStoreActivity extends AppCompatActivity implements Register_View{

    private RadioButton priceRb0;
    private RadioButton priceRb1;
    private RadioButton priceRb2;
    private RadioButton priceRb3;
    private RadioGroup radioGroup;
    private RegisterPresenter presenter;
    private ImageView igKoreanF;
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

        radioGroup=(RadioGroup)findViewById(R.id.rg);
        igKoreanF=(ImageView) findViewById(R.id.igKorean);
        btnRegister=(Button) findViewById(R.id.btnRegister);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){
                    case R.id.priceRb0:
                        priceTag="0";
                        checkChecked =true;
                        break;
                    case R.id.priceRb1:
                        priceTag="1";
                        checkChecked =true;
                        break;
                    case R.id.priceRb2:
                        priceTag="2";
                        checkChecked =true;
                        break;
                    case R.id.priceRb3:
                        priceTag="3";
                        checkChecked =true;
                        break;
                }
            }
        });

        igKoreanF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodTag="korean";
            }
        });
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
}
