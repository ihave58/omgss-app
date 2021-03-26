package in.omgss.ui.orders;

import androidx.lifecycle.Observer;

import in.omgss.base.BaseViewModel;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.myorders.OrdersListResponse;
import okhttp3.MultipartBody;

public class OrdersViewModel extends BaseViewModel {
    private final OrdersRepo mRepository = new OrdersRepo();

    private RichMediatorLiveData<OrdersListResponse> ordersLiveData;

    @Override
    public void initLiveData() {
        if (ordersLiveData == null) {
            ordersLiveData = new RichMediatorLiveData<OrdersListResponse>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return OrdersViewModel.this.getFailureObserver();
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return OrdersViewModel.this.getErrorObserver();
                }
            };
        }
    }

    public RichMediatorLiveData<OrdersListResponse> getOrdersLiveData() {
        return ordersLiveData;
    }


    public String getUserId() {
        return mRepository.getUserId();
    }

    public void getOrdersList(MultipartBody requestBody) {
        mRepository.getOrdersList(requestBody, ordersLiveData);
    }
}
