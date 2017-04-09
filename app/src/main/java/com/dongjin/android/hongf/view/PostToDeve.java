package com.dongjin.android.hongf.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dongjin.android.hongf.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PostToDeve extends AppCompatActivity {

    private EditText et;
    private Button ok;
    private Button cn;
    private DatabaseReference todeveloper;

    private Handler handler;
    private Runnable runnable;
    private String stringdate;
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_to_deve);
        todeveloper= FirebaseDatabase.getInstance().getReference().child("todevel");
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
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        handler.postDelayed(runnable, 1 * 1000);

        et=(EditText)findViewById(R.id.dev_et);
        et.setText("");

        ok=(Button)findViewById(R.id.dev_ok);
        cn=(Button)findViewById(R.id.dev_cn);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et.getText().toString().equals("")) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(PostToDeve.this);
                    dialog.setTitle("보내기");
                    dialog.setMessage("보내시겠습니까?");
                    dialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            String content=stringdate+"  "+et.getText().toString();
                            todeveloper.push().setValue(content);
                            et.setText("");
                            imm.hideSoftInputFromWindow(et.getWindowToken(), 0);

                        }
                    });
                    dialog.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                        }
                    });

                    dialog.show();

                }else{
                    Toast.makeText(PostToDeve.this,"내용을 입력해주세요",Toast.LENGTH_LONG).show();
                }

            }
        });
        cn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et.getText().toString().equals("")) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(PostToDeve.this);
                    dialog.setTitle("취소");
                    dialog.setMessage("정말 취소 하시겠습니까?");
                    dialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            finish();

                        }
                    });
                    dialog.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                        }
                    });

                    dialog.show();

                }else{
                    finish();
                }


            }
        });

    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (!et.getText().toString().equals("")) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("취소");
            dialog.setMessage("정말 취소 하시겠습니까?");
            dialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    finish();

                }
            });
            dialog.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                }
            });

            dialog.show();

        }else{
            finish();
        }
    }

}
