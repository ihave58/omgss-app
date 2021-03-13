package in.omgss.ui.addedaddress;

import in.omgss.base.NetworkCallback;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.constants.PreferenceConstants;
import in.omgss.data.DataManager;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.CommonApiResponse;
import in.omgss.pojo.responses.addresslist.AddressListResponse;
import okhttp3.MultipartBody;

public class AddedAddressRepo {

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

    public void deleteAddress(MultipartBody requestBody, RichMediatorLiveData<CommonApiResponse> ordersLiveData) {
        DataManager.getInstance().deleteAddress(requestBody).enqueue(new NetworkCallback<CommonApiResponse>() {
            @Override
            public void onSuccess(CommonApiResponse responseBody) {
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

    public String getUserId() {
        return DataManager.getInstance().getStringFromPreference(PreferenceConstants.USER_ID);
    }

    public String getEmail() {
        return DataManager.getInstance().getStringFromPreference(PreferenceConstants.EMAIL);
    }
}
