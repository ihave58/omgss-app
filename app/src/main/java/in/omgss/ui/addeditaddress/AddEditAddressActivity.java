package in.omgss.ui.addeditaddress;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.omgss.R;
import in.omgss.base.BaseActivity;
import in.omgss.data.api.ApiConstants;
import in.omgss.pojo.responses.CommonApiResponse;
import okhttp3.MultipartBody;

public class AddEditAddressActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rlToolbar)
    RelativeLayout rlToolbar;
    @BindView(R.id.et_address_profile_name)
    EditText etAddressProfileName;
    @BindView(R.id.et_full_name)
    EditText etFullName;
    @BindView(R.id.et_email_address)
    EditText etEmailAddress;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_city)
    EditText etCity;
    @BindView(R.id.et_state)
    EditText etState;
    @BindView(R.id.et_zip_code)
    EditText etZipCode;
    @BindView(R.id.btn_save)
    Button btnSave;

    private AddEditAddressViewModel mViewModel;
    private String addressId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mViewModel = new ViewModelProvider(this).get(AddEditAddressViewModel.class);
        mViewModel.setGenericObservers(getErrorResponseObserver(), getFailureResponseObserver());

        setObservers();

        if (getIntent().getExtras() == null) {
            tvTitle.setText("Add Service Address");
            etEmailAddress.setText(mViewModel.getEmail());

        } else {
            tvTitle.setText("Edit Service Address");
            addressId = getIntent().getStringExtra("addressId");
            etAddressProfileName.setText(getIntent().getStringExtra("addressName"));
            etFullName.setText(getIntent().getStringExtra("fullName"));
            etEmailAddress.setText(getIntent().getStringExtra("email"));
            etAddress.setText(getIntent().getStringExtra("address"));
            etCity.setText(getIntent().getStringExtra("city"));
            etState.setText(getIntent().getStringExtra("state"));
            etZipCode.setText(getIntent().getStringExtra("zip"));
        }

    }


    private void setObservers() {
        mViewModel.getAddAddressLiveData().observe(this, new Observer<CommonApiResponse>() {
            @Override
            public void onChanged(CommonApiResponse response) {
                hideProgressDialog();
                if (response != null) {
                    if (response.getStatus().equalsIgnoreCase(ApiConstants.STATUS_SUCCESS)) {
                        setResult(RESULT_OK);
                        finish();

                    } else {
                        showToast(response.getMessage());
                    }
                }
            }
        });
    }


    @Override
    protected int getResourceId() {
        return R.layout.activity_add_edit_address;
    }


    @OnClick({R.id.iv_toolbar_left, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_left:
                onBackPressed();
                break;

            case R.id.btn_save:
                if (isNetworkAvailable()) {
                    if (isValidated()) {
                        showProgressDialog();

                        String addressName = etAddressProfileName.getText().toString().trim();

                        MultipartBody.Builder builder = getMultipartRequestBuilder()
                                .addFormDataPart("userid", mViewModel.getUserId())
                                .addFormDataPart("addressprofilename", addressName.isEmpty() ? "Other" : addressName)
                                .addFormDataPart("fullname", etFullName.getText().toString().trim())
                                .addFormDataPart("Email", etEmailAddress.getText().toString().trim())
                                .addFormDataPart("Address", etAddress.getText().toString().trim())
                                .addFormDataPart("City", etCity.getText().toString().trim())
                                .addFormDataPart("State", etState.getText().toString().trim())
                                .addFormDataPart("Zip", etZipCode.getText().toString().trim());

                        if (getIntent().getExtras() == null) {
                            mViewModel.addAddress(builder.build());

                        } else {
                            builder.addFormDataPart("addid", addressId);
                            mViewModel.editAddress(builder.build());

                        }

                    }
                } else {
                    showNoNetworkError();
                }
                break;
        }

    }

    private boolean isValidated() {
        if (etFullName.getText().toString().trim().isEmpty()) {
            showToast("Please enter name");
            return false;

        } else if (etEmailAddress.getText().toString().trim().isEmpty()) {
            showToast("Please enter email address");
            return false;

        } else if (etAddress.getText().toString().trim().isEmpty()) {
            showToast("Please enter address");
            return false;

        } else if (etCity.getText().toString().trim().isEmpty()) {
            showToast("Please enter city");
            return false;

        } else if (etState.getText().toString().trim().isEmpty()) {
            showToast("Please enter state");
            return false;

        } else if (etZipCode.getText().toString().trim().isEmpty()) {
            showToast("Please enter zip code");
            return false;
        }
        return true;
    }

}

