package com.dongjin.android.hongf.presenter;

import com.dongjin.android.hongf.view.Story_View;

/**
 * Created by kimdongjin on 2017. 2. 22..
 */

public class StoryPresenter implements Presenter<Story_View> {

    Story_View view;

    @Override
    public void attachView(Story_View view) {
        this.view=view;
    }

    @Override
    public void detachView() {

    }
    public void setReviewDatas(){
        view.showReviewList();
    }
    public void setReviewPhotos(){
        view.showReviewPhotos();
    }
}
