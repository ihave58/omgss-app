package in.omgss.ui.complain;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import butterknife.BindView;
import butterknife.OnClick;
import in.omgss.R;
import in.omgss.base.BaseActivity;
import in.omgss.data.api.ApiConstants;
import in.omgss.pojo.responses.CommonApiResponse;
import okhttp3.RequestBody;

public class ComplainActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_toolbar_left)
    ImageView ivToolbarLeft;
    @BindView(R.id.iv_toolbar_right)
    ImageView ivToolbarRight;
    @BindView(R.id.rlToolbar)
    RelativeLayout rlToolbar;
    @BindView(R.id.et_fullname)
    EditText etFullname;
    @BindView(R.id.ete_email)
    EditText eteEmail;
    @BindView(R.id.tv_country_code)
    TextView tvCountryCode;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.ll_phone_number)
    LinearLayout llPhoneNumber;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.etcomments)
    EditText etcomments;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.et_complain_details)
    EditText etComplainDetails;
    private ComplainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("Complain");
        mViewModel = new ViewModelProvider(this).get(ComplainViewModel.class);

        mViewModel.setGenericObservers(getErrorResponseObserver(), getFailureResponseObserver());
        mViewModel.getmResendOtpLiveData().observe(this, new Observer<CommonApiResponse>() {
            @Override
            public void onChanged(CommonApiResponse responseBody) {
                hideProgressDialog();
                if (responseBody.getStatus().equalsIgnoreCase(ApiConstants.STATUS_SUCCESS)) {
                    showToast("Your request has been submitted");
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
            }
        });
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_complain;
    }


    @OnClick({R.id.iv_toolbar_left, R.id.btn_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_left:
                finish();
                break;

            case R.id.btn_update:
                if (isNetworkAvailable()) {
                    showProgressDialog();
                    RequestBody body = getMultipartRequestBuilder()
                            .addFormDataPart("fullname", etFullname.getText().toString().trim())
                            .addFormDataPart("email", eteEmail.getText().toString().trim())
                            .addFormDataPart("phone", etPhoneNumber.getText().toString().trim())
                            .addFormDataPart("address", etAddress.getText().toString().trim())
                            .addFormDataPart("complaindetails", etComplainDetails.getText().toString().trim())
                            .addFormDataPart("message", etcomments.getText().toString().trim())
                            .build();
                    mViewModel.login(body);
                } else {
                    showNoNetworkError();
                }
                break;
        }
    }
}