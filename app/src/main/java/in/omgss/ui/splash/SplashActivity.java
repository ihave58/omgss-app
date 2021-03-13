package in.omgss.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import in.omgss.R;
import in.omgss.base.BaseActivity;
import in.omgss.constants.PreferenceConstants;
import in.omgss.data.preferences.PreferenceManager;
import in.omgss.ui.login.LoginActivity;
import in.omgss.ui.main.MainActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goToNextActivity();
    }

    /**
     * method to go to next activity
     */
    private void goToNextActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isFinishing() && !isDestroyed()) {
                    if (PreferenceManager.getInstance().getBoolean(PreferenceConstants.IS_LOGGED_IN)) {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }

                    finish();
                }
            }
        }, 3000);
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_splash;
    }
}
