package in.omgss.ui.main.mydevices;

import in.omgss.base.NetworkCallback;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.constants.PreferenceConstants;
import in.omgss.data.DataManager;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.CommonApiResponse;
import in.omgss.pojo.responses.mydevices.MyDevicesResponse;
import okhttp3.MultipartBody;

public class MyDevicesRepo {

    public void getMyDevices(MultipartBody requestBody, RichMediatorLiveData<MyDevicesResponse> ordersLiveData) {
        DataManager.getInstance().getMyDevices(requestBody).enqueue(new NetworkCallback<MyDevicesResponse>() {
            @Override
            public void onSuccess(MyDevicesResponse responseBody) {
                ordersLiveData.setValue(responseBody);
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

    public void createDeviceComplaint(MultipartBody requestBody, RichMediatorLiveData<CommonApiResponse> ordersLiveData) {
        DataManager.getInstance().createDeviceComplaint(requestBody).enqueue(new NetworkCallback<CommonApiResponse>() {
            @Override
            public void onSuccess(CommonApiResponse responseBody) {
                ordersLiveData.setValue(responseBody);
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
}
