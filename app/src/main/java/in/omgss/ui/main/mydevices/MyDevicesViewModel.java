package in.omgss.ui.main.mydevices;

import androidx.lifecycle.Observer;

import in.omgss.base.BaseViewModel;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.CommonApiResponse;
import in.omgss.pojo.responses.mydevices.MyDevicesResponse;
import okhttp3.MultipartBody;

public class MyDevicesViewModel extends BaseViewModel {
    private MyDevicesRepo mRepository = new MyDevicesRepo();

    private RichMediatorLiveData<MyDevicesResponse> myDevicesLiveData;
    private RichMediatorLiveData<CommonApiResponse> deviceComplaintLiveData;

    @Override
    public void initLiveData() {
        if (myDevicesLiveData == null) {
            myDevicesLiveData = new RichMediatorLiveData<MyDevicesResponse>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return MyDevicesViewModel.this.getFailureObserver();
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return MyDevicesViewModel.this.getErrorObserver();
                }
            };
        }
        if (deviceComplaintLiveData == null) {
            deviceComplaintLiveData = new RichMediatorLiveData<CommonApiResponse>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return MyDevicesViewModel.this.getFailureObserver();
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return MyDevicesViewModel.this.getErrorObserver();
                }
            };
        }
    }

    public RichMediatorLiveData<MyDevicesResponse> getMyDevicesLiveData() {
        return myDevicesLiveData;
    }

    public RichMediatorLiveData<CommonApiResponse> getDeviceComplaintLiveData() {
        return deviceComplaintLiveData;
    }

    public String getUserId() {
        return mRepository.getUserId();
    }

    public void getMyDevices(MultipartBody requestBody) {
        mRepository.getMyDevices(requestBody, myDevicesLiveData);
    }

    public void createDeviceComplaint(MultipartBody requestBody) {
        mRepository.createDeviceComplaint(requestBody, deviceComplaintLiveData);
    }

}
