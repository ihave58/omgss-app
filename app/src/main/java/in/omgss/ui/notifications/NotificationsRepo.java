package in.omgss.ui.notifications;

import in.omgss.base.NetworkCallback;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.data.DataManager;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.CommonApiResponse;
import in.omgss.pojo.responses.notifications.NotificationsResponse;
import okhttp3.RequestBody;

public class NotificationsRepo {

    public void getNotifications(final RichMediatorLiveData<NotificationsResponse> liveData, RequestBody request) {
        DataManager.getInstance().notifications(request).enqueue(new NetworkCallback<NotificationsResponse>() {
            @Override
            public void onSuccess(NotificationsResponse loginResponse) {
                if (loginResponse != null) {
                    liveData.setValue(loginResponse);
                }
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


    public void clearNotificationCount(final RichMediatorLiveData<CommonApiResponse> liveData, RequestBody request) {
        DataManager.getInstance().clearNotificationCount(request).enqueue(new NetworkCallback<CommonApiResponse>() {
            @Override
            public void onSuccess(CommonApiResponse loginResponse) {
                if (loginResponse != null) {
                    liveData.setValue(loginResponse);
                }
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

}
