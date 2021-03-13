package in.omgss.ui.main.mydevices;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import in.omgss.R;
import in.omgss.base.BaseActivity;
import in.omgss.data.api.ApiConstants;
import in.omgss.pojo.responses.CommonApiResponse;
import in.omgss.pojo.responses.mydevices.Response;
import okhttp3.MultipartBody;

public class DeviceComplaintActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.et_comment)
    EditText etComment;

    private MyDevicesViewModel mViewModel;

    private ArrayList<Response> devicesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvTitle.setText("Complain");

        mViewModel = new ViewModelProvider(this).get(MyDevicesViewModel.class);
        mViewModel.setGenericObservers(getErrorResponseObserver(), getFailureResponseObserver());

        devicesList = getIntent().getParcelableArrayListExtra("data");

        if (devicesList != null) {
            ArrayAdapter<Response> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, devicesList);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);
        }

        setObservers();

    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_complain2;
    }


    private void setObservers() {
        mViewModel.getDeviceComplaintLiveData().observe(this, new Observer<CommonApiResponse>() {
            @Override
            public void onChanged(CommonApiResponse responseBody) {
                hideProgressDialog();
                if (responseBody != null) {
                    if (responseBody.getStatus().equalsIgnoreCase(ApiConstants.STATUS_SUCCESS)) {
                        showToast("Your complaint has been submitted");
                        finish();

                    } else {
                        showToast(responseBody.getMessage());
                    }
                }
            }
        });
    }


    @OnClick({R.id.iv_toolbar_left, R.id.btn_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_left:
                onBackPressed();
                break;

            case R.id.btn_update:
                if (etComment.getText().toString().trim().isEmpty()) {
                    showToast("Please enter your comments!");

                } else if (devicesList != null && devicesList.size() > 0) {
                    if (isNetworkAvailable()) {
                        showProgressDialog();

                        MultipartBody body = getMultipartRequestBuilder()
                                .addFormDataPart("userid", getUserId())
                                .addFormDataPart("deviceid", devicesList.get(spinner.getSelectedItemPosition()).getId())
                                .addFormDataPart("complaint", etComment.getText().toString().trim())
                                .build();

                        mViewModel.createDeviceComplaint(body);

                    } else {
                        showNoNetworkError();
                    }
                }
                break;
        }
    }

}