package com.dongjin.android.hongf.presenter;

import com.dongjin.android.hongf.view.Map_View;

/**
 * Created by kimdongjin on 2017. 1. 18..
 */

public class MapPresenter implements Presenter<Map_View> {

    Map_View map_view;

    @Override
    public void attachView(Map_View view) {

        this.map_view=view;
    }

    @Override
    public void detachView() {
        this.map_view=null;


    }


}
