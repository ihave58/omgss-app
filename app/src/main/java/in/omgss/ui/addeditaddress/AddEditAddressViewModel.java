package in.omgss.ui.addeditaddress;

import androidx.lifecycle.Observer;

import in.omgss.base.BaseViewModel;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.CommonApiResponse;
import okhttp3.MultipartBody;

public class AddEditAddressViewModel extends BaseViewModel {
    private final AddEditAddressRepo mRepository = new AddEditAddressRepo();

    private RichMediatorLiveData<CommonApiResponse> addAddressLiveData;

    @Override
    public void initLiveData() {
        if (addAddressLiveData == null) {
            addAddressLiveData = new RichMediatorLiveData<CommonApiResponse>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return AddEditAddressViewModel.this.getFailureObserver();
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return AddEditAddressViewModel.this.getErrorObserver();
                }
            };
        }
    }

    public RichMediatorLiveData<CommonApiResponse> getAddAddressLiveData() {
        return addAddressLiveData;
    }


    public String getUserId() {
        return mRepository.getUserId();
    }

    public String getEmail() {
        return mRepository.getEmail();
    }

    public void addAddress(MultipartBody requestBody) {
        mRepository.addAddress(requestBody, addAddressLiveData);
    }

    public void editAddress(MultipartBody requestBody) {
        mRepository.editAddress(requestBody, addAddressLiveData);
    }
}
