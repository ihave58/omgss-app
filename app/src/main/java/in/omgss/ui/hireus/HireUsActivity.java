package in.omgss.ui.hireus;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.omgss.R;
import in.omgss.base.BaseActivity;
import in.omgss.data.api.ApiConstants;
import in.omgss.pojo.responses.CommonApiResponse;
import in.omgss.pojo.responses.categories.CategoriesResponse;
import in.omgss.pojo.responses.categories.Response;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class HireUsActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.spinner_service)
    Spinner spinnerService;
    @BindView(R.id.et_fullname)
    EditText etFullname;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.spinner_time)
    Spinner spinnerTime;
    @BindView(R.id.et_why)
    EditText etWhy;
    @BindView(R.id.etcomments)
    EditText etcomments;

    private HireUsViewModel mViewModel;

    private DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        tvTitle.setText("Hire Us");

        mViewModel = new ViewModelProvider(this).get(HireUsViewModel.class);
        mViewModel.setGenericObservers(getErrorResponseObserver(), getFailureResponseObserver());

        setObservers();

        setTimeList();

        getCategories();
    }


    @Override
    protected int getResourceId() {
        return R.layout.activity_hire_us;
    }


    private void setTimeList() {
        ArrayList<String> timeList = new ArrayList<>();
        timeList.add("09:00 AM");
        timeList.add("10:00 AM");
        timeList.add("11:00 AM");
        timeList.add("12:00 PM");
        timeList.add("01:00 PM");
        timeList.add("03:00 PM");
        timeList.add("04:00 PM");
        timeList.add("05:00 PM");
        timeList.add("06:00 PM");
        timeList.add("07:00 PM");
        timeList.add("08:00 PM");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, timeList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(arrayAdapter);
    }


    private void setObservers() {
        mViewModel.getCategoriesLiveData().observe(this, new Observer<CategoriesResponse>() {
            @Override
            public void onChanged(CategoriesResponse responseBody) {
                hideProgressDialog();
                if (responseBody != null) {
                    if (responseBody.getStatus().equalsIgnoreCase(ApiConstants.STATUS_SUCCESS)) {
                        if (responseBody.getResponse() != null) {

                            ArrayAdapter<Response> arrayAdapter = new ArrayAdapter<>(HireUsActivity.this, android.R.layout.simple_spinner_item, responseBody.getResponse());
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerService.setAdapter(arrayAdapter);
                        }

                    } else {
                        showToast(responseBody.getMessage());
                    }
                }
            }
        });

        mViewModel.getHireUsLiveData().observe(this, new Observer<CommonApiResponse>() {
            @Override
            public void onChanged(CommonApiResponse responseBody) {
                hideProgressDialog();
                if (responseBody != null) {
                    if (responseBody.getStatus().equalsIgnoreCase(ApiConstants.STATUS_SUCCESS)) {
                        showToast("Your request has been submitted");
                        finish();

                    } else {
                        showToast(responseBody.getMessage());
                    }
                }
            }
        });

    }


    private void getCategories() {
        if (isNetworkAvailable()) {
            showProgressDialog();

            MultipartBody body = getMultipartRequestBuilder()
                    .addFormDataPart("userid", getUserId())
                    .build();

            mViewModel.getCategories(body);

        } else {
            showNoNetworkError();
        }
    }


    @OnClick({R.id.iv_toolbar_left, R.id.tv_date, R.id.btn_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_left:
                onBackPressed();
                break;

            case R.id.tv_date:
                if (datePickerDialog == null) {
                    Calendar calender = Calendar.getInstance();
                    datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            StringBuilder date = new StringBuilder();

                            if (dayOfMonth < 10) {
                                date.append("0");
                            }
                            date.append(dayOfMonth);
                            date.append("/");

                            if (month + 1 < 9) {
                                date.append("0");
                            }
                            date.append(month + 1);
                            date.append("/");

                            date.append(year);

                            tvDate.setText(date.toString());

                        }
                    }, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH));

                    datePickerDialog.getDatePicker().setMinDate(calender.getTimeInMillis());
                }

                if (!datePickerDialog.isShowing())
                    datePickerDialog.show();

                break;

            case R.id.btn_update:
                if (isNetworkAvailable()) {
                    if (isValidated()) {
                        showProgressDialog();

                        RequestBody body = getMultipartRequestBuilder()
                                .addFormDataPart("service", spinnerService.getSelectedItem().toString())
                                .addFormDataPart("name", etFullname.getText().toString().trim())
                                .addFormDataPart("phone", etPhoneNumber.getText().toString().trim())
                                .addFormDataPart("date", tvDate.getText().toString().trim())
                                .addFormDataPart("time", spinnerTime.getSelectedItem().toString())
                                .addFormDataPart("reason", etWhy.getText().toString().trim())
                                .addFormDataPart("message", etcomments.getText().toString().trim())
                                .build();

                        mViewModel.hireUs(body);
                    }
                } else {
                    showNoNetworkError();
                }
                break;
        }
    }

    private boolean isValidated() {
        if (etFullname.getText().toString().trim().isEmpty()) {
            showToast("Please enter full name");
            return false;

        } else if (etPhoneNumber.getText().toString().trim().isEmpty()) {
            showToast("Please enter phone number");
            return false;

        } else if (tvDate.getText().toString().trim().isEmpty()) {
            showToast("Please select date");
            return false;

        } else if (etWhy.getText().toString().trim().isEmpty()) {
            showToast("Please enter reason for hiring us");
            return false;

        } else if (etcomments.getText().toString().trim().isEmpty()) {
            showToast("Please enter comments");
            return false;

        }
        return true;
    }

}