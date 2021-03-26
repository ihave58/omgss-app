package in.omgss.ui.orderdetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.OnClick;
import in.omgss.R;
import in.omgss.adapters.ItemsOrderDetailAdapter;
import in.omgss.base.BaseActivity;
import in.omgss.data.api.ApiConstants;
import in.omgss.dialogs.ConfirmationDialog;
import in.omgss.interfaces.DialogCallback;
import in.omgss.pojo.responses.CommonApiResponse;
import in.omgss.pojo.responses.myorders.Orderdetail;
import in.omgss.pojo.responses.myorders.Response;
import in.omgss.ui.cart.CartActivity;
import in.omgss.utils.AppUtils;
import okhttp3.MultipartBody;


public class OrderDetailActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_items)
    RecyclerView rvItems;
    @BindView(R.id.tv_cancel_order)
    TextView tvCancelOrder;
    @BindView(R.id.tv_reorder)
    TextView tvReorder;
    @BindView(R.id.tv_label_order_id)
    TextView tvLabelOrderId;
    @BindView(R.id.tv_order_date)
    TextView tvOrderDate;
    @BindView(R.id.tv_tv_label_order_state)
    TextView tvTvLabelOrderState;
    @BindView(R.id.tv_label_order_accepted_date)
    TextView tvLabelOrderAcceptedDate;
    @BindView(R.id.tv_address_name)
    TextView tvAddressName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_label_item_total)
    TextView tvLabelItemTotal;
    @BindView(R.id.tv_label_coupon_discount)
    TextView tvLabelCouponDiscount;
    @BindView(R.id.tv_label_tax)
    TextView tvLabelTax;
    @BindView(R.id.tv_label_delivery_charges)
    TextView tvLabelDeliveryCharges;
    @BindView(R.id.tv_item_total)
    TextView tvItemTotal;
    @BindView(R.id.tv_coupon_discount)
    TextView tvCouponDiscount;
    @BindView(R.id.tv_tax)
    TextView tvTax;
    @BindView(R.id.tv_delivery_charges)
    TextView tvDeliveryCharges;
    @BindView(R.id.tv_to_pay_label)
    TextView tvToPayLabel;
    @BindView(R.id.tv_total)
    TextView tvTotal;

    private OrderDetailViewModel mViewModel;

    private Response data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("Order Detail");

        mViewModel = new ViewModelProvider(this).get(OrderDetailViewModel.class);
        mViewModel.setGenericObservers(getErrorResponseObserver(), getFailureResponseObserver());

        data = getIntent().getParcelableExtra("data");

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (data != null) {
            if (data.getOrderstate().equalsIgnoreCase("Delivered") || data.getOrderstate().equalsIgnoreCase("Cancelled")) {
                tvCancelOrder.setVisibility(View.GONE);
                tvReorder.setVisibility(View.GONE);

                if (data.getOrderstate().equalsIgnoreCase("Cancelled")) {
                    tvTvLabelOrderState.setTextColor(ContextCompat.getColor(this, R.color.colorRed));
                    tvTvLabelOrderState.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_canelled_order, 0, 0, 0);
                }

            } else {
                tvCancelOrder.setVisibility(View.VISIBLE);
                tvReorder.setVisibility(View.GONE);

            }

            tvTvLabelOrderState.setText("Order ");
            tvTvLabelOrderState.append(data.getOrderstate());

            tvLabelOrderId.append(data.getId());

            tvOrderDate.setText(AppUtils.formatDateTime(data.getDatetime(), "yyyy-MM-dd HH:mm:ss", "dd MMMM, yyyy"));
            tvLabelOrderAcceptedDate.setText(tvOrderDate.getText().toString());

            tvAddressName.setText(data.getFullname());

            tvAddress.setText(data.getAddress());
            if (data.getCity() != null && !data.getCity().isEmpty()) {
                tvAddress.append(", ");
                tvAddress.append(data.getCity());
            }
            if (data.getState() != null && !data.getState().isEmpty()) {
                tvAddress.append(", ");
                tvAddress.append(data.getState());
            }
            if (data.getZip() != null && !data.getZip().isEmpty()) {
                tvAddress.append(", ");
                tvAddress.append(data.getZip());
            }

            double itemTotal = 0;
            for (Orderdetail item : data.getOrderdetails()) {
                itemTotal = itemTotal + (item.getQuantity() * item.getActualprice());
            }

            tvItemTotal.setText(getString(R.string.currency));
            tvItemTotal.append(String.format("%.2f", itemTotal));

            tvCouponDiscount.setText(getString(R.string.currency));
            tvCouponDiscount.append(String.format("%.2f", data.getDiscountvalue()));

            tvTax.setText(getString(R.string.currency));
            tvTax.append(String.format("%.2f", data.getTaxvalue()));

            tvTotal.setText(getString(R.string.currency));
            tvTotal.append(String.format("%.2f", data.getTotalordervalue()));

            ItemsOrderDetailAdapter mOrdersAdapter = new ItemsOrderDetailAdapter(data.getOrderdetails());
            rvItems.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            rvItems.setAdapter(mOrdersAdapter);

        }

        setObserver();

    }


    @Override
    protected int getResourceId() {
        return R.layout.activity_order_detail;
    }


    private void setObserver() {
        mViewModel.getCancelOrderLiveData().observe(this, new Observer<CommonApiResponse>() {
            @Override
            public void onChanged(CommonApiResponse response) {
                hideProgressDialog();
                if (response != null) {
                    if (response.getStatus().equalsIgnoreCase(ApiConstants.STATUS_SUCCESS)) {
                        setResult(RESULT_OK);
                        finish();

                    } else {
                        response.getMessage();
                    }
                }
            }
        });
    }


    @OnClick({R.id.iv_toolbar_left, R.id.tv_cancel_order, R.id.tv_reorder})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_left:
                onBackPressed();
                break;

            case R.id.tv_cancel_order:
                ConfirmationDialog confirmationDialog = new ConfirmationDialog(this, "Cancel Order", "Are you sure you want to cancel this order?", "Yes", "No",
                        new DialogCallback() {
                            @Override
                            public void onSubmit(View view, Object result) {
                                if (isNetworkAvailable()) {
                                    if (data != null) {
                                        showProgressDialog();

                                        MultipartBody body = getMultipartRequestBuilder()
                                                .addFormDataPart("loggeduserid", getUserId())
                                                .addFormDataPart("orderid", data.getId())
                                                .build();

                                        mViewModel.cancelOrder(body);
                                    }
                                } else {
                                    showNoNetworkError();
                                }
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                confirmationDialog.show();
                break;


            case R.id.tv_reorder:
                startActivity(new Intent(this, CartActivity.class));
                break;
        }
    }


}