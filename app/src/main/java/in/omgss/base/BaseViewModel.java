package in.omgss.base;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import in.omgss.pojo.FailureResponse;

/**
 * Created by Navjot Singh
 * on 27/12/18.
 */

public abstract class BaseViewModel extends ViewModel {

    private Observer<FailureResponse> mFailureObserver;
    private Observer<Throwable> mErrorObserver;

    public void setGenericObservers(Observer<Throwable> errorObserver, Observer<FailureResponse> failureObserver) {
        mErrorObserver = errorObserver;
        mFailureObserver = failureObserver;

        initLiveData();
    }

    /**
     * Method to initialize live data objects
     */
    public abstract void initLiveData();

    public Observer<FailureResponse> getFailureObserver() {
        return mFailureObserver;
    }

    public Observer<Throwable> getErrorObserver() {
        return mErrorObserver;
    }

}
