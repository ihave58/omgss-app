package in.omgss.ui.hireus;

import in.omgss.base.NetworkCallback;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.data.DataManager;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.CommonApiResponse;
import in.omgss.pojo.responses.categories.CategoriesResponse;
import okhttp3.RequestBody;

public class HireUsRepo {

    public void getCategories(final RichMediatorLiveData<CategoriesResponse> liveData, RequestBody request) {
        DataManager.getInstance().categories(request).enqueue(new NetworkCallback<CategoriesResponse>() {
            @Override
            public void onSuccess(CategoriesResponse loginResponse) {
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

    public void hireUs(final RichMediatorLiveData<CommonApiResponse> liveData, RequestBody request) {
        DataManager.getInstance().hireus(request).enqueue(new NetworkCallback<CommonApiResponse>() {
            @Override
            public void onSuccess(CommonApiResponse response) {
                if (response != null) {
                    liveData.setValue(response);
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
