package com.dongjin.android.hongf.model;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by kimdongjin on 2017. 2. 9..
 */

public class Review {
    String username;

    public ArrayList<Uri> getUrls() {
        return urls;
    }

    public void setUrls(ArrayList<Uri> urls) {
        this.urls = urls;
    }

    ArrayList<Uri> urls;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    String storeName;
    String content;

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    float rate;

}
