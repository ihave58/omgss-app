package in.omgss.ui.offers;

import in.omgss.base.NetworkCallback;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.data.DataManager;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.offers.OffersResponse;
import okhttp3.RequestBody;

public class OffersRepo {

    public void getOffers(final RichMediatorLiveData<OffersResponse> liveData, RequestBody request) {
        DataManager.getInstance().offers(request).enqueue(new NetworkCallback<OffersResponse>() {
            @Override
            public void onSuccess(OffersResponse loginResponse) {
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
