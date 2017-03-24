package com.dongjin.android.hongf.view;


import android.graphics.ColorFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.adapter.MyPageAdapter;
import com.dongjin.android.hongf.model.KaKaoInfo;
import com.dongjin.android.hongf.model.Store;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPageFragment extends Fragment {

    private MyPageAdapter adapter;
    private ImageView profileImage;
    private TextView profileName;
    ArrayList<Store> stores_finder;
    ArrayList<Store> stores_bookmark;
    private RecyclerView recyclerView;
    DatabaseReference myListRef;
    KaKaoInfo kaKaoInfo;
    private ImageView mypage_ig_bookmark;
    private ImageView mypage_ig_ifound;
    private TextView mypage_tv_bookmark;
    private TextView mypage_tv_ifound;

    public MyPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        myListRef= FirebaseDatabase.getInstance().getReference().child("Store");
        myListRef.keepSynced(true);

        myListRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Store store = dataSnapshot.getValue(Store.class);

                if (dataSnapshot.child("finderId").getValue().equals(kaKaoInfo.read_id_kakao())) {
                    Log.e("MyPAGE data test", store.getImageUrl().toString());
                    stores_finder.add(store);

                }

                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put(kaKaoInfo.read_id_kakao(), kaKaoInfo.read_name_kakao());

                if(dataSnapshot.hasChild("Bookmark")) {
                    if (dataSnapshot.child("Bookmark").getValue().equals(hashMap)) {
                        stores_bookmark.add(store);


                    }
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);
        kaKaoInfo = KaKaoInfo.getInstance();
        stores_bookmark=new ArrayList<>();
        stores_finder=new ArrayList<>();


        mypage_tv_ifound=(TextView)view.findViewById(R.id.mypage_tv_ifound);
        mypage_tv_bookmark=(TextView)view.findViewById(R.id.mypage_tv_bookmark);
        profileImage = (ImageView) view.findViewById(R.id.mypage_ig_profile);

        profileName = (TextView) view.findViewById(R.id.mypage_tv_name);
        recyclerView = (RecyclerView) view.findViewById(R.id.mypage_recy_myinfo);
        mypage_ig_bookmark=(ImageView)view.findViewById(R.id.mypage_ig_bookmark);
        final ColorFilter colorFilter1 =mypage_ig_bookmark.getColorFilter();

        mypage_ig_ifound=(ImageView)view.findViewById(R.id.mypage_ig_ifound);
        final ColorFilter colorFilter2 =mypage_ig_ifound.getColorFilter();
        mypage_ig_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRotate(mypage_ig_bookmark);
                mypage_tv_bookmark.setVisibility(View.VISIBLE);
                mypage_tv_ifound.setVisibility(View.INVISIBLE);
                mypage_ig_bookmark.setImageResource(R.drawable.btn_common_card_like_pressed);
                mypage_ig_bookmark.setColorFilter(ContextCompat.getColor(getContext(), R.color.blue));

                mypage_ig_ifound.setColorFilter(colorFilter2);
                adapter=new MyPageAdapter(stores_bookmark,getContext());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            }
        });
        mypage_ig_ifound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mypage_tv_bookmark.setVisibility(View.INVISIBLE);
                mypage_tv_ifound.setVisibility(View.VISIBLE);
                mypage_ig_ifound.setColorFilter(ContextCompat.getColor(getContext(), R.color.red));
                mypage_ig_bookmark.setImageResource(R.drawable.btn_unpress_like);
                mypage_ig_bookmark.setColorFilter(colorFilter1);
                onClickRotate(mypage_ig_ifound);
                adapter=new MyPageAdapter(stores_finder,getContext());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            }
        });


        if(!kaKaoInfo.read_picture_kakao().equals("")){
            Glide.with(getContext()).load(kaKaoInfo.read_picture_kakao()).
                    bitmapTransform(new CropCircleTransformation(getContext())).into(profileImage);

        }else if(kaKaoInfo.read_picture_kakao().equals("")){
            profileImage.setImageResource(R.drawable.ic_account_24dp);
        }
        profileName.setText(kaKaoInfo.read_name_kakao());

        return view;
    }

    public void onClickRotate(View v) {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        v.startAnimation(animation);
    }


}
