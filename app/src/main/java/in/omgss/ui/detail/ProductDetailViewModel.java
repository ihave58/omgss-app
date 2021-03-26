package in.omgss.ui.detail;

import androidx.lifecycle.Observer;

import in.omgss.base.BaseViewModel;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.viewproduct.ViewproductResponse;
import okhttp3.RequestBody;

public class ProductDetailViewModel extends BaseViewModel {
    private final ProductDetailRepo mRepository = new ProductDetailRepo();

    private RichMediatorLiveData<ViewproductResponse> detailsLiveData;

    @Override
    public void initLiveData() {
        if (detailsLiveData == null) {
            detailsLiveData = new RichMediatorLiveData<ViewproductResponse>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return ProductDetailViewModel.this.getFailureObserver();
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return ProductDetailViewModel.this.getErrorObserver();
                }
            };
        }
    }

    public RichMediatorLiveData<ViewproductResponse> getDetailsLiveData() {
        return detailsLiveData;
    }

    public void getDetails(RequestBody request) {
        mRepository.getOrderDetail(detailsLiveData, request);
    }


}