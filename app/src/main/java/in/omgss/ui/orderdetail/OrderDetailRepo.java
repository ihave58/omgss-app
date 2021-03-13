package in.omgss.ui.orderdetail;

import in.omgss.base.NetworkCallback;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.data.DataManager;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.CommonApiResponse;
import okhttp3.MultipartBody;

public class OrderDetailRepo {

    public void cancelOrder(RichMediatorLiveData<CommonApiResponse> liveData, MultipartBody body) {
        DataManager.getInstance().cancelOrder(body).enqueue(new NetworkCallback<CommonApiResponse>() {
            @Override
            public void onSuccess(CommonApiResponse response) {
                liveData.setValue(response);
            }

            @Override
            public void onFailure(FailureResponse failureResponse) {
                liveData.setFailure(failureResponse);
            }

            @Override
            public void onError(Throwable t) {
                liveData.setError(t);
            }
        });
    }

}
