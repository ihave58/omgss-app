package in.omgss.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.omgss.R;
import in.omgss.base.BaseActivity;
import in.omgss.constants.AppConstants;
import in.omgss.data.api.ApiConstants;
import in.omgss.pojo.responses.LoginResponse;
import in.omgss.ui.main.MainActivity;
import in.omgss.ui.signup.SignUpActivity;
import in.omgss.ui.updatemobilenumber.UpdateMobileNumberActivity;
import in.omgss.ui.webview.WebViewActivity;
import in.omgss.utils.AppUtils;
import in.omgss.utils.IClickableSpanListener;
import okhttp3.RequestBody;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.tv_terms_and_conditions)
    TextView tvTermsAndConditions;
    @BindView(R.id.et_email_address)
    EditText etEmailAddress;
    @BindView(R.id.et_password)
    EditText etPassword;

    private LoginViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        initViewModels();
        setObservers();
        setListener();
    }


    private void initViewModels() {
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        mViewModel.setGenericObservers(getErrorResponseObserver(), getFailureResponseObserver());

    }

    private void setObservers() {
        mViewModel.getLoginLiveData().observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse responseBody) {
                hideProgressDialog();
                if (responseBody.getStatus().equalsIgnoreCase(ApiConstants.STATUS_SUCCESS)) {

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

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


    private void setListener() {
        SpannableString spannableString = new SpannableString(getString(R.string.by_signing_in_you_agreee_to_our_terms_and_conditions));
        AppUtils.setClickableSpan(spannableString, getString(R.string.by_signing_in_you_agreee_to_our_terms_and_conditions), "Terms and Conditions", false, tvTermsAndConditions, new IClickableSpanListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(LoginActivity.this, WebViewActivity.class);
                intent.putExtra("url", AppConstants.TERMS_AND_CONDITIONS);
                intent.putExtra("title", "Terms & Conditions");
                startActivity(intent);
            }
        });
        tvTermsAndConditions.setText(spannableString);

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    showProgressDialog();
                    RequestBody body = getMultipartRequestBuilder()
                            .addFormDataPart(ApiConstants.EMAIL, etEmailAddress.getText().toString().trim())
                            .addFormDataPart(ApiConstants.PASSWORD, etPassword.getText().toString().trim())
                            .build();
                    mViewModel.login(body, etEmailAddress.getText().toString(), etPassword.getText().toString());
                } else
                    showNoNetworkError();

            }
        });

        findViewById(R.id.tv_forgot_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, UpdateMobileNumberActivity.class);
                startActivity(intent);

            }
        });

        findViewById(R.id.btn_sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);

            }
        });
    }


    @Override
    protected int getResourceId() {
        return R.layout.activity_login;
    }


}
