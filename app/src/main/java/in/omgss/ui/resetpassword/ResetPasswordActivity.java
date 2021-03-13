package in.omgss.ui.resetpassword;

import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import in.omgss.R;
import in.omgss.base.BaseActivity;
import in.omgss.dialogs.VerificationSuccessfulDialog;
import in.omgss.interfaces.VerificationDialogInterface;

public class ResetPasswordActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_reset_password;
    }


    @OnClick({R.id.iv_back, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.btn_submit:
                VerificationSuccessfulDialog verificationSuccessfulDialog = new VerificationSuccessfulDialog(this, new VerificationDialogInterface() {
                    @Override
                    public void OnDoneClick() {
                        finish();
                    }
                }, "Password Changed Successfully");
                verificationSuccessfulDialog.show();
                break;
        }
    }


}