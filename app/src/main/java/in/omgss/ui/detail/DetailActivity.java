package in.omgss.ui.detail;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.OnClick;
import in.omgss.R;
import in.omgss.base.BaseActivity;
import in.omgss.data.api.ApiConstants;
import in.omgss.pojo.responses.viewproduct.ViewproductResponse;
import okhttp3.RequestBody;

public class DetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_toolbar_left)
    ImageView ivToolbarLeft;
    @BindView(R.id.iv_toolbar_right)
    ImageView ivToolbarRight;
    @BindView(R.id.rlToolbar)
    RelativeLayout rlToolbar;
    @BindView(R.id.iv_heart)
    ImageView ivHeart;
    @BindView(R.id.tvdiscountprice)
    TextView tvdiscountprice;
    @BindView(R.id.tv_original)
    TextView tvOriginal;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.tvDiscount)
    TextView tvDiscount;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.image)
    SimpleDraweeView image;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.ll_main)
    LinearLayout llMain;

    private ProductDetailViewModel mCategoriesViewmodel;

    private String productId;
    private int position;
    private int isAddedToCart;
    private int isAddedToWishlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("Service Detail");

        mCategoriesViewmodel = new ViewModelProvider(this).get(ProductDetailViewModel.class);
        mCategoriesViewmodel.setGenericObservers(getErrorResponseObserver(), getFailureResponseObserver());

        productId = getIntent().getStringExtra("id");
        position = getIntent().getIntExtra("position", -1);
        isAddedToCart = getIntent().getIntExtra("isAddedToCart", 0);
        isAddedToWishlist = getIntent().getIntExtra("isAddedToWishlist", 0);

    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_detail;
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mCategoriesViewmodel.getDetailsLiveData().observe(this, new Observer<ViewproductResponse>() {
            @Override
            public void onChanged(ViewproductResponse responseBody) {
                hideProgressDialog();
                if (responseBody != null) {
                    if (responseBody.getStatus().equalsIgnoreCase(ApiConstants.STATUS_SUCCESS)) {

                        llMain.setVisibility(View.VISIBLE);

                        tvOriginal.setText(getString(R.string.currency));
                        tvOriginal.append(String.valueOf(responseBody.get1().getActualprice()));
                        tvOriginal.setPaintFlags(tvOriginal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


                        tvdiscountprice.setText(getString(R.string.currency));
                        tvdiscountprice.append(String.valueOf(responseBody.get1().getSaleprice()));

                        if (responseBody.get1().getDiscount() != null && !responseBody.get1().getDiscount().isEmpty()) {
                            tvDiscount.setText(responseBody.get1().getDiscount());
                            tvDiscount.append(" discount");
                            tvDiscount.setVisibility(View.VISIBLE);

                        } else {
                            tvDiscount.setVisibility(View.GONE);
                        }

                        name.setText(responseBody.get1().getName());

                        description.setText(Html.fromHtml(responseBody.get1().getDescription()));

                        image.setImageURI(responseBody.get1().getImage());

//                        isAddedToWishlist = responseBody.get1().getIsAddedToWishlist();
                        if (isAddedToWishlist == 1) {
                            ivHeart.setImageResource(R.drawable.ic_heart_filled);
                        } else {
                            ivHeart.setImageResource(R.drawable.ic_heart_outline);
                        }

//                        isAddedToCart = responseBody.get1().getIsAddedToCart();
                        if (isAddedToCart == 1) {
                            tvAdd.setText(getString(R.string.remove));
                            tvAdd.setBackgroundResource(R.drawable.drawable_grey_btn_bg);
                        } else {
                            tvAdd.setText(getString(R.string.add));
                            tvAdd.setBackgroundResource(R.drawable.drawable_green_btn_bg_rounded);
                        }

                    }
                }
            }
        });


        getServiceDetails();

    }


    private void getServiceDetails() {
        if (isNetworkAvailable()) {
            showProgressDialog();

            RequestBody body = getMultipartRequestBuilder()
                    .addFormDataPart("userid", getUserId())
                    .addFormDataPart("productid", productId)
                    .build();

            mCategoriesViewmodel.getDetails(body);

        } else {
            showNoNetworkError();
        }
    }


    @OnClick({R.id.iv_toolbar_left, R.id.iv_heart, R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_left:
                onBackPressed();
                break;

            case R.id.iv_heart:
                if (isAddedToWishlist == 1) {
                    removeFromWishlist("", productId, true);

                    isAddedToWishlist = 0;
                    ivHeart.setImageResource(R.drawable.ic_heart_outline);

                } else {
                    addToWishList(productId, true);

                    isAddedToWishlist = 1;
                    ivHeart.setImageResource(R.drawable.ic_heart_filled);
                }
                break;

            case R.id.tv_add:
                if (isAddedToCart == 1) {
                    removeFromCart("", productId, true);

                    isAddedToCart = 0;
                    tvAdd.setText(getString(R.string.add));
                    tvAdd.setBackgroundResource(R.drawable.drawable_green_btn_bg_rounded);

                } else {
                    addToCart(productId, 1, true);

                    isAddedToCart = 1;
                    tvAdd.setText(getString(R.string.remove));
                    tvAdd.setBackgroundResource(R.drawable.drawable_grey_btn_bg);

                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (position != -1) {
            Intent intent = new Intent();
            intent.putExtra("position", position);
            intent.putExtra("isAddedToCart", isAddedToCart);
            intent.putExtra("isAddedToWishlist", isAddedToWishlist);
            setResult(RESULT_OK, intent);
        }
        super.onBackPressed();
    }

}