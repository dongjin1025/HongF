package com.dongjin.android.hongf.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.dongjin.android.hongf.view.PostReviewActivity;
import com.dongjin.android.hongf.view.StoreDetail_View;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by kimdongjin on 2017. 2. 17..
 */

public class DetailPresenter implements Presenter<StoreDetail_View> {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    StoreDetail_View view;
    @Override
    public void attachView(StoreDetail_View view) {
        this.view=view;
    }

    @Override
    public void detachView() {

    }
    public void getReviewImages(ArrayList<Uri> uris){
        view.showStorePhotos(uris);
    }
    public void navigateToPostReview(Context context, Class<PostReviewActivity> activity,String title,String id,String foodtag){
        Intent intent =new Intent(context,activity);
        intent.putExtra("title",title);
        intent.putExtra("id",id);
        intent.putExtra("foodtag",foodtag);
        context.startActivity(intent);
    }
    public void setBookMakrk(){
        view.setClickedBookmark();
    }


}

