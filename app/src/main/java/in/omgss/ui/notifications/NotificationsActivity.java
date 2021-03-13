package in.omgss.ui.notifications;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.OnClick;
import in.omgss.R;
import in.omgss.adapters.NotificationsAdapter;
import in.omgss.base.BaseActivity;
import in.omgss.constants.PreferenceConstants;
import in.omgss.data.DataManager;
import in.omgss.data.api.ApiConstants;
import in.omgss.pojo.responses.CommonApiResponse;
import in.omgss.pojo.responses.notifications.NotificationsResponse;
import okhttp3.RequestBody;

public class NotificationsActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_notifications)
    RecyclerView rvAddresses;
    @BindView(R.id.atv_no_data_found)
    AppCompatTextView atvNoDataFound;
    private NotificationsAdapter mAddressAdapter;
    private NotificationsViewModel mCategoriesViewmodel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("Notifications");

        mCategoriesViewmodel = new ViewModelProvider(this).get(NotificationsViewModel.class);

        setObservers();

        getNotifications();

    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_notifications;
    }


    private void setObservers() {
        mCategoriesViewmodel.setGenericObservers(getErrorResponseObserver(), getFailureResponseObserver());

        mCategoriesViewmodel.getNotificationsLiveData().observe(this, new Observer<NotificationsResponse>() {
            @Override
            public void onChanged(NotificationsResponse responseBody) {
                hideProgressDialog();
                if (responseBody != null) {
                    if (responseBody.getStatus().equalsIgnoreCase(ApiConstants.STATUS_SUCCESS)) {
                        if (responseBody.getResponse() != null) {
                            mAddressAdapter = new NotificationsAdapter(responseBody.getResponse());
                            rvAddresses.setLayoutManager(new LinearLayoutManager(NotificationsActivity.this, RecyclerView.VERTICAL, false));
                            rvAddresses.setAdapter(mAddressAdapter);

                            atvNoDataFound.setVisibility(responseBody.getResponse().size() > 0 ? View.GONE : View.VISIBLE);
                        }
                    }
                }
            }
        });

        mCategoriesViewmodel.getClearNotificationCountLiveData().observe(this, new Observer<CommonApiResponse>() {
            @Override
            public void onChanged(CommonApiResponse responseBody) {
                hideProgressDialog();
                if (responseBody != null) {
                    if (responseBody.getStatus().equalsIgnoreCase(ApiConstants.STATUS_SUCCESS)) {
                        DataManager.getInstance().saveIntInPreferences(PreferenceConstants.NOTIFICATION_COUNT, 0);
                        updateBatchCount();
                    }
                }
            }
        });

    }


    private void getNotifications() {
        if (isNetworkAvailable()) {
            showProgressDialog();

            RequestBody body = getMultipartRequestBuilder()
                    .addFormDataPart("userid", getUserId())
                    .build();

            mCategoriesViewmodel.getNotifications(body);

            mCategoriesViewmodel.clearNotificationCount(body);

        } else {
            showNoNetworkError();
        }
    }


    @OnClick(R.id.iv_toolbar_left)
    public void onViewClicked() {
        onBackPressed();
    }

}