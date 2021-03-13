package in.omgss.ui.profile;

import android.content.Intent;
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
import in.omgss.dialogs.ConfirmationDialog;
import in.omgss.interfaces.DialogCallback;
import in.omgss.pojo.responses.CommonApiResponse;
import in.omgss.pojo.responses.LoginResponse;
import in.omgss.ui.addedaddress.AddedAddressActivity;
import in.omgss.ui.changepassword.ChangePasswordActivity;
import okhttp3.MultipartBody;

public class ProfileActivity extends BaseActivity {

    @BindView(R.id.et_first_name)
    EditText etFirstName;
    @BindView(R.id.et_last_name)
    EditText etLastName;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_email_address)
    EditText etEmailAddress;

    private ProfileViewModel mViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        mViewModel.setGenericObservers(getErrorResponseObserver(), getFailureResponseObserver());

        setObservers();

        getProfileData();

    }


    @Override
    protected int getResourceId() {
        return R.layout.activity_profile;
    }


    private void setObservers() {
        mViewModel.getProfileLiveData().observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse responseBody) {
                hideProgressDialog();
                if (responseBody != null) {
                    if (responseBody.getStatus().equalsIgnoreCase(ApiConstants.STATUS_SUCCESS)) {

                        etFirstName.setText(responseBody.getResult().getName().split(" ")[0]);
                        etLastName.setText(responseBody.getResult().getName().split(" ")[1]);

                        etPhoneNumber.setText(responseBody.getResult().getPhone());
                        etEmailAddress.setText(responseBody.getResult().getEMail());

                    } else {
                        showToast(responseBody.getMessage());
                    }
                }
            }
        });

        mViewModel.getUpdateProfileLiveData().observe(this, new Observer<CommonApiResponse>() {
            @Override
            public void onChanged(CommonApiResponse responseBody) {
                hideProgressDialog();
                if (responseBody != null) {
                    if (responseBody.getStatus().equalsIgnoreCase(ApiConstants.STATUS_SUCCESS)) {
                        showToast("Profile updated successfully!");

                    } else {
                        showToast(responseBody.getMessage());
                    }
                }
            }
        });

    }


    private void getProfileData() {
        if (isNetworkAvailable()) {
            showProgressDialog();

            MultipartBody requestBody = getMultipartRequestBuilder()
                    .addFormDataPart("loggedinuserid", mViewModel.getUserId())
                    .build();

            mViewModel.getProfileData(requestBody);

        } else {
            showNoNetworkError();
        }
    }


    private void updateProfile() {
        if (isNetworkAvailable()) {

            if (etFirstName.getText().toString().trim().isEmpty()) {
                showToast("Please enter first name");

            } else if (etLastName.getText().toString().trim().isEmpty()) {
                showToast("Please enter last name");

            } else if (etPhoneNumber.getText().toString().trim().isEmpty()) {
                showToast("Please enter phone number");

            } else if (etEmailAddress.getText().toString().trim().isEmpty()) {
                showToast("Please enter email");

            } else {

                showProgressDialog();

                MultipartBody requestBody = getMultipartRequestBuilder()
                        .addFormDataPart("userid", mViewModel.getUserId())
                        .addFormDataPart("Name", etFirstName.getText().toString().trim() + " " + etLastName.getText().toString().trim())
                        .addFormDataPart("Phone", etPhoneNumber.getText().toString().trim())
                        .addFormDataPart("eMail", etEmailAddress.getText().toString().trim())
                        .addFormDataPart("Address", " ")
                        .addFormDataPart("Location", " ")
                        .build();

                mViewModel.updateProfile(requestBody);
            }
        } else {
            showNoNetworkError();
        }
    }


    @OnClick({R.id.iv_back, R.id.btn_verify, R.id.cv_change_password, R.id.cv_addresses, R.id.cv_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.cv_change_password:
                startActivity(new Intent(this, ChangePasswordActivity.class));
                break;

            case R.id.iv_back:
                onBackPressed();
                break;

            case R.id.btn_verify:
                updateProfile();
                break;

            case R.id.cv_logout:
                ConfirmationDialog customAlerDialog = new ConfirmationDialog(this, "Logout", "Are you sure you want to logout?", "Yes", "No",
                        new DialogCallback() {
                            @Override
                            public void onSubmit(View view, Object result) {
                                logout();
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                customAlerDialog.show();
                break;

            case R.id.cv_addresses:
                startActivity(new Intent(this, AddedAddressActivity.class));
                break;
        }
    }

}