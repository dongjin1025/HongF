package com.dongjin.android.hongf.kakaotest;

import android.app.Activity;
import android.content.Intent;

/**
 * @author leoshin, created at 15. 7. 20..
 */
public class BaseActivity extends Activity {

    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, SampleLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}
