package in.omgss.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import java.util.Objects;

import in.omgss.pojo.FailureResponse;
import okhttp3.MultipartBody;


public abstract class BaseFragment extends Fragment {

    private Observer<Throwable> errorObserver;
    private Observer<FailureResponse> failureResponseObserver;
    private Observer<Boolean> loadingStateObserver;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initObservers();
        initVariables();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hideProgressDialog();
        setListeners();
    }


    private void initObservers() {
        errorObserver = new Observer<Throwable>() {
            @Override
            public void onChanged(@Nullable Throwable throwable) {
                hideProgressDialog();
                onErrorOccurred(throwable);
            }
        };

        failureResponseObserver = new Observer<FailureResponse>() {
            @Override
            public void onChanged(@Nullable FailureResponse failureResponse) {
                hideProgressDialog();
                onFailure(failureResponse);
            }
        };

        loadingStateObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean != null)
                    onLoadingStateChanged(aBoolean);
            }
        };
    }


    public abstract void initVariables();

    public abstract void setListeners();


    public Observer<Throwable> getErrorResponseObserver() {
        return errorObserver;
    }


    public Observer<FailureResponse> getFailureResponseObserver() {
        return failureResponseObserver;
    }


    public Observer<Boolean> getLoadingStateObserver() {
        return loadingStateObserver;
    }


    protected void onFailure(FailureResponse failureResponse) {
        if (failureResponse != null) {
            showSnackbarShort(failureResponse.getErrorMessage());
        }
    }


    protected void onErrorOccurred(Throwable throwable) {
        showSnackbarShort(throwable.getMessage());
    }


    protected void onLoadingStateChanged(boolean aBoolean) {

    }


    public void addFragment(int layoutResId, BaseFragment fragment, String tag, boolean addToBackStack) {
        if (isFragmentAdded())
            ((BaseActivity) Objects.requireNonNull(getActivity())).addFragment(layoutResId, fragment, tag, addToBackStack);

    }


    public void replaceFragment(int layoutResId, BaseFragment fragment, String tag, boolean addToBackStack) {
        if (isFragmentAdded())
            ((BaseActivity) Objects.requireNonNull(getActivity())).replaceFragment(layoutResId, fragment, tag, addToBackStack);

    }


    public void addFragmentInsideFragment(int layoutResId, BaseFragment fragment, String tag, boolean addToBackStack) {
        if (isFragmentAdded()) {
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.add(layoutResId, fragment, tag);
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(tag);
            }
            fragmentTransaction.commitAllowingStateLoss();
        }

    }


    public void replaceFragmentInsideFragment(int layoutResId, BaseFragment fragment, String tag, boolean addToBackStack) {
        if (isFragmentAdded()) {
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(layoutResId, fragment, tag);
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(tag);
            }
            fragmentTransaction.commitAllowingStateLoss();
        }

    }


    public boolean isFragmentAdded() {
        return (getActivity() != null && !getActivity().isFinishing() && isAdded());
    }


    public void showSnackbarLong(String message) {
        if (isFragmentAdded())
            ((BaseActivity) Objects.requireNonNull(getActivity())).showSnackbarLong(message);
    }


    public void showSnackbarShort(String message) {
        if (isFragmentAdded())
            ((BaseActivity) Objects.requireNonNull(getActivity())).showSnackbarShort(message);
    }

    public void showToast(String message) {
        if (isFragmentAdded())
            ((BaseActivity) Objects.requireNonNull(getActivity())).showToast(message);
    }


    public void showProgressDialog() {
        if (isFragmentAdded())
            ((BaseActivity) Objects.requireNonNull(getActivity())).showProgressDialog();
    }

    public void hideProgressDialog() {
        if (isFragmentAdded())
            ((BaseActivity) Objects.requireNonNull(getActivity())).hideProgressDialog();
    }

    public void hideKeyboard() {
        if (isFragmentAdded())
            ((BaseActivity) Objects.requireNonNull(getActivity())).hideKeyboard();
    }

    public void showNoNetworkError() {
        if (isFragmentAdded())
            ((BaseActivity) Objects.requireNonNull(getActivity())).showNoNetworkError();
    }

    public boolean isNetworkAvailable() {
        if (isFragmentAdded())
            return ((BaseActivity) Objects.requireNonNull(getActivity())).isNetworkAvailable();
        else
            return false;
    }

    public boolean isLocationEnabled() {
        if (isFragmentAdded())
            return ((BaseActivity) Objects.requireNonNull(getActivity())).isLocationEnabled();
        else
            return false;
    }


    public MultipartBody.Builder getMultipartRequestBuilder() {
        if (getActivity() instanceof BaseActivity) {
            return ((BaseActivity) getActivity()).getMultipartRequestBuilder();

        } else {
            return null;
        }
    }


    public String getUserId() {
        if (getActivity() instanceof BaseActivity) {
            return ((BaseActivity) getActivity()).getUserId();

        } else {
            return "null";
        }
    }


    public void logout() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).logout();
        }
    }
}
