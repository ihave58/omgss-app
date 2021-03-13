package in.omgss.ui.cart;

import in.omgss.base.NetworkCallback;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.constants.PreferenceConstants;
import in.omgss.data.DataManager;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.CommonApiResponse;
import in.omgss.pojo.responses.addresslist.AddressListResponse;
import in.omgss.pojo.responses.cartresponse.CartResponse;
import in.omgss.pojo.responses.createrazorpayorder.CreateRazorPayOrder;
import in.omgss.pojo.responses.validatecoupon.ValidateCouponResponse;
import okhttp3.MultipartBody;

public class CartRepo {

    public String getUserId() {
        return DataManager.getInstance().getStringFromPreference(PreferenceConstants.USER_ID);
    }


    public String getEmail() {
        return DataManager.getInstance().getStringFromPreference(PreferenceConstants.EMAIL);
    }


    public void validateCoupon(MultipartBody requestBody, RichMediatorLiveData<ValidateCouponResponse> liveData) {
        DataManager.getInstance().validateCoupon(requestBody).enqueue(new NetworkCallback<ValidateCouponResponse>() {
            @Override
            public void onSuccess(ValidateCouponResponse responseBody) {
                if (responseBody != null) {
                    liveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(FailureResponse failureResponse) {
                liveData.setFailure(failureResponse);
            }

            @Override
            public void onError(Throwable t) {
                liveData.setError(null);
            }
        });
    }


    public void getCart(MultipartBody requestBody, RichMediatorLiveData<CartResponse> liveData) {
        DataManager.getInstance().getCart(requestBody).enqueue(new NetworkCallback<CartResponse>() {
            @Override
            public void onSuccess(CartResponse responseBody) {
                if (responseBody != null) {
                    liveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(FailureResponse failureResponse) {
                liveData.setFailure(failureResponse);
            }

            @Override
            public void onError(Throwable t) {
                liveData.setError(null);
            }
        });
    }


    public void createRazorPayOrder(MultipartBody requestBody, RichMediatorLiveData<CreateRazorPayOrder> liveData) {
        DataManager.getInstance().createRazorPayOrder(requestBody).enqueue(new NetworkCallback<CreateRazorPayOrder>() {
            @Override
            public void onSuccess(CreateRazorPayOrder responseBody) {
                if (responseBody != null) {
                    liveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(FailureResponse failureResponse) {
                liveData.setFailure(failureResponse);
            }

            @Override
            public void onError(Throwable t) {
                liveData.setError(null);
            }
        });
    }


    public void createOrder(MultipartBody requestBody, RichMediatorLiveData<CommonApiResponse> liveData) {
        DataManager.getInstance().createOrder(requestBody).enqueue(new NetworkCallback<CommonApiResponse>() {
            @Override
            public void onSuccess(CommonApiResponse responseBody) {
                if (responseBody != null) {
                    liveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(FailureResponse failureResponse) {
                liveData.setFailure(failureResponse);
            }

            @Override
            public void onError(Throwable t) {
                liveData.setError(null);
            }
        });
    }


    public void getAddedAddresses(MultipartBody requestBody, RichMediatorLiveData<AddressListResponse> ordersLiveData) {
        DataManager.getInstance().getAddressList(requestBody).enqueue(new NetworkCallback<AddressListResponse>() {
            @Override
            public void onSuccess(AddressListResponse responseBody) {
                if (responseBody != null) {
                    ordersLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(FailureResponse failureResponse) {
                ordersLiveData.setFailure(failureResponse);
            }

            @Override
            public void onError(Throwable t) {
                ordersLiveData.setError(null);
            }
        });
    }


}
