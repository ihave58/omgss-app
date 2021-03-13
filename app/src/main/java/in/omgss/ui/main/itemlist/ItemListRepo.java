package in.omgss.ui.main.itemlist;

import in.omgss.base.NetworkCallback;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.data.DataManager;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.products.ProductsResponse;
import okhttp3.RequestBody;

public class ItemListRepo {

    public void getProducts(final RichMediatorLiveData<ProductsResponse> liveData, RequestBody request) {
        DataManager.getInstance().getproducts(request).enqueue(new NetworkCallback<ProductsResponse>() {
            @Override
            public void onSuccess(ProductsResponse loginResponse) {
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
