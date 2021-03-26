package in.omgss.ui.notifications;

import androidx.lifecycle.Observer;

import in.omgss.base.BaseViewModel;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.CommonApiResponse;
import in.omgss.pojo.responses.notifications.NotificationsResponse;
import okhttp3.RequestBody;

public class NotificationsViewModel extends BaseViewModel {
    private final NotificationsRepo mRepository = new NotificationsRepo();

    private RichMediatorLiveData<NotificationsResponse> notificationsLiveData;
    private RichMediatorLiveData<CommonApiResponse> clearNotificationCountLiveData;

    @Override
    public void initLiveData() {
        if (notificationsLiveData == null) {
            notificationsLiveData = new RichMediatorLiveData<NotificationsResponse>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return NotificationsViewModel.this.getFailureObserver();
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return NotificationsViewModel.this.getErrorObserver();
                }
            };
        }
        if (clearNotificationCountLiveData == null) {
            clearNotificationCountLiveData = new RichMediatorLiveData<CommonApiResponse>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return NotificationsViewModel.this.getFailureObserver();
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return NotificationsViewModel.this.getErrorObserver();
                }
            };
        }
    }

    public RichMediatorLiveData<NotificationsResponse> getNotificationsLiveData() {
        return notificationsLiveData;
    }

    public RichMediatorLiveData<CommonApiResponse> getClearNotificationCountLiveData() {
        return clearNotificationCountLiveData;
    }

    public void getNotifications(RequestBody request) {
        mRepository.getNotifications(notificationsLiveData, request);
    }

    public void clearNotificationCount(RequestBody request) {
        mRepository.clearNotificationCount(clearNotificationCountLiveData, request);
    }


}