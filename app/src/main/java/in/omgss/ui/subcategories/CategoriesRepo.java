package in.omgss.ui.subcategories;

import in.omgss.base.NetworkCallback;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.data.DataManager;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.subcategory.SubcategoryResponse;
import okhttp3.RequestBody;

public class CategoriesRepo {
    public void getCategories(final RichMediatorLiveData<SubcategoryResponse> liveData, RequestBody request) {
        DataManager.getInstance().subCategories(request).enqueue(new NetworkCallback<SubcategoryResponse>() {
            @Override
            public void onSuccess(SubcategoryResponse loginResponse) {
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
