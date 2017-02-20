package com.dongjin.android.hongf.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kimdongjin on 2017. 1. 25..
 */

public class Store implements Parcelable {

    public String storename;
    public String storeaddress;
    public Store(Parcel in) {
        readFromParcel(in);
    }

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
    public String bookmarkcount;

    public String getBookmarkcount() {
        return bookmarkcount;
    }

    public void setBookmarkcount(String bookmarkcount) {
        this.bookmarkcount = bookmarkcount;
    }

    public String getReviewcount() {
        return reviewcount;
    }

    public void setReviewcount(String reviewcount) {
        this.reviewcount = reviewcount;
    }

    public String getDiscoveredBy() {
        return discoveredBy;
    }

    public void setDiscoveredBy(String discoveredBy) {
        this.discoveredBy = discoveredBy;
    }

    public String getAveragerating() {
        return averagerating;
    }

    public void setAveragerating(String averagerating) {
        this.averagerating = averagerating;
    }

    public String reviewcount;
    public String discoveredBy;
    public String averagerating;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String phone;

    public String longni;

    public String getLatit() {
        return latit;
    }

    public void setLatit(String latit) {
        this.latit = latit;
    }

    public String getLongni() {
        return longni;
    }

    public void setLongni(String longni) {
        this.longni = longni;
    }

    public String latit;


    public Store() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Store(String storename, String storeaddress, String storefood, String storeprice, String id, String imageUrl,
                 String reviewcount, String discoveredBy, String averagerating, String bookmarkcount, String phone
    , String latit, String longni) {
        this.storename = storename;
        this.storeaddress = storeaddress;
        this.storefood = storefood;
        this.storeprice = storeprice;
        this.id=id;
        this.imageUrl =imageUrl;
        this.reviewcount=reviewcount;
        this.discoveredBy=discoveredBy;
        this.averagerating=averagerating;
        this.bookmarkcount=bookmarkcount;
        this.phone=phone;
        this.latit=latit;
        this.longni=longni;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(storename);
        dest.writeString(storeaddress);
        dest.writeString(storefood);
        dest.writeString(storeprice);
        dest.writeString(imageUrl);
        dest.writeString(reviewcount);
        dest.writeString(discoveredBy);
        dest.writeString(averagerating);
        dest.writeString(bookmarkcount);
        dest.writeString(phone);
        dest.writeString(longni);
        dest.writeString(latit);


    }
    private void readFromParcel(Parcel in){

        id=in.readString();
        storename=in.readString();
        storeaddress=in.readString();
        storefood=in.readString();
        storeprice=in.readString();
        imageUrl=in.readString();
        reviewcount=in.readString();
        discoveredBy=in.readString();
        averagerating=in.readString();
        bookmarkcount=in.readString();
        phone=in.readString();
        longni=in.readString();
        latit=in.readString();

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Store createFromParcel(Parcel in) {
            return new Store(in);
        }

        public Store[] newArray(int size) {
            return new Store[size];
        }
    };
}
