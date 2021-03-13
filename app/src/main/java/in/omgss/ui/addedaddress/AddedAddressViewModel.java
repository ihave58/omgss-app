package in.omgss.ui.addedaddress;

import androidx.lifecycle.Observer;

import in.omgss.base.BaseViewModel;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.CommonApiResponse;
import in.omgss.pojo.responses.addresslist.AddressListResponse;
import okhttp3.MultipartBody;

public class AddedAddressViewModel extends BaseViewModel {
    private AddedAddressRepo mRepository = new AddedAddressRepo();

    private RichMediatorLiveData<AddressListResponse> addressListLiveData;
    private RichMediatorLiveData<CommonApiResponse> deleteAddressLiveData;

    @Override
    public void initLiveData() {
        if (addressListLiveData == null) {
            addressListLiveData = new RichMediatorLiveData<AddressListResponse>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return AddedAddressViewModel.this.getFailureObserver();
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return AddedAddressViewModel.this.getErrorObserver();
                }
            };
        }
        if (deleteAddressLiveData == null) {
            deleteAddressLiveData = new RichMediatorLiveData<CommonApiResponse>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return AddedAddressViewModel.this.getFailureObserver();
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return AddedAddressViewModel.this.getErrorObserver();
                }
            };
        }
    }

    public RichMediatorLiveData<AddressListResponse> getAddressListLiveData() {
        return addressListLiveData;
    }

    public RichMediatorLiveData<CommonApiResponse> getDeleteAddressLiveData() {
        return deleteAddressLiveData;
    }


    public String getUserId() {
        return mRepository.getUserId();
    }

    public String getEmail() {
        return mRepository.getEmail();
    }


    public void getAddressList(MultipartBody requestBody) {
        mRepository.getAddedAddresses(requestBody, addressListLiveData);
    }

    public void deleteAddress(MultipartBody requestBody) {
        mRepository.deleteAddress(requestBody, deleteAddressLiveData);
    }
}
