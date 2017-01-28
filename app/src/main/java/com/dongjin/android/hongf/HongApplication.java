package com.dongjin.android.hongf;

import android.app.Application;
import android.content.Context;

import com.dongjin.android.hongf.model.DaumSearchApi;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by kimdongjin on 2017. 1. 26..
 */

public class HongApplication extends Application {

    private DaumSearchApi daumSearchApi;
    private Scheduler defaultSubscribeScheduler;

    public static HongApplication get(Context context) {
        return (HongApplication)context.getApplicationContext();
    }

    public DaumSearchApi getDaumSearchApi() {
        if (daumSearchApi == null) {
            daumSearchApi = DaumSearchApi.Factory.create();
        }
        return daumSearchApi;
    }

    //For setting mocks during testing
    public void setDaumSearchApi(DaumSearchApi daumSearchApi) {
        this.daumSearchApi = daumSearchApi;
    }

    public Scheduler defaultSubscribeScheduler() {
        if (defaultSubscribeScheduler == null) {
            defaultSubscribeScheduler = Schedulers.io();
        }
        return defaultSubscribeScheduler;
    }

    //User to change scheduler from tests
    public void setDefaultSubscribeScheduler(Scheduler scheduler) {
        this.defaultSubscribeScheduler = scheduler;
    }
}