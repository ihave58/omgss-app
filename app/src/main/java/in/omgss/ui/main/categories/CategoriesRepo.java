package in.omgss.ui.main.categories;

import in.omgss.base.NetworkCallback;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.data.DataManager;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.categories.CategoriesResponse;
import okhttp3.RequestBody;

public class CategoriesRepo {

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
}
