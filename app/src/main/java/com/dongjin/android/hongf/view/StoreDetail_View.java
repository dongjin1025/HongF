package com.dongjin.android.hongf.view;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by kimdongjin on 2017. 2. 17..
 */

public interface StoreDetail_View extends MvpView {

    void showStorePhotos(ArrayList<Uri> uris);
}
