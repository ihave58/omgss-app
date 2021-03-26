package in.omgss.ui.cart;

import androidx.lifecycle.Observer;

import in.omgss.base.BaseViewModel;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.CommonApiResponse;
import in.omgss.pojo.responses.addresslist.AddressListResponse;
import in.omgss.pojo.responses.cartresponse.CartResponse;
import in.omgss.pojo.responses.createrazorpayorder.CreateRazorPayOrder;
import in.omgss.pojo.responses.validatecoupon.ValidateCouponResponse;
import okhttp3.MultipartBody;

public class CartViewModel extends BaseViewModel {
    private final CartRepo mRepository = new CartRepo();

    private RichMediatorLiveData<ValidateCouponResponse> validateCouponLiveData;

    private RichMediatorLiveData<CartResponse> cartLiveData;
    private RichMediatorLiveData<CreateRazorPayOrder> createRazorPayOrderLiveData;
    private RichMediatorLiveData<CommonApiResponse> createOrderLiveData;
    private RichMediatorLiveData<AddressListResponse> addressListLiveData;

    @Override
    public void initLiveData() {
        if (validateCouponLiveData == null) {
            validateCouponLiveData = new RichMediatorLiveData<ValidateCouponResponse>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return CartViewModel.this.getFailureObserver();
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return CartViewModel.this.getErrorObserver();
                }
            };
        }
        if (cartLiveData == null) {
            cartLiveData = new RichMediatorLiveData<CartResponse>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return CartViewModel.this.getFailureObserver();
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return CartViewModel.this.getErrorObserver();
                }
            };
        }
        if (createRazorPayOrderLiveData == null) {
            createRazorPayOrderLiveData = new RichMediatorLiveData<CreateRazorPayOrder>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return CartViewModel.this.getFailureObserver();
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return CartViewModel.this.getErrorObserver();
                }
            };
        }
        if (createOrderLiveData == null) {
            createOrderLiveData = new RichMediatorLiveData<CommonApiResponse>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return CartViewModel.this.getFailureObserver();
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return CartViewModel.this.getErrorObserver();
                }
            };
        }
        if (addressListLiveData == null) {
            addressListLiveData = new RichMediatorLiveData<AddressListResponse>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return CartViewModel.this.getFailureObserver();
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return CartViewModel.this.getErrorObserver();
                }
            };
        }
    }

    public RichMediatorLiveData<CartResponse> getCartLiveData() {
        return cartLiveData;
    }

    public RichMediatorLiveData<ValidateCouponResponse> getValidateCouponLiveData() {
        return validateCouponLiveData;
    }

    public RichMediatorLiveData<CreateRazorPayOrder> getCreateRazorPayOrderLiveData() {
        return createRazorPayOrderLiveData;
    }

    public RichMediatorLiveData<CommonApiResponse> getCreateOrderLiveData() {
        return createOrderLiveData;
    }

    public RichMediatorLiveData<AddressListResponse> getAddressListLiveData() {
        return addressListLiveData;
    }

    public String getUserId() {
        return mRepository.getUserId();
    }

    public String getEmail() {
        return mRepository.getEmail();
    }


    public void validateCoupon(MultipartBody requestBody) {
        mRepository.validateCoupon(requestBody, validateCouponLiveData);
    }


    public void getCartDetails(MultipartBody requestBody) {
        mRepository.getCart(requestBody, cartLiveData);
    }


    public void createRazorPayOrder(MultipartBody requestBody) {
        mRepository.createRazorPayOrder(requestBody, createRazorPayOrderLiveData);
    }


    public void createOrder(MultipartBody requestBody) {
        mRepository.createOrder(requestBody, createOrderLiveData);
    }


    public void getAddressList(MultipartBody body) {
        mRepository.getAddedAddresses(body, addressListLiveData);
    }

}
