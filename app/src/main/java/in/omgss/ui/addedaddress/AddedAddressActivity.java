package in.omgss.ui.addedaddress;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import in.omgss.R;
import in.omgss.adapters.AddressAdapter;
import in.omgss.base.BaseActivity;
import in.omgss.data.api.ApiConstants;
import in.omgss.dialogs.ConfirmationDialog;
import in.omgss.interfaces.AddressItemClickListener;
import in.omgss.interfaces.DialogCallback;
import in.omgss.pojo.responses.CommonApiResponse;
import in.omgss.pojo.responses.addresslist.AddressListResponse;
import in.omgss.pojo.responses.addresslist.Response;
import in.omgss.ui.addeditaddress.AddEditAddressActivity;
import okhttp3.MultipartBody;

public class AddedAddressActivity extends BaseActivity {

    @BindView(R.id.iv_toolbar_left)
    ImageView ivToolbarLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_addresses)
    RecyclerView rvAddresses;
    @BindView(R.id.iv_toolbar_right)
    ImageView ivToolbarRight;
    @BindView(R.id.rlToolbar)
    RelativeLayout rlToolbar;
    @BindView(R.id.atv_no_data_found)
    AppCompatTextView atvNoDataFound;

    private AddedAddressViewModel mViewModel;

    private ArrayList<Response> addressList;
    private AddressAdapter mAddressAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("Addresses");

        ivToolbarRight.setImageResource(R.drawable.ic_add);
        ivToolbarRight.setVisibility(View.VISIBLE);

        mViewModel = new ViewModelProvider(this).get(AddedAddressViewModel.class);
        mViewModel.setGenericObservers(getErrorResponseObserver(), getFailureResponseObserver());

        setObservers();
        setUpAdapter();

        getAddressList();

    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_added_address;
    }


    private void setObservers() {
        mViewModel.getAddressListLiveData().observe(this, new Observer<AddressListResponse>() {
            @Override
            public void onChanged(AddressListResponse responseBody) {
                hideProgressDialog();
                if (responseBody != null) {
                    if (responseBody.getStatus().equalsIgnoreCase(ApiConstants.STATUS_SUCCESS)) {

                        addressList.clear();

                        if (responseBody.getResponse() != null)
                            addressList.addAll(responseBody.getResponse());

                        mAddressAdapter.notifyDataSetChanged();

                    }

                    atvNoDataFound.setVisibility(addressList.size() > 0 ? View.GONE : View.VISIBLE);

                }
            }
        });

        mViewModel.getDeleteAddressLiveData().observe(this, new Observer<CommonApiResponse>() {
            @Override
            public void onChanged(CommonApiResponse responseBody) {
                hideProgressDialog();
                if (responseBody != null) {
                    if (responseBody.getStatus().equalsIgnoreCase(ApiConstants.STATUS_SUCCESS)) {
//                        showToast(responseBody.getStatus());

                    }

                }
            }
        });
    }


    private void setUpAdapter() {
        addressList = new ArrayList<>();
        mAddressAdapter = new AddressAdapter(addressList, getIntent().getStringExtra("from"), new AddressItemClickListener() {
            @Override
            public void onAddressSelected(int position) {
                if (position >= 0 && position < addressList.size()) {
                    Response model = addressList.get(position);
                    Intent intent = new Intent();
                    intent.putExtra("addressId", model.getId());
                    intent.putExtra("addressName", model.getAddressprofilename());
                    intent.putExtra("fullName", model.getFullname());
                    intent.putExtra("email", model.getEmail());
                    intent.putExtra("address", model.getAddress());
                    intent.putExtra("city", model.getCity());
                    intent.putExtra("state", model.getState());
                    intent.putExtra("zip", model.getZip());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }

            @Override
            public void onEditClicked(int position) {
                if (position >= 0 && position < addressList.size()) {
                    Response model = addressList.get(position);
                    Intent intent = new Intent(AddedAddressActivity.this, AddEditAddressActivity.class);
                    intent.putExtra("addressId", model.getId());
                    intent.putExtra("addressName", model.getAddressprofilename());
                    intent.putExtra("fullName", model.getFullname());
                    intent.putExtra("email", model.getEmail());
                    intent.putExtra("address", model.getAddress());
                    intent.putExtra("city", model.getCity());
                    intent.putExtra("state", model.getState());
                    intent.putExtra("zip", model.getZip());
                    startActivityForResult(intent, 1);
                }
            }

            @Override
            public void onDeleteClicked(int position) {
                ConfirmationDialog deleteAddressDialog = new ConfirmationDialog(AddedAddressActivity.this,
                        "Delete Address?",
                        "Are you sure you want to delete the address?",
                        "Yes", "No",
                        new DialogCallback() {
                            @Override
                            public void onSubmit(View view, Object result) {
                                if (position >= 0 && position < addressList.size()) {

                                    deleteAddress(addressList.get(position).getId());

                                    addressList.remove(position);
                                    mAddressAdapter.notifyItemRemoved(position);

                                    atvNoDataFound.setVisibility(addressList.size() > 0 ? View.GONE : View.VISIBLE);
                                }
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                deleteAddressDialog.show();
            }
        });
        rvAddresses.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvAddresses.setAdapter(mAddressAdapter);
    }


    private void deleteAddress(String addressId) {
        if (isNetworkAvailable()) {
            showProgressDialog();

            MultipartBody body = getMultipartRequestBuilder()
                    .addFormDataPart("userid", mViewModel.getUserId())
                    .addFormDataPart("addressid", addressId)
                    .build();

            mViewModel.deleteAddress(body);

        } else {
            showNoNetworkError();
        }
    }


    private void getAddressList() {
        if (isNetworkAvailable()) {
            showProgressDialog();

            MultipartBody body = getMultipartRequestBuilder()
                    .addFormDataPart("userid", mViewModel.getUserId())
                    .build();

            mViewModel.getAddressList(body);

        } else {
            showNoNetworkError();
        }
    }


    @OnClick({R.id.iv_toolbar_left, R.id.iv_toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_left:
                onBackPressed();
                break;

            case R.id.iv_toolbar_right:
                Intent intent = new Intent(AddedAddressActivity.this, AddEditAddressActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    getAddressList();
                }
        }
    }

}
