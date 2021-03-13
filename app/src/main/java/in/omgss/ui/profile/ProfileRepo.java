package in.omgss.ui.profile;

import in.omgss.base.NetworkCallback;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.constants.PreferenceConstants;
import in.omgss.data.DataManager;
import in.omgss.data.api.ApiConstants;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.CommonApiResponse;
import in.omgss.pojo.responses.LoginResponse;
import in.omgss.pojo.responses.Result;
import okhttp3.MultipartBody;

public class ProfileRepo {

    public void updateProfile(MultipartBody requestBody, RichMediatorLiveData<CommonApiResponse> profileLiveData) {
        DataManager.getInstance().updateProfile(requestBody).enqueue(new NetworkCallback<CommonApiResponse>() {
            @Override
            public void onSuccess(CommonApiResponse responseBody) {
                profileLiveData.setValue(responseBody);
            }

            @Override
            public void onFailure(FailureResponse failureResponse) {
                profileLiveData.setFailure(failureResponse);
            }

            @Override
            public void onError(Throwable t) {
                profileLiveData.setError(null);
            }
        });
    }

    public void getProfileData(MultipartBody requestBody, RichMediatorLiveData<LoginResponse> profileLiveData) {
        DataManager.getInstance().profile(requestBody).enqueue(new NetworkCallback<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse responseBody) {
                if (responseBody != null && responseBody.getStatus().equalsIgnoreCase(ApiConstants.STATUS_SUCCESS)) {
                    saveProfileData(responseBody.getResult());
                }
                profileLiveData.setValue(responseBody);
            }

            @Override
            public void onFailure(FailureResponse failureResponse) {
                profileLiveData.setFailure(failureResponse);
            }

            @Override
            public void onError(Throwable t) {
                profileLiveData.setError(null);
            }
        });
    }

    private void saveProfileData(Result result) {
        DataManager.getInstance().saveStringInPreferences(PreferenceConstants.EMAIL, result.getEMail());
        DataManager.getInstance().saveStringInPreferences(PreferenceConstants.NUMBER, result.getPhone());
        DataManager.getInstance().saveStringInPreferences(PreferenceConstants.FIRST_NAME, result.getName().split(" ")[0]);
        DataManager.getInstance().saveStringInPreferences(PreferenceConstants.LAST_NAME, result.getName().split(" ")[1]);
    }


    public String getUserId() {
        return DataManager.getInstance().getStringFromPreference(PreferenceConstants.USER_ID);
    }
}
