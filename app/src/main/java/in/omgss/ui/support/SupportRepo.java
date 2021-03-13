package in.omgss.ui.support;

import in.omgss.base.NetworkCallback;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.data.DataManager;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.CommonApiResponse;
import okhttp3.RequestBody;

public class SupportRepo {
    public void support(final RichMediatorLiveData<CommonApiResponse> liveData, RequestBody request) {
        DataManager.getInstance().support(request).enqueue(new NetworkCallback<CommonApiResponse>() {
            @Override
            public void onSuccess(CommonApiResponse loginResponse) {
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
