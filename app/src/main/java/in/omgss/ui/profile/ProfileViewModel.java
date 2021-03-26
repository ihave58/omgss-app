package in.omgss.ui.profile;

import androidx.lifecycle.Observer;

import in.omgss.base.BaseViewModel;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.CommonApiResponse;
import in.omgss.pojo.responses.LoginResponse;
import okhttp3.MultipartBody;

public class ProfileViewModel extends BaseViewModel {
    private final ProfileRepo mRepository = new ProfileRepo();

    private RichMediatorLiveData<LoginResponse> profileLiveData;
    private RichMediatorLiveData<CommonApiResponse> updateProfileLiveData;

    @Override
    public void initLiveData() {
        if (profileLiveData == null) {
            profileLiveData = new RichMediatorLiveData<LoginResponse>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return ProfileViewModel.this.getFailureObserver();
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return ProfileViewModel.this.getErrorObserver();
                }
            };
        }
        if (updateProfileLiveData == null) {
            updateProfileLiveData = new RichMediatorLiveData<CommonApiResponse>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return ProfileViewModel.this.getFailureObserver();
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return ProfileViewModel.this.getErrorObserver();
                }
            };
        }
    }

    public RichMediatorLiveData<LoginResponse> getProfileLiveData() {
        return profileLiveData;
    }

    public RichMediatorLiveData<CommonApiResponse> getUpdateProfileLiveData() {
        return updateProfileLiveData;
    }


    public void getProfileData(MultipartBody requestBody) {
        mRepository.getProfileData(requestBody, profileLiveData);
    }

    public void updateProfile(MultipartBody requestBody) {
        mRepository.updateProfile(requestBody, updateProfileLiveData);
    }

    public String getUserId() {
        return mRepository.getUserId();
    }
}
