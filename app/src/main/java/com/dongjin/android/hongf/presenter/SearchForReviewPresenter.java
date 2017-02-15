package com.dongjin.android.hongf.presenter;

import com.dongjin.android.hongf.view.SearchForReview_View;

/**
 * Created by kimdongjin on 2017. 2. 15..
 */

public class SearchForReviewPresenter implements Presenter<SearchForReview_View> {
    SearchForReview_View view;
    @Override
    public void attachView(SearchForReview_View view) {

        this.view=view;
    }

    @Override
    public void detachView() {

    }


}
