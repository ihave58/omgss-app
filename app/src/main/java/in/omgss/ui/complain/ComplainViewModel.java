package in.omgss.ui.complain;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import in.omgss.base.BaseViewModel;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.constants.AppConstants;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.CommonApiResponse;
import okhttp3.RequestBody;

public class ComplainViewModel extends BaseViewModel {


    private final ComplainRepo mRepository = new ComplainRepo();
    private MutableLiveData<Integer> validationliveData;

    private RichMediatorLiveData<CommonApiResponse> mResendOtpLiveData;

    @Override
    public void initLiveData() {
        if (validationliveData == null) {
            validationliveData = new MutableLiveData<>();
        }
        if (mResendOtpLiveData == null) {
            mResendOtpLiveData = new RichMediatorLiveData<CommonApiResponse>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return ComplainViewModel.this.getFailureObserver();
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return ComplainViewModel.this.getErrorObserver();
                }
            };
        }
    }

    public MutableLiveData<Integer> getValidationliveData() {
        return validationliveData;
    }

    public boolean isFormValid(String emailAddress, String password) {

        if (emailAddress.isEmpty()) {
            validationliveData.setValue(AppConstants.EMPTY_EMAIL);
            return false;
        } else if (password.isEmpty()) {
            validationliveData.setValue(AppConstants.EMPTY_PASSWORD);
            return false;
        } else return true;
    }

    public RichMediatorLiveData<CommonApiResponse> getmResendOtpLiveData() {
        return mResendOtpLiveData;
    }

    public void login(RequestBody request) {
        mRepository.complain(mResendOtpLiveData, request);
    }


}

