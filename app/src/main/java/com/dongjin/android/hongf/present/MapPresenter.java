package com.dongjin.android.hongf.present;

import android.util.Log;

import com.dongjin.android.hongf.HongApplication;
import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.model.DaumSearchApi;
import com.dongjin.android.hongf.model.SearchItem;
import com.dongjin.android.hongf.view.Map_View;


import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by kimdongjin on 2017. 1. 18..
 */

public class MapPresenter implements Presenter<Map_View> {

    Map_View map_view;
    public static String TAG = "MapPresenter";

    private Subscription subscription;
    private List<SearchItem> searchItems;
    @Override
    public void attachView(Map_View view) {

        this.map_view=view;
    }

    @Override
    public void detachView() {
        this.map_view=null;
        if (subscription != null) subscription.unsubscribe();

    }

    public void loadPlace(String enteredPlace){
        String username = enteredPlace.trim();
        if (username.isEmpty()) return;


        if (subscription != null) subscription.unsubscribe();
        HongApplication application = HongApplication.get(map_view.getContext());
        DaumSearchApi daumSearchApi = application.getDaumSearchApi();
        subscription = daumSearchApi.daumSearch(application.getPackageName(),"android",enteredPlace,"37.551593, 126.924979",3000)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Subscriber<List<SearchItem>>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "Searchs loaded " + searchItems);
                        if (!searchItems.isEmpty()) {

                        } else {

                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        Log.e(TAG, "Error loading GitHub repos ", error);
//                        if (isHttp404(error)) {
//                            mainMvpView.showMessage(R.string.error_username_not_found);
//                        } else {
//                            mainMvpView.showMessage(R.string.error_loading_repos);
//                        }
                    }

                    @Override
                    public void onNext(List<SearchItem> searchItems) {
                        MapPresenter.this.searchItems=searchItems;
                    }

                });
    }
}
