package in.omgss.ui.updatemobilenumber;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.OnClick;
import in.omgss.R;
import in.omgss.base.BaseActivity;
import in.omgss.ui.verifyOtp.VerifyOtpActivity;

public class UpdateMobileNumberActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_update_mobile_number;
    }

    @OnClick({R.id.iv_back, R.id.btn_verify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.btn_verify:
                startActivity(new Intent(this, VerifyOtpActivity.class));
                finish();
                break;
        }
    }

}