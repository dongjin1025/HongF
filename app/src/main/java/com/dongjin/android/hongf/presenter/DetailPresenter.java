package com.dongjin.android.hongf.presenter;

import android.net.Uri;

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
}