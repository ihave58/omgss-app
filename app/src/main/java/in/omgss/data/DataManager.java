package in.omgss.data;

import android.content.Context;

import in.omgss.constants.PreferenceConstants;
import in.omgss.data.api.ApiManager;
import in.omgss.data.preferences.PreferenceManager;
import in.omgss.pojo.responses.CommonApiResponse;
import in.omgss.pojo.responses.HomeSliderResponse;
import in.omgss.pojo.responses.LoginResponse;
import in.omgss.pojo.responses.SignUpResponse;
import in.omgss.pojo.responses.addresslist.AddressListResponse;
import in.omgss.pojo.responses.cartcountresponse.BatchCountResponse;
import in.omgss.pojo.responses.cartresponse.CartResponse;
import in.omgss.pojo.responses.categories.CategoriesResponse;
import in.omgss.pojo.responses.createrazorpayorder.CreateRazorPayOrder;
import in.omgss.pojo.responses.mydevices.MyDevicesResponse;
import in.omgss.pojo.responses.myorders.OrdersListResponse;
import in.omgss.pojo.responses.notifications.NotificationsResponse;
import in.omgss.pojo.responses.offers.OffersResponse;
import in.omgss.pojo.responses.products.ProductsResponse;
import in.omgss.pojo.responses.subcategory.SubcategoryResponse;
import in.omgss.pojo.responses.validatecoupon.ValidateCouponResponse;
import in.omgss.pojo.responses.viewproduct.ViewproductResponse;
import in.omgss.pojo.responses.wishlist.WishlistResponse;
import okhttp3.RequestBody;
import retrofit2.Call;

public class DataManager {

    private static DataManager instance;
    private ApiManager apiManager;
    private PreferenceManager preferenceManager;


    private DataManager(Context context) {
        preferenceManager = PreferenceManager.init(context);
        apiManager = ApiManager.init();
    }


    /**
     * Method used to create an instance of {@link DataManager}
     *
     * @return instance if it is null
     */
    public synchronized static DataManager init(Context context) {
        if (instance == null) {
            instance = new DataManager(context);
        }
        return instance;
    }


    /**
     * Returns the single instance of {@link DataManager} if
     * {@link #init(Context)} is called first
     *
     * @return instance
     */
    public static DataManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Call init() before init()");
        }
        return instance;
    }

    public void saveStringInPreferences(String key, String value) {
        preferenceManager.putString(key, value);
    }

    public void saveIntInPreferences(String key, int value) {
        preferenceManager.putInt(key, value);
    }

    public void saveBooleaInPreferences(String key, boolean value) {
        preferenceManager.putBoolean(key, value);
    }


    public String getStringFromPreference(String key) {
        return preferenceManager.getString(key);
    }

    public int getIntFromPreference(String key) {
        return preferenceManager.getInt(key);
    }

    public boolean getBooleanFromPreference(String key) {
        return preferenceManager.getBoolean(key);
    }

    public boolean isLoggedIn() {
        return getBooleanFromPreference(PreferenceConstants.IS_LOGGED_IN);
    }

    public String getUserId() {
        return getStringFromPreference(PreferenceConstants.USER_ID);
    }

    public String getDeviceToken() {
        return getStringFromPreference(PreferenceConstants.DEVICE_ACCESS_TOKEN);
    }

    public void clearPreferences() {
        preferenceManager.clearAllPrefs();
    }

    /* ----- API Call methods ----- */

    public Call<LoginResponse> login(RequestBody request) {
        return apiManager.login(request);
    }

    public Call<SignUpResponse> register(RequestBody request) {
        return apiManager.register(request);
    }


    public Call<HomeSliderResponse> homeslider(RequestBody requestBody) {
        return apiManager.homeslider(requestBody);
    }

    public Call<CategoriesResponse> categories(RequestBody requestBody) {
        return apiManager.categories(requestBody);
    }

    public Call<OffersResponse> offers(RequestBody requestBody) {
        return apiManager.offers(requestBody);
    }

    public Call<CommonApiResponse> complain(RequestBody requestBody) {
        return apiManager.complain(requestBody);
    }

    public Call<CommonApiResponse> support(RequestBody requestBody) {
        return apiManager.support(requestBody);
    }

    public Call<CommonApiResponse> carrers(RequestBody requestBody) {
        return apiManager.carrers(requestBody);
    }

    public Call<CommonApiResponse> contactus(RequestBody requestBody) {
        return apiManager.contactus(requestBody);
    }

    public Call<CommonApiResponse> hireus(RequestBody requestBody) {
        return apiManager.hireus(requestBody);
    }

    public Call<SubcategoryResponse> subCategories(RequestBody requestBody) {
        return apiManager.subCategories(requestBody);
    }

    public Call<ProductsResponse> getproducts(RequestBody requestBody) {
        return apiManager.getproducts(requestBody);
    }

    public Call<LoginResponse> profile(RequestBody requestBody) {
        return apiManager.profile(requestBody);
    }

    public Call<CommonApiResponse> updateProfile(RequestBody requestBody) {
        return apiManager.updateProfile(requestBody);
    }

    public Call<MyDevicesResponse> getMyDevices(RequestBody requestBody) {
        return apiManager.getMyDevices(requestBody);
    }

    public Call<CommonApiResponse> createDeviceComplaint(RequestBody requestBody) {
        return apiManager.createDeviceComplaint(requestBody);
    }

    public Call<AddressListResponse> getAddressList(RequestBody requestBody) {
        return apiManager.getAddressList(requestBody);
    }

    public Call<CommonApiResponse> addAddress(RequestBody requestBody) {
        return apiManager.addAddress(requestBody);
    }

    public Call<CommonApiResponse> editAddress(RequestBody requestBody) {
        return apiManager.editAddress(requestBody);
    }

    public Call<CommonApiResponse> changePassword(RequestBody requestBody) {
        return apiManager.changePassword(requestBody);
    }


    public Call<OrdersListResponse> orderlist(RequestBody requestBody) {
        return apiManager.orderlist(requestBody);
    }

    public Call<ViewproductResponse> viewproduct(RequestBody requestBody) {
        return apiManager.viewproduct(requestBody);
    }

    public Call<CommonApiResponse> addtocart(RequestBody requestBody) {
        return apiManager.addtocart(requestBody);
    }

    public Call<CommonApiResponse> addToWishlist(RequestBody requestBody) {
        return apiManager.addToWishlist(requestBody);
    }

    public Call<CommonApiResponse> removeFromCart(RequestBody requestBody) {
        return apiManager.removeFromCart(requestBody);
    }

    public Call<CommonApiResponse> removeFromWishlist(RequestBody requestBody) {
        return apiManager.removeFromWishlist(requestBody);
    }

    public Call<CartResponse> getCart(RequestBody requestBody) {
        return apiManager.getCart(requestBody);
    }

    public Call<WishlistResponse> wishlist(RequestBody requestBody) {
        return apiManager.wishlist(requestBody);
    }

    public Call<NotificationsResponse> notifications(RequestBody requestBody) {
        return apiManager.notifications(requestBody);
    }

    public Call<CommonApiResponse> deleteAddress(RequestBody requestBody) {
        return apiManager.deleteAddress(requestBody);
    }

    public Call<ValidateCouponResponse> validateCoupon(RequestBody requestBody) {
        return apiManager.validateCoupon(requestBody);
    }

    public Call<CreateRazorPayOrder> createRazorPayOrder(RequestBody requestBody) {
        return apiManager.createRazorPayOrder(requestBody);
    }

    public Call<CommonApiResponse> createOrder(RequestBody requestBody) {
        return apiManager.createOrder(requestBody);
    }

    public Call<CommonApiResponse> cancelOrder(RequestBody requestBody) {
        return apiManager.cancelOrder(requestBody);
    }


    public Call<CommonApiResponse> clearNotificationCount(RequestBody requestBody) {
        return apiManager.clearNotificationCount(requestBody);
    }

    public Call<BatchCountResponse> notificationCount(RequestBody requestBody) {
        return apiManager.notificationCount(requestBody);
    }

    public Call<BatchCountResponse> cartCount(RequestBody requestBody) {
        return apiManager.cartCount(requestBody);
    }

    public Call<BatchCountResponse> wishlistCount(RequestBody requestBody) {
        return apiManager.wishlistCount(requestBody);
    }
}
