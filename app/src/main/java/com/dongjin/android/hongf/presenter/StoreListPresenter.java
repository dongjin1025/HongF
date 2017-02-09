package com.dongjin.android.hongf.presenter;

import com.dongjin.android.hongf.model.Store;
import com.dongjin.android.hongf.view.StoreList_View;

import java.util.ArrayList;

/**
 * Created by kimdongjin on 2017. 2. 6..
 */

public class StoreListPresenter implements Presenter<StoreList_View> {

    StoreList_View storeList_view;
    ArrayList<Store> stores;



    @Override
    public void attachView(StoreList_View view) {
        this.storeList_view=view;
    }
    @Override
    public void detachView() { }


    public void setSotreListData(){
        storeList_view.showStoreList();
    }


}
