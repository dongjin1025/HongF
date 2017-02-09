package com.dongjin.android.hongf.model;

/**
 * Created by kimdongjin on 2017. 1. 25..
 */

public class Store {

    public String storename;
    public String storeaddress;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String imageUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String id;

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public String getStoreaddress() {
        return storeaddress;
    }

    public void setStoreaddress(String storeaddress) {
        this.storeaddress = storeaddress;
    }

    public String getStorefood() {
        return storefood;
    }

    public void setStorefood(String storefood) {
        this.storefood = storefood;
    }

    public String getStoreprice() {
        return storeprice;
    }

    public void setStoreprice(String storeprice) {
        this.storeprice = storeprice;
    }

    public String storefood;
    public String storeprice;


    public Store() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Store(String storename, String storeaddress,String storefood,String storeprice,String id,String imageUrl) {
        this.storename = storename;
        this.storeaddress = storeaddress;
        this.storefood = storefood;
        this.storeprice = storeprice;
        this.id=id;
        this.imageUrl =imageUrl;
    }

}
