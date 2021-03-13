package in.omgss.ui.main.categories;

import androidx.lifecycle.Observer;

import in.omgss.base.BaseViewModel;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.categories.CategoriesResponse;
import okhttp3.RequestBody;

public class CategoriesViewModel extends BaseViewModel {
    private CategoriesRepo mRepository = new CategoriesRepo();

    private RichMediatorLiveData<CategoriesResponse> mResendOtpLiveData;

    @Override
    public void initLiveData() {
        if (mResendOtpLiveData == null) {
            mResendOtpLiveData = new RichMediatorLiveData<CategoriesResponse>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return CategoriesViewModel.this.getFailureObserver();
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return CategoriesViewModel.this.getErrorObserver();
                }
            };
        }
    }

    public RichMediatorLiveData<CategoriesResponse> getmResendOtpLiveData() {
        return mResendOtpLiveData;
    }

    public void getCategories(RequestBody request) {
        mRepository.getCategories(mResendOtpLiveData, request);
    }


}
