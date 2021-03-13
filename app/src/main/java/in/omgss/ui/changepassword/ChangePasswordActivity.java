package in.omgss.ui.changepassword;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import butterknife.BindView;
import butterknife.OnClick;
import in.omgss.R;
import in.omgss.base.BaseActivity;
import in.omgss.data.api.ApiConstants;
import in.omgss.pojo.responses.CommonApiResponse;
import okhttp3.MultipartBody;

public class ChangePasswordActivity extends BaseActivity {

    @BindView(R.id.et_old_password)
    EditText etOldPassword;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;

    private ChangePasswordViewModel mViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(ChangePasswordViewModel.class);
        mViewModel.setGenericObservers(getErrorResponseObserver(), getFailureResponseObserver());

        setObservers();

    }


    @Override
    protected int getResourceId() {
        return R.layout.activity_change_password;
    }


    private void setObservers() {
        mViewModel.getChangePasswordLiveData().observe(this, new Observer<CommonApiResponse>() {
            @Override
            public void onChanged(CommonApiResponse responseBody) {
                hideProgressDialog();
                if (responseBody != null) {
                    if (responseBody.getStatus().equalsIgnoreCase(ApiConstants.STATUS_SUCCESS)) {
                        showToast(responseBody.getMessage());
                        onBackPressed();

                    } else {
                        showToast(responseBody.getMessage());
                    }
                }
            }
        });
    }


    @OnClick({R.id.iv_back, R.id.btn_sumit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;

            case R.id.btn_sumit:
                onChangePasswordClicked();
                break;
        }
    }


    private void onChangePasswordClicked() {
        if (isNetworkAvailable()) {
            if (isValidated()) {
                showProgressDialog();

                MultipartBody body = getMultipartRequestBuilder()
                        .addFormDataPart("userid", mViewModel.getUserId())
                        .addFormDataPart("opass", etOldPassword.getText().toString())
                        .addFormDataPart("npass", etNewPassword.getText().toString())
                        .addFormDataPart("cpass", etConfirmPassword.getText().toString())
                        .build();

                mViewModel.changePassword(body);

            }
        } else {
            showNoNetworkError();
        }
    }


    private boolean isValidated() {
        if (etOldPassword.getText().toString().isEmpty()) {
            showToast("Please enter old password");
            return false;

        } else if (etNewPassword.getText().toString().isEmpty()) {
            showToast("Please enter new password");
            return false;

        } else if (etConfirmPassword.getText().toString().isEmpty()) {
            showToast("Please confirm password");
            return false;

        } else if (!etNewPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
            showToast("Passwords doesn't match");
            return false;

        }
        return true;
    }

}