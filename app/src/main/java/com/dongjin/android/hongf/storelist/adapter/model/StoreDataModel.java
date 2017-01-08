package com.dongjin.android.hongf.storelist.adapter.model;

import com.dongjin.android.hongf.networkmodel.Store;

/**
 * Created by user on 2016-12-27.
 */

public interface StoreDataModel {

    void add(Store store);
    Store removeStore(int position);
    Store getStore(int position);
    int getSize();

}
