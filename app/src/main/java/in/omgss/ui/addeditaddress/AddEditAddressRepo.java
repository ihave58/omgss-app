package in.omgss.ui.addeditaddress;

import in.omgss.base.NetworkCallback;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.constants.PreferenceConstants;
import in.omgss.data.DataManager;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.CommonApiResponse;
import okhttp3.MultipartBody;

public class AddEditAddressRepo {

    public void addAddress(MultipartBody requestBody, RichMediatorLiveData<CommonApiResponse> ordersLiveData) {
        DataManager.getInstance().addAddress(requestBody).enqueue(new NetworkCallback<CommonApiResponse>() {
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

    public void editAddress(MultipartBody requestBody, RichMediatorLiveData<CommonApiResponse> ordersLiveData) {
        DataManager.getInstance().editAddress(requestBody).enqueue(new NetworkCallback<CommonApiResponse>() {
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
