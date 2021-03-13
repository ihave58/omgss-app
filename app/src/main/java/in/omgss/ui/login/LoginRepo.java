package in.omgss.ui.login;

import in.omgss.base.NetworkCallback;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.constants.PreferenceConstants;
import in.omgss.data.DataManager;
import in.omgss.data.api.ApiConstants;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.LoginResponse;
import okhttp3.RequestBody;

public class LoginRepo {

    public void login(final RichMediatorLiveData<LoginResponse> liveData, RequestBody request) {
        DataManager.getInstance().login(request).enqueue(new NetworkCallback<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse loginResponse) {
                if (loginResponse != null && loginResponse.getStatus().equalsIgnoreCase(ApiConstants.STATUS_SUCCESS)) {
                    saveDataInPreferences(loginResponse);
                }
                liveData.setValue(loginResponse);
            }

            @Override
            public void onFailure(FailureResponse failureResponse) {
                liveData.setFailure(failureResponse);
            }

            @Override
            public void onError(Throwable t) {
                liveData.setError(t);
            }
        });

    }

    private void saveDataInPreferences(LoginResponse loginResponse) {
        DataManager.getInstance().saveBooleaInPreferences(PreferenceConstants.IS_LOGGED_IN, true);

        DataManager.getInstance().saveStringInPreferences(PreferenceConstants.USER_ID, loginResponse.getResult().getId());
        DataManager.getInstance().saveStringInPreferences(PreferenceConstants.FIRST_NAME, loginResponse.getResult().getName().split(" ")[0]);
        DataManager.getInstance().saveStringInPreferences(PreferenceConstants.LAST_NAME, loginResponse.getResult().getName().split(" ")[1]);
        DataManager.getInstance().saveStringInPreferences(PreferenceConstants.EMAIL, loginResponse.getResult().getEMail());
        DataManager.getInstance().saveStringInPreferences(PreferenceConstants.NUMBER, loginResponse.getResult().getPhone());
        DataManager.getInstance().saveStringInPreferences(PreferenceConstants.DEVICE_ACCESS_TOKEN, loginResponse.getResult().getAccessToken());
    }
}
