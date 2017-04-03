package com.dongjin.android.hongf.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.dongjin.android.hongf.R;

public class SplashActivity extends AppCompatActivity {

    private RelativeLayout conta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        conta=(RelativeLayout)findViewById(R.id.activity_splash);
        loadMain();
    }
    private final int SPLASH_DISPLAY_LENGTH = 1500;

    private void loadMain(){

        // SPLASH_DISPLAY_LENGTH 뒤에 메인 액티비티를 실행시키고 종료한다.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 메인 액티비티를 실행하고 로딩화면을 죽인다.
                Intent intent = new Intent(SplashActivity.this, CheckHongik.class);
                SplashActivity.this.startActivity(intent);
                //애니메이션 적용
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
