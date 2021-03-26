package in.omgss.ui.subcategories;

import androidx.lifecycle.Observer;

import in.omgss.base.BaseViewModel;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.subcategory.SubcategoryResponse;
import okhttp3.RequestBody;

public class CategoriesViewModel extends BaseViewModel {
    private final CategoriesRepo mRepository = new CategoriesRepo();

    private RichMediatorLiveData<SubcategoryResponse> mResendOtpLiveData;

    @Override
    public void initLiveData() {
        if (mResendOtpLiveData == null) {
            mResendOtpLiveData = new RichMediatorLiveData<SubcategoryResponse>() {
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

    public RichMediatorLiveData<SubcategoryResponse> getmResendOtpLiveData() {
        return mResendOtpLiveData;
    }

    public void getCategories(RequestBody request) {
        mRepository.getCategories(mResendOtpLiveData, request);
    }


}