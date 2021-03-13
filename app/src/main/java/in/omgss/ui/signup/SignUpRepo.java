package in.omgss.ui.signup;

import in.omgss.base.NetworkCallback;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.data.DataManager;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.SignUpResponse;
import okhttp3.RequestBody;

public class SignUpRepo {
    public void register(final RichMediatorLiveData<SignUpResponse> liveData, RequestBody request) {
        DataManager.getInstance().register(request).enqueue(new NetworkCallback<SignUpResponse>() {
            @Override
            public void onSuccess(SignUpResponse loginResponse) {
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
