package in.omgss.ui.main.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.omgss.R;
import in.omgss.adapters.BannerAdapter;
import in.omgss.adapters.ItemsAdapter;
import in.omgss.base.BaseActivity;
import in.omgss.base.BaseFragment;
import in.omgss.constants.AppConstants;
import in.omgss.data.api.ApiConstants;
import in.omgss.interfaces.ProductItemClickListener;
import in.omgss.pojo.responses.HomeSliderResponse;
import in.omgss.pojo.responses.Response;
import in.omgss.pojo.responses.categories.CategoriesResponse;
import in.omgss.ui.detail.DetailActivity;
import me.relex.circleindicator.CircleIndicator;
import okhttp3.RequestBody;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.vp_banners)
    ViewPager vpBanners;
    @BindView(R.id.rv_items)
    RecyclerView rvItems;
    @BindView(R.id.indicator)
    CircleIndicator indicator;

    private HomeViewModel mHomeViewModel;

    private ItemsAdapter mItemsAdapter;

    private Handler handler;
    private Runnable runnable;

    private ArrayList<in.omgss.pojo.responses.categories.Response> itemsList;


    private BroadcastReceiver refreshDataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (isFragmentAdded()) {
                getHomeData();
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        if (getActivity() != null)
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(refreshDataReceiver, new IntentFilter(AppConstants.FILTER_REFRESH_HOME));

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mHomeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        mHomeViewModel.setGenericObservers(getErrorResponseObserver(), getFailureResponseObserver());

        setObservers();
        setUpItemsAdapter();

        getTopBanners();
        getHomeData();

    }


    private void setObservers() {
        mHomeViewModel.getHomeSliderLiveData().observe(getViewLifecycleOwner(), new Observer<HomeSliderResponse>() {
            @Override
            public void onChanged(HomeSliderResponse responseBody) {
                if (responseBody != null && responseBody.getStatus().equalsIgnoreCase(ApiConstants.STATUS_SUCCESS)) {
                    setBannerData(responseBody.getResponse());
                }
            }
        });

        mHomeViewModel.getCategoryLiveData().observe(getViewLifecycleOwner(), new Observer<CategoriesResponse>() {
            @Override
            public void onChanged(CategoriesResponse responseBody) {
                hideProgressDialog();
                if (responseBody != null && responseBody.getStatus().equalsIgnoreCase(ApiConstants.STATUS_SUCCESS)) {
                    itemsList.clear();
                    itemsList.addAll(responseBody.getResponse());
                    mItemsAdapter.notifyDataSetChanged();
                }
            }
        });

    }


    private void setBannerData(ArrayList<Response> data) {
        if (data != null && data.size() > 0) {
            BannerAdapter mBannerAdapter = new BannerAdapter((AppCompatActivity) getActivity(), data);
            vpBanners.setAdapter(mBannerAdapter);
            vpBanners.setOffscreenPageLimit(data.size() - 1);
            indicator.setViewPager(vpBanners);

            if (data.size() > 1) {
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }
        }
    }


    private void setUpItemsAdapter() {
        mItemsAdapter = new ItemsAdapter(itemsList, new ProductItemClickListener() {
            @Override
            public void onItemClicked(int sectionPosition, int itemPosition) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("id", itemsList.get(sectionPosition).getProducts().get(itemPosition).getId());
                intent.putExtra("isAddedToCart", itemsList.get(sectionPosition).getProducts().get(itemPosition).getIsAddedToCart());
                intent.putExtra("isAddedToWishlist", itemsList.get(sectionPosition).getProducts().get(itemPosition).getIsAddedToWishlist());
                startActivity(intent);

            }

            @Override
            public void onAddToWishListClicked(int sectionPosition, int itemPosition) {
                if (getActivity() instanceof BaseActivity) {
                    if (itemsList.get(sectionPosition).getProducts().get(itemPosition).getIsAddedToWishlist() == 0) {
                        ((BaseActivity) getActivity()).addToWishList(itemsList.get(sectionPosition).getProducts().get(itemPosition).getId(), false);

                    } else {
                        ((BaseActivity) getActivity()).removeFromWishlist("", itemsList.get(sectionPosition).getProducts().get(itemPosition).getId(), false);
                    }
                }
            }

            @Override
            public void onAddToCartClicked(int sectionPosition, int itemPosition) {
                if (getActivity() instanceof BaseActivity) {
                    if (itemsList.get(sectionPosition).getProducts().get(itemPosition).getIsAddedToCart() == 0) {
                        ((BaseActivity) getActivity()).addToCart(itemsList.get(sectionPosition).getProducts().get(itemPosition).getId(), 1, false);

                    } else {
                        ((BaseActivity) getActivity()).removeFromCart("", itemsList.get(sectionPosition).getProducts().get(itemPosition).getId(), false);
                    }
                }
            }

        });

        rvItems.setAdapter(mItemsAdapter);

    }


    @Override
    public void initVariables() {
        itemsList = new ArrayList<>();

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (vpBanners.getChildCount() > 1) {
                    int pos = vpBanners.getCurrentItem();
                    if (pos == (vpBanners.getChildCount() - 1))
                        vpBanners.setCurrentItem(0);
                    else
                        vpBanners.setCurrentItem(++pos, true);

                    handler.postDelayed(this, 3000);
                }
            }
        };

    }


    @Override
    public void setListeners() {

    }


    private void getTopBanners() {
        if (isNetworkAvailable()) {
            RequestBody body = getMultipartRequestBuilder()
                    .addFormDataPart("userid", mHomeViewModel.getUserId())
                    .build();

            mHomeViewModel.getHomeSliders(body);

        } else {
            showNoNetworkError();
        }
    }


    private void getHomeData() {
        if (isNetworkAvailable()) {
            showProgressDialog();

            RequestBody body = getMultipartRequestBuilder()
                    .addFormDataPart("userid", mHomeViewModel.getUserId())
                    .build();

            mHomeViewModel.category(body);

        } else {
            showNoNetworkError();
        }
    }


    @Override
    public void onDestroyView() {
        if (getActivity() != null)
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(refreshDataReceiver);

        super.onDestroyView();
    }


}
