package in.omgss.ui.cart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import in.omgss.BuildConfig;
import in.omgss.R;
import in.omgss.adapters.CartItemsAdapter;
import in.omgss.base.BaseActivity;
import in.omgss.constants.PreferenceConstants;
import in.omgss.data.DataManager;
import in.omgss.data.api.ApiConstants;
import in.omgss.dialogs.ConfirmationDialog;
import in.omgss.interfaces.CartItemClickListener;
import in.omgss.interfaces.DialogCallback;
import in.omgss.pojo.responses.CommonApiResponse;
import in.omgss.pojo.responses.addresslist.AddressListResponse;
import in.omgss.pojo.responses.cartresponse.CartResponse;
import in.omgss.pojo.responses.cartresponse.Orderdetail;
import in.omgss.pojo.responses.createrazorpayorder.CreateRazorPayOrder;
import in.omgss.pojo.responses.validatecoupon.Response;
import in.omgss.pojo.responses.validatecoupon.ValidateCouponResponse;
import in.omgss.ui.addedaddress.AddedAddressActivity;
import in.omgss.ui.offers.OffersActivty;
import in.omgss.ui.orders.OrderReceivedActivity;
import okhttp3.MultipartBody;

public class CartActivity extends BaseActivity implements PaymentResultWithDataListener {

  @BindView(R.id.tv_title)
  TextView tvTitle;
  @BindView(R.id.rv_items)
  RecyclerView rvItems;
  @BindView(R.id.et_coupon)
  EditText etCoupon;
  @BindView(R.id.tv_label_coupon_discount)
  TextView tvLabelCouponDiscount;
  @BindView(R.id.tv_label_tax)
  TextView tvLabelTax;
  @BindView(R.id.tv_item_total)
  TextView tvItemTotal;

  // code add here
  @BindView(R.id.tv_new_total_after_discount)
  TextView tvNewTotal;
  //end 
  @BindView(R.id.tv_coupon_discount)
  TextView tvCouponDiscount;
  @BindView(R.id.tv_tax)
  TextView tvTax;

  @BindView(R.id.tv_total)
  TextView tvTotal;
  @BindView(R.id.tv_address_type)
  TextView tvAddressType;
  @BindView(R.id.tv_address)
  TextView tvAddress;
  @BindView(R.id.atv_apply_coupon)
  AppCompatTextView atvApplyCoupon;
  @BindView(R.id.atv_remove_coupon)
  AppCompatTextView atvRemoveCoupon;
  @BindView(R.id.rb_online)
  RadioButton rbOnline;
  @BindView(R.id.rb_cos)
  RadioButton rbCos;
  @BindView(R.id.tv_change_address)
  TextView tvChangeAddress;
  @BindView(R.id.atv_no_data_found)
  AppCompatTextView atvNoDataFound;
  @BindView(R.id.cl_main)
  ConstraintLayout clMain;

  private CartViewModel mViewModel;

  private ArrayList < Orderdetail > cartList;
  private CartItemsAdapter mCartItemsAdapter;

  private String addressId;
  private String addressName;
  private String fullName;
  private String email;
  private String address;
  private String city;
  private String state;
  private String zip;

  private double itemTotal;
  private double taxPercent;
  private double discountValue;
  private double deliveryCharges;
  private double cartTotal;

  private double discount;

  private Response couponResponse;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    tvTitle.setText("Cart");

    Checkout.preload(getApplicationContext());

    mViewModel = new ViewModelProvider(this).get(CartViewModel.class);

  }

  @Override
  protected void onPostCreate(@Nullable Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    tvAddress.setText("Select Address");
    tvChangeAddress.setText("Select Address");

    setObservers();
    setUpAdapter();

    getCartDetails();

  }

  @Override
  protected int getResourceId() {
    return R.layout.activity_cart;
  }

  private void setUpAdapter() {
    cartList = new ArrayList < > ();

    mCartItemsAdapter = new CartItemsAdapter(cartList, new CartItemClickListener() {

      @Override
      public void onQuantityUpdate() {
        updateCartAmount();
      }

      @Override
      public void handleDeleteCartItem(int position) {
        ConfirmationDialog customAlerDialog = new ConfirmationDialog(CartActivity.this,
          "Delete Item",
          "Are you sure you want to delete this item from the cart?",
          "Yes", "No",
          new DialogCallback() {
            @Override
            public void onSubmit(View view, Object result) {
              removeFromCart(cartList.get(position).getId(), cartList.get(position).getPrdid(), true);

              cartList.remove(position);
              mCartItemsAdapter.notifyItemRemoved(position);

              updateCartAmount();

              if (cartList.size() == 0) {
                clMain.setVisibility(View.GONE);
                atvNoDataFound.setText("Your cart is empty!");
              }

            }

            @Override
            public void onCancel() {}
          });
        customAlerDialog.show();
      }
    });

    rvItems.setLayoutManager(new LinearLayoutManager(this));
    rvItems.setAdapter(mCartItemsAdapter);
  }

  private void updateCartAmount() {
    itemTotal = 0;
    //code addd
    double newtotal = 0;
    //end
    for (Orderdetail items: cartList) {
      itemTotal = itemTotal + (items.getQuantity() * items.getActualprice());
      //code addd
      newtotal = newtotal + (items.getQuantity() * items.getSaleprice());
      //end

      // double newtotal = items.getActualprice()-items.getSaleprice();
    }

    //code addd
    if (couponResponse == null) {
      // coupon is not applied
      discountValue = itemTotal - newtotal;

    } else {
      // coupon is applied
      newtotal = itemTotal - discountValue;
    }
    //end

    double tax = newtotal * (taxPercent / 100);

    cartTotal = newtotal + tax;

    // cartTotal = newtotal + tax + deliveryCharges - discountValue;

    tvItemTotal.setText(getString(R.string.currency));
    tvItemTotal.append(String.format("%.2f", itemTotal));

    //code addd
    tvCouponDiscount.setText(getString(R.string.currency));
    tvCouponDiscount.append(String.format("%.2f", discountValue));

    tvNewTotal.setText(getString(R.string.currency));
    tvNewTotal.append(String.format("%.2f", newtotal));
    //end

    // tvCouponDiscount.setText(getString(R.string.currency));
    //tvCouponDiscount.append(String.format("%.2f", discountValue));

    // tvDiscount.setText(getString(R.string.currency));
    //tvDiscount.append(String.format("%.2f", tax));

    tvTax.setText(getString(R.string.currency));
    tvTax.append(String.format("%.2f", tax));

    //tvDeliveryCharges.setText(getString(R.string.currency));
    //tvDeliveryCharges.append(String.format("%.2f", deliveryCharges));

    tvTotal.setText(getString(R.string.currency));
    tvTotal.append(String.format("%.2f", cartTotal));

  }

  private void setObservers() {
    mViewModel.setGenericObservers(getErrorResponseObserver(), getFailureResponseObserver());

    mViewModel.getValidateCouponLiveData().observe(this, new Observer < ValidateCouponResponse > () {
      @Override
      public void onChanged(ValidateCouponResponse response) {
        hideProgressDialog();
        if (response != null) {
          if (response.getStatus().equalsIgnoreCase(ApiConstants.STATUS_SUCCESS)) {
            if (response.getResponse() != null && response.getResponse().size() > 0) {
              couponResponse = response.getResponse().get(0);

              atvRemoveCoupon.setVisibility(View.VISIBLE);
              atvApplyCoupon.setVisibility(View.INVISIBLE);

              etCoupon.setEnabled(false);

              if (couponResponse.getCoupontype() == 1) {
                discountValue = itemTotal * (couponResponse.getCouponamount() / 100);

              } else {
                discountValue = couponResponse.getCouponamount();
              }
              updateCartAmount();
            }

          } else {
            showToast(response.getMessage());
          }
        }
      }
    });

    mViewModel.getAddressListLiveData().observe(this, new Observer < AddressListResponse > () {
      @Override
      public void onChanged(AddressListResponse response) {
        hideProgressDialog();
        if (response != null) {
          if (response.getStatus().equalsIgnoreCase(ApiConstants.STATUS_SUCCESS)) {
            if (response.getResponse() != null && response.getResponse().size() > 0) {

              in.omgss.pojo.responses.addresslist.Response data = response.getResponse().get(0);
              if (data != null) {
                addressId = data.getId();
                addressName = data.getAddressprofilename();
                fullName = data.getFullname();
                email = data.getEmail();
                address = data.getAddress();
                state = data.getState();
                city = data.getCity();
                zip = data.getZip();

                setAddress();
              }

            }
          }
        }
      }
    });

    mViewModel.getCartLiveData().observe(this, new Observer < CartResponse > () {
      @Override
      public void onChanged(CartResponse response) {
        hideProgressDialog();
        if (response != null) {
          if (response.getStatus().equalsIgnoreCase(ApiConstants.STATUS_SUCCESS)) {

            clMain.setVisibility(View.VISIBLE);

            cartList.clear();

            if (response.getOrderdetails() != null) {
              cartList.addAll(response.getOrderdetails());
            }

            mCartItemsAdapter.notifyDataSetChanged();

            itemTotal = response.getSubtotal();
            taxPercent = response.getTaxPercent();
            updateCartAmount();

          } else {
            atvNoDataFound.setText(response.getMessage());
          }
        }
      }
    });

    mViewModel.getCreateRazorPayOrderLiveData().observe(this, new Observer < CreateRazorPayOrder > () {
      @Override
      public void onChanged(CreateRazorPayOrder response) {
        hideProgressDialog();
        if (response != null) {
          if (response.getStatus().equalsIgnoreCase(ApiConstants.STATUS_SUCCESS)) {
            startRazorPayPayment(response.getRazorpayorderid());

          } else {
            showToast(response.getMessage());
          }
        }
      }
    });

    mViewModel.getCreateOrderLiveData().observe(this, new Observer < CommonApiResponse > () {
      @Override
      public void onChanged(CommonApiResponse response) {
        hideProgressDialog();
        if (response != null) {
          if (response.getStatus().equalsIgnoreCase(ApiConstants.STATUS_SUCCESS)) {

            DataManager.getInstance().saveIntInPreferences(PreferenceConstants.CART_COUNT, 0);
            updateBatchCount();

            refreshHomeData();

            startActivity(new Intent(CartActivity.this, OrderReceivedActivity.class));
            finish();

          } else {
            showToast(response.getMessage());
          }
        }
      }
    });

  }

  /**
   * method to hit validate coupon api
   */
  private void validateCoupon() {
    if (isNetworkAvailable()) {
      showProgressDialog();

      MultipartBody body = getMultipartRequestBuilder()
        .addFormDataPart("userid", mViewModel.getUserId())
        .addFormDataPart("couponcode", etCoupon.getText().toString().trim())
        .build();

      mViewModel.validateCoupon(body);

    } else {
      showNoNetworkError();
    }
  }

  /**
   * method to hit api to get cart details & address list
   */
  private void getCartDetails() {
    if (isNetworkAvailable()) {
      showProgressDialog();

      MultipartBody body = getMultipartRequestBuilder()
        .addFormDataPart("userid", mViewModel.getUserId())
        .build();

      mViewModel.getCartDetails(body);

      mViewModel.getAddressList(body);

    } else {
      showNoNetworkError();
    }
  }

  /**
   * method to hit create RazorPay Order api
   */
  private void createRazorPayOrder() {
    if (isNetworkAvailable()) {
      showProgressDialog();

      MultipartBody body = getMultipartRequestBuilder()
        .addFormDataPart("amount", String.format("%.2f", cartTotal))
        .build();

      mViewModel.createRazorPayOrder(body);

    } else {
      showNoNetworkError();
    }
  }

  /**
   * method to hit create order api
   */
  private void createOrder(String paymentId) {
    if (isNetworkAvailable()) {
      showProgressDialog();

      String paymentMode = rbCos.isChecked() ? "cos" : "prep";

      MultipartBody body = getMultipartRequestBuilder()
        .addFormDataPart("userid", getUserId())
        .addFormDataPart("email", mViewModel.getEmail())
        .addFormDataPart("fullname", fullName)
        .addFormDataPart("address", address)
        .addFormDataPart("city", city)
        .addFormDataPart("state", state)
        .addFormDataPart("zip", zip)
        .addFormDataPart("paymenttype", paymentMode)
        .addFormDataPart("totalordervalue", String.format("%.2f", cartTotal))
        .addFormDataPart("discount", String.format("%.2f", discountValue))
        .addFormDataPart("razorpayid", paymentId)
        .addFormDataPart("couponcode", couponResponse != null ? couponResponse.getId() : "")
        .addFormDataPart("coupondetails", couponResponse != null ? createCouponDetailJson() : "")
        .addFormDataPart("orderdetails", createOrderDetailJson())
        .build();

      mViewModel.createOrder(body);

    } else {
      showNoNetworkError();
    }
  }

  @NotNull
  private String createOrderDetailJson() {
    JSONObject obj = new JSONObject();
    try {
      int i = 1;
      for (Orderdetail item: cartList) {
        JSONObject data = new JSONObject();
        data.put("productid", item.getPrdid());
        data.put("saleprice", String.format("%.2f", item.getSaleprice()));
        data.put("quantity", String.valueOf(item.getQuantity()));

        obj.put(String.valueOf(i++), data);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return obj.toString();
  }

  @NotNull
  private String createCouponDetailJson() {
    JSONObject data = new JSONObject();
    if (couponResponse != null) {
      try {
        data.put("id", couponResponse.getId());
        data.put("couponname", couponResponse.getCouponname());
        data.put("couponcode", couponResponse.getCouponcode());
        data.put("coupontype", couponResponse.getCoupontype());
        data.put("couponamount", String.format("%.2f", couponResponse.getCouponamount()));
        data.put("usageperuser", String.valueOf(couponResponse.getUsageperuser()));

      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    return data.toString();
  }

  /**
   * method to start RazorPay Payment
   */
  private void startRazorPayPayment(String orderId) {
    if (!isFinishing()) {
      try {
        // Pass your payment options to the Razor pay Checkout as a JSONObject
        Checkout checkout = new Checkout();

        //Setting business logo
        //                checkout.setImage(R.drawable.logo);

        JSONObject options = new JSONObject();

        //Merchant Name
        options.put("name", getString(R.string.app_name));

        // orderId received from backend
        if (orderId != null && !orderId.isEmpty()) {
          options.put("order_id", orderId);
        }

        String total = String.format("%.2f", cartTotal);
        int amount = (int)(Double.parseDouble(total) * 100);

        // Amount is always passed in PAISE, Eg: 100 = Rs 1.00
        //                options.put("amount", 100);
        options.put("amount", amount);
        options.put("currency", "INR");

        options.put("description", "Your Order: " + orderId);

        checkout.open(CartActivity.this, options);

      } catch (Exception e) {
        Log.e("RAZOR_PAY_ERROR", "Error in starting RazorPay Checkout", e);
      }
    }
  }

  @OnClick({
    R.id.iv_toolbar_left,
    R.id.atv_apply_coupon,
    R.id.atv_remove_coupon,
    R.id.tv_view_coupons,
    R.id.tv_change_address,
    R.id.tv_proceed_to_checkout
  })
  public void onViewClicked(View view) {
    switch (view.getId()) {
    case R.id.iv_toolbar_left:
      onBackPressed();
      break;

    case R.id.atv_apply_coupon:
      if (etCoupon.getText().toString().trim().isEmpty()) {
        showToast("Please enter coupon code");

      } else {
        validateCoupon();
      }
      break;

    case R.id.atv_remove_coupon:
      couponResponse = null;
      discountValue = 0.0;
      updateCartAmount();

      etCoupon.setText("");
      etCoupon.setEnabled(true);

      atvRemoveCoupon.setVisibility(View.INVISIBLE);
      atvApplyCoupon.setVisibility(View.VISIBLE);

      break;

    case R.id.tv_view_coupons:
      startActivity(new Intent(this, OffersActivty.class));
      break;

    case R.id.tv_change_address:
      Intent intent = new Intent(this, AddedAddressActivity.class);
      intent.putExtra("from", CartActivity.class.getName());
      startActivityForResult(intent, 11);
      break;

    case R.id.tv_proceed_to_checkout:
      if (addressId == null) {
        showToast("Please select address!");

      } else {
        if (isNetworkAvailable()) {
          if (rbOnline.isChecked()) {
            showProgressDialog();

            if (BuildConfig.FLAVOR.equalsIgnoreCase("development")) {
              startRazorPayPayment(null);

            } else {
              createRazorPayOrder();
            }

          } else {
            createOrder("");
          }
        } else {
          showNoNetworkError();
        }
      }
      break;
    }
  }

  private void setAddress() {
    if (!isFinishing() && addressId != null) {

      tvAddressType.setText(getString(R.string.service_address));
      tvAddressType.append(": ");
      tvAddressType.append(addressName != null ? addressName : "");

      tvAddress.setText(address != null ? address : "");
      tvAddress.append(", ");
      tvAddress.append(city != null ? city : "");
      tvAddress.append(", ");
      tvAddress.append(state != null ? state : "");
      tvAddress.append(", ");
      tvAddress.append(zip != null ? zip : "");

      tvChangeAddress.setText("Change Address");
    }
  }

  @Override
  public void onPaymentSuccess(String s, PaymentData paymentData) {
    if (!isFinishing()) {
      createOrder(paymentData.getPaymentId());
    }
  }

  @Override
  public void onPaymentError(int i, String response, PaymentData paymentData) {
    hideProgressDialog();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
    case 11:
      if (resultCode == RESULT_OK && data != null) {
        addressId = data.getStringExtra("addressId");
        addressName = data.getStringExtra("addressName");
        fullName = data.getStringExtra("fullName");
        email = data.getStringExtra("email");
        address = data.getStringExtra("address");
        city = data.getStringExtra("city");
        state = data.getStringExtra("state");
        zip = data.getStringExtra("zip");

        setAddress();

      }
      break;
    }
  }

}