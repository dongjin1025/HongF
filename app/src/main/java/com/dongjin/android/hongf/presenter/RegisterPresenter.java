package com.dongjin.android.hongf.presenter;

import com.dongjin.android.hongf.model.Store;
import com.dongjin.android.hongf.view.Register_View;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by kimdongjin on 2017. 2. 6..
 */

public class RegisterPresenter implements Presenter<Register_View> {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://hongf-153308.appspot.com");

    public void writeNewStore(Store store) {
        myRef.child("Store").child(store.getId()).setValue(store);
        myRef.child("Store2").child(store.getStorefood()).child(store.getId()).setValue(store);
    }

    @Override
    public void attachView(Register_View view) {

    }

    @Override
    public void detachView() {

    }
}
