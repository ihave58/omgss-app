package in.omgss.ui.changepassword;

import androidx.lifecycle.Observer;

import in.omgss.base.BaseViewModel;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.CommonApiResponse;
import okhttp3.MultipartBody;

public class ChangePasswordViewModel extends BaseViewModel {
    private final ChangePasswordRepo mRepository = new ChangePasswordRepo();

    private RichMediatorLiveData<CommonApiResponse> changePasswordLiveData;

    @Override
    public void initLiveData() {
        if (changePasswordLiveData == null) {
            changePasswordLiveData = new RichMediatorLiveData<CommonApiResponse>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return ChangePasswordViewModel.this.getFailureObserver();
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return ChangePasswordViewModel.this.getErrorObserver();
                }
            };
        }
    }

    public RichMediatorLiveData<CommonApiResponse> getChangePasswordLiveData() {
        return changePasswordLiveData;
    }


    public String getUserId() {
        return mRepository.getUserId();
    }

    public void changePassword(MultipartBody requestBody) {
        mRepository.changePassword(requestBody, changePasswordLiveData);
    }
}
