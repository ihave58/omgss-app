package in.omgss.ui.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import in.omgss.base.BaseViewModel;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.constants.AppConstants;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.LoginResponse;
import okhttp3.RequestBody;

public class LoginViewModel extends BaseViewModel {
    private LoginRepo mRepository = new LoginRepo();

    private MutableLiveData<Integer> validationliveData;
    private RichMediatorLiveData<LoginResponse> loginLiveData;

    @Override
    public void initLiveData() {
        if (validationliveData == null) {
            validationliveData = new MutableLiveData<>();
        }

        if (loginLiveData == null) {
            loginLiveData = new RichMediatorLiveData<LoginResponse>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return LoginViewModel.this.getFailureObserver();
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return LoginViewModel.this.getErrorObserver();
                }
            };
        }
    }

    public MutableLiveData<Integer> getValidationliveData() {
        return validationliveData;
    }

    public RichMediatorLiveData<LoginResponse> getLoginLiveData() {
        return loginLiveData;
    }


    public void login(RequestBody request, String email, String password) {
        if (isFormValid(email, password))
            mRepository.login(loginLiveData, request);
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


}