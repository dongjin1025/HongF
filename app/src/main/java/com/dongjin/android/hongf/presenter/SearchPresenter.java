package com.dongjin.android.hongf.presenter;

import android.util.Log;

import com.dongjin.android.hongf.GlobalApplication;
import com.dongjin.android.hongf.model.DaumSearchApi;
import com.dongjin.android.hongf.model.Item;
import com.dongjin.android.hongf.model.RootData;
import com.dongjin.android.hongf.view.Search_View;

import java.util.ArrayList;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by kimdongjin on 2017. 1. 30..
 */

public class SearchPresenter implements Presenter<Search_View> {

    public static String TAG = "SearchPresenter";
    private Subscription subscription;
    private RootData rootData;
    private Search_View search_view;

    @Override
    public void attachView(Search_View view) {
        this.search_view=view;
    }

    @Override
    public void detachView() {

        this.search_view=null;
        if (subscription != null) subscription.unsubscribe();
    }
    public void loadPlace(String enteredPlace){
        search_view.hideKeyBoard();

        String username = enteredPlace.trim();
        if (username.isEmpty()) return;


        if (subscription != null) subscription.unsubscribe();
        GlobalApplication application = GlobalApplication.getGlobalApplicationContext().get(search_view.getContext());
        DaumSearchApi daumSearchApi = application.getDaumSearchApi();
        subscription = daumSearchApi.daumSearch(application.getPackageName(),"android","a40a441f065ed60aecb1b3a05805a68c",enteredPlace,"37.551593,126.924979",3000)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Subscriber<RootData>() {
                    @Override
                    public void onCompleted() {
                        ArrayList<Item> items;

                        if (rootData!=null) {

                            items = rootData.getChannel().getItem();
                            search_view.showSearchedPlaces(items);
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
                    public void onNext(RootData rootData) {
                        SearchPresenter.this.rootData=rootData;
                    }

                });
    }
}
