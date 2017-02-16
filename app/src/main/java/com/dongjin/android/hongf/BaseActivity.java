package com.dongjin.android.hongf;

import android.app.Activity;
import android.content.Intent;

import com.dongjin.android.hongf.view.LoginActivity;

/**
 * @author leoshin, created at 15. 7. 20..
 */
public class BaseActivity extends Activity {

    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}
