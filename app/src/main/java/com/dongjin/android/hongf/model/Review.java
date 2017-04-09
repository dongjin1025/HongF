package com.dongjin.android.hongf.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kimdongjin on 2017. 2. 9..
 */

public class Review implements Parcelable{

    public Review(Parcel in) {
        readFromParcel(in);
    }
    public Review(){

    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }


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



    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }


    String username;
    String storeName;
    String content;
    float rate;
    String userPicture;
    String key;
    String storeId;
    String storeTag;

    public String getStoreTag() {
        return storeTag;
    }

    public void setStoreTag(String storeTag) {
        this.storeTag = storeTag;
    }

    String date;
    int likeCount;
    int commentCount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    String userId;


    public Review(String username, String storeName, String content, String userPicture, String key, String storeId,
                 int likeCount, float rate, int commentCount ,String date,String userId,String storeTag) {
        this.username = username;
        this.storeName = storeName;
        this.content = content;
        this.userPicture = userPicture;
        this.key=key;
        this.storeId =storeId;
        this.likeCount=likeCount;
        this.rate=rate;
        this.commentCount=commentCount;
        this.date=date;
        this.userId=userId;
        this.storeTag=storeTag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(storeName);
        dest.writeString(content);
        dest.writeString(userPicture);
        dest.writeString(key);
        dest.writeString(storeId);
        dest.writeInt(likeCount);
        dest.writeString(date);
        dest.writeFloat(rate);
        dest.writeInt(commentCount);
        dest.writeString(userId);
        dest.writeString(storeTag);

    }
    private void readFromParcel(Parcel in){

        username=in.readString();
        storeName=in.readString();
        content=in.readString();
        userPicture=in.readString();
        key=in.readString();
        storeId=in.readString();
        likeCount=in.readInt();
        date=in.readString();
        rate=in.readFloat();
        commentCount=in.readInt();
        userId=in.readString();
        storeTag=in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

}
