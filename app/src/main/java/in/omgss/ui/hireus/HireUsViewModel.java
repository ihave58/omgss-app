package in.omgss.ui.hireus;

import androidx.lifecycle.Observer;

import in.omgss.base.BaseViewModel;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.CommonApiResponse;
import in.omgss.pojo.responses.categories.CategoriesResponse;
import okhttp3.RequestBody;

public class HireUsViewModel extends BaseViewModel {
    private HireUsRepo mRepository = new HireUsRepo();

    private RichMediatorLiveData<CommonApiResponse> hireUsLiveData;
    private RichMediatorLiveData<CategoriesResponse> categoriesLiveData;


    @Override
    public void initLiveData() {
        if (hireUsLiveData == null) {
            hireUsLiveData = new RichMediatorLiveData<CommonApiResponse>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return HireUsViewModel.this.getFailureObserver();
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return HireUsViewModel.this.getErrorObserver();
                }
            };
        }
        if (categoriesLiveData == null) {
            categoriesLiveData = new RichMediatorLiveData<CategoriesResponse>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return HireUsViewModel.this.getFailureObserver();
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return HireUsViewModel.this.getErrorObserver();
                }
            };
        }
    }

    public RichMediatorLiveData<CommonApiResponse> getHireUsLiveData() {
        return hireUsLiveData;
    }

    public RichMediatorLiveData<CategoriesResponse> getCategoriesLiveData() {
        return categoriesLiveData;
    }


    public void getCategories(RequestBody request) {
        mRepository.getCategories(categoriesLiveData, request);
    }

    public void hireUs(RequestBody request) {
        mRepository.hireUs(hireUsLiveData, request);
    }


}
