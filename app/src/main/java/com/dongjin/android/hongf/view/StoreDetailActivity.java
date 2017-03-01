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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.adapter.DetailPhotoAdapter;
import com.dongjin.android.hongf.model.KaKaoInfo;
import com.dongjin.android.hongf.model.Store;
import com.dongjin.android.hongf.model.Urls;
import com.dongjin.android.hongf.presenter.DetailPresenter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StoreDetailActivity extends AppCompatActivity implements StoreDetail_View {

    private Toolbar toolbar;
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
    DetailPresenter presenter;
    private String detail_id;
    Store store;
    DetailPhotoAdapter photoAdapter;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference myRef=firebaseDatabase.getReference();
    ArrayList<Uri> uris;

    private List<Urls> mModels;
    private List<String> mKeys;
    private Boolean bookmarked=false;
    private KaKaoInfo kaKaoInfo;
    DatabaseReference bookmarkRef;
    DatabaseReference storePhotosRef;



    @Override
    protected void onStart() {
        super.onStart();

        storePhotosRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String ur= (String) dataSnapshot.getValue();

                uris.add(Uri.parse(ur));
                presenter.getReviewImages(uris);
                bookmarkRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String id=(String) dataSnapshot.getValue();
                        if(id==kaKaoInfo.read_id_kakao()){
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
                });

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
        });
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

        store=bundle.getParcelable("Store");
        detail_id=store.getId();
        bookmarkRef=myRef.child("Store").child(detail_id).child("Bookmark");
        storePhotosRef=myRef.child("Store").child(detail_id).child("Urls");
        storePhotosRef.keepSynced(true);
        bookmarkRef.keepSynced(true);

        photoAdapter=new DetailPhotoAdapter(this);


        recyclerView=(RecyclerView)findViewById(R.id.detail_recycler);
        recyclerView.setAdapter(photoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));



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


                presenter.navigateToPostReview(getContext(),SearchForReviewActivity.class);
            }
        });
        linearLayout_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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
        linearLayout_addReview=(LinearLayout)findViewById(R.id.detail_review_btn);



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
            bookmarked=true;
        }else{
            detail_ig_bookmark.setImageResource(R.drawable.btn_unpress_like);
            bookmarkRef.child(kaKaoInfo.read_id_kakao()).removeValue();
            bookmarked=false;
        }

    }
}
