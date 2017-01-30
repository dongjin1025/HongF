package com.dongjin.android.hongf.view;

import com.dongjin.android.hongf.model.Item;

import java.util.ArrayList;

/**
 * Created by kimdongjin on 2017. 1. 30..
 */

public interface Search_View extends MvpView {

    void showSearchedPlaces(ArrayList<Item> items);
}
