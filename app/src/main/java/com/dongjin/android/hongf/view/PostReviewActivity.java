package com.dongjin.android.hongf.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;

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

    private EditText etContent;
    private float rate;


    private PostReviewPresenter presenter;
    public ArrayList<Bitmap> bitmaps;
    private PostReivewPhotosAdapter adapter;
    private RecyclerView recyclerView;
    private String id;

    private String kakoProfile;
    private int likeCountl;
    private int commentCount;
    private String username;
    private String storename;
    private String content;
    ArrayList<Image> images;
    KaKaoInfo kaKaoInfo;
    Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_review);
        Intent intent= getIntent();
        Bundle bundle=intent.getExtras();
        storename=bundle.getString("title");
        id=bundle.getString("id");


        kaKaoInfo=KaKaoInfo.getInstance();
        if(kaKaoInfo.read_picture_kakao()!=null){
            kakoProfile=kaKaoInfo.read_picture_kakao();
        }
        username=kaKaoInfo.read_name_kakao();

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
                                presenter.postReviewAndPhotosAsWell(images,username,storename,kakoProfile,id,content,rate);
                                store=null;
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
                                presenter.postReview(username,storename,kakoProfile,id,content,rate);
                                store=null;
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
