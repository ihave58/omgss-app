package in.omgss.ui.detail;

import in.omgss.base.NetworkCallback;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.data.DataManager;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.viewproduct.ViewproductResponse;
import okhttp3.RequestBody;

public class ProductDetailRepo {

    public void getOrderDetail(final RichMediatorLiveData<ViewproductResponse> liveData, RequestBody request) {
        DataManager.getInstance().viewproduct(request).enqueue(new NetworkCallback<ViewproductResponse>() {
            @Override
            public void onSuccess(ViewproductResponse loginResponse) {
                if (loginResponse != null) {
                    liveData.setValue(loginResponse);
                }
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
