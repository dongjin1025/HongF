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
    public int bookmarkcount;

    public String getFinder() {
        return finder;
    }

    String finderId;

    public String getFinderId() {
        return finderId;
    }

    public void setFinderId(String finderId) {
        this.finderId = finderId;
    }

    public void setFinder(String finder) {
        this.finder = finder;
    }

    public String finder;

    public int getBookmarkcount() {
        return bookmarkcount;
    }

    public void setBookmarkcount(int bookmarkcount) {
        this.bookmarkcount = bookmarkcount;
    }

    public int getReviewcount() {
        return reviewcount;
    }

    public void setReviewcount(int reviewcount) {
        this.reviewcount = reviewcount;
    }



    public float getAveragerating() {
        return averagerating;
    }

    public void setAveragerating(float averagerating) {
        this.averagerating = averagerating;
    }

    public int reviewcount;

    public float averagerating;

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
                 int reviewcount, String finder, float averagerating, int bookmarkcount, String phone
    , String latit, String longni) {
        this.storename = storename;
        this.storeaddress = storeaddress;
        this.storefood = storefood;
        this.storeprice = storeprice;
        this.id=id;
        this.imageUrl =imageUrl;
        this.reviewcount=reviewcount;
        this.finder=finder;
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
        dest.writeInt(reviewcount);
        dest.writeString(finder);
        dest.writeFloat(averagerating);
        dest.writeInt(bookmarkcount);
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
        reviewcount=in.readInt();
        finder=in.readString();
        averagerating=in.readFloat();
        bookmarkcount=in.readInt();
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
