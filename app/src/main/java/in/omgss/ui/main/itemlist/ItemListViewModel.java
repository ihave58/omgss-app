package in.omgss.ui.main.itemlist;

import androidx.lifecycle.Observer;

import in.omgss.base.BaseViewModel;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.products.ProductsResponse;
import okhttp3.RequestBody;

public class ItemListViewModel extends BaseViewModel {
    private ItemListRepo mRepository = new ItemListRepo();

    private RichMediatorLiveData<ProductsResponse> mResendOtpLiveData;

    @Override
    public void initLiveData() {
        if (mResendOtpLiveData == null) {
            mResendOtpLiveData = new RichMediatorLiveData<ProductsResponse>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return ItemListViewModel.this.getFailureObserver();
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return ItemListViewModel.this.getErrorObserver();
                }
            };
        }
    }

    public RichMediatorLiveData<ProductsResponse> getmResendOtpLiveData() {
        return mResendOtpLiveData;
    }

    public void getProductsList(RequestBody request) {
        mRepository.getProducts(mResendOtpLiveData, request);
    }


}
