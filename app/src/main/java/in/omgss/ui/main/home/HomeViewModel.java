package in.omgss.ui.main.home;

import androidx.lifecycle.Observer;

import in.omgss.base.BaseViewModel;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.HomeSliderResponse;
import in.omgss.pojo.responses.categories.CategoriesResponse;
import okhttp3.RequestBody;

public class HomeViewModel extends BaseViewModel {
    private final HomeRepo mRepository = new HomeRepo();

    private RichMediatorLiveData<HomeSliderResponse> homeSliderLiveData;
    private RichMediatorLiveData<CategoriesResponse> categoryLiveData;


    @Override
    public void initLiveData() {
        if (homeSliderLiveData == null) {
            homeSliderLiveData = new RichMediatorLiveData<HomeSliderResponse>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return HomeViewModel.this.getFailureObserver();
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return HomeViewModel.this.getErrorObserver();
                }
            };
        }
        if (categoryLiveData == null) {
            categoryLiveData = new RichMediatorLiveData<CategoriesResponse>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return HomeViewModel.this.getFailureObserver();
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return HomeViewModel.this.getErrorObserver();
                }
            };
        }
    }

    public String getUserId() {
        return mRepository.getUserId();
    }


    public RichMediatorLiveData<HomeSliderResponse> getHomeSliderLiveData() {
        return homeSliderLiveData;
    }

    public RichMediatorLiveData<CategoriesResponse> getCategoryLiveData() {
        return categoryLiveData;
    }


    public void getHomeSliders(RequestBody request) {
        mRepository.homeSlider(homeSliderLiveData, request);
    }

    public void category(RequestBody request) {
        mRepository.getCategories(categoryLiveData, request);
    }


}
