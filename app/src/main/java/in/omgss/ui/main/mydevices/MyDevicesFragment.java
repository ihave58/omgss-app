package in.omgss.ui.main.mydevices;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.omgss.R;
import in.omgss.adapters.DevicesAdapter;
import in.omgss.base.BaseFragment;
import in.omgss.data.api.ApiConstants;
import in.omgss.pojo.responses.mydevices.MyDevicesResponse;
import in.omgss.pojo.responses.mydevices.Response;
import okhttp3.MultipartBody;


public class MyDevicesFragment extends BaseFragment {
    @BindView(R.id.srlDevices)
    SwipeRefreshLayout srlDevices;
    @BindView(R.id.rv_my_devices)
    RecyclerView rvMyDevices;
    @BindView(R.id.atv_no_data_found)
    AppCompatTextView atvNoDataFound;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    private MyDevicesViewModel mViewModel;

    private DevicesAdapter mAddressAdapter;
    private ArrayList<Response> devicesList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_devices, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MyDevicesViewModel.class);
        mViewModel.setGenericObservers(getErrorResponseObserver(), getFailureResponseObserver());

        setObservers();
        setUpAdapter();

        getMyDevices(true);
    }

    @Override
    public void initVariables() {

    }

    @Override
    public void setListeners() {
        srlDevices.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMyDevices(false);
            }
        });
    }


    private void setObservers() {
        mViewModel.getMyDevicesLiveData().observe(getViewLifecycleOwner(), new Observer<MyDevicesResponse>() {
            @Override
            public void onChanged(MyDevicesResponse responseBody) {
                hideProgressDialog();

                if (srlDevices.isRefreshing())
                    srlDevices.setRefreshing(false);

                if (responseBody != null) {
                    if (responseBody.getStatus().equalsIgnoreCase(ApiConstants.STATUS_SUCCESS)) {
                        devicesList.clear();

                        if (responseBody.getResponse() != null) {
                            devicesList.addAll(responseBody.getResponse());
                        }

                        mAddressAdapter.notifyDataSetChanged();

                    }

                    atvNoDataFound.setVisibility(devicesList.size() > 0 ? View.GONE : View.VISIBLE);
                    btnSubmit.setVisibility(devicesList.size() > 0 ? View.VISIBLE : View.GONE);

                }
            }
        });
    }


    private void setUpAdapter() {
        devicesList = new ArrayList<>();

        mAddressAdapter = new DevicesAdapter(devicesList);
        rvMyDevices.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        rvMyDevices.setAdapter(mAddressAdapter);
    }


    private void getMyDevices(boolean showProgress) {
        if (isNetworkAvailable()) {

            MultipartBody body = getMultipartRequestBuilder()
                    .addFormDataPart("userid", mViewModel.getUserId())
                    .build();

            mViewModel.getMyDevices(body);

        } else {
            showNoNetworkError();
        }
    }


    @OnClick({R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                Intent intent = new Intent(getActivity(), DeviceComplaintActivity.class);
                intent.putExtra("data", devicesList);
                startActivity(intent);
                break;
        }
    }

}