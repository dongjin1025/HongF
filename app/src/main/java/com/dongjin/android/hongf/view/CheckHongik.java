package com.dongjin.android.hongf.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dongjin.android.hongf.R;

public class CheckHongik extends AppCompatActivity {

    private EditText et;
    private Button  btn;
    private RelativeLayout container;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getSharedPreferences("stNum", MODE_PRIVATE);
        setContentView(R.layout.activity_check_hongik);
        String preStN=read_id_stN().toString();
        if(!preStN.equals("")){
            Intent intent=new Intent(CheckHongik.this,LoginActivity.class);
            startActivity(intent);
        }










        container=(RelativeLayout)findViewById(R.id.activity_check_hongik);
        //container.getBackground().setAlpha(70);
        et=(EditText)findViewById(R.id.check_et_number);
        et.setText("");
        btn=(Button)findViewById(R.id.check_btn_ok);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sNumber=et.getText().toString();
                if(et.equals("")){
                    Toast.makeText(CheckHongik.this,"잘못된 학번입니다",Toast.LENGTH_LONG).show();
                }
                checkNum(sNumber);

            }
        });
    }
    public String read_id_stN() {
        String text = pref.getString("stNum", "");
        return text;
    }
    public void write_id_stN(String obj){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("stNum", obj);
        editor.commit();
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("종료");
        dialog.setMessage("정말 앱을 종료 하시겠습니까?");
        dialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAndRemoveTask();

            }
        });
        dialog.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });

        dialog.show();
    }

    public void checkNum(String n){

        char[] charArray=n.toCharArray();
        if(charArray.length!=7){
            Toast.makeText(this,"잘못된 학번입니다",Toast.LENGTH_LONG).show();
        }else{
            if(charArray[2]=='7'&&charArray[3]=='3'){
                Intent intent=new Intent(CheckHongik.this,LoginActivity.class);
                write_id_stN(n);
                startActivity(intent);
            }else{
                Toast.makeText(this,"잘못된 학번입니다",Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
