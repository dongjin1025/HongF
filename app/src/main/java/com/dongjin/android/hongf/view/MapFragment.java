package com.dongjin.android.hongf.view;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.adapter.MapPagerAdapter;
import com.dongjin.android.hongf.model.MarkerItem;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback,Map_View {

    private GoogleMap googleMap;
    private static View v;

    private ViewPager pager;
    private CameraUpdate center;
    private Marker selectedMarker;
    private View marker_root_view;
    private ImageView ig_marker;
    private ArrayList<MarkerItem> items;
    private ArrayList<Marker> markers;
    private MapPagerAdapter adapter;
    HashMap<Marker,Integer> hashMap;
    HashMap<Integer,Marker> hashMap2;


    DatabaseReference markerRef;


    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try{
            v=inflater.inflate(R.layout.fragment_map, container, false);
            SupportMapFragment supportMapFragment=(SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
            supportMapFragment.getMapAsync(this);
        }catch (Exception e){

        }
        markerRef = FirebaseDatabase.getInstance().getReference().child("storeMarker");
        markerRef.keepSynced(true);
        pager= (ViewPager)v.findViewById(R.id.pager);
        adapter= new MapPagerAdapter(inflater,getContext());
        pager.setAdapter(adapter);
        pager.bringToFront();
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {

                changeSelectedMarkerWithPager(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

                if(state==markers.size()-1){
                    pager.setCurrentItem(0,true);
                }

            }
        });



        return v;
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;



        setCustomMarkerView();
        getMarkerItems();
        LatLng hongdae = new LatLng(37.551593, 126.924979);
        //googleMap.addMarker(new MarkerOptions().position(hongdae).title("Marker in hongdae"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hongdae,14));


        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {



                center = CameraUpdateFactory.newLatLng(marker.getPosition());
                googleMap.animateCamera(center);

                if(marker!=selectedMarker){
                    changeSelectedMarker(marker);
                }
                marker.setZIndex(markers.size());

                pager.setCurrentItem(hashMap.get(marker),true);


                return true;
            }
        });



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(v!=null){
            ViewGroup parent = (ViewGroup)v.getParent();
            if(parent!=null){
                parent.removeView(v);
            }
        }
    }

    @Override
    public void displayStores() {



    }
    private void changeSelectedMarkerWithPager(int position){
        if(selectedMarker !=null){

            Marker seleMarker;
            seleMarker=addMarker(selectedMarker,false);
            selectedMarker.remove();


        }
        try{
            Marker marker=hashMap2.get(position);
            selectedMarker=addMarker(marker,true);
            selectedMarker.setZIndex(markers.size());
            hashMap.put(selectedMarker,position);
            hashMap2.remove(position);
            hashMap2.put(position,selectedMarker);

            center = CameraUpdateFactory.newLatLng(selectedMarker.getPosition());
            googleMap.animateCamera(center);

        }catch (Exception e){

        }



    }
    private void changeSelectedMarker(Marker marker) {
        // 선택했던 마커 되돌리기

        if (selectedMarker != null) {
            int tag=hashMap.get(selectedMarker);
            addMarker(selectedMarker, false);


            selectedMarker.remove();


        }

        // 선택한 마커 표시
        if (marker != null && marker!=selectedMarker) {
            int tag=hashMap.get(marker);
           // hashMap.remove(marker);
            hashMap2.remove(tag);
            selectedMarker = addMarker(marker, true);
            hashMap.put(selectedMarker,tag);
            hashMap2.put(tag,selectedMarker);
            marker.remove();

        }


    }


    private void getMarkerItems() {
        hashMap=new HashMap<>();
        hashMap2=new HashMap<>();
        markers=new ArrayList<>();
        items=new ArrayList<>();
        markerRef.addChildEventListener(new ChildEventListener() {
            int i=0;

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                MarkerItem item=dataSnapshot.getValue(MarkerItem.class);

                Marker marker;
                items.add(item);
                Log.e("ITEM TAG",item.getLat()+"");
                marker=addMarker(item, false);
                marker.setTag(i);
                Log.e("MARKER TAG TAG",marker.getTag()+"");
                hashMap.put(marker,i);
                hashMap2.put(i,marker);
                i++;

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    private void setCustomMarkerView() {

        marker_root_view = LayoutInflater.from(getContext()).inflate(R.layout.item_marker, null);
        ig_marker = (ImageView) marker_root_view.findViewById(R.id.ig_marker);
    }

    private Marker addMarker(Marker marker, boolean isSelectedMarker) {
        double lat = marker.getPosition().latitude;
        double lon = marker.getPosition().longitude;
        //int order = ((int) marker.getZIndex());


        MarkerItem temp=new MarkerItem();
        temp.setLat(lat);
        temp.setLon(lon);
        return addMarker(temp, isSelectedMarker);

    }
    private Marker addMarker(MarkerItem item, boolean isSelectedMarker) {


        LatLng position = new LatLng(item.getLat(),item.getLon());

        if (isSelectedMarker) {
            ig_marker.setImageResource(R.drawable.marker_like_select);

        } else {
            ig_marker.setImageResource(R.drawable.marker_like_unselect);

        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(position);
        //markerOptions.zIndex(item.getOrder());
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(marker_root_view)));


        return googleMap.addMarker(markerOptions);

    }
    private Bitmap createDrawableFromView(View view) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
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
