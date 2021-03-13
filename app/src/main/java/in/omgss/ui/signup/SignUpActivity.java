package in.omgss.ui.signup;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import butterknife.BindView;
import butterknife.OnClick;
import in.omgss.R;
import in.omgss.base.BaseActivity;
import in.omgss.constants.AppConstants;
import in.omgss.data.api.ApiConstants;
import in.omgss.pojo.responses.SignUpResponse;
import okhttp3.RequestBody;

public class SignUpActivity extends BaseActivity {
    @BindView(R.id.et_first_name)
    EditText etFirstName;
    @BindView(R.id.et_last_name)
    EditText etLastName;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_location)
    EditText etLocation;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_email_address)
    EditText etemail;

    private SignupViewModel mViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(SignupViewModel.class);

        mViewModel.setGenericObservers(getErrorResponseObserver(), getFailureResponseObserver());

        mViewModel.getRegistrationLiveData().observe(this, new Observer<SignUpResponse>() {
            @Override
            public void onChanged(SignUpResponse responseBody) {
                hideProgressDialog();
                if (responseBody.getStatus().equalsIgnoreCase(ApiConstants.STATUS_SUCCESS)) {
                    showToast(responseBody.getMessage());
                    finish();

                } else {
                    showToast(responseBody.getMessage());
                }
            }
        });

        mViewModel.getValidationliveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                hideProgressDialog();
                switch (integer) {
                    case AppConstants.EMPTY_FIRSTNAME:
                        showToast("Please enter first name");
                        break;

                    case AppConstants.EMPTY_LAStNAME:
                        showToast("Please enter last name");
                        break;

                    case AppConstants.EMPTY_MOBILE_NUMBER:
                        showToast("Please enter phone number");
                        break;

                    case AppConstants.INVALID_MOBILE_NUMBER:
                        showToast("Please enter valid phone number");

                        break;

                    case AppConstants.EMPTY_EMAIL:
                        showToast("Please enter email address");

                        break;

                    case AppConstants.EMPTY_PASSWORD:
                        showToast("Please enter password");
                        break;
                }
            }
        });
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_sign_up;
    }

    @OnClick({R.id.iv_back, R.id.btn_verify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_verify:
                if (isNetworkAvailable()) {
                    showProgressDialog();
                    RequestBody body = getMultipartRequestBuilder()
                            .addFormDataPart("eMail", etemail.getText().toString())
                            .addFormDataPart("pass", etPassword.getText().toString())
                            .addFormDataPart("fname", etFirstName.getText().toString())
                            .addFormDataPart("lname", etLastName.getText().toString())
                            .addFormDataPart("Phone", etPhoneNumber.getText().toString())
                            .addFormDataPart("Address", etAddress.getText().toString())
                            .addFormDataPart("Location", etLocation.getText().toString())
                            .build();
                    mViewModel.register(body, etFirstName.getText().toString(), etLastName.getText().toString(), etPhoneNumber.getText().toString(), etemail.getText().toString(), etPassword.getText().toString());
                } else
                    showNoNetworkError();
                break;
        }
    }
}