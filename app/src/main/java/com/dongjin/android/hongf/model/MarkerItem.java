package com.dongjin.android.hongf.model;

/**
 * Created by kimdongjin on 2017. 2. 25..
 */

public class MarkerItem {
    double lat;
    double lon;

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    int tag;

    public String getFoodTag() {
        return foodTag;
    }

    public void setFoodTag(String foodTag) {
        this.foodTag = foodTag;
    }

    String foodTag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;





    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
