package in.omgss.base;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.snackbar.Snackbar;

import butterknife.ButterKnife;
import in.omgss.BuildConfig;
import in.omgss.R;
import in.omgss.constants.AppConstants;
import in.omgss.constants.PreferenceConstants;
import in.omgss.data.DataManager;
import in.omgss.data.api.ApiConstants;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.CommonApiResponse;
import in.omgss.pojo.responses.cartcountresponse.BatchCountResponse;
import in.omgss.services.LocationUpdateService;
import in.omgss.ui.login.LoginActivity;
import in.omgss.utils.AppUtils;
import okhttp3.MultipartBody;


public abstract class BaseActivity extends AppCompatActivity {

    private Observer<Throwable> errorObserver;
    private Observer<FailureResponse> failureResponseObserver;
    private Observer<Boolean> loadingStateObserver;

    private FrameLayout flBaseContainer;
    private Dialog mProgressDialog;
    private boolean mShouldExitApp;


    /**
     * @return true if all permissions are granted else false
     */
    public boolean areAllPermissionsGranted(int[] grantResults) {
        boolean areAllPermissionsGranted = true;
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                areAllPermissionsGranted = false;
                break;
            }
        }
        return areAllPermissionsGranted;
    }

    /**
     * @return true if location permission is granted else false
     */
    public boolean isLocationPermissionGranted() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * method to navigate to permission settings of the app
     */
    public void openPermissionSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", getPackageName(), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * method to navigate to location settings of the device
     */
    public void openLocationSettings() {
        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), AppConstants.LOCATION_SERVICES);
    }

    /**
     * method to start location services
     */
    public void startLocationService() {
        startService(new Intent(this, LocationUpdateService.class));
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        flBaseContainer = findViewById(R.id.fl_container_base);
        setLayout();

        ButterKnife.bind(this);

        initObservers();

    }


    @Override
    public void onBackPressed() {
        //if current activity is root of task, then showing toast
        if (isTaskRoot()) {
            if (!mShouldExitApp) {
                showSnackbarShort(getString(R.string.press_back_again_to_exit));
                mShouldExitApp = true;
            } else {
                super.onBackPressed();
            }
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    mShouldExitApp = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
        }

    }


    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Method is used by the sub class for passing the id of the layout ot be inflated in the relative layout
     *
     * @return id of the resource to be inflated
     */
    protected abstract int getResourceId();


    /**
     * Method is used to set the layout in the Base Activity.
     */
    private void setLayout() {
        if (getResourceId() != -1) {
            removeLayout();
            getLayoutInflater().inflate(getResourceId(), flBaseContainer, true);
        }
    }


    /**
     * This method is used to remove the view already present as a child in base container.
     */
    private void removeLayout() {
        if (flBaseContainer != null && flBaseContainer.getChildCount() >= 1)
            flBaseContainer.removeAllViews();
    }


    public void setStatusBarColor(int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && getWindow() != null) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, colorId));
        }
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

        /*
         * experimental
         */
        loadingStateObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                onLoadingStateChanged(aBoolean);
            }
        };
    }

    public Observer<Throwable> getErrorResponseObserver() {
        return errorObserver;
    }

    public Observer<FailureResponse> getFailureResponseObserver() {
        return failureResponseObserver;
    }

    public Observer<Boolean> getLoadingStateObserver() {
        return loadingStateObserver;
    }


    protected void onLoadingStateChanged(boolean aBoolean) {

    }

    protected void onFailure(FailureResponse failureResponse) {
        if (failureResponse != null)
            showSnackbarShort(failureResponse.getErrorMessage());
    }

    protected void onErrorOccurred(Throwable throwable) {
        if (throwable != null) {
            showSnackbarShort(throwable.getMessage());
            Log.e("onErrorOccurred: ", throwable.getMessage());
        }
    }


    /**
     * hides keyboard onClick anywhere besides edit text
     *
     * @param motionEvent MotionEvent
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        View view = getCurrentFocus();
        if (view != null && (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int[] scrCoOrds = new int[2];
            view.getLocationOnScreen(scrCoOrds);
            float x = motionEvent.getRawX() + view.getLeft() - scrCoOrds[0];
            float y = motionEvent.getRawY() + view.getTop() - scrCoOrds[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom()) {
                Object service = getSystemService(Context.INPUT_METHOD_SERVICE);
                if (service != null)
                    ((InputMethodManager) service).hideSoftInputFromWindow((getWindow().getDecorView().getApplicationWindowToken()), 0);
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }


    public void makeActivityFullscreen() {
        if (!isFinishing()) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        }
    }

    public void makeActivityNonFullscreen() {
        if (!isFinishing()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                );
            }
        }
    }


    /**
     * Method used to get unique device id
     */
    public String getDeviceId() {
        return AppUtils.getUniqueDeviceId(this);
    }


    public void addFragment(int layoutResId, BaseFragment fragment, String tag, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(layoutResId, fragment, tag);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(tag);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }


    public void replaceFragment(int layoutResId, BaseFragment fragment, String tag, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(layoutResId, fragment, tag);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(tag);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }


    public void showSnackbarLong(CharSequence message) {
        Snackbar snack = Snackbar.make(flBaseContainer, message, Snackbar.LENGTH_LONG);
        snack.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
        TextView textView = snack.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.colorBlack));
        snack.show();
    }

    public void showSnackbarShort(CharSequence message) {
        Snackbar snack = Snackbar.make(flBaseContainer, message, Snackbar.LENGTH_SHORT);
        snack.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
        TextView textView = snack.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.colorBlack));
        snack.show();
    }


    public void showProgressDialog() {
        if (!isFinishing() && !isDestroyed()) {
            hideProgressDialog();
            mProgressDialog = new Dialog(this);
            mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            View view = LayoutInflater.from(this).inflate(R.layout.include_layout_progress_bar, null);
            mProgressDialog.setContentView(view);

            view.setVisibility(View.VISIBLE);
            mProgressDialog.setCancelable(false);

            if (mProgressDialog.getWindow() != null) {
                mProgressDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(this, android.R.color.transparent));
                mProgressDialog.getWindow().setDimAmount(0);
                mProgressDialog.getWindow().setGravity(Gravity.CENTER);
            }
            mProgressDialog.show();
        }
    }

    public void hideProgressDialog() {
        if (!isFinishing() && mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }


    public void showUnknownRetrofitError() {
        hideProgressDialog();
        showSnackbarLong("Something went wrong");
    }


    public void showNoNetworkError() {
        showSnackbarLong("No internet");
    }


    public boolean isNetworkAvailable() {
        return AppUtils.isNetworkAvailable(this);
    }


    /**
     * method to check if location service is enabled or not
     */
    public boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return (locationManager != null && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
    }


    public void hideKeyboard() {
        AppUtils.hideKeyboard(this);
    }


    public void popFragment() {
        if (getSupportFragmentManager() != null) {
            getSupportFragmentManager().popBackStack();
        }
    }


    public MultipartBody.Builder getMultipartRequestBuilder() {
        return new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart(ApiConstants.APP_ID, BuildConfig.APP_ID)
                .addFormDataPart(ApiConstants.TOKEN, DataManager.getInstance().getDeviceToken());
    }


    public void logout() {
        if (!isFinishing()) {
            DataManager dataManager = DataManager.getInstance();
            if (dataManager != null) {
                dataManager.clearPreferences();
            }
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }


    public String getUserId() {
        return DataManager.getInstance().getUserId();
    }


    public void addToWishList(String productId, boolean shouldRefreshHomeData) {
        if (isNetworkAvailable()) {

            MultipartBody body = getMultipartRequestBuilder()
                    .addFormDataPart("userid", getUserId())
                    .addFormDataPart("prdid", productId)
                    .build();

            DataManager.getInstance().addToWishlist(body).enqueue(new NetworkCallback<CommonApiResponse>() {
                @Override
                public void onSuccess(CommonApiResponse commonApiResponse) {
                    hideProgressDialog();
                    if (shouldRefreshHomeData) {
                        refreshHomeData();
                    }
                }

                @Override
                public void onFailure(FailureResponse failureResponse) {
                    hideProgressDialog();
                }

                @Override
                public void onError(Throwable t) {
                    hideProgressDialog();
                }
            });
        }
    }

    public void removeFromWishlist(String wishListId, String productId, boolean shouldRefreshHomeData) {
        if (isNetworkAvailable()) {

            MultipartBody body = getMultipartRequestBuilder()
                    .addFormDataPart("userid", getUserId())
                    .addFormDataPart("wishid", wishListId)
                    .addFormDataPart("prdid", productId)
                    .build();

            DataManager.getInstance().removeFromWishlist(body).enqueue(new NetworkCallback<CommonApiResponse>() {
                @Override
                public void onSuccess(CommonApiResponse commonApiResponse) {
                    hideProgressDialog();
                    if (shouldRefreshHomeData) {
                        refreshHomeData();
                    }
                }

                @Override
                public void onFailure(FailureResponse failureResponse) {
                    hideProgressDialog();
                }

                @Override
                public void onError(Throwable t) {
                    hideProgressDialog();
                }
            });
        }
    }


    public void addToCart(String productId, int quantity, boolean shouldRefreshHomeData) {
        if (isNetworkAvailable()) {
            showProgressDialog();

            MultipartBody body = getMultipartRequestBuilder()
                    .addFormDataPart("userid", getUserId())
                    .addFormDataPart("prdid", productId)
                    .addFormDataPart("quantity", String.valueOf(quantity))
                    .build();

            DataManager.getInstance().addtocart(body).enqueue(new NetworkCallback<CommonApiResponse>() {
                @Override
                public void onSuccess(CommonApiResponse commonApiResponse) {
                    hideProgressDialog();
                    showToast(commonApiResponse.getMessage());

                    int cartCount = DataManager.getInstance().getIntFromPreference(PreferenceConstants.CART_COUNT);
                    DataManager.getInstance().saveIntInPreferences(PreferenceConstants.CART_COUNT, cartCount + 1);
                    updateBatchCount();

                    if (shouldRefreshHomeData) {
                        refreshHomeData();
                    }
                }

                @Override
                public void onFailure(FailureResponse failureResponse) {
                    hideProgressDialog();
                }

                @Override
                public void onError(Throwable t) {
                    hideProgressDialog();
                }
            });
        }
    }

    public void removeFromCart(String cartId, String productId, boolean shouldRefreshHomeData) {
        if (isNetworkAvailable()) {
            showProgressDialog();

            MultipartBody body = getMultipartRequestBuilder()
                    .addFormDataPart("userid", getUserId())
                    .addFormDataPart("cartid", cartId)
                    .addFormDataPart("prdid", productId)
                    .build();

            DataManager.getInstance().removeFromCart(body).enqueue(new NetworkCallback<CommonApiResponse>() {
                @Override
                public void onSuccess(CommonApiResponse commonApiResponse) {
                    hideProgressDialog();

                    int cartCount = DataManager.getInstance().getIntFromPreference(PreferenceConstants.CART_COUNT);
                    if (cartCount > 0)
                        DataManager.getInstance().saveIntInPreferences(PreferenceConstants.CART_COUNT, cartCount - 1);
                    updateBatchCount();

                    if (shouldRefreshHomeData) {
                        refreshHomeData();
                    }
                }

                @Override
                public void onFailure(FailureResponse failureResponse) {
                    hideProgressDialog();
                }

                @Override
                public void onError(Throwable t) {
                    hideProgressDialog();
                }
            });
        }
    }


    public void getCartCount() {
        if (isNetworkAvailable()) {

            MultipartBody body = getMultipartRequestBuilder()
                    .addFormDataPart("userid", getUserId())
                    .build();

            DataManager.getInstance().cartCount(body).enqueue(new NetworkCallback<BatchCountResponse>() {
                @Override
                public void onSuccess(BatchCountResponse response) {
                    hideProgressDialog();
                    DataManager.getInstance().saveIntInPreferences(PreferenceConstants.CART_COUNT, response.getCountofcart());
                    updateBatchCount();
                }

                @Override
                public void onFailure(FailureResponse failureResponse) {
                    hideProgressDialog();
                }

                @Override
                public void onError(Throwable t) {
                    hideProgressDialog();
                }
            });
        }
    }


    public void getNotificationCount() {
        if (isNetworkAvailable()) {

            MultipartBody body = getMultipartRequestBuilder()
                    .addFormDataPart("userid", getUserId())
                    .build();

            DataManager.getInstance().notificationCount(body).enqueue(new NetworkCallback<BatchCountResponse>() {
                @Override
                public void onSuccess(BatchCountResponse response) {
                    hideProgressDialog();
                    DataManager.getInstance().saveIntInPreferences(PreferenceConstants.NOTIFICATION_COUNT, response.getCountofnotification());
                    updateBatchCount();
                }

                @Override
                public void onFailure(FailureResponse failureResponse) {
                    hideProgressDialog();
                }

                @Override
                public void onError(Throwable t) {
                    hideProgressDialog();
                }
            });
        }
    }


    public void refreshHomeData() {
        Intent intent = new Intent(AppConstants.FILTER_REFRESH_HOME);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public void updateBatchCount() {
        Intent intent = new Intent(AppConstants.FILTER_UPDATE_BATCH_COUNT);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}
