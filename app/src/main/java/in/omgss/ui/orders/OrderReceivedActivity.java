package in.omgss.ui.orders;

import android.os.Bundle;
import android.os.Handler;

import in.omgss.R;
import in.omgss.base.BaseActivity;

public class OrderReceivedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_order_received;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

}