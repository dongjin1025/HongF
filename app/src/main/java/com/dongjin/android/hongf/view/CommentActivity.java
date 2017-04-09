package com.dongjin.android.hongf.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dongjin.android.hongf.R;
import com.dongjin.android.hongf.adapter.CommentAdapter;
import com.dongjin.android.hongf.model.InterestedUser;
import com.dongjin.android.hongf.model.KaKaoInfo;
import com.dongjin.android.hongf.model.Review;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class CommentActivity extends AppCompatActivity {

    private String key;
    private DatabaseReference commentRef;
    private DatabaseReference storyRef;
    private DatabaseReference storyRef2;

    private CommentAdapter adapter;
    private RecyclerView recyclerView;
    private Button btnSend;
    private EditText etContent;
    private KaKaoInfo kaKaoInfo;


    private String storeId;
    private String stringdate;
    private Handler handler;
    private Runnable runnable;
    private View view;
    private ImageButton btnBack;
    private TextView tvCo;
    private int commentCount;
    private InputMethodManager imm;


    @Override
    protected void onStart() {
        super.onStart();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        handler=new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    Date date = new Date();
                    Date newDate = new Date(date.getTime());
                    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
                    stringdate= dt.format(newDate);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1 * 1000);


        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        key=bundle.getString("key");
        storeId=bundle.getString("id");
        commentRef= FirebaseDatabase.getInstance().getReference().child("comments").child(key);
        storyRef=FirebaseDatabase.getInstance().getReference().child("Story").child(key);
        storyRef2=FirebaseDatabase.getInstance().getReference().child("story2").child(storeId).child(key);
        storyRef.keepSynced(true);
        storyRef2.keepSynced(true);
        commentRef.keepSynced(true);
        view=findViewById(R.id.tb_comment);
//        tvCo=(TextView)view.findViewById(R.id.list_tv_orderfilter);
//        tvCo.setText("댓글");
        btnBack=(ImageButton)view.findViewById(R.id.ib_back_toolbar);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        storyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Review review=dataSnapshot.getValue(Review.class);
                commentCount=review.getCommentCount();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adapter=new CommentAdapter(this,key,storeId);

        kaKaoInfo=KaKaoInfo.getInstance();

        recyclerView=(RecyclerView)findViewById(R.id.comment_recy);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);





        btnSend=(Button)findViewById(R.id.comment_ig_sendbtn);
        etContent=(EditText)findViewById(R.id.comment_et_content);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=etContent.getText().toString();
                InterestedUser user=new InterestedUser();

                user.setComment(content);
                user.setUserId(kaKaoInfo.read_id_kakao());
                user.setUsername(kaKaoInfo.read_name_kakao());
                user.setProfilepath(kaKaoInfo.read_picture_kakao());
                user.setDate(stringdate);
                commentRef.push().setValue(user);

                commentCount +=1;
                HashMap<String,Object> hashMap= new HashMap<>();
                hashMap.put("commentCount",commentCount);
                storyRef.updateChildren(hashMap);
                storyRef2.updateChildren(hashMap);



                etContent.setText("");
                imm.hideSoftInputFromWindow(etContent.getWindowToken(), 0);


            }
        });





    }
}
