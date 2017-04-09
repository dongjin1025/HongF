package com.dongjin.android.hongf.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;
import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.adapter.PostReivewPhotosAdapter;
import com.dongjin.android.hongf.model.KaKaoInfo;
import com.dongjin.android.hongf.model.Store;
import com.dongjin.android.hongf.presenter.PostReviewPresenter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class PostReviewActivity extends AppCompatActivity {

    private Button btnAddPhotos;
    private Button btnPost;
    private ImageView igGood;
    private ImageView igSoSo;
    private ImageView igFuck;
    private ImageView selectedIg;
    private ArrayList<ImageView> ratingImages;
    private TextView tvGood;
    private TextView tvSoSo;
    private TextView tvFuck;
    private ArrayList<TextView> ratingTexts;
    private LinearLayout linearGood;
    private LinearLayout linearSoSo;
    private LinearLayout linearFuck;

    private ImageView btn_cancel;

    private EditText etContent;
    private float rate;
    private float currentRate;


    private PostReviewPresenter presenter;
    public ArrayList<Bitmap> bitmaps;
    private PostReivewPhotosAdapter adapter;
    private RecyclerView recyclerView;
    private String id;
    DatabaseReference storyRef;

    private String kakoProfile;
    private int likeCountl;
    private int commentCount;
    private float averageRating;
    private String username;
    private String storename;
    private String content;
    private String userId;
    private String foodtag;



    ArrayList<Image> images;
    KaKaoInfo kaKaoInfo;
    Store store;
    DatabaseReference storeRef;
    DatabaseReference storeRef2;
    int reviewcount;
    HashMap<String,Object> countHashmap;
    HashMap<String,Object> rateHashmap;
    Store ratingStore;

    @Override
    protected void onStart() {
        super.onStart();
        storyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                reviewcount = (int) dataSnapshot.getChildrenCount();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        storeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ratingStore=dataSnapshot.getValue(Store.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder dialog = new AlertDialog.Builder(PostReviewActivity.this);
        dialog.setTitle("취소");
        dialog.setMessage("정말 취소 하시겠습니까?");
        dialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                finish();

            }
        });
        dialog.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });

        dialog.show();

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_review);
        Intent intent= getIntent();
        Bundle bundle=intent.getExtras();
        storename=bundle.getString("title");
        id=bundle.getString("id");
        foodtag=bundle.getString("foodtag");
        Log.e("postReview foodtag id",foodtag+id);


        storyRef= FirebaseDatabase.getInstance().getReference().child("story2").child(id);
        storeRef=FirebaseDatabase.getInstance().getReference().child("Store").child(id);
        storeRef2=FirebaseDatabase.getInstance().getReference().child("Store2").child(foodtag).child(id);
        storeRef.keepSynced(true);
        storyRef.keepSynced(true);

        countHashmap=new HashMap<String, Object>();
        rateHashmap=new HashMap<>();

        kaKaoInfo=KaKaoInfo.getInstance();
        if(kaKaoInfo.read_picture_kakao()!=null){
            kakoProfile=kaKaoInfo.read_picture_kakao();
        }
        username=kaKaoInfo.read_name_kakao();
        userId=kaKaoInfo.read_id_kakao();

        selectedIg=null;
        images=new ArrayList<>();
        ratingImages=new ArrayList<>();
        ratingTexts=new ArrayList<>();
        adapter=new PostReivewPhotosAdapter();
        presenter=new PostReviewPresenter();
        btnAddPhotos=(Button)findViewById(R.id.btnAddPhotos);
        btnAddPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addPhotos(PostReviewActivity.this);
            }
        });
        btnPost=(Button)findViewById(R.id.btnPost);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content=etContent.getText().toString();
                if(selectedIg==igGood){
                    rate=5;
                }else if(selectedIg==igSoSo){
                    rate=(float) 2.5;
                }else if(selectedIg==igFuck){
                    rate=0;
                }

                if(reviewcount!=0){

                    currentRate=ratingStore.getAveragerating()*reviewcount;
                    float crating=(currentRate+rate)/(reviewcount+1)*100;
                    int crating2=(int)crating/100;
                    averageRating=(float)crating2;
                }




                if(content==""){
                    Toast.makeText(PostReviewActivity.this,"내용을 입력해주세요!",Toast.LENGTH_LONG).show();
                }else if(selectedIg==null){
                    Toast.makeText(PostReviewActivity.this,"표정으로 평가해주세요!",Toast.LENGTH_LONG).show();
                }else{
                    if(images.size()!=0){
                        AlertDialog.Builder dialog = new AlertDialog.Builder(PostReviewActivity.this);
                        dialog.setTitle("등록");
                        dialog.setMessage("리뷰를 등록 하시겠습니까?");
                        dialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                presenter.postReviewAndPhotosAsWell(images,userId,username,storename,kakoProfile,id,content,rate,foodtag);

                                if (reviewcount != 0) {
                                    countHashmap.put("reviewcount", reviewcount + 1);
                                    rateHashmap.put("averagerating", averageRating);
                                    storeRef.updateChildren(rateHashmap);
                                    storeRef.updateChildren(countHashmap);
                                    storeRef2.updateChildren(rateHashmap);
                                    storeRef2.updateChildren(countHashmap);
                                }else{
                                    countHashmap.put("reviewcount", 1);
                                    rateHashmap.put("averagerating", rate);
                                    storeRef.updateChildren(rateHashmap);
                                    storeRef.updateChildren(countHashmap);
                                    storeRef2.updateChildren(rateHashmap);
                                    storeRef2.updateChildren(countHashmap);

                                }

                                store = null;
                                Toast.makeText(PostReviewActivity.this,"리뷰가 등록 되었습니다." +
                                        "사진 업로드는 시간이 약간 걸릴 수 있어요!",Toast.LENGTH_LONG).show();
                                finish();




                            }
                        });
                        dialog.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                            }
                        });

                        dialog.show();


                    }else{
                        AlertDialog.Builder dialog = new AlertDialog.Builder(PostReviewActivity.this);
                        dialog.setTitle("등록");
                        dialog.setMessage("사진 없이 리뷰를 등록 하시겠습니까?");
                        dialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                presenter.postReview(username,userId,storename,kakoProfile,id,content,rate,foodtag);
                                if (reviewcount != 0) {
                                    countHashmap.put("reviewcount", reviewcount + 1);
                                    rateHashmap.put("averagerating", averageRating);
                                    storeRef.updateChildren(rateHashmap);
                                    storeRef.updateChildren(countHashmap);
                                    storeRef2.updateChildren(rateHashmap);
                                    storeRef2.updateChildren(countHashmap);
                                }else{
                                    countHashmap.put("reviewcount", 1);
                                    rateHashmap.put("averagerating", rate);
                                    storeRef.updateChildren(rateHashmap);
                                    storeRef.updateChildren(countHashmap);
                                    storeRef2.updateChildren(rateHashmap);
                                    storeRef2.updateChildren(countHashmap);

                                }
                                store=null;
                                Toast.makeText(PostReviewActivity.this,"리뷰가 등록 되었습니다",Toast.LENGTH_LONG).show();
                                finish();

                            }
                        });
                        dialog.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                            }
                        });

                        dialog.show();

                    }



                }


            }
        });
        etContent=(EditText)findViewById(R.id.etContent);

        btn_cancel=(ImageView)findViewById(R.id.postReview_btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(PostReviewActivity.this);
                dialog.setTitle("취소");
                dialog.setMessage("정말 취소 하시겠습니까?");
                dialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        finish();

                    }
                });
                dialog.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                });

                dialog.show();

            }
        });

        igGood=(ImageView)findViewById(R.id.igGood);
        igSoSo=(ImageView)findViewById(R.id.igSoSo);
        igFuck=(ImageView)findViewById(R.id.igFuck);
        tvGood=(TextView)findViewById(R.id.tvGood);
        tvSoSo=(TextView)findViewById(R.id.tvSoSo);
        tvFuck=(TextView)findViewById(R.id.tvFuck);
        linearGood=(LinearLayout)findViewById(R.id.linearGood);
        linearSoSo=(LinearLayout)findViewById(R.id.linearSoSo);
        linearFuck=(LinearLayout)findViewById(R.id.linearFuck);
        ratingImages.add(igGood);
        ratingImages.add(igSoSo);
        ratingImages.add(igFuck);
        ratingTexts.add(tvGood);
        ratingTexts.add(tvSoSo);
        ratingTexts.add(tvFuck);
        linearGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedIg=presenter.setSelectedIg(igGood,ratingImages,selectedIg,tvGood
                        ,ratingTexts,PostReviewActivity.this);
            }
        });
        linearSoSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedIg=presenter.setSelectedIg(igSoSo,ratingImages,selectedIg,tvSoSo
                        ,ratingTexts,PostReviewActivity.this);
            }
        });
        linearFuck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedIg=presenter.setSelectedIg(igFuck,ratingImages,selectedIg,tvFuck
                        ,ratingTexts,PostReviewActivity.this);
            }
        });
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(manager);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            //The array list has the image paths of the selected images
            bitmaps=new ArrayList<>();
            images= data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);

            for(int i=0; i<images.size();i++){
                Bitmap bitmap=presenter.getThumbnailImage(images.get(i).path);
                bitmaps.add(bitmap);
            }
            adapter.setReviewPhotos(bitmaps);

        }

    }


}
