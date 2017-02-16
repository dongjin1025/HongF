package com.dongjin.android.hongf.presenter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dongjin.android.hongf.view.StoreDetail_View;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by kimdongjin on 2017. 2. 17..
 */

public class DetailPresenter implements Presenter<StoreDetail_View> {

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://hongf-153308.appspot.com");
    ArrayList<Uri> uris;
    StoreDetail_View view;
    @Override
    public void attachView(StoreDetail_View view) {
        this.view=view;
    }

    @Override
    public void detachView() {

    }
    public void getReviewImages(String storeId){
        uris=new ArrayList<>();

        storageRef.child("Store").child(storeId).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                uris.add(uri);
                view.showStorePhotos(uris);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Failed to get Images",e.toString());
            }
        });

    }
}
