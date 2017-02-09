package com.dongjin.android.hongf.presenter;

import android.content.Context;
import android.content.Intent;

import com.dongjin.android.hongf.view.Add_View;
import com.dongjin.android.hongf.view.PostReviewActivity;
import com.dongjin.android.hongf.view.SearchActivity;

/**
 * Created by kimdongjin on 2017. 2. 5..
 */

public class AddPresenter implements Presenter<Add_View> {
    Add_View add_view;
    @Override
    public void attachView(Add_View view) {
        this.add_view=view;

    }

    @Override
    public void detachView() {

    }
    public void navigateToSearch(Context context, Class<SearchActivity> activity){
        Intent intent = new Intent(context,activity);
        context.startActivity(intent);
    }
    public void navigateToPostReview(Context context, Class<PostReviewActivity> activity){
        Intent intent = new Intent(context,activity);
        context.startActivity(intent);
    }
}
