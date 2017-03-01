package com.dongjin.android.hongf.presenter;

import com.dongjin.android.hongf.model.MarkerItem;
import com.dongjin.android.hongf.model.Store;
import com.dongjin.android.hongf.view.Register_View;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

/**
 * Created by kimdongjin on 2017. 2. 6..
 */

public class RegisterPresenter implements Presenter<Register_View> {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    FirebaseStorage storage = FirebaseStorage.getInstance();


    public void writeNewStore(Store store) {
        MarkerItem item=new MarkerItem();
        double lati= Double.parseDouble(store.getLatit());
        double longi= Double.parseDouble(store.getLongni());
        item.setLat(lati);
        item.setLon(longi);
        item.setId(store.getId());
        item.setFoodTag((store.getStorefood()));

        myRef.child("Store").child(store.getId()).setValue(store);
        myRef.child("Store2").child(store.getStorefood()).child(store.getId()).setValue(store);
        myRef.child("storeMarker").child(store.getId()).setValue(item);
        myRef.child("storeMarker2").child(store.getStorefood()).push().setValue(item);
    }

    @Override
    public void attachView(Register_View view) {

    }

    @Override
    public void detachView() {

    }
}
