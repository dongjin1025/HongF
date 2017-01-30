package com.dongjin.android.hongf.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;
import com.dongjin.android.hongf.PostReivewPhotosAdapter;
import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.present.PostReviewPresenter;

import java.util.ArrayList;

public class PostReviewActivity extends AppCompatActivity {

    private Button btnAddPhotos;
    private PostReviewPresenter presenter;
    public ArrayList<Bitmap> bitmaps;
    private PostReivewPhotosAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_review);

        adapter=new PostReivewPhotosAdapter();
        presenter=new PostReviewPresenter();
        btnAddPhotos=(Button)findViewById(R.id.btnAddPhotos);
        btnAddPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addPhotos(PostReviewActivity.this);
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
            ArrayList<Image> images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);

            for(int i=0; i<images.size();i++){
                Bitmap bitmap=presenter.getThumbnailImage(images.get(i).path);
                bitmaps.add(bitmap);
            }
            adapter.setReviewPhotos(bitmaps);

        }

    }

}
