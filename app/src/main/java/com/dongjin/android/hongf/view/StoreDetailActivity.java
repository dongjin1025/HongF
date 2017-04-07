package com.dongjin.android.hongf.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.adapter.DetailPhotoAdapter;
import com.dongjin.android.hongf.adapter.StoryAdapter2;
import com.dongjin.android.hongf.model.KaKaoInfo;
import com.dongjin.android.hongf.model.Review;
import com.dongjin.android.hongf.model.Store;
import com.dongjin.android.hongf.presenter.DetailPresenter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class StoreDetailActivity extends AppCompatActivity implements StoreDetail_View {

    private LinearLayout linearLayout_addReview;
    private LinearLayout linearLayout_bookmark;
    private ImageView detail_ig_bookmark;
    private TextView tvAddress;
    private TextView tvType;
    private TextView tvPrice;
    private TextView tvTell;
    private TextView tvStoreName;
    private TextView tvInfo;
    private RecyclerView recyclerView;
    private RecyclerView review_recycler;
    private View view;
    StoryAdapter2 adapter;
    DetailPresenter presenter;
    private String detail_id;
    Store store;

    private String foodtag;
    private ImageButton btnBack;
    DetailPhotoAdapter photoAdapter;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference myRef=firebaseDatabase.getReference();
    ArrayList<Uri> uris;
    private Boolean bookmarked=false;
    private KaKaoInfo kaKaoInfo;
    DatabaseReference storyRef= FirebaseDatabase.getInstance().getReference();
    ArrayList<Review> reviews;
    ArrayList<String> keyArray;
    DatabaseReference bookmarkRef;
    DatabaseReference bookmarkRef2;
    DatabaseReference storePhotosRef;
    DatabaseReference storeref;
    private ChildEventListener photoChildL;
    private ChildEventListener bookChildL;
    private ChildEventListener storyChildL;
    private TextView naviToReviewList;




    @Override
    protected void onStart() {
        super.onStart();
        photoChildL=new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String ur= (String) dataSnapshot.getValue();
                Log.e("BOOKMARKREF KEYUYYY123",ur);

                uris.add(Uri.parse(ur));
                Collections.reverse(uris);
                presenter.getReviewImages(uris);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        storePhotosRef.addChildEventListener(photoChildL);

        bookChildL=new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String id=dataSnapshot.getKey().toString();
                Log.e("BOOKMARKREF KEYUYYY",id);
                if(dataSnapshot.getKey().toString().equals(kaKaoInfo.read_id_kakao())){
                    bookmarked=true;
                    if(bookmarked){
                        detail_ig_bookmark.setImageResource(R.drawable.btn_common_card_like_pressed);
                    }else{
                        detail_ig_bookmark.setImageResource(R.drawable.btn_unpress_like);
                    }

                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        bookmarkRef.addChildEventListener(bookChildL);
        reviews=new ArrayList<>();
        keyArray=new ArrayList<>();

        storyChildL=new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Review review=dataSnapshot.getValue(Review.class);
                String key=dataSnapshot.getKey();
                if(!key.equals("")){
                    keyArray.add(key);
                }
                Collections.reverse(keyArray);
                Log.e("keyArrayTag",""+keyArray.size());
                reviews.add(review);
                Collections.reverse(keyArray);
                adapter.setAdapterData(reviews,keyArray);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        storyRef.child("story2").child(detail_id).addChildEventListener(storyChildL);
        storyRef.child("Story").keepSynced(true);


    }

    @Override
    protected void onStop() {
        storyRef.child("story2").child(detail_id).removeEventListener(storyChildL);
        bookmarkRef.removeEventListener(bookChildL);
        storePhotosRef.removeEventListener(photoChildL);

        reviews.clear();
        keyArray.clear();

        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);
        presenter=new DetailPresenter();
        presenter.attachView(this);
        kaKaoInfo=KaKaoInfo.getInstance();
        uris=new ArrayList<>();
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();



        store = bundle.getParcelable("Store");



        detail_id = store.getId();


        foodtag = store.getStorefood();
        bookmarkRef = myRef.child("Store").child(detail_id).child("Bookmark");
        storePhotosRef = myRef.child("Store").child(detail_id).child("Urls");
        bookmarkRef2 = myRef.child("bookmark").child(kaKaoInfo.read_id_kakao()).child(detail_id);
        storePhotosRef.keepSynced(true);
        bookmarkRef.keepSynced(true);
        bookmarkRef2.keepSynced(true);

        photoAdapter = new DetailPhotoAdapter(this);
        adapter = new StoryAdapter2(this);


        recyclerView = (RecyclerView) findViewById(R.id.detail_recycler);
        recyclerView.setAdapter(photoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        review_recycler = (RecyclerView) findViewById(R.id.detail_review_recycler);

        review_recycler.setAdapter(adapter);
        LinearLayoutManager manager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager.setReverseLayout(true);
        review_recycler.setLayoutManager(manager);


        view = findViewById(R.id.tb_detail);
        btnBack = (ImageButton) view.findViewById(R.id.ib_back_toolbar);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        naviToReviewList=(TextView)findViewById(R.id.detail_tv_morereview);
        tvAddress=(TextView)findViewById(R.id.detail_tv_address);
        tvPrice=(TextView)findViewById(R.id.detail_tv_price);
        tvTell=(TextView)findViewById(R.id.detail_tv_tell);
        tvType=(TextView)findViewById(R.id.detail_tv_type);
        tvStoreName=(TextView)findViewById(R.id.detail_tv_storename);
        tvInfo=(TextView)findViewById(R.id.detail_tv_info);
        linearLayout_addReview=(LinearLayout)findViewById(R.id.detail_review_btn);
        linearLayout_bookmark=(LinearLayout)findViewById(R.id.detail_btn_bookmark);
        detail_ig_bookmark=(ImageView)findViewById(R.id.detail_ig_bookmark);

        linearLayout_addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.navigateToPostReview(getContext(),PostReviewActivity.class,store.getStorename(),store.getId(),store.getStorefood());
            }
        });
        linearLayout_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.setBookMakrk();

            }
        });
        naviToReviewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StoreDetailActivity.this,Detail_ReviwList.class);
                intent.putExtra("keyarray",keyArray);
                intent.putExtra("reviews",reviews);
                startActivity(intent);
            }
        });



        tvAddress.setText(store.getStoreaddress());
        tvTell.setText(store.getPhone());
        tvType.setText(store.getStorefood());
        tvPrice.setText(store.getStoreprice());
        setFoodTagText(store.getStorefood());
        tvStoreName.setText(store.getStorename());
        tvInfo.setText("즐겨찾기: "+store.getBookmarkcount()+
        " 평점: "+store.getAveragerating()+" 리뷰: "+store.getReviewcount());

        linearLayout_addReview=(LinearLayout)findViewById(R.id.detail_review_btn);



    }
    public void setFoodTagText(String tag){
        switch (tag){
            case "korean":
                tvType.setText("한식");
                break;
            case "japanease":
                tvType.setText("일식");

                break;
            case "chinease":
                tvType.setText("중식");

                break;
            case "wastern":
                tvType.setText("양식");

                break;
            case "world":
                tvType.setText("기타세계음식");

                break;
            case "cafe":
                tvType.setText("까페/디저트");
                break;
            case "bar":
                tvType.setText("술집");

                break;
            case "hope":
                tvType.setText("술집");

                break;
            case "fastfood":
                tvType.setText("패스트푸드");

                break;
            case "koreansnack":
                tvType.setText("분식");

                break;


        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showStorePhotos(ArrayList<Uri> uris) {
        photoAdapter.setImageDatas(uris);
    }

    @Override
    public void setClickedBookmark() {
        if(!bookmarked){
            detail_ig_bookmark.setImageResource(R.drawable.btn_common_card_like_pressed);

            HashMap<String,Object> hashmap=new HashMap<>();
            hashmap.put(kaKaoInfo.read_id_kakao(),kaKaoInfo.read_name_kakao());
            bookmarkRef.updateChildren(hashmap);
            bookmarkRef2.updateChildren(hashmap);
            bookmarked=true;

        }else{
            detail_ig_bookmark.setImageResource(R.drawable.btn_unpress_like);
            bookmarkRef.child(kaKaoInfo.read_id_kakao()).removeValue();
            bookmarkRef2.child(detail_id).removeValue();
            bookmarked=false;
        }

        myRef.child("Store").child(detail_id).child("Bookmark").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count= (int) dataSnapshot.getChildrenCount();
                Log.e("BOOKMARK COUNT TAG",count+"");
                HashMap<String,Object> hashmap=new HashMap<>();
                hashmap.put("bookmarkcount",count);
                myRef.child("Store").child(detail_id).updateChildren(hashmap);
                myRef.child("Store2").child(foodtag).child(detail_id).updateChildren(hashmap);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
