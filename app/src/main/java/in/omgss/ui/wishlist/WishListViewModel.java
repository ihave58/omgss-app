package in.omgss.ui.wishlist;

import androidx.lifecycle.Observer;

import in.omgss.base.BaseViewModel;
import in.omgss.base.RichMediatorLiveData;
import in.omgss.pojo.FailureResponse;
import in.omgss.pojo.responses.wishlist.WishlistResponse;
import okhttp3.MultipartBody;


public class WishListViewModel extends BaseViewModel {
    private WishlistRepo mRepository = new WishlistRepo();

    private RichMediatorLiveData<WishlistResponse> wishlistLiveData;


    @Override
    public void initLiveData() {
        if (wishlistLiveData == null) {
            wishlistLiveData = new RichMediatorLiveData<WishlistResponse>() {
                @Override
                protected Observer<FailureResponse> getFailureObserver() {
                    return WishListViewModel.this.getFailureObserver();
                }

                @Override
                protected Observer<Throwable> getErrorObserver() {
                    return WishListViewModel.this.getErrorObserver();
                }
            };
        }
    }

    public RichMediatorLiveData<WishlistResponse> getWishlistLiveData() {
        return wishlistLiveData;
    }


    public String getUserId() {
        return mRepository.getUserId();
    }


    public void getWishlist(MultipartBody body) {
        mRepository.getWishList(wishlistLiveData, body);
    }
}
