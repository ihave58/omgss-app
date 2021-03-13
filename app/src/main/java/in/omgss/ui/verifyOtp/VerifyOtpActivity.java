package in.omgss.ui.verifyOtp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.OnClick;
import in.omgss.R;
import in.omgss.base.BaseActivity;
import in.omgss.ui.resetpassword.ResetPasswordActivity;

public class VerifyOtpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_verify_otp;
    }


    @OnClick({R.id.iv_back, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.btn_submit:
                startActivity(new Intent(this, ResetPasswordActivity.class));
                finish();
                break;
        }
    }

}