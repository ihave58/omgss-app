package in.omgss.data.api;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import in.omgss.BuildConfig;
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
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Navjot Singh
 * on 27/3/18.
 */

public class ApiManager {

    private static ApiManager instance;
    private ApiInterface apiClient;
    private OkHttpClient httpClient;

    private ApiManager() {
        httpClient = getHttpClient();
        apiClient = getAuthenticatedRetrofitService();
    }

    /**
     * Method used to create an instance of {@link ApiManager}
     */
    public synchronized static ApiManager init() {
        if (instance == null) {
            instance = new ApiManager();
        }
        return instance;
    }


    /**
     * Returns the single instance of {@link ApiManager} if
     * {@link #init()} is called first
     *
     * @return instance
     */
    public static ApiManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Call init() before init()");
        }
        return instance;
    }


    private ApiInterface getAuthenticatedRetrofitService() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .baseUrl(BuildConfig.API_BASE_URL)
                .build();

        return retrofit.create(ApiInterface.class);
    }

    /**
     * Method to create {@link OkHttpClient} builder by adding required headers in the {@link Request}
     *
     * @return OkHttpClient object
     */
    private OkHttpClient getHttpClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(30, TimeUnit.SECONDS);

        // adding authenticator for laravel
//        httpClientBuilder.authenticator(new CustomAuthenticator());

        // TODO comment before creating signed apk
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder.addInterceptor(logging);

        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder;
                requestBuilder = original.newBuilder()
//                        .header("appid", "androidomgss9436")
//                        .header("token", "e528d06aa603e7411dd84094d24d7736")
                        .method(original.method(), original.body());
                Request request = requestBuilder.build();
                Response response = chain.proceed(request);
                return response;
            }
        });

        return httpClientBuilder.build();

    }


    /* ----- API Call methods ----- */


    public Call<LoginResponse> login(RequestBody requestBody) {
        return apiClient.login(requestBody);
    }

    public Call<SignUpResponse> register(RequestBody requestBody) {
        return apiClient.register(requestBody);
    }

    public Call<HomeSliderResponse> homeslider(RequestBody requestBody) {
        return apiClient.homeslider(requestBody);
    }

    public Call<CategoriesResponse> categories(RequestBody requestBody) {
        return apiClient.categories(requestBody);
    }

    public Call<OffersResponse> offers(RequestBody requestBody) {
        return apiClient.offers(requestBody);
    }

    public Call<CommonApiResponse> complain(RequestBody requestBody) {
        return apiClient.complain(requestBody);
    }

    public Call<CommonApiResponse> support(RequestBody requestBody) {
        return apiClient.supoort(requestBody);
    }

    public Call<CommonApiResponse> carrers(RequestBody requestBody) {
        return apiClient.careers(requestBody);
    }

    public Call<CommonApiResponse> contactus(RequestBody requestBody) {
        return apiClient.contactus(requestBody);
    }

    public Call<CommonApiResponse> hireus(RequestBody requestBody) {
        return apiClient.hireus(requestBody);
    }

    public Call<SubcategoryResponse> subCategories(RequestBody requestBody) {
        return apiClient.subcategories(requestBody);
    }

    public Call<ProductsResponse> getproducts(RequestBody requestBody) {
        return apiClient.getproducts(requestBody);
    }

    public Call<LoginResponse> profile(RequestBody requestBody) {
        return apiClient.profile(requestBody);
    }

    public Call<CommonApiResponse> updateProfile(RequestBody requestBody) {
        return apiClient.updateProfile(requestBody);
    }

    public Call<MyDevicesResponse> getMyDevices(RequestBody requestBody) {
        return apiClient.getMyDevices(requestBody);
    }

    public Call<CommonApiResponse> createDeviceComplaint(RequestBody requestBody) {
        return apiClient.createDeviceComplaint(requestBody);
    }

    public Call<AddressListResponse> getAddressList(RequestBody requestBody) {
        return apiClient.getAddressList(requestBody);
    }

    public Call<CommonApiResponse> addAddress(RequestBody requestBody) {
        return apiClient.addAddress(requestBody);
    }

    public Call<CommonApiResponse> editAddress(RequestBody requestBody) {
        return apiClient.editAddress(requestBody);
    }

    public Call<CommonApiResponse> changePassword(RequestBody requestBody) {
        return apiClient.changePassword(requestBody);
    }


    public Call<OrdersListResponse> orderlist(RequestBody requestBody) {
        return apiClient.orderlist(requestBody);
    }


    public Call<ViewproductResponse> viewproduct(RequestBody requestBody) {
        return apiClient.viewproduct(requestBody);
    }

    public Call<CommonApiResponse> addtocart(RequestBody requestBody) {
        return apiClient.addtocart(requestBody);
    }

    public Call<CommonApiResponse> addToWishlist(RequestBody requestBody) {
        return apiClient.addToWishlist(requestBody);
    }

    public Call<CommonApiResponse> removeFromWishlist(RequestBody requestBody) {
        return apiClient.removeFromWishlist(requestBody);
    }

    public Call<CommonApiResponse> removeFromCart(RequestBody requestBody) {
        return apiClient.removeFromCart(requestBody);
    }

    public Call<CartResponse> getCart(RequestBody requestBody) {
        return apiClient.getCart(requestBody);
    }

    public Call<WishlistResponse> wishlist(RequestBody requestBody) {
        return apiClient.wishlist(requestBody);
    }

    public Call<NotificationsResponse> notifications(RequestBody requestBody) {
        return apiClient.notifications(requestBody);
    }


    public Call<CommonApiResponse> deleteAddress(RequestBody requestBody) {
        return apiClient.deleteAddress(requestBody);
    }

    public Call<ValidateCouponResponse> validateCoupon(RequestBody requestBody) {
        return apiClient.validateCoupon(requestBody);
    }

    public Call<CreateRazorPayOrder> createRazorPayOrder(RequestBody requestBody) {
        return apiClient.createRazorPayOrder(requestBody);
    }

    public Call<CommonApiResponse> createOrder(RequestBody requestBody) {
        return apiClient.createOrder(requestBody);
    }

    public Call<CommonApiResponse> cancelOrder(RequestBody requestBody) {
        return apiClient.cancelOrder(requestBody);
    }


    public Call<CommonApiResponse> clearNotificationCount(RequestBody requestBody) {
        return apiClient.clearNotificationCount(requestBody);
    }

    public Call<BatchCountResponse> notificationCount(RequestBody requestBody) {
        return apiClient.notificationCount(requestBody);
    }

    public Call<BatchCountResponse> cartCount(RequestBody requestBody) {
        return apiClient.cartCount(requestBody);
    }

    public Call<BatchCountResponse> wishlistCount(RequestBody requestBody) {
        return apiClient.wishlistCount(requestBody);
    }
}
