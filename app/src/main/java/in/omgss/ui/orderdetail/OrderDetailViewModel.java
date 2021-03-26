package in.omgss.ui.orderdetail;

import androidx.lifecycle.Observer;

import in.omgss.base.BaseViewModel;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.CommonApiResponse;
import okhttp3.MultipartBody;

public class OrderDetailViewModel extends BaseViewModel {
    private final OrderDetailRepo mRepository = new OrderDetailRepo();

    private RichMediatorLiveData<CommonApiResponse> cancelOrderLiveData;

    @Override
    public void initLiveData() {
        if (cancelOrderLiveData == null) {
            cancelOrderLiveData = new RichMediatorLiveData<CommonApiResponse>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return OrderDetailViewModel.this.getFailureObserver();
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return OrderDetailViewModel.this.getErrorObserver();
                }
            };
        }
    }

    public RichMediatorLiveData<CommonApiResponse> getCancelOrderLiveData() {
        return cancelOrderLiveData;
    }

    public void cancelOrder(MultipartBody body) {
        mRepository.cancelOrder(cancelOrderLiveData, body);
    }

}
