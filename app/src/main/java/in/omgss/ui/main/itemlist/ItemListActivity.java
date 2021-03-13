package in.omgss.ui.main.itemlist;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.omgss.R;
import in.omgss.adapters.ListItemsAdapter;
import in.omgss.base.BaseActivity;
import in.omgss.data.api.ApiConstants;
import in.omgss.interfaces.ProductItemClickListener;
import in.omgss.pojo.responses.products.ProductsResponse;
import in.omgss.pojo.responses.products.Response;
import in.omgss.ui.detail.DetailActivity;
import okhttp3.RequestBody;

public class ItemListActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rlSearch)
    RelativeLayout rlSearch;
    @BindView(R.id.rlToolbar)
    RelativeLayout rlToolbar;
    @BindView(R.id.rv_items)
    RecyclerView rvItems;
    @BindView(R.id.atv_no_data_found)
    AppCompatTextView atvNoDataFound;
    @BindView(R.id.tv_item_count)
    TextView tvItemCount;

    private ItemListViewModel mViewModel;

    private ArrayList<Response> productsList;
    private ArrayList<Response> mList;
    private ListItemsAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        tvTitle.setText("Services");

        mViewModel = new ViewModelProvider(this).get(ItemListViewModel.class);
        mViewModel.setGenericObservers(getErrorResponseObserver(), getFailureResponseObserver());

        setObservers();
        setUpAdapter();

        getProducts();

    }


    @Override
    protected int getResourceId() {
        return R.layout.activity_item_list;
    }


    private void setObservers() {
        mViewModel.getmResendOtpLiveData().observe(this, new Observer<ProductsResponse>() {
            @Override
            public void onChanged(ProductsResponse responseBody) {
                hideProgressDialog();
                if (responseBody != null) {
                    if (responseBody.getStatus().equalsIgnoreCase(ApiConstants.STATUS_SUCCESS)) {

                        productsList.clear();
                        mList.clear();

                        if (responseBody.getResponse() != null) {
                            productsList.addAll(responseBody.getResponse());
                        }

                        mAdapter.notifyDataSetChanged();

                        mList.addAll(productsList);

                    }

                    updateItemsCount();

                }
            }
        });


        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence input, int start, int before, int count) {
                if (input.toString().trim().isEmpty()) {
                    productsList.clear();
                    productsList.addAll(mList);
                    mAdapter.notifyDataSetChanged();

                } else {
                    productsList.clear();
                    for (Response item : mList) {
                        if (item.getName().toLowerCase().contains(input.toString().toLowerCase())) {
                            productsList.add(item);
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }

                updateItemsCount();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void setUpAdapter() {
        productsList = new ArrayList<>();
        mList = new ArrayList<>();

        mAdapter = new ListItemsAdapter(productsList, 0, new ProductItemClickListener() {
            @Override
            public void onItemClicked(int sectionPosition, int itemPosition) {
                Intent intent = new Intent(ItemListActivity.this, DetailActivity.class);
                intent.putExtra("id", productsList.get(itemPosition).getId());
                intent.putExtra("position", itemPosition);
                intent.putExtra("isAddedToCart", productsList.get(itemPosition).getIsAddedToCart());
                intent.putExtra("isAddedToWishlist", productsList.get(itemPosition).getIsAddedToWishlist());
                startActivityForResult(intent, 21);
            }

            @Override
            public void onAddToWishListClicked(int sectionPosition, int itemPosition) {
                if (productsList.get(itemPosition).getIsAddedToWishlist() == 0) {
                    addToWishList(productsList.get(itemPosition).getId(), true);

                } else {
                    removeFromWishlist("", productsList.get(itemPosition).getId(), true);
                }
            }

            @Override
            public void onAddToCartClicked(int sectionPosition, int itemPosition) {
                if (productsList.get(itemPosition).getIsAddedToCart() == 0) {
                    addToCart(productsList.get(itemPosition).getId(), 1, true);

                } else {
                    removeFromCart("", productsList.get(itemPosition).getId(), true);
                }
            }
        });

        rvItems.setLayoutManager(new GridLayoutManager(ItemListActivity.this, 2));
        rvItems.setAdapter(mAdapter);

    }


    /**
     * method to get products list
     */
    private void getProducts() {
        if (isNetworkAvailable()) {
            showProgressDialog();

            RequestBody body = getMultipartRequestBuilder()
                    .addFormDataPart("catid", getIntent().getStringExtra("catid"))
                    .addFormDataPart("subcatid", getIntent().getStringExtra("subcatid"))
                    .build();

            mViewModel.getProductsList(body);

        } else {
            showNoNetworkError();
        }
    }


    private void updateItemsCount() {
        tvItemCount.setText(String.valueOf(productsList.size()));
        tvItemCount.append(" ");
        tvItemCount.append(getString(R.string.products_found));

        atvNoDataFound.setVisibility(productsList.size() > 0 ? View.GONE : View.VISIBLE);

    }


    @OnClick({R.id.iv_toolbar_left, R.id.iv_toolbar_right, R.id.iv_cancel_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_left:
                onBackPressed();
                break;

            case R.id.iv_toolbar_right:
                rlSearch.setVisibility(View.VISIBLE);
                break;

            case R.id.iv_cancel_icon:
                cancelSearch();
                break;
        }
    }

    private void cancelSearch() {
        etSearch.setText("");
        rlSearch.setVisibility(View.GONE);
    }


    @Override
    public void onBackPressed() {
        if (rlSearch.isShown()) {
            cancelSearch();

        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 21:
                if (resultCode == RESULT_OK && data != null) {

                    int position = data.getIntExtra("position", -1);

                    if (position >= 0 && position < productsList.size()) {

                        productsList.get(position).setIsAddedToCart(data.getIntExtra("isAddedToCart", 0));
                        productsList.get(position).setIsAddedToWishlist(data.getIntExtra("isAddedToWishlist", 0));

                        mAdapter.notifyDataSetChanged();

                    }

                }
                break;
        }
    }

}