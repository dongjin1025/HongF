package com.dongjin.android.hongf;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.dongjin.android.hongf.adapter.KakaoSDKAdapter;
import com.dongjin.android.hongf.model.DaumSearchApi;
import com.kakao.auth.KakaoSDK;

import rx.Scheduler;
import rx.schedulers.Schedulers;

public class GlobalApplication extends Application {
    private static GlobalApplication mInstance;
    private static volatile Activity currentActivity = null;
    private DaumSearchApi daumSearchApi;
    private Scheduler defaultSubscribeScheduler;

    public static GlobalApplication get(Context context) {
        return (GlobalApplication) context.getApplicationContext();
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

    public static Activity getCurrentActivity() {
        Log.d("TAG", "++ currentActivity : " + (currentActivity != null ? currentActivity.getClass().getSimpleName() : ""));
        return currentActivity;
    }

    public static void setCurrentActivity(Activity currentActivity) {
        GlobalApplication.currentActivity = currentActivity;
    }

    /**
     * singleton
     * @return singleton
     */
    public static GlobalApplication getGlobalApplicationContext() {
        if(mInstance == null)
            throw new IllegalStateException("this application does not inherit GlobalApplication");
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        KakaoSDK.init(new KakaoSDKAdapter());
    }
}
