package com.dongjin.android.hongf.presenter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.dongjin.android.hongf.model.Store;
import com.dongjin.android.hongf.view.Map_View;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by kimdongjin on 2017. 1. 18..
 */

public class MapPresenter implements Presenter<Map_View> {
    Map_View map_view;
    Context context;

    public MapPresenter (){

        context=map_view.getContext();

    }
    @Override
    public void attachView(Map_View view) {

        this.map_view=view;
    }

    @Override
    public void detachView() {
        this.map_view=null;

    }
    public Marker addStoreMarker(GoogleMap googleMap,View marker_root_view,Store store,Marker marker,boolean isSelected){
        double lati= (long) marker.getPosition().latitude;
        double longni=marker.getPosition().longitude;

        LatLng latLng=new LatLng(lati,longni);

        return addStoreMarker(googleMap,marker_root_view,store,isSelected);

    }
    private Marker addStoreMarker(GoogleMap googleMap,View marker_root_view,Store store,boolean isSelected){

        double lati= Long.parseLong(store.getLatit());
        double longni= Long.parseLong(store.getLongni());
        LatLng latLng = new LatLng(lati,longni);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView
                (map_view.getContext(),marker_root_view)));


        return googleMap.addMarker(markerOptions);

    }

    private Bitmap createDrawableFromView(Context context, View view) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;

    }



}
