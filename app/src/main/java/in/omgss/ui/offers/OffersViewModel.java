package in.omgss.ui.offers;

import androidx.lifecycle.Observer;

import in.omgss.base.BaseViewModel;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.offers.OffersResponse;
import okhttp3.RequestBody;

public class OffersViewModel extends BaseViewModel {


    private final OffersRepo mRepository = new OffersRepo();

    private RichMediatorLiveData<OffersResponse> mResendOtpLiveData;

    @Override
    public void initLiveData() {
        if (mResendOtpLiveData == null) {
            mResendOtpLiveData = new RichMediatorLiveData<OffersResponse>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return OffersViewModel.this.getFailureObserver();
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return OffersViewModel.this.getErrorObserver();
                }
            };
        }
    }

    public RichMediatorLiveData<OffersResponse> getmResendOtpLiveData() {
        return mResendOtpLiveData;
    }

    public void login(RequestBody request) {
        mRepository.getOffers(mResendOtpLiveData, request);
    }


}
