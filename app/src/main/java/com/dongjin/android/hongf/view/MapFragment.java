package com.dongjin.android.hongf.view;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.model.MarkerItem;
import com.dongjin.android.hongf.model.Store;
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

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback,Map_View,View.OnClickListener {

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
    private ImageView filter_map;
    private ImageView map_ig_filter;
    private TextView map_tv_filter;
    private ProgressBar pb;
    private String tag;
    HashMap<Integer,Marker> hashMap;
    HashMap<LatLng,Integer> hashmap2;
    DatabaseReference storeRef;
    LayoutInflater inflater;


    DatabaseReference markerRef;
    DatabaseReference markerRef2;


    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!tag.equals("null")) {
            getMarkerItems2(tag);
        } else if (tag.equals("null")) {

            getMarkerItems();
        }
        resetPager(inflater, tag);
        setFilterInfo(tag);

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
        this.inflater=inflater;
        pb=(ProgressBar)v.findViewById(R.id.pbar);

        tag="null";
        markerRef = FirebaseDatabase.getInstance().getReference().child("storeMarker");
        markerRef2 = FirebaseDatabase.getInstance().getReference().child("storeMarker2");
        markerRef.keepSynced(true);
        markerRef2.keepSynced(true);
        map_ig_filter=(ImageView)v.findViewById(R.id.map_ig_filter);
        map_tv_filter=(TextView)v.findViewById(R.id.map_tv_filter);

        resetPager(inflater,"null");


        filter_map=(ImageView)v.findViewById(R.id.map_btn_filter);
        filter_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(),Dialog_Map_Filter.class);

                startActivityForResult(intent,700);

            }
        });



        return v;
    }
    public void resetPager(LayoutInflater inflater,String foodTag){
        pager= (ViewPager)v.findViewById(R.id.pager);
        adapter= new MapPagerAdapter(inflater,getContext(),foodTag);
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



            }
        });
        pager.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                pb.setVisibility(View.GONE);
            }
        });

    }
    public void setFilterInfo(String tag){
        switch (tag) {
            case "korean":
                //map_ig_filter.setImageResource(R.drawable.foodicon1);
                map_ig_filter.setColorFilter(null);
                map_tv_filter.setText("한식");
                break;
            case "japanease":
                //map_ig_filter.setImageResource(R.drawable.foodicon3);
                map_tv_filter.setText("일식");

                break;
            case "chinease":
                //map_ig_filter.setImageResource(R.drawable.foodicon4);
                map_tv_filter.setText("중식");
                break;
            case "wastern":
                //map_ig_filter.setImageResource(R.drawable.foodicon2);
                map_tv_filter.setText("양식");
                break;
            case "world":
               //map_ig_filter.setImageResource(R.drawable.foodicon5);
                map_tv_filter.setText("세계음식");
                break;
            case "cafe":
                //map_ig_filter.setImageResource(R.drawable.foodicon8);
                map_tv_filter.setText("까페");
                break;
            case "hope":
                //map_ig_filter.setImageResource(R.drawable.foodicon10);
                map_tv_filter.setText("술집");
                break;
//            case "hope":
//                map_ig_filter.setImageResource(R.drawable.foodicon9);
//                map_tv_filter.setText("술집");
//                break;
            case "fastfood":
                //map_ig_filter.setImageResource(R.drawable.foodicon7);
                map_tv_filter.setText("패스트푸드");
                break;
            case "koreansnack":
                //map_ig_filter.setImageResource(R.drawable.foodicon6);
                map_tv_filter.setText("분식");
                break;
            case "null":
                //map_ig_filter.setImageResource(R.drawable.bottomicon4);
                map_tv_filter.setText("All");
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(markers.size()!=0){
            for(int i=0;i<markers.size();i++){
                markers.get(i).remove();
            }

        }

        if (resultCode == RESULT_OK) {
            tag= data.getStringExtra("tagkey");

            Log.e("MAP FILTER TAG",tag);
            if (!tag.equals("null")) {
                getMarkerItems2(tag);
            } else if (tag.equals("null")) {

                getMarkerItems();
            }
            resetPager(inflater, tag);
            setFilterInfo(tag);

        }

    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;


        pb.setVisibility(View.VISIBLE);
        pb.bringToFront();

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
                    //marker.setZIndex(markers.size());
                    Log.e("hashmap marker position",hashmap2.get(marker.getPosition())+"");
                    pager.setCurrentItem(hashmap2.get(marker.getPosition()),true);

                }



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


            markers.add(addMarker(selectedMarker,false));


            selectedMarker.remove();


        }
        try{
            Marker marker=hashMap.get(position);

            selectedMarker=addMarker(marker,true);
            markers.add(selectedMarker);
            //selectedMarker.setZIndex(markers.size());

            center = CameraUpdateFactory.newLatLng(selectedMarker.getPosition());
            googleMap.animateCamera(center);

        }catch (Exception e){

        }



    }
    private void changeSelectedMarker(Marker marker) {


        if (selectedMarker != null) {

            markers.add(addMarker(selectedMarker, false));

            selectedMarker.remove();

        }
        int positionM=0;


        if (marker != null) {

            positionM=hashmap2.get(marker.getPosition());
            selectedMarker = addMarker(marker, true);
            markers.add(selectedMarker);
            //selectedMarker.setZIndex(markers.size());



            pager.setCurrentItem(positionM,true);
            marker.remove();

        }


    }


    private void getMarkerItems() {
        Log.e("getmarkerit-----","dddddddddd");
        hashMap=new HashMap<>();
        hashmap2=new HashMap<>();

        markers=new ArrayList<>();
        items=new ArrayList<>();
        markerRef.addChildEventListener(new ChildEventListener() {
            int i=0;

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                MarkerItem item=dataSnapshot.getValue(MarkerItem.class);

                if (item != null) {
                    Marker marker;
                    items.add(item);
                    Log.e("ITEM TAG", item.getLat() + "");
                    marker = addMarker(item, false);
                    marker.setTag(i);
                    markers.add(marker);


                    hashmap2.put(marker.getPosition(),i);

                    hashMap.put(i, marker);
                    if(i==0){
                        changeSelectedMarker(hashMap.get(0));
                    }
                    i++;
                    pb.setVisibility(View.GONE);
                }

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
    private void getMarkerItems2(final String tag) {

        if(selectedMarker!=null){
            selectedMarker=null;
        }

        hashMap=new HashMap<>();
        markers=new ArrayList<>();
        items=new ArrayList<>();
        markerRef.addChildEventListener(new ChildEventListener() {
            int i=0;

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                MarkerItem item = dataSnapshot.getValue(MarkerItem.class);

                if (dataSnapshot.child("foodTag").getValue(String.class).equals(tag)) {
                    Log.e("GETMARKERS2 TAG", tag);
                    Log.e("GETMARKES TAG222", item.getFoodTag());
                    Marker marker;

                    marker = addMarker(item, false);
                    items.add(item);



                    marker.setTag(i);
                    markers.add(marker);
                    hashmap2.put(marker.getPosition(),i);

                    hashMap.put(i, marker);

                    ++i;
                }



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

    @Override
    public void onClick(View v) {

    }

    public class MapPagerAdapter extends PagerAdapter {

        LayoutInflater inflater;
        ArrayList<Store> stores;
        ArrayList<Uri> uris;
        String url;

        Context context;
        DatabaseReference storeRef;

        public MapPagerAdapter(LayoutInflater inflater, Context context, String foodTag) {
            // TODO Auto-generated constructor stub
            this.context = context;
            this.inflater = inflater;
            stores = new ArrayList<>();
            uris = new ArrayList<>();


            Log.e("mappageradapter tag tag",foodTag);
            if (foodTag.equals("null")) {
                storeRef = FirebaseDatabase.getInstance().getReference().child("Store");
                storeRef.keepSynced(true);
                Log.e(" tStoreRef1 excuted!!!!",foodTag);

            } else if(!foodTag.equals("null")) {
                storeRef = FirebaseDatabase.getInstance().getReference().child("Store2").child(foodTag);
                storeRef.keepSynced(true);
                Log.e(" tStoreRef2 excuted!!!!",foodTag);

            }

            Log.e("mappageradapter ref tag",storeRef.getKey().toString());


            storeRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Store store = dataSnapshot.getValue(Store.class);
                    stores.add(store);

                    notifyDataSetChanged();

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


        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return stores.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            final Store store = stores.get(position);

            View view = null;
            view = inflater.inflate(R.layout.item_map_pager, null);

            final ProgressBar mappb=(ProgressBar)view.findViewById(R.id.map_pb);
            CardView cardView = (CardView) view.findViewById(R.id.cardView);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView4);
            TextView tvStoreName = (TextView) view.findViewById(R.id.tvStoreName);
            TextView tvRating = (TextView) view.findViewById(R.id.tvRating);
            TextView tvAddress = (TextView) view.findViewById(R.id.tvAddress);


            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    loadMain(store);
                }
            });

            if (store.getImageUrl().equals("")) {
                imageView.setImageResource(android.R.drawable.ic_menu_camera);
                mappb.setVisibility(View.GONE);
            } else {
                mappb.bringToFront();
                mappb.setVisibility(View.VISIBLE);
                Glide.with(context).load(store.getImageUrl()).listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        mappb.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        mappb.setVisibility(View.GONE);
                        return false;
                    }
                }).into(imageView);
            }

            tvStoreName.setText(store.getStorename());
            tvRating.setText("평점 :"+store.getAveragerating()+" 즐겨찾기 :"+store.getBookmarkcount()+" 리뷰 :"+store.getReviewcount());
            tvAddress.setText(store.getStoreaddress());


            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View v, Object obj) {
            // TODO Auto-generated method stub
            return v == obj;
        }

        private final int SPLASH_DISPLAY_LENGTH = 1100;

        private void loadMain(final Store store) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getContext(), StoreDetailActivity.class);
                    intent.putExtra("Store", store);
                    getContext().startActivity(intent);
                    getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                }
            }, SPLASH_DISPLAY_LENGTH);


        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStop() {
        hashMap.clear();
        hashmap2.clear();

        markers.clear();
        items.clear();
        super.onStop();
    }
}
