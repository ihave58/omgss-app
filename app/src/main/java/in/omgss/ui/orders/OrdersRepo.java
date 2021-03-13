package in.omgss.ui.orders;

import in.omgss.base.NetworkCallback;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.constants.PreferenceConstants;
import in.omgss.data.DataManager;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.myorders.OrdersListResponse;
import okhttp3.MultipartBody;

public class OrdersRepo {

    public void getOrdersList(MultipartBody requestBody, RichMediatorLiveData<OrdersListResponse> ordersLiveData) {
        DataManager.getInstance().orderlist(requestBody).enqueue(new NetworkCallback<OrdersListResponse>() {
            @Override
            public void onSuccess(OrdersListResponse responseBody) {
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
}
