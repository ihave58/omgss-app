package in.omgss.ui.wishlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import in.omgss.R;
import in.omgss.adapters.WishlistItemAdapter;
import in.omgss.base.BaseActivity;
import in.omgss.data.api.ApiConstants;
import in.omgss.interfaces.ProductItemClickListener;
import in.omgss.pojo.responses.wishlist.Orderdetail;
import in.omgss.pojo.responses.wishlist.WishlistResponse;
import in.omgss.ui.detail.DetailActivity;
import okhttp3.MultipartBody;


public class WishlistActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_wishlist)
    RecyclerView rvWishlist;
    @BindView(R.id.atv_no_data_found)
    AppCompatTextView atvNoDataFound;

    private WishListViewModel mViewModel;

    private ArrayList<Orderdetail> itemsList;
    private WishlistItemAdapter mWishListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("Wishlist");

        mViewModel = new ViewModelProvider(this).get(WishListViewModel.class);
        mViewModel.setGenericObservers(getErrorResponseObserver(), getFailureResponseObserver());

        setObservers();
        setUpAdapter();

        getWishlist();
    }


    @Override
    protected int getResourceId() {
        return R.layout.activity_wishlist;
    }


    private void setUpAdapter() {
        itemsList = new ArrayList<>();

        mWishListAdapter = new WishlistItemAdapter(itemsList, 0, new ProductItemClickListener() {
            @Override
            public void onItemClicked(int sectionPosition, int itemPosition) {
                Intent intent = new Intent(WishlistActivity.this, DetailActivity.class);
                intent.putExtra("id", itemsList.get(itemPosition).getPrdid());
                intent.putExtra("position", itemPosition);
                intent.putExtra("isAddedToCart", itemsList.get(itemPosition).getIsAddedToCart());
                intent.putExtra("isAddedToWishlist", 1);
                startActivityForResult(intent, 21);
            }

            @Override
            public void onAddToWishListClicked(int sectionPosition, int itemPosition) {
                removeFromWishlist(itemsList.get(itemPosition).getId(), itemsList.get(itemPosition).getPrdid(), true);

                itemsList.remove(itemPosition);
                mWishListAdapter.notifyItemRemoved(itemPosition);

                atvNoDataFound.setVisibility(itemsList.size() > 0 ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onAddToCartClicked(int sectionPosition, int itemPosition) {
                if (itemsList.get(itemPosition).getIsAddedToCart() == 0) {
                    addToCart(itemsList.get(itemPosition).getPrdid(), 1, true);

                } else {
                    removeFromCart("", itemsList.get(itemPosition).getPrdid(), true);
                }
            }
        });

        rvWishlist.setLayoutManager(new GridLayoutManager(this, 2));
        rvWishlist.setAdapter(mWishListAdapter);
    }


    private void setObservers() {
        mViewModel.getWishlistLiveData().observe(this, new Observer<WishlistResponse>() {
            @Override
            public void onChanged(WishlistResponse responseBody) {
                hideProgressDialog();
                if (responseBody != null) {
                    if (responseBody.getStatus().equalsIgnoreCase(ApiConstants.STATUS_SUCCESS)) {
                        itemsList.clear();

                        if (responseBody.getOrderdetails() != null)
                            itemsList.addAll(responseBody.getOrderdetails());

                        mWishListAdapter.notifyDataSetChanged();

                    }

                    atvNoDataFound.setVisibility(itemsList.size() > 0 ? View.GONE : View.VISIBLE);

                }
            }
        });
    }

    private void getWishlist() {
        if (isNetworkAvailable()) {
            showProgressDialog();

            MultipartBody body = getMultipartRequestBuilder()
                    .addFormDataPart("userid", mViewModel.getUserId())
                    .build();

            mViewModel.getWishlist(body);

        } else {
            showNoNetworkError();
        }
    }


    @OnClick(R.id.iv_toolbar_left)
    public void onViewClicked() {
        onBackPressed();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 21:
                if (resultCode == RESULT_OK && data != null) {

                    int position = data.getIntExtra("position", -1);

                    if (position >= 0 && position < itemsList.size()) {

                        itemsList.get(position).setIsAddedToCart(data.getIntExtra("isAddedToCart", 0));

                        if (data.getIntExtra("isAddedToWishlist", 0) == 0) {
                            itemsList.remove(position);
                        }

                        mWishListAdapter.notifyDataSetChanged();

                        atvNoDataFound.setVisibility(itemsList.size() > 0 ? View.GONE : View.VISIBLE);

                    }

                }
                break;
        }
    }

}