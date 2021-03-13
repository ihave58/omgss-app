package in.omgss.ui.signup;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import in.omgss.base.BaseViewModel;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.constants.AppConstants;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.SignUpResponse;
import okhttp3.RequestBody;

public class SignupViewModel extends BaseViewModel {
    private SignUpRepo mRepository = new SignUpRepo();
    private MutableLiveData<Integer> validationliveData;
    private RichMediatorLiveData<SignUpResponse> registrationLiveData;

    @Override
    public void initLiveData() {
        if (validationliveData == null) {
            validationliveData = new MutableLiveData<>();
        }
        if (registrationLiveData == null) {
            registrationLiveData = new RichMediatorLiveData<SignUpResponse>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return SignupViewModel.this.getFailureObserver();
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return SignupViewModel.this.getErrorObserver();
                }
            };
        }
    }

    public MutableLiveData<Integer> getValidationliveData() {
        return validationliveData;
    }

    public boolean isFormValid(String firstName, String lastname, String mobileNumber, String emailAddress, String password) {
        if (firstName.isEmpty()) {
            validationliveData.setValue(AppConstants.EMPTY_FIRSTNAME);
            return false;
        } else if (lastname.isEmpty()) {
            validationliveData.setValue(AppConstants.EMPTY_LAStNAME);
            return false;
        }
        if (mobileNumber.isEmpty()) {
            validationliveData.setValue(AppConstants.EMPTY_MOBILE_NUMBER);
            return false;
        } else if (mobileNumber.length() < 7) {
            validationliveData.setValue(AppConstants.INVALID_MOBILE_NUMBER);
            return false;
        } else if (emailAddress.isEmpty()) {
            validationliveData.setValue(AppConstants.EMPTY_EMAIL);
            return false;
        } else if (password.isEmpty()) {
            validationliveData.setValue(AppConstants.EMPTY_PASSWORD);
            return false;
        } else return true;
    }

    public RichMediatorLiveData<SignUpResponse> getRegistrationLiveData() {
        return registrationLiveData;
    }


    public void register(RequestBody request, String firstName, String lastname, String mobileNumber, String emailAddress, String password) {
        if (isFormValid(firstName, lastname, mobileNumber, emailAddress, password))
            mRepository.register(registrationLiveData, request);
    }
}