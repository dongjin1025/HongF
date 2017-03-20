package com.dongjin.android.hongf.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;

import com.dongjin.android.hongf.R;

public class Dialog_StoreList_Filter extends AppCompatActivity{

//    private ImageView igKoreanF;
//    private ImageView chinease;
//    private ImageView japanease;
//    private ImageView wastern;
//    private ImageView bar;
//    private ImageView koreansnack;
//    private ImageView hope;
//    private ImageView world;
//    private ImageView cafe;
//    private ImageView fastfood;
//    private ImageView clickedIg;

    private Button btnApply;
    private Button btnCancel;
    private String orderby;
    RadioGroup rg;

//    private String foodTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.dialog_storelistorder_filter);
        //foodTag="null";
        orderby="null";


        //setImages();
        rg=(RadioGroup)findViewById(R.id.dialog_rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.dialog_rb_bookmark:
                        orderby="bookmarkcount";
                        break;

                    case R.id.dialog_rb_rate:
                        orderby="averagerating";
                        break;
                    case R.id.dialog_rb_review:
                        orderby="reviewcount";
                        break;
                }
            }
        });
        btnApply=(Button)findViewById(R.id.filter_btn_apply);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                //intent.putExtra("tagkey",foodTag);
                intent.putExtra("orderby",orderby);
                setResult(RESULT_OK,intent);
                finish();

            }
        });
        btnCancel=(Button)findViewById(R.id.filter_btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //foodTag="";
                Intent intent =new Intent();
                //intent.putExtra("tagkey",foodTag);
                intent.putExtra("orderby",orderby);
                setResult(990,intent);

                finish();
            }
        });






    }


//    public void setImages() {
//
//        igKoreanF = (ImageView) findViewById(R.id.register_ig_korean);
//        igKoreanF.setOnClickListener(this);
//        japanease = (ImageView) findViewById(R.id.register_ig_japanease);
//        japanease.setOnClickListener(this);
//        chinease = (ImageView) findViewById(R.id.register_ig_chinease);
//        chinease.setOnClickListener(this);
//        wastern = (ImageView) findViewById(R.id.register_ig_wastern);
//        wastern.setOnClickListener(this);
//        world = (ImageView) findViewById(R.id.register_ig_world);
//        world.setOnClickListener(this);
//        bar = (ImageView) findViewById(R.id.register_ig_bar);
//        bar.setOnClickListener(this);
//        hope = (ImageView) findViewById(R.id.register_ig_hope);
//        hope.setOnClickListener(this);
//        koreansnack = (ImageView) findViewById(R.id.register_ig_koreansnack);
//        koreansnack.setOnClickListener(this);
//        fastfood = (ImageView) findViewById(R.id.register_ig_fastfood);
//        fastfood.setOnClickListener(this);
//        cafe = (ImageView) findViewById(R.id.register_ig_cafe);
//        cafe.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.register_ig_korean:
//                foodTag="korean";
//                clickImageView(igKoreanF);
//                break;
//            case R.id.register_ig_japanease:
//                foodTag="japanease";
//                clickImageView(japanease);
//                break;
//            case R.id.register_ig_chinease:
//                foodTag="chinease";
//                clickImageView(chinease);
//                break;
//            case R.id.register_ig_wastern:
//                foodTag="wastern";
//                clickImageView(wastern);
//                break;
//            case R.id.register_ig_world:
//                foodTag="world";
//                clickImageView(world);
//                break;
//            case R.id.register_ig_cafe:
//                foodTag="cafe";
//                clickImageView(cafe);
//                break;
//            case R.id.register_ig_bar:
//                foodTag="bar";
//                clickImageView(bar);
//                break;
//            case R.id.register_ig_hope:
//                foodTag="hope";
//                clickImageView(hope);
//                break;
//            case R.id.register_ig_fastfood:
//                foodTag="fastfood";
//                clickImageView(fastfood);
//                break;
//            case R.id.register_ig_koreansnack:
//                foodTag="koreansnack";
//                clickImageView(koreansnack);
//                break;
//
//
//        }
//    }
//
//    public void clickImageView(ImageView imageView) {
//
//        if(clickedIg==null){
//            imageView.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
//            clickedIg=imageView;
//        }else if(clickedIg==imageView){
//            imageView.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
//            clickedIg=null;
//            foodTag="null";
//        }else if(clickedIg!=null && clickedIg!=imageView){
//            clickedIg.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
//            imageView.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
//            clickedIg=imageView;
//        }
//
//
//    }
}
