package com.dongjin.android.hongf.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dongjin.android.hongf.R;

public class CheckHongik extends AppCompatActivity {

    private EditText et;
    private Button  btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_hongik);




        et=(EditText)findViewById(R.id.check_et_number);
        btn=(Button)findViewById(R.id.check_btn_ok);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sNumber=et.getText().toString();
                checkNum(sNumber);

            }
        });
    }

    public void checkNum(String n){

        char[] charArray=n.toCharArray();
        if(charArray.length!=7){
            Toast.makeText(this,"잘못된 학번입니다",Toast.LENGTH_LONG).show();
        }else{
            if(charArray[2]=='7'&&charArray[3]=='3'){
                Intent intent=new Intent(CheckHongik.this,LoginActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(this,"잘못된 학번입니다",Toast.LENGTH_LONG).show();
            }
        }

        Log.e("STUDENT NUM",charArray[2]+"");
    }
}
