package in.omgss.ui.wishlist;

import in.omgss.base.NetworkCallback;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.constants.PreferenceConstants;
import in.omgss.data.DataManager;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.wishlist.WishlistResponse;
import okhttp3.RequestBody;

public class WishlistRepo {

    public void getWishList(final RichMediatorLiveData<WishlistResponse> liveData, RequestBody request) {
        DataManager.getInstance().wishlist(request).enqueue(new NetworkCallback<WishlistResponse>() {
            @Override
            public void onSuccess(WishlistResponse loginResponse) {
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

    public String getUserId() {
        return DataManager.getInstance().getStringFromPreference(PreferenceConstants.USER_ID);
    }

}
