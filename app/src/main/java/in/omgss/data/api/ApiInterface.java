package in.omgss.data.api;


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
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("login.php")
    Call<LoginResponse> login(@Body RequestBody registerRequest);

    @POST("register.php")
    Call<SignUpResponse> register(@Body RequestBody registerRequest);

    @POST("homeslider.php")
    Call<HomeSliderResponse> homeslider(@Body RequestBody registerRequest);

    @POST("categories.php")
    Call<CategoriesResponse> categories(@Body RequestBody registerRequest);

    @POST("offers.php")
    Call<OffersResponse> offers(@Body RequestBody registerRequest);

    @POST("complain.php")
    Call<CommonApiResponse> complain(@Body RequestBody registerRequest);

    @POST("support.php")
    Call<CommonApiResponse> supoort(@Body RequestBody registerRequest);

    @POST("careers.php")
    Call<CommonApiResponse> careers(@Body RequestBody registerRequest);

    @POST("contactus.php")
    Call<CommonApiResponse> contactus(@Body RequestBody registerRequest);

    @POST("hireus.php")
    Call<CommonApiResponse> hireus(@Body RequestBody registerRequest);

    @POST("subcategories.php")
    Call<SubcategoryResponse> subcategories(@Body RequestBody registerRequest);

    @POST("products.php")
    Call<ProductsResponse> getproducts(@Body RequestBody registerRequest);

    @POST("profile.php")
    Call<LoginResponse> profile(@Body RequestBody registerRequest);

    @POST("updateprofile.php")
    Call<CommonApiResponse> updateProfile(@Body RequestBody registerRequest);

    @POST("mydevices.php")
    Call<MyDevicesResponse> getMyDevices(@Body RequestBody registerRequest);

    @POST("createcomplaint.php")
    Call<CommonApiResponse> createDeviceComplaint(@Body RequestBody registerRequest);

    @POST("addresslist.php")
    Call<AddressListResponse> getAddressList(@Body RequestBody registerRequest);

    @POST("addaddress.php")
    Call<CommonApiResponse> addAddress(@Body RequestBody registerRequest);

    @POST("editaddress.php")
    Call<CommonApiResponse> editAddress(@Body RequestBody registerRequest);

    @POST("changepassword.php")
    Call<CommonApiResponse> changePassword(@Body RequestBody registerRequest);

    @POST("orderlist.php")
    Call<OrdersListResponse> orderlist(@Body RequestBody registerRequest);

    @POST("viewproduct.php")
    Call<ViewproductResponse> viewproduct(@Body RequestBody registerRequest);

    @POST("addtocart.php")
    Call<CommonApiResponse> addtocart(@Body RequestBody registerRequest);

    @POST("addtowishlist.php")
    Call<CommonApiResponse> addToWishlist(@Body RequestBody registerRequest);

    @POST("deletefromwishlist.php")
    Call<CommonApiResponse> removeFromWishlist(@Body RequestBody registerRequest);

    @POST("cart.php")
    Call<CartResponse> getCart(@Body RequestBody registerRequest);

    @POST("deletefromcart.php")
    Call<CommonApiResponse> removeFromCart(@Body RequestBody registerRequest);

    @POST("wishlist.php")
    Call<WishlistResponse> wishlist(@Body RequestBody registerRequest);

    @POST("notifications.php")
    Call<NotificationsResponse> notifications(@Body RequestBody registerRequest);

    @POST("deleteaddress.php")
    Call<CommonApiResponse> deleteAddress(@Body RequestBody registerRequest);

    @POST("validatecoupon.php")
    Call<ValidateCouponResponse> validateCoupon(@Body RequestBody registerRequest);

    @POST("createrazorpayorderid.php")
    Call<CreateRazorPayOrder> createRazorPayOrder(@Body RequestBody registerRequest);

    @POST("createorder.php")
    Call<CommonApiResponse> createOrder(@Body RequestBody registerRequest);

    @POST("cancelorder.php")
    Call<CommonApiResponse> cancelOrder(@Body RequestBody registerRequest);

    @POST("clearcountnotification.php")
    Call<CommonApiResponse> clearNotificationCount(@Body RequestBody registerRequest);

    @POST("notificationcount.php")
    Call<BatchCountResponse> notificationCount(@Body RequestBody registerRequest);

    @POST("cartcount.php")
    Call<BatchCountResponse> cartCount(@Body RequestBody registerRequest);

    @POST("wishlistcount.php")
    Call<BatchCountResponse> wishlistCount(@Body RequestBody registerRequest);
}
