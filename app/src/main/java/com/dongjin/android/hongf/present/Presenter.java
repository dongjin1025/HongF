package com.dongjin.android.hongf.present;

/**
 * Created by kimdongjin on 2017. 1. 30..
 */

public interface Presenter<V> {
    void attachView(V view);
    void detachView();
}
